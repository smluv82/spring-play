package me.play.domain.study.coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

class CoroutineContextTests {


    @DisplayName("코루틴의 싱글톤 키를 이용한 CoroutineContext 구성 요소 접근하기")
    @Test
    fun coroutineContextKeyTest() {
        /**
         * 키를 이용한 접근. 여기서는 CoroutineName.Key를 이용하여 접근
         *
         * 출력 :
         * CoroutineName(MyProtin)
         */
        runBlocking {
            val coroutineContext = CoroutineName("MyProtein") + Dispatchers.IO
            // get은 연산자 함수 (operator fun)이기 때문에 대괄호([])로 대체 할 수 있다.
            // 1. key로 접근 방식.
//            val nameFromContext = coroutineContext[CoroutineName.Key]
            // 2. 구성요소 자체로 접근
            val nameFromContext = coroutineContext[CoroutineName]
            println(nameFromContext)
        }
    }


    @DisplayName("CoroutineContext minusKey사용해 구성요소 제거하기 ")
    @Test
    fun coroutineContextMinusKeyTest() {
        /**
         * 출력 :
         * [CoroutineName(MyPower), JobImpl{Active}@7de0c6ae, Dispatchers.IO]
         * [JobImpl{Active}@7de0c6ae, Dispatchers.IO]
         */
        runBlocking {
            val coroutineName = CoroutineName("MyPower")
            val dispatcher = Dispatchers.IO
            val myJob = Job()
            val coroutineContext: CoroutineContext = coroutineName + dispatcher + myJob

            // minusKey 함수를 이용한 구성요소 제거하기
            val deletedCoroutineContext = coroutineContext.minusKey(CoroutineName)

            println(coroutineContext)
            println(deletedCoroutineContext)
        }
    }
}