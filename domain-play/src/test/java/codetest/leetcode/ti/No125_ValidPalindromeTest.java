package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/valid-palindrome/?envType=study-plan-v2&envId=top-interview-150
 */
public class No125_ValidPalindromeTest {

    /**
     * idea
     * - Palindrome : 앞으로, 뒤로 둘다 동일한 문자
     * 소문자 변환 및 단어와 숫자만 허용하는 로직 필요.
     * 아닌 경우 다음 값으로 넘김
     * 앞쪽에서 읽어오는 포인터 변수와 뒤쪽에서 읽어오는 포인터 변수가 필요함. (two-pointer)
     */
    boolean solution(String s) {
        int left = 0;
        int right = s.length() - 1;
        boolean result = true;

        while (left < right) {
            if (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(s.charAt(right))) {
                right--;
                continue;
            }

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                result = false;
                break;
            }
            left++;
            right--;
        }

        return result;
    }

    @Test
    void test1() {
        String s = "A man, a plan, a canal: Panama";

        boolean result = solution(s);

        assertThat(result).isTrue();
    }


    @Test
    void test2() {
        String s = "race a car";

        boolean result = solution(s);

        assertThat(result).isFalse();
    }
}
