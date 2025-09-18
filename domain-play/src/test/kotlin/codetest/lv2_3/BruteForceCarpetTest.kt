package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42842
 */
class BruteForceCarpetTest {


    fun solution(brown: Int, yellow: Int): IntArray {
        for (height in 1..yellow) {
            if (yellow % height != 0)
                continue

            val width = yellow / height
            if (width < height)
                continue

            val brownCalc = (width + 2) * (height + 2) - yellow
            if (brownCalc == brown)
                return intArrayOf(width + 2, height + 2)
        }

        return intArrayOf(0, 0)
    }

    @Test
    fun test1() {
        val brown = 10
        val yellow = 2
        val result = solution(brown, yellow)
        assertThat(result).isEqualTo(intArrayOf(4, 3))
    }
}