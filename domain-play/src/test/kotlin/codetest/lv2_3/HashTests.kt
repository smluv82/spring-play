package codetest.lv2_3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42578
 */
class ClothTest {

    fun solution(clothes: Array<Array<String>>): Int {
        val grouped = clothes.groupBy({ it[1] }, { it[0] })

        var total = 1
        grouped.forEach { (_, values) ->
            total *= (values.size + 1)
        }
        return total - 1
    }

    @Test
    fun test1() {
        val clothes = arrayOf(
            arrayOf("yellow_hat", "headgear"),
            arrayOf("blue_sunglasses", "eyewear"),
            arrayOf("green_turban", "headgear")
        )
        val result = solution(clothes)
        println(result)
        assertThat(result).isEqualTo(5)
    }
}


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42579
 */
class BestAlbumTest {

    fun solution(genres: Array<String>, plays: IntArray): IntArray {
        val genreKeyToPlayToNoMap = mutableMapOf<String, MutableList<Pair<Int, Int>>>()

        // 그룹화 및 플레이 횟수와 순번 저장
        for ((index, value) in genres.withIndex()) {
            genreKeyToPlayToNoMap.getOrPut(value) { mutableListOf() }.add(Pair(plays[index], index))
        }

        // 장르별 총 플레이 횟수 계산 후 플레이 횟수가 더 큰 순으로 내림차순 정렬
        val totalPlays = genreKeyToPlayToNoMap.mapValues { entry ->
            entry.value.sumOf { it.first }
        }
        val sortedTotalPlays = totalPlays.entries.sortedByDescending { it.value }

        // 장르순으로 뽑은 후 플레이 횟수 내림차순 + 순번 오름차순 정렬 하여, 장르별 최대 2개까지만 결과에 저장
        val answer = mutableListOf<Int>()
        for ((key, _) in sortedTotalPlays) {
            val songs =
                genreKeyToPlayToNoMap[key]!!.sortedWith(compareByDescending<Pair<Int, Int>> { it.first }.thenBy { it.second })

            for (i in 0 until minOf(2, songs.size)) {
                answer.add(songs[i].second)
            }
        }

        return answer.toIntArray()


//        return genres.indices.groupBy { genres[it] }
//            .toList()
//            .sortedByDescending { it.second.sumOf { x -> plays[x] } }
//            .map { it.second.sortedByDescending { x -> plays[x] }.take(2) }
//            .flatten()
//            .toIntArray()
    }


    @Test
    fun test1() {
        val genres = arrayOf("classic", "pop", "classic", "classic", "pop")
        val plays = intArrayOf(500, 600, 150, 800, 2500)

        val result = solution(genres, plays)

        assertThat(result).isEqualTo(intArrayOf(4, 1, 3, 0))
    }
}