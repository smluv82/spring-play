package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42747
 *
 * 정렬이 핵심. 정렬 후
 * 배열 길이 : 발표 한 논문 수
 * 반복문의 i + 1 : 현재까지 확인 한 논문수
 */
class HindexTest {

    fun solution(citations: IntArray): Int {
        citations.sortDescending()

        var h = 0
        for (i in citations.indices) {
            if (citations[i] >= i + 1)
                h = i + 1
            else
                break
        }
        return h
    }


    @Test
    fun test1() {
        val citations = intArrayOf(3, 0, 6, 1, 5)
        val result = solution(citations)
        assertThat(result).isEqualTo(3)
    }
}