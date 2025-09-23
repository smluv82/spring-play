package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FibonacciModularTest {


    fun solution(n: Int): Int {
        val divide = 1234567
        var before = 0
        var current = 1

        for (i in 2..n) {
            val next = (before + current) % divide
            before = current
            current = next
        }

        return current
    }

    @Test
    fun test1() {
        val n = 3
        val result = solution(n)
        println(result)
        assertThat(result).isEqualTo(2)
    }


    @Test
    fun test2() {
        val n = 5
        val result = solution(n)
        println(result)
        assertThat(result).isEqualTo(5)
    }
}