package me.play.domain.study.coroutine

import kotlinx.coroutines.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AsyncDeferredTests {

    @DisplayName("await test")
    @Test
    fun awaitTest() {
        /**
         * 출력 :
         *
         * await start [1764135599692, threadName=Test worker @coroutine#2]
         * await end [1764135601699, threadName=Test worker @coroutine#2]
         */
        runBlocking {
            val networkDeferred: Deferred<String> = async(Dispatchers.IO) {
                delay(2000L)
                return@async "Dummy Response"
            }

            // await() 를 통한 결과값 반환 때까지 runBlocking 코루틴 일시 중단함.
            println("await start [${System.currentTimeMillis()}, threadName=${Thread.currentThread().name}]")
            val result = networkDeferred.await()
            println("await end [${System.currentTimeMillis()}, threadName=${Thread.currentThread().name}]")
            assertThat(result).isEqualTo("Dummy Response")
        }
    }


    @DisplayName("Deferred는 Job의 자식 인터페이스이다.")
    @Test
    fun deferredTest() {
        runBlocking {
            val deferred = async(Dispatchers.IO) {
                delay(2000L)
                return@async "Dummy Response"
            }

            // Deferred 객체는 job 인터페이스의 자식이라 동일하게 함수 및 프로퍼티를 사용 가능하다.
            deferred.join()
            printJobState(deferred)
        }
    }


    @DisplayName("복수의 코루틴 결과 수신")
    @Test
    fun multipleCoroutinesTest() {
        /**
         * await를 partDeferred1 바로 하단으로 호출하면 순차적으로 호출되어 메모리 낭비 발생함.
         *
         * 출력 :
         * [DefaultDispatcher-worker-1 @coroutine#4] partDeferred2 is done
         * [DefaultDispatcher-worker-1 @coroutine#3] partDeferred1 is done
         * [Test worker @coroutine#2][2005] result : [DefaultDispatcher-worker-1 @coroutine#3] partDeferred1 is done <> [DefaultDispatcher-worker-1 @coroutine#4] partDeferred2 is done
         *
         * await()를 partDeferred1 바로 하단에 호출 한 경우 결과
         * --
         * [DefaultDispatcher-worker-1 @coroutine#3] partDeferred1 is done
         * [DefaultDispatcher-worker-1 @coroutine#4] partDeferred2 is done
         * [Test worker @coroutine#2][3022] result : [DefaultDispatcher-worker-1 @coroutine#3] partDeferred1 is done <> [DefaultDispatcher-worker-1 @coroutine#4] partDeferred2 is done
         */
        runBlocking {
            val startTime = System.currentTimeMillis()

            val partDeferred1 = async(Dispatchers.IO) {
                delay(2000L)
                val message = "[${Thread.currentThread().name}] partDeferred1 is done"
                println(message)
                return@async message
            }

            val partDeferred2 = async(Dispatchers.IO) {
                delay(1000L)
                val message = "[${Thread.currentThread().name}] partDeferred2 is done"
                println(message)
                return@async message
            }

            val partResult1 = partDeferred1.await()
            val partResult2 = partDeferred2.await()

            println("[${Thread.currentThread().name}][${System.currentTimeMillis() - startTime}] result : ${partResult1} <> ${partResult2}")
        }
    }


    @DisplayName("Collection에서 awaitAll함수 처리")
    @Test
    fun collectionAwaitAllTest() {
        /**
         * 출력 :
         * [Test worker @coroutine#2][2009] result : [steven, derek, meriel, lina, peter]
         */
        runBlocking {
            val startTime = System.currentTimeMillis()

            val partDeferred1 = async(Dispatchers.IO) {
                delay(2000L)
                arrayOf("steven", "derek")
            }

            val partDeferred2 = async(Dispatchers.IO) {
                delay(1000L)
                arrayOf("meriel", "lina", "peter")
            }

            // Collection 함수를 이용한 확장함수 awaitAll 사용
            val results = listOf(partDeferred1, partDeferred2).awaitAll()

            println(
                "[${Thread.currentThread().name}][${System.currentTimeMillis() - startTime}] result : ${
                    listOf(
                        *results[0],
                        *results[1]
                    )
                }"
            )
        }
    }


    @DisplayName("withContext 테스트")
    @Test
    fun withContextTest() {
        /**
         * withContext 함수는 새로운 코루틴을 만드는 대신 기존의 코루틴(호출 코루틴)에서 CoroutineContext 객체만 바꿔서 실행한다.
         * 아래 예제를 보면 @coroutine#2 로 동일한 걸 확인 가능.
         *
         *
         * 출력 :
         *
         * Test worker @coroutine#2 runBlocking start
         * DefaultDispatcher-worker-1 @coroutine#2 withContext start
         */
        runBlocking {
            println("${Thread.currentThread().name} runBlocking start")

            withContext(Dispatchers.IO) {
                println("${Thread.currentThread().name} withContext start")
            }
        }
    }
}