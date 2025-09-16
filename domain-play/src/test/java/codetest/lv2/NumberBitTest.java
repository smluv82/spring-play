package codetest.lv2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12911?language=java
 */
public class NumberBitTest {

    public int solution(int n) {
        int answer;
        int oneCount = Integer.bitCount(n);

        int nextNumber = n + 1;

        while (true) {
            if (Integer.bitCount(nextNumber) == oneCount) {
                answer = nextNumber;
                break;
            }
            nextNumber++;
        }

        return answer;
    }


    @Test
    void test1() {
        int n = 78;

        int result = solution(n);

        assertThat(result).isEqualTo(83);
    }
}
