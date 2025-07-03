package me.play.domain.study.thread

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * 기본적인 쓰레드 테스트
 * 쓰레드 생성 및 사용은 Thread를 상속받거나, Runnable을 구현하여 할수 있다.
 * start() : 쓰레드 실행
 * join() : 쓰레드가 완료될때까지 wait
 */
class ThreadBasicTest : FunSpec({

    test("Thread 클래스를 상속받아 실행") {
        class StevenThread(private val threadName: String, val results: CopyOnWriteArrayList<String>) : Thread() {
            override fun run() {
                results.add(threadName)
            }
        }

        /**
         * CopyOnWriteArrayList : 읽기 작업에서는 lock을 하지 않고, 쓰기 작업이 있는 경우 배열 전체를 복사해서 새로운 배열 쓰기 작업을 수행.
         * 즉 동시성을 보장한다. 이 말은, 쓰기 작업이 있을 때마다 새로운 배열을 생성하고, 기존 배열을 수정하지 않으므로 동시성을 보장함으로써
         * 다른 쓰레드에서 읽기 작업을 안전하게 수행
         * 단, 매번 쓰기 작업 시 신규 객체를 생성하기 때문에 비용이 많이 든다. 따라서 쓰기 작업은 많이 없는 건에서 유리.
         */
        val results = CopyOnWriteArrayList<String>()

        val thread1 = StevenThread("thread1", results)
        val thread2 = StevenThread("thread2", results)

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        results.size shouldBe 2
        results shouldContainExactly listOf("thread1", "thread2")
    }

    test("Runnable 인터페이스 구현하여 실행") {
        val results = CopyOnWriteArrayList<String>()

        val task1 = Runnable { results.add("task1") }
        val task2 = Runnable { results.add("task2") }

        val thread1 = Thread(task1)
        val thread2 = Thread(task2)

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        results.size shouldBe 2
        results shouldContainExactly listOf("task1", "task2")
    }

    test("여러 쓰레드 동시에 실행") {
        val results = CopyOnWriteArrayList<String>()

        val threads = (1..10).map { i ->
            Thread {
                results.add("Thread[$i]")
                Thread.sleep(Duration.ofSeconds(3))
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        results.size shouldBe 10
        results.forEach { println(it) }
    }
})


/**
 * Synchronized를 이용하여 락을 걸어서, thread safe 한 지 테스트.
 * 첫번째 test는 실패 할 수 있음.
 */
class ThreadSynchronizationTest : FunSpec({

    test("synchroinzed 없이는 race condition(교착상태)가 발생할 수 있다. (테스트 실패 가능성 있음)") {
        val listSize = 1000
        val repeatTimes = 10
        var count = 0

        // List() {} 는 생성자가 아님. 1번째 파라미터 size, 2번째 파라미터는 람다로 init하는 역할로 구성되어 있다.
        val threads = List(listSize) {
            Thread {
                repeat(repeatTimes) {
                    count++
                }
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }

        println("result : ${count}")
        count shouldBeExactly listSize * repeatTimes
    }


    test("synchronized 키워드로 동기화하여 올바른 결과를 얻는 테스트") {
        var count = 0
        val lock = Any()
        val listSize = 1000
        val repeatTimes = 100

        val threads = List(listSize) {
            Thread {
                synchronized(lock) {
                    repeat(repeatTimes) {
                        count++
                    }
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        println("synchronized result : ${count}")
        count shouldBeExactly listSize * repeatTimes
    }


    test("AtomicInteger를 사용하여 동기화 없이 증가 테스트") {
        val atomicInteger = AtomicInteger(0)
        val listSize = 1000
        val repeatTimes = 100

        val threads = List(listSize) {
            Thread {
                repeat(repeatTimes) {
                    atomicInteger.incrementAndGet()
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        println("AtomicInteger result : ${atomicInteger.get()}")
        atomicInteger.get() shouldBe listSize * repeatTimes
    }
})


/**
 * ReentrantLock, CountDownLatch, ExecutorService에 대하여 테스트
 */
class ConcurrentThreadTest : StringSpec({

    /**
     *  ReentrantLock : synchronized와 동일하게 락을 획득하는것인데 명시적으로 락을 획득하고 해제 한다.
     *  같은 쓰레드가 여러번 락이 획득 가능함.
     */
    "ReentrantLock - 동시에 출금해도 잔액이 음수가 되지 않아야 한다" {
        class BankAccount(var balance: Int) {
            private val lock = ReentrantLock()

            fun withdraw(amount: Int): Boolean {
                lock.withLock {
                    return if (balance >= amount) {
                        Thread.sleep(10) // race condition 유도
                        balance -= amount
                        true
                    } else {
                        false
                    }
                }
            }
        }

        val listSize = 100
        val balance = 1000
        val withdrawAmount = 10

        val account = BankAccount(balance)
        val successCount = mutableListOf<Boolean>()

        val threads = List(listSize) {
            Thread {
                val result = account.withdraw(withdrawAmount)
                synchronized(successCount) {
                    successCount.add(result)
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        // 1000 / 10 = 100건만 출금 가능
        successCount.count { it } shouldBeExactly listSize
        account.balance shouldBeExactly 0
    }


    /**
     * CountDownLatch : 특정 개수만큼 .countDown() 호출되어 전부 완료(count = 0) 되기 전까지 .await() 상태로 대기
     * 한번쓰고 재사용은 불가함.
     * 여러 서비스 초기화 대기할때 사용 할 수 있을듯.
     */
    "CountDownLatch 모든 초기화 작업이 완료된다음에 실행" {
        val initLatch = CountDownLatch(3)
        val result = CopyOnWriteArrayList<String>()

        val services = listOf("user", "admin", "seller")
        services.forEach { service ->
            Thread {
                Thread.sleep((1000..5000).random().toLong())    //초기화 지연할 sleep
                result.add("$service-$initLatch")
                println("service : $service-$initLatch")
                initLatch.countDown()
            }.start()
        }

        initLatch.await()
        println("await 끝난 후 : ${result}")
        result.add("System started")

        result shouldHaveSize 4
        result.last().shouldBe("System started")
    }


    /**
     * ExecutorService는 직접 Thread를 생성하여 호출하지 않고, 쓰레드 풀 내에서 재사용함, 즉 효율적인 리소스 관리가 됨.
     * 함수 :
     * submit() : 작업결과를 Future<T>로 확인 가능
     * execute() : 리턴타입이 void여서 Task의 실행 결과는 확인 불가함.
     * invokeAny() : Task를 Collection에 넣어서 처리. 성공한 결과 중 하나를 리턴
     * invokeAll() : Task의 Collection에 넣어서 처리. 성공한 결과 전체를 List<Future<>> 로 리턴
     *
     * 종류 :
     * 1. 직접 ThreadPoolExecutor로 생성
     * 2. Static Factory Method로 생성
     *  - newCacheThreadPool() : 쓰레드를 캐싱하는 쓰레드풀 (60초동안 작업이 없으면 Pool에서 제거). 쓰레드가 폭발적으로 증가 할 수 있다는 단점이 있음.
     *    짧고 빠른 비동기 작업에서 사용해야 함.
     *  - newFixedThreadPool(n) : 고정된 쓰레드 풀을 생성함. CPU 코어 수를 고려하여 생성 할 것
     *  - newSingleThreadPool() : 1개의 쓰레드만 사용. 1개의 쓰레드이기 때문에 순차 실행 보장함.
     *  - newScheduledThreadPool(n) : 일정 시간 후 실행 또는 주기적 실행. 타이머, 주기적 작업에서 사용
     *  - newWorkStealingPool() : Fork/Join 기반 병렬 작업. 자바 8에서 추가 됨.
     */
    "FixedThreadPool 테스트" {
        val executor = Executors.newFixedThreadPool(4)
        (1..10).forEach { i ->
            executor.execute {
                println(
                    "[${
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    }][$i] executed Thread name : ${Thread.currentThread().name}"
                )
                Thread.sleep(1000)
            }
        }

        executor.shutdown()
    }

    "SingleThreadExecutor 테스트" {
        val executor = Executors.newSingleThreadExecutor()

        (1..10).forEach { i ->
            executor.execute {
                println(
                    "[${
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    }][$i] executed Thread name : ${Thread.currentThread().name}"
                )
                Thread.sleep(10)
            }
        }

        executor.shutdown()
    }

    "FixedThreadPool은 제한된 쓰레드로 작업을 처리한다" {
        val executor = Executors.newFixedThreadPool(2)
        val threads = mutableSetOf<String>()

        val futures = (1..4).map {
            executor.submit {
                Thread.sleep(100)
                threads.add(Thread.currentThread().name)
            }
        }

        futures.forEach { it.get() }
        executor.shutdown()

        // 동시에 최대 2개의 쓰레드만 사용됨
        println("Used threads: $threads")
        threads.size shouldBeExactly 2
    }

    "타임아웃 예외 테스트" {
        val executor = Executors.newSingleThreadExecutor()

        val future = executor.submit {
            Thread.sleep(2000)
        }

        shouldThrow<TimeoutException> {
            future.get(500, TimeUnit.MILLISECONDS)
        }

        executor.shutdownNow()
    }
})