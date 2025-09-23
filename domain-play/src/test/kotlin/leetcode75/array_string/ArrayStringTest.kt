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


/**
 * https://leetcode.com/problems/greatest-common-divisor-of-strings/description/?envType=study-plan-v2&envId=leetcode-75
 */
class GreatestCommonDivisorOfStringsTest {
    fun solution(str1: String, str2: String): String {
        // 앞뒤가 같은지 먼저 체크, 앞뒤로 안맞으면 틀림.
        if (str1 + str2 != str2 + str1) {
            return ""
        }
        val gcd = getGCD(str1.length, str2.length)

        return str2.substring(0, gcd)
    }

    private tailrec fun getGCD(a: Int, b: Int): Int {
        if (b == 0)
            return a
        return getGCD(b, a % b)
    }

    @Test
    fun test1() {
        val str1 = "ABCABC"
        val str2 = "ABC"

        val result = solution(str1, str2)

        assertThat(result).isEqualTo("ABC")
    }

    @Test
    fun test2() {
        val str1 = "ABABAB"
        val str2 = "ABAB"

        val result = solution(str1, str2)

        assertThat(result).isEqualTo("AB")
    }

    @Test
    fun test3() {
        val str1 = "LEET"
        val str2 = "CODE"

        val result = solution(str1, str2)

        assertThat(result).isEqualTo("")
    }
}