package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42842
 */
class CarpetTest {

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


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/87946?language=kotlin
 */
class TiredTest {

    fun solution(k: Int, dungeons: Array<IntArray>): Int {
        var maxVisit = 0
        val visited = BooleanArray(dungeons.size) { false }

        fun rep(tired: Int, visitArray: BooleanArray) {
            for (i in dungeons.indices) {
                if (!visitArray[i] && tired >= dungeons[i][0]) {
                    visitArray[i] = true
                    rep(tired - dungeons[i][1], visitArray)
                    visitArray[i] = false
                }
            }
            maxVisit = maxVisit.coerceAtLeast(visitArray.count { it })
        }

        rep(k, visited)
        return maxVisit
    }


    @Test
    fun test1() {
        val tired = 80
        val dungeons = arrayOf(intArrayOf(80, 20), intArrayOf(50, 40), intArrayOf(30, 10))

        val result = solution(tired, dungeons)

        assertEquals(
            expected = 3,
            actual = result
        )
    }
}
