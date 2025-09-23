package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BinaryConversionTest {

    fun solution(s: String): IntArray {
        var calcInput = s
        var count = 0
        var countRemoveZero = 0

        while ("1" != calcInput) {
            val initLength = calcInput.length
            val removeLength = calcInput.replace("0", "").length

            calcInput = Integer.toBinaryString(removeLength)
            count++
            countRemoveZero += initLength - removeLength
        }

        return intArrayOf(count, countRemoveZero)
    }

    @Test
    fun test1() {
        val s = "110010101001"
        val result = solution(s)
        println(result)
        assertThat(result).isEqualTo(intArrayOf(3, 8))
    }

    @Test
    fun test2() {
        val s = "01110"
        val result = solution(s)
        println(result)
        assertThat(result).isEqualTo(intArrayOf(3, 3))
    }

    @Test
    fun test3() {
        val s = "1111111"
        val result = solution(s)
        println(result)
        assertThat(result).isEqualTo(intArrayOf(4, 1))
    }
}