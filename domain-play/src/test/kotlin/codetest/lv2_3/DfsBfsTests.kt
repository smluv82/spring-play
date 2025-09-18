package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TargetNumberTest {

    fun solution(numbers: IntArray, target: Int): Int {
        fun dfs(index: Int, sum: Int): Int {
            if (index == numbers.size) {
                return if (sum == target) 1 else 0
            }

            val plus = dfs(index + 1, sum + numbers[index])
            val minus = dfs(index + 1, sum - numbers[index])

            return plus + minus
        }

        return dfs(0, 0)
    }


    @Test
    fun test1() {
        val numbers = intArrayOf(1, 1, 1, 1, 1)
        val target = 3

        val result = solution(numbers, target)

        assertThat(result).isEqualTo(5)
    }


    @Test
    fun test2() {
        val numbers = intArrayOf(4, 1, 2, 1)
        val target = 4

        val result = solution(numbers, target)

        assertThat(result).isEqualTo(2)
    }
}


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