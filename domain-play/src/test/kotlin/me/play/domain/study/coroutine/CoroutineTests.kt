package me.play.domain.study.coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class CoroutineTests {
    @Test
    fun firstCoroutine() {
        // 코루틴으로 메인쓰레드 블로킹 후 안쪽의 구문을 다 실행 후 메인 이후를 실행함
        // CoroutineName으로 코루틴 이름을 줄 수 있음.
        runBlocking(context = CoroutineName("runBlocking")) {
            println("[${Thread.currentThread().name}] Hello runBlocking")

            // launch는 추가적인 코루틴을 생성함.
            launch(context = CoroutineName("launch1")) {
                println("[${Thread.currentThread().name}] Hello launch#1")
            }

            launch(context = CoroutineName("launch2")) {
                println("[${Thread.currentThread().name}] Hello launch#2")
//                Thread.sleep(10000)
            }
        }

        println("[${Thread.currentThread().name}] Hello main")
    }
}