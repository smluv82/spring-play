package codetest.lv2_3

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12939
 */
class NumberMinMax {

    fun solution(s: String): String {
        if (s.isBlank()) return ""

        val inputNumbers = s.split(" ").map { it.toInt() }
        var min = inputNumbers[0]
        var max = inputNumbers[0]


        for (i in inputNumbers) {
            if (i < min)
                min = i
            if (i > max)
                max = i
        }


        val answer = "${min} ${max}"
        return answer
    }
}


fun main() {
    val s = "1 2 3 4"
    val numberMinMax = NumberMinMax()

    val result = numberMinMax.solution(s)
    println("result : ${result}")
}