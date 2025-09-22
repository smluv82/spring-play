package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42586
 */
class ProgressSpeedTest {

    fun solution(progresses: IntArray, speeds: IntArray): IntArray {
        val days = ArrayDeque<Int>()

        for (i in progresses.indices) {
            val remain = 100 - progresses[i]
            var day = remain / speeds[i]
            if (remain % speeds[i] > 0)
                day++
            days.add(day)
        }

        val results = mutableListOf<Int>()
        if (days.isEmpty())
            return intArrayOf()

        var maxDay = days.removeFirst()
        var count = 1

        while (days.isNotEmpty()) {
            val curr = days.removeFirst()
            if (curr <= maxDay) {
                count++
            } else {
                results.add(count)
                count = 1
                maxDay = curr
            }
        }
        results.add(count)

        return results.toIntArray()
    }

    @Test
    fun test1() {
        val progresses = intArrayOf(93, 30, 55)
        val speeds = intArrayOf(1, 30, 5)

        val result = solution(progresses, speeds)
        println(result)
        assertThat(result).isEqualTo(intArrayOf(2, 1))
    }

    @Test
    fun test2() {
        val progresses = intArrayOf(95, 90, 99, 99, 80, 99)
        val speeds = intArrayOf(1, 1, 1, 1, 1, 1)

        val result = solution(progresses, speeds)
        println(result)
        assertThat(result).isEqualTo(intArrayOf(1, 3, 2))
    }

}


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42587
 */
class ProcessTest {


    fun solution(priorities: IntArray, location: Int): Int {
        val queue = ArrayDeque<Map<Int, Int>>(priorities.size)
        for ((index, value) in priorities.withIndex()) {
            queue.addLast(mapOf(value to index))
        }

        var count = 1
        while (queue.isNotEmpty()) {
            val maxData = queue.maxOf { it.keys.first() }
            val data = queue.removeFirst()
            if (data.keys.first() == maxData) {
                if (location == data.values.first()) {
                    break
                } else {
                    count++
                }
            } else {
                queue.addLast(data)
            }
        }

        return count
    }


    @Test
    fun test1() {
        val priorities = intArrayOf(2, 1, 3, 2)
        val location = 2

        val result = solution(priorities, location)

        assertThat(result).isEqualTo(1)
    }


    @Test
    fun test2() {
        val priorities = intArrayOf(1, 1, 9, 1, 1, 1)
        val location = 0

        val result = solution(priorities, location)

        assertThat(result).isEqualTo(5)
    }
}