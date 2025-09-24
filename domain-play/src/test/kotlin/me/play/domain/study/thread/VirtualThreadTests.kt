package me.play.domain.study.thread

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class VirtualThreadTests {


    @Test
    fun `기본 Virtual Thread 실행`() {
        val virtualThread = Thread.ofVirtual().start {
            println("Virtual Thread : ${Thread.currentThread()}")
        }
        virtualThread.join()
        assertThat(!virtualThread.isAlive).isTrue()
    }


    @Test
    fun `여러 Virtual Thread 실행`() {
        val results = mutableListOf<Int>()
        val vThreads = (1..500).map {
            Thread.ofVirtual().start {
                println("number[${it}] <> Virtual Thread : ${Thread.currentThread()}")
                results.add(it)
            }
        }

        vThreads.forEach { it.join() }

        assertThat(results.size).isEqualTo(5)
    }


    @Test
    fun `Blocking IO 작업 처리`() {
        val millis = measureTimeMillis {
            val vThreads = (1..100).map {
                Thread.ofVirtual().start {
                    // mock Blocking (ex : DB call, rest api call)
                    TimeUnit.MILLISECONDS.sleep(200)
                    println("Finished task $it")
                }
            }
            vThreads.forEach(Thread::join)
        }

        println("Total time : $millis ms")
        assertThat(millis < 100 * 200).isTrue()
    }

    @Test
    fun `Platform Thread vs Virtual Thread 대량 생성 비교`() {
        val count = 1000

        val platformThreadTime = measureTimeMillis {
            val pool = Executors.newCachedThreadPool()

            val futures = (1..count).map {
                pool.submit {
                    TimeUnit.MILLISECONDS.sleep(100)
                }
            }
            futures.forEach { it.get() }
            pool.shutdown()
        }


        val virtualThreadTime = measureTimeMillis {
            val vThreads = (1..count).map {
                Thread.ofVirtual().start {
                    TimeUnit.MILLISECONDS.sleep(100)
                }
            }
            vThreads.forEach(Thread::join)
        }

        println("platformThreadTime[${platformThreadTime}]ms <> virtualThreadTime[${virtualThreadTime}]ms")
        assertThat(platformThreadTime > virtualThreadTime).isTrue()
    }

    @Test
    fun `Virtual Thread Executor 사용`() {
        val executor = Executors.newVirtualThreadPerTaskExecutor()
        executor.use { task ->
            val futures = (1..5).map {
                task.submit<Int> {
                    it.also { println("number[${it}] <> vThread[${Thread.currentThread()}]") }
                }
            }
            val results = futures.map { it.get() }
            println("result : ${results}")
            assertThat(listOf(1, 2, 3, 4, 5)).isEqualTo(results)
        }
    }


    /**
     * 밑의 CompletableFuture 방식과 Virtual Thread 방식과 속도 비교해보면 크게 차이가 나지 않는다.
     */
    @Test
    fun `CompletableFuture IO 처리 방식`() {

        val timeMs = measureTimeMillis {
            val executor = Executors.newFixedThreadPool(3)

            val f1 = CompletableFuture.supplyAsync({ queryDb1() }, executor)
            val f2 = CompletableFuture.supplyAsync({ queryDb2() }, executor)
            val f3 = CompletableFuture.supplyAsync({ queryDb3() }, executor)

            val results = CompletableFuture.allOf(f1, f2, f3).thenApply {
                listOf(f1.join(), f2.join(), f3.join())
            }.join()

            println("results : ${results.joinToString(", ")}")
            assertThat(results.size == 3).isTrue()

            executor.shutdown()
        }

        println("CompletableFuture IO 처리 방식 : $timeMs ms")
    }


    @Test
    fun `Virtual Thread IO 처리 방식`() {
        val timeMs = measureTimeMillis {
            val executor = Executors.newVirtualThreadPerTaskExecutor()

            // 제네릭의 타입 지정을 해야지만 결과 Callable<T>의 올바르게 return. 안그러면 return type이 Unit
            val f1 = executor.submit<String> { queryDb1() }
            val f2 = executor.submit<String> { queryDb2() }
            val f3 = executor.submit<String> { queryDb3() }

            val results = listOf(f1.get(), f2.get(), f3.get())

            println("results : ${results.joinToString(", ")}")
            assertThat(results.size == 3).isTrue()

            executor.shutdown()
        }

        println("Virtual Thread IO 처리 방식 : $timeMs ms")
    }

    private fun queryDb1(): String {
        TimeUnit.SECONDS.sleep(1)
        return "Db1"
    }

    private fun queryDb2(): String {
        TimeUnit.SECONDS.sleep(2)
        return "Db2"
    }

    private fun queryDb3(): String {
        TimeUnit.SECONDS.sleep(3)
        return "Db3"
    }
}