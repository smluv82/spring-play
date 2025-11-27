package me.play.domain.study.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CoroutineDispatcherTests {


    @DisplayName("싱글스레드 코루틴 디스패처")
    @Test
    fun testSingleThread() {
        runBlocking {
            // 싱글 스레드 코루틴 디스패처
            val dispatcher: CoroutineDispatcher = newSingleThreadContext(name = "SingleThread")
            launch(context = dispatcher) {
                println("[${Thread.currentThread().name}] Start")
            }
        }
    }

    @DisplayName("멀티스레드 코루틴 디스패처")
    @Test
    fun testMultiThread() {
        /**
         * 실행 로그
         * [MultiThread-1 @coroutine#3] Start
         * [MultiThread-2 @coroutine#4] Start
         * [MultiThread-2 @coroutine#5] Start
         */
        runBlocking {
            // 스레드 n개 생성, 아래에서는 2개의 스레드에서 코루틴 3개를 실행함.
            val dispatcher: CoroutineDispatcher = newFixedThreadPoolContext(
                nThreads = 2,
                name = "MultiThread"
            )

            launch(context = dispatcher) {
                println("[${Thread.currentThread().name}] Start")
                Thread.sleep(3000)
            }

            launch(context = dispatcher) {
                println("[${Thread.currentThread().name}] Start")
            }

            launch(context = dispatcher) {
                println("[${Thread.currentThread().name}] Start")
            }
        }
    }


    @DisplayName("부모 - 자식 코루틴")
    @Test
    fun testParentChildCoroutine() {
        runBlocking {
            val dispatcher: CoroutineDispatcher = newFixedThreadPoolContext(
                nThreads = 2,
                name = "MultiThread",
            )

            launch(context = dispatcher) {
                println("[${Thread.currentThread().name}] 부모 코루틴")
                launch {
                    println("[${Thread.currentThread().name}] 자식 코루틴-1")
                }

                launch {
                    println("[${Thread.currentThread().name}] 자식 코루틴-2")
                }
            }
        }
    }


    @DisplayName("CoroutineDispatcher.IO ")
    @Test
    fun testDispatcherIO() {
        // 출력 주석
        // [DefaultDispatcher-worker-1 @coroutine#3] Hello CoroutineDispatcher.IO
        runBlocking {
            launch(Dispatchers.IO) {
                println("[${Thread.currentThread().name}] Hello CoroutineDispatcher.IO")
            }
        }
    }


    @DisplayName("CoroutineDispatcher.Default 이고, limitedParalleism 함수를 사용")
    @Test
    fun testDispatcherDefault() {
        runBlocking {
            // 최대 스레드 개수 사용을 3대로 제한 한다는 설정
            launch(Dispatchers.Default.limitedParallelism(3)) {
                repeat(100) {
                    launch {
                        println("[${Thread.currentThread().name}] Hello CoroutineDispatcher.Default")
                    }
                }
            }
        }
    }
}