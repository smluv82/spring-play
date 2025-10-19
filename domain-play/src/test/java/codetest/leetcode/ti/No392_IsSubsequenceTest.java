package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/is-subsequence/?envType=study-plan-v2&envId=top-interview-150
 */
public class No392_IsSubsequenceTest {

    /**
     * idea
     * - s에 있는 단어들이 t에 모두 있으면 true, 아니면 false
     * - 제약조건이 10의 4승이라 2중 for문으로는 불가함.
     * - while문으로 2개의 문자에 처리하여하는데 t가 더 크니 t 기반으로 while문 돌릴것.
     * - 2개의 변수로 s , t 에 대하여 left로 처리
     * - s: 의 값이 t에 있으면 sLeft++
     * - t: 무조건 올린다.
     */
    boolean solution(String s, String t) {
        if (s.isBlank())
            return true;
        if (t.isBlank())
            return false;

        int tLeft = 0;
        int sLeft = 0;

        while (tLeft < t.length()) {
            if (t.charAt(tLeft) == s.charAt(sLeft)) {
                sLeft++;
            }
            tLeft++;

            // sLeft의 길이가 s.length와 동일하면 early return
            if (sLeft == s.length())
                return true;
        }

        return sLeft == s.length();
    }


    @Test
    void test1() {
        String s = "abc";
        String t = "ahbgdc";

        boolean result = solution(s, t);

        assertThat(result).isTrue();
    }
}
