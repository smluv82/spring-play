package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No26_RemoveDuplicatesFromSortedArrayTest {

    /**
     * idea
     * - 동일 배열에서 처리해야 함.
     * - 정렬이 되어 있으므로 최대 숫자를 저장하는 변수. 호출 범위가 -100에서 100까지임. 초기값 -101로 설정
     * - i : 처음부터 순차적으로 배열 조회하는 포인터
     * - k : 중복되지 않는 값을 저장하는 포인터
     */
    public int solution(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int max = -101;
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }


    @Test
    void test1() {
        int[] nums = {1, 1, 2};

        int result = solution(nums);

        assertThat(result).isEqualTo(2);
    }
}
