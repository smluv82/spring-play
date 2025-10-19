package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No28_FindIndexFirstOccurrenceStringTest {

    /**
     * idea
     * - 첫번째로 needle과 일치하는 hayStack의 문자 위치를 반환
     * - hayStack 또는 needle 이 길이가 0이면 -1
     * - needle의 문자열 길이와, 첫번째 문자가 일치하면 substring으로 비교하면 될듯.
     */
    int solution(String haystack, String needle) {
        if (haystack.isBlank() || needle.isBlank())
            return -1;

        char needleFirstChar = needle.charAt(0);

        for (int i = 0; i < haystack.length(); i++) {
            if (i + needle.length() > haystack.length())
                return -1;
            if (haystack.charAt(i) == needleFirstChar) {
                if (haystack.substring(i, i + needle.length()).equals(needle))
                    return i;
            }
        }
        return -1;
    }


    @Test
    void test1() {
        String haystack = "sadbutsad";
        String needle = "sad";

        int result = solution(haystack, needle);

        assertThat(result).isEqualTo(0);
    }

    @Test
    void test2() {
        String haystack = "leetcode";
        String needle = "leeto";

        int result = solution(haystack, needle);

        assertThat(result).isEqualTo(-1);
    }
}
