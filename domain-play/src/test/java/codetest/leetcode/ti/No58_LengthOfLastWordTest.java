package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/length-of-last-word/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No58_LengthOfLastWordTest {

    /**
     * idea
     * - input : 문자 + 공백으로 이루어진 문자열
     * - 마지막 단어의 길이에 대해서 return 하는데, 공백없는 문자로만 이루어진 것만 결과 값
     * - 뒤에서 부터 가져오면서 단어가 공백이 아닌 경우 count 시작
     * - 있는 경우 count 하다가 공백을 만난 경우 for 문 중단 후 결과 return
     * - 공백인 경우 반환 변수가 0 이 아닌 경우 반복문 중단 (앞에서 빈 문자열인지 체크하였으므로 개수는 0개 이상일것이라서)
     */
    int solution(String s) {
        if (s.isBlank())
            return 0;

        int result = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c != ' ') {
                result++;
            } else {
                if (result != 0)
                    break;
            }
        }

        return result;
    }

    @Test
    void test1() {
        String s = "Hello World";

        int result = solution(s);

        assertThat(result).isEqualTo(5);
    }

    @Test
    void test2() {
        String s = "   fly me   to   the moon  ";

        int result = solution(s);

        assertThat(result).isEqualTo(4);
    }
}
