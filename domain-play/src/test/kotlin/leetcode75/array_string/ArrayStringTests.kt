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


/**
 * https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/description/?envType=study-plan-v2&envId=leetcode-75
 */
class KidsWithTheGreatestNumberOfCandiesTest {
    fun solution(candies: IntArray, extraCandies: Int): List<Boolean> {
        val maxValue = candies.max()
        return candies.map { it + extraCandies >= maxValue }.toList()
    }


    @Test
    fun test1() {
        val candies = intArrayOf(2, 3, 5, 1, 3)
        val extraCandies = 3

        val result = solution(candies, extraCandies)

        assertThat(result).isEqualTo(listOf(true, true, true, false, true))
    }

    @Test
    fun test2() {
        val candies = intArrayOf(4, 2, 1, 1, 2)
        val extraCandies = 1

        val result = solution(candies, extraCandies)

        assertThat(result).isEqualTo(listOf(true, false, false, false, false))
    }

    @Test
    fun test3() {
        val candies = intArrayOf(12, 1, 12)
        val extraCandies = 10

        val result = solution(candies, extraCandies)

        assertThat(result).isEqualTo(listOf(true, false, true))
    }
}


/**
 * https://leetcode.com/problems/can-place-flowers/description/?envType=study-plan-v2&envId=leetcode-75
 */
class CanPlaceFlowersTest {

    fun solution(flowerbed: IntArray, n: Int): Boolean {
        var count = 0
        var i = 0

        if (flowerbed.size == 1) {
            if (flowerbed[0] == 0) {
                count++
            }
            return count >= n
        }

        while (i < flowerbed.size) {
            if (i == 0) {
                if (flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
                    count++
                    i += 2
                } else {
                    i++
                }
            } else {
                if (i + 1 < flowerbed.size) {
                    if (flowerbed[i] == 0 && flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                        count++
                        i += 2
                    } else {
                        i++
                    }
                } else {
                    if (flowerbed[i] == 0 && flowerbed[i - 1] == 0) {
                        count++
                        i += 2
                    } else {
                        i++
                    }
                }
            }
        }

        return count >= n
    }


    @Test
    fun test1() {
        val flowerbed = intArrayOf(1, 0, 0, 0, 1)
        val n = 1

        val result = solution(flowerbed, n)

        assertThat(result).isTrue()
    }

    @Test
    fun test2() {
        val flowerbed = intArrayOf(1, 0, 0, 0, 1)
        val n = 2

        val result = solution(flowerbed, n)

        assertThat(result).isFalse()
    }

    @Test
    fun test3() {
        val flowerbed = intArrayOf(1, 0, 0, 0, 1, 0, 0)
        val n = 2

        val result = solution(flowerbed, n)

        assertThat(result).isTrue()
    }


    @Test
    fun test4() {
        val flowerbed = intArrayOf(
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            1,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            1,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            0,
            1,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            1,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            1,
            0,
            0,
            0,
            1,
            0,
            1,
            0,
            0,
            0,
            0,
            0,
            0
        )
        val n = 17

        val result = solution(flowerbed, n)

        assertThat(result).isFalse()
    }
}


/**
 * https://leetcode.com/problems/reverse-vowels-of-a-string/description/?envType=study-plan-v2&envId=leetcode-75
 */
class ReverseVowelsOfStringTest {

    private val VOWELS = setOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')

    fun solution(s: String): String {
        // 밑의 주석 친게 내가 푼것인데, 속도가 느림. O(n2)
//        val result = StringBuilder()
//        var max = s.length
//
//        for (i in s.indices) {
//            if (s[i] in VOWELS) {
//                for (j in s.length downTo 1) {
//                    if (j > max)
//                        continue
//
//                    if (s[j - 1] in VOWELS) {
//                        result.append(s[j - 1])
//                        max = j - 1
//                        break
//                    }
//                }
//            } else {
//                result.append(s[i])
//            }
//        }
//
//        return result.toString()


        // O(n), 투 포인터 기법
        val chars = s.toCharArray()
        var left = 0
        var right = s.length - 1

        while (left < right) {
            // Find the next vowel from the left
            while (left < right && chars[left] !in VOWELS) {
                left++
            }
            // Find the next vowel from the right
            while (left < right && chars[right] !in VOWELS) {
                right--
            }
            // Swap vowels
            if (left < right) {
                val temp = chars[left]
                chars[left] = chars[right]
                chars[right] = temp
                left++
                right--
            }
        }

        return String(chars)
    }


    @Test
    fun test1() {
        val s = "IceCreAm"

        val result = solution(s)

        assertThat(result).isEqualTo("AceCreIm")
    }


    @Test
    fun test2() {
        val s = "leetcode"

        val result = solution(s)

        assertThat(result).isEqualTo("leotcede")
    }

    @Test
    fun test3() {
        val s = "Ui"

        val result = solution(s)

        assertThat(result).isEqualTo("iU")
    }
}