package me.play.domain.study.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


fun printJobState(job: Job) {
    println(
        """
            isActive >> ${job.isActive}
            isCancelled >> ${job.isCancelled}
            isCompleted >> ${job.isCompleted}
        """.trimIndent()
    )
}

class CoroutineBuilderJobTests {

    @DisplayName("join을 이용한 순차 처리")
    @Test
    fun joinTest() {
        /**
         * 출력 : join을 통한 block으로 순차적으로 호출되는 걸 확인
         *
         * DefaultDispatcher-worker-1 @coroutine#3 token update start
         * DefaultDispatcher-worker-1 @coroutine#3 token update end
         * DefaultDispatcher-worker-1 @coroutine#4 network call start
         */
        runBlocking {
            val updateTokenJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} token update start")
                delay(1000L)
                println("${Thread.currentThread().name} token update end")
            }

            updateTokenJob.join()

            val networkCallJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} network call start")
            }
        }
    }


    @DisplayName("join을 이용한 순차 처리#2")
    @Test
    fun joinTest2() {
        /**
         * 출력 : join을 통한 block은 해당 코루틴만 일시 중단되고, 이전에 호출한 코루틴은 실행되는걸 확인
         *
         * DefaultDispatcher-worker-1 @coroutine#3 token update start
         * DefaultDispatcher-worker-3 @coroutine#4 independenceJob start
         * DefaultDispatcher-worker-1 @coroutine#3 token update end
         * DefaultDispatcher-worker-1 @coroutine#5 network call start
         */
        runBlocking {
            val updateTokenJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} token update start")
                delay(1000L)
                println("${Thread.currentThread().name} token update end")
            }

            val independenceJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} independenceJob start")
            }

            updateTokenJob.join()

            val networkCallJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} network call start")
            }
        }
    }


    @DisplayName("joinAll 함수")
    @Test
    fun joinAllTest() {
        /**
         * convertImageJob1, convertImageJob2 실행 후 joinAll을 통해 완료 될 때까지 대기
         *
         * 출력:
         * DefaultDispatcher-worker-1 @coroutine#3 convertImageJob1 start
         * DefaultDispatcher-worker-2 @coroutine#4 convertImageJob2 start
         * DefaultDispatcher-worker-1 @coroutine#5 uploadImageJob 이미지 1,2 업로드 start
         */
        runBlocking {
            val convertImageJob1 = launch(Dispatchers.Default) {
                Thread.sleep(1000L)
                println("${Thread.currentThread().name} convertImageJob1 start")
            }

            val convertImageJob2 = launch(Dispatchers.Default) {
                Thread.sleep(1000L)
                println("${Thread.currentThread().name} convertImageJob2 start")
            }

            joinAll(convertImageJob1, convertImageJob2)

            val uploadImageJob = launch(Dispatchers.IO) {
                println("${Thread.currentThread().name} uploadImageJob 이미지 1,2 업로드 start")
            }
        }
    }


    @DisplayName("코루틴 지연 (Lazy Coroutine)")
    @Test
    fun lazyCoroutineTest() {
        /**
         * start = CoroutineStart.LAZY로 지연 실행 시킴
         * job을 받아서 start() 함수를 호출해야만 실행이 됨.
         *
         * 출력:
         * Test worker @coroutine#2 coroutine main
         * DefaultDispatcher-worker-1 @coroutine#3 coroutine start
         */
        runBlocking {
            val lazyJob = launch(Dispatchers.IO, start = CoroutineStart.LAZY) {
                println("${Thread.currentThread().name} coroutine start")
            }
            println("${Thread.currentThread().name} coroutine main")
            delay(2000L)
            lazyJob.start()
        }
    }


    @DisplayName("cancel() 함수를 이용한 취소")
    @Test
    fun cancelTest() {
        /**
         * cancel() 을 이용한 코루틴 중단 테스트
         *
         * 출력 :
         * Test worker @coroutine#3 시간[1006] 반복 횟수[0]
         * Test worker @coroutine#3 시간[2021] 반복 횟수[1]
         * Test worker @coroutine#3 시간[3026] 반복 횟수[2]
         */
        runBlocking {
            val startMs = System.currentTimeMillis()
            val longJob = launch {
                repeat(10) { repeatTime ->
                    delay(1000L)
                    println("${Thread.currentThread().name} 시간[${System.currentTimeMillis() - startMs}] 반복 횟수[${repeatTime}]")
                }
            }
            delay(3500L)
            longJob.cancel()
        }
    }


    @DisplayName("delay, yield를 이용한 취소 확인")
    @Test
    fun delayAndYieldTest() {
        /**
         * while문의 delay() or yield() 함수가 있어야 cancel() 함수를 통해 취소 처리가 가능함.
         * delay, yield 함수는 while 문을 한번 돌 때 마다 스레드 사용이 양보되면서 일시 중단 되는 문제가 있다.
         */
        runBlocking {
            val whileJob = launch {
                while (true) {
                    println("working")
//                    delay(1L)
                    yield()
                }
            }

            delay(10L)
            whileJob.cancel()
        }
    }


    @DisplayName("CoroutineScope.isActive를 이용한 취소 확인 처리")
    @Test
    fun isActiveTest() {
        /**
         * while 인자에 this.isActive를 통한 코루틴 취소 처리 여부 확인하여 취소 처리.
         * 그러나 정상 동작을 하지 않은 것처럼 보이는데 loop가 너무 빨리 돌아서 그러함.
         * 교재랑 다르게 ensureActive()로 처리
         */
        runBlocking {
            val whileJob = launch(Dispatchers.Default) {
                while (true) {
                    ensureActive()
                    println("working")
                }
            }
            delay(1000L)
            whileJob.cancel()
        }
    }
}

