package leetcode.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/valid-anagram/description/
 */
public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        // 1개씩 문자열 소모하여 s와 t가 단어수가 일치하는지 체크하는 문제
        // 시간복잡도 O(n), 공간복잡도 O(n)이 아니라 알파벳은 고정된 크기 (알파벳 26개 이므로 상수)라서 O(1)
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            int num = map.getOrDefault(c, 0);

            if (num == 0) {
                return false;
            }
            map.put(c, num - 1);
        }
        return true;
    }
}
