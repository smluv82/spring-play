package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class No80_RemoveDuplicatesFromSortedArray2Test {

    /**
     * idea
     * - 최대 2개까지 같은 단어 중복 허용이 가능함.
     * //     * - 변수 : Map<Int, Int> : key : 지정 숫자, value: 횟수 저장 (회수가 2회이상이면 continue)
     * //     * - 변수 : count : 숫자가 저장된걸 카운트 하는 변수
     * - 문제만 풀면 안되고, 입력했던배열도 올바르게 변형해야 하는 문제임.
     * - two pointer로 풀어야 함.
     * - 변수 : read, write
     */
    int solution(int[] nums) {
        if (nums == null) return 0;
        if (nums.length <= 2)
            return nums.length;

        int write = 2;

        for (int read = 2; read < nums.length; read++) {
            if (nums[read] > nums[write - 2]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }


    @Test
    void test1() {
        int[] nums = {1, 1, 1, 2, 2, 3};

        int result = solution(nums);

        assertThat(result).isEqualTo(5);
    }
}
