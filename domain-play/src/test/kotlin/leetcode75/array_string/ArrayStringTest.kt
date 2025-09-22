package leetcode75.array_string

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test


/**
 * https://leetcode.com/problems/merge-strings-alternately/description/?envType=study-plan-v2&envId=leetcode-75
 */
class MergeStringsAlternatelyTest {

    fun solution(word1: String, word2: String): String {
        val builder = StringBuilder()

        for (i in 0 until maxOf(word1.length, word2.length)) {
            if (i < word1.length) {
                builder.append(word1[i])
            }
            if (i < word2.length) {
                builder.append(word2[i])
            }
        }

        return builder.toString()
    }


    @Test
    fun test1() {
        val word1 = "abc"
        val word2 = "pqr"

        val result = solution(word1, word2)
        println(result)
        assertThat(result).isEqualTo("apbqcr")
    }


    @Test
    fun test2() {
        val word1 = "ab"
        val word2 = "pqrs"

        val result = solution(word1, word2)
        println(result)
        assertThat(result).isEqualTo("apbqrs")
    }


    @Test
    fun test3() {
        val word1 = "abcd"
        val word2 = "pq"

        val result = solution(word1, word2)
        println(result)
        assertThat(result).isEqualTo("apbqcd")
    }
}