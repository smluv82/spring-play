package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/palindrome-number/?envType=study-plan-v2&envId=top-interview-150
 */
public class No9_PalindromeNumberTest {

    /**
     * idea
     * - 앞뒤로 모두 동일할 때 true, 아니면 false
     * - 음수인 경우는 -가 앞에 있어서 앞뒤가 같아질수 없으므로 -는 false로 return
     * - 문자로 변경 후 left와 right의 값이 동일한 경우 ++, -- 한 후 반복, 틀린경우 false로 return
     * - 반복문은 left != right일때까지
     */
    boolean solution(int x) {
        if (x < 0) return false;

        String s = String.valueOf(x);
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @Test
    void test1() {
        int x = 121;

        boolean result = solution(x);

        assertThat(result).isTrue();
    }
}
