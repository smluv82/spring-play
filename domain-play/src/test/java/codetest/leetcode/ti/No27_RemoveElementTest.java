package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/remove-element/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No27_RemoveElementTest {

    // in-place (같은 배열에서 작업) 해야 해서 stream은 신규 생성하기 때문에 X
//    int solution(int[] nums, int val) {
//        return (int) Arrays.stream(nums)
//                .filter(it -> it != val)
//                .count();
//    }

    /**
     * idea
     * - 2개 포인터 (i, k)
     * i : 배열 순회하며 원소를 확인하는 포인터
     * k : 유효한 원소 (val과 일치하지 않는 value)의 포인터
     * <p>
     * 최종적으로 k의 값이 유효한 배열 개수
     */
    int solution(int[] nums, int val) {
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }


    @Test
    void test1() {
        int[] nums = {3, 2, 2, 3};
        int val = 3;

        int result = solution(nums, val);

        assertThat(result).isEqualTo(2);
    }
}
