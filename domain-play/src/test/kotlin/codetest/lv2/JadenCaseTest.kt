package codetest.lv2

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12951
 */
class JadenCaseTest {

    fun solution(s: String): String {
        var answer = mutableListOf<String>()
        val splitStr = s.split(" ")
        splitStr.forEach {
            var word = StringBuilder()
            for (index in it.indices) {
                if (it[index].isDigit()) {
                    word.append(it[index])
                    continue
                }
                if (word.isBlank()) {
                    word.append(it[index].uppercaseChar())
                } else {
                    word.append(it[index].lowercaseChar())
                }
            }
            answer.add(word.toString())
        }
        return answer.joinToString(" ")
    }

    @Test
    fun test1() {
        val result = solution("3people unFollowed me")
        print(result)
        assertThat(result).isEqualTo("3people Unfollowed Me")
    }

    @Test
    fun test2() {
        val result = solution("for the last week")
        print(result)
        assertThat(result).isEqualTo("For The Last Week")
    }
}