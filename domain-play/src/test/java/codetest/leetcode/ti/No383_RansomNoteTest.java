package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/ransom-note/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No383_RansomNoteTest {

    /**
     * idea
     * - ransomNote의 변수에 있는 값을 magazine의 값으로 만들수 있는 지 여부
     * Map으로 ransomMap, magazineMap을 만들어 key : 각 단어, value : 나온 회수를 저장
     * for 문으로 ransomMap이 magazineMap보다 큰 경우는 만들수가 없기 때문에 false 리턴, 이외에는 true
     */
    boolean solution(String ransomNote, String magazine) {
        Map<Character, Integer> ransomMap = new HashMap<>();
        Map<Character, Integer> magazineMap = new HashMap<>();
        boolean result = true;
        
        for (int i = 0; i < ransomNote.length(); i++) {
            ransomMap.put(ransomNote.charAt(i), ransomMap.getOrDefault(ransomNote.charAt(i), 0) + 1);
        }
        for (int i = 0; i < magazine.length(); i++) {
            magazineMap.put(magazine.charAt(i), magazineMap.getOrDefault(magazine.charAt(i), 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : ransomMap.entrySet()) {
            if (magazineMap.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                result = false;
                break;
            }
        }

        return result;
    }

    @Test
    void test1() {
        String ransomNote = "a";
        String magazine = "b";

        boolean result = solution(ransomNote, magazine);

        assertThat(result).isFalse();
    }

    @Test
    void test2() {
        String ransomNote = "aa";
        String magazine = "ab";

        boolean result = solution(ransomNote, magazine);

        assertThat(result).isFalse();
    }

    @Test
    void test3() {
        String ransomNote = "aa";
        String magazine = "aab";

        boolean result = solution(ransomNote, magazine);

        assertThat(result).isTrue();
    }
}
