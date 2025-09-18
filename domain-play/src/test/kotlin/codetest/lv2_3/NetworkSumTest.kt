package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/43162
 */
class NetworkSumTest {

    fun solution(n: Int, computers: Array<IntArray>): Int {
        var networkCount = 0
        val checked = BooleanArray(n) { false }

        fun dfs(computer: Int) {
            checked[computer] = true
            for (anotherComputer in 0 until n) {
                if (computers[computer][anotherComputer] == 1 && !checked[anotherComputer]) {
                    dfs(anotherComputer)
                }
            }
        }

        for (i in 0 until n) {
            if (!checked[i]) {
                dfs(i)
                networkCount++
            }
        }

        return networkCount
    }


    @Test
    fun test1() {
        val computerCount = 3
        val computers = arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 0, 1)
        )
        val result = solution(computerCount, computers)
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun test2() {
        val computerCount = 1
        val computers = arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 1, 1)
        )
        val result = solution(computerCount, computers)
        assertThat(result).isEqualTo(1)
    }
}