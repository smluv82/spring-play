package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12953?language=kotlin
 */
class LeastCommonMultipleTest {


    fun solution(arr: IntArray): Int {
        if (arr.size == 1)
            return arr[0]

        var lcm = arr[0]
        for (i in 1 until arr.size) {
            val gcd = calcGCD(lcm, arr[i])
            lcm = (lcm / gcd) * arr[i]
        }

        return lcm
    }

    private tailrec fun calcGCD(a: Int, b: Int): Int {
        if (b == 0) {
            return a
        }
        return calcGCD(b, a % b)
    }

    @Test
    fun test1() {
        val arr = intArrayOf(2, 6, 8, 14)
        val result = solution(arr)
        println(result)
        assertThat(result).isEqualTo(168)
    }

}