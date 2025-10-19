package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/longest-common-prefix/?envType=study-plan-v2&envId=top-interview-150
 */
public class No14_LongestCommonPrefixTest {

    /**
     * idea :
     * - 공통된 prefix 단어가 있는 경우 해당 단어를 return
     * - 이중 for문 : 기준이 되는 String은 첫번째껄로 하고, 문자열 길이 만큼 반복, 안쪽에서는 그다음 배열의 String부터 단어가 일치한지 여부 체크, 길이가 다르면 거기까지가 동일한걸로 return
     * - 배열이 빈 경우 ""으로 return
     */
    String solution(String[] strs) {

        if (strs == null || strs.length == 0)
            return "";

        for (int i = 0; i < strs[0].length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                // 길이 체크 및 문자값 비교하여 불일치하는 경우 return
                if (i == strs[j].length() || strs[j].charAt(i) != strs[0].charAt(i)) {
                    return strs[0].substring(0, i);
                }
            }

        }

        return strs[0];
    }

    @Test
    void test1() {
        String[] strs = new String[]{"flower", "flow", "flight"};

        String result = solution(strs);

        assertThat(result).isEqualTo("fl");
    }
}
