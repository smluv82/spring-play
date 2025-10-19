package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/plus-one/?envType=study-plan-v2&envId=top-interview-150
 */
public class No66_PlusOneTest {

    /**
     * idea
     * - 1을 더했을 떄의 배열을 return
     * - 마지막 열부터 더해서 0이 된 경우에만 그 상위의 값을 1을 더하면 됨.
     * - 뒤에서 부터 1을 더하여 0이면 계속 아니면 반복문 종료.
     * - 위와같이 하면 될꺼 같은데 배열의 앞자리가 추가될때 어떻게 해야할까? int로 만든 다음에 1더하고 다시 배열로 바꿔서 일단 풀자.
     * <p>
     * - 아래와 같이 하면 오류나면서 안됨.
     * (TODO 다시 풀자)
     */
    int[] solution(int[] digits) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits.length; i++) {
            sb.append(digits[i]);
        }
        String digit = String.valueOf(Long.parseLong(sb.toString()) + 1);

        int[] result = new int[digit.length()];
        for (int i = 0; i < digit.length(); i++) {
            result[i] = digit.charAt(i) - '0';
        }

        return result;
    }


    @Test
    void test1() {
        int[] digits = {1, 2, 3};

        int[] result = solution(digits);

        assertThat(result).isEqualTo(new int[]{1, 2, 4});
    }
}
