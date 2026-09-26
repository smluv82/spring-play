package leetcode.binary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/sum-of-two-integers/description/
 */
public class SumOfTwoIntegers {

    public int getSum(int a, int b) {
        // 시간복잡도는 고정된 크기만큼 (상수) 이므로 O(1), 공간복잡도는 O(1)
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }


    @Test
    void test1() {
        int a = 2;
        int b = 3;
        int result = getSum(a, b);
        assertThat(result).isEqualTo(5);
    }

    @Test
    void test2() {
        int a = 1;
        int b = 2;
        int result = getSum(a, b);
        assertThat(result).isEqualTo(3);
    }
}
