package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/summary-ranges/?envType=study-plan-v2&envId=top-interview-150
 */
public class No228_SummaryRangesTest {


    /**
     * idea
     * - 연속되는 값이 있으면 -> 로 표시
     * - 시작되는 값과 연속여부를 포함하는 값
     * - start : 시작되는 값 (초기값 첫번째 값으로 세팅)
     * - plus : 값을 얼마나 더 해야 하는지 저장하는 값 (초기값 : 1)
     * - 다음값과 비교 후 plus만큼 늘어난 경우 통과하며 plus 값 ++, 불통과시 list add, start는 i + 1로 변경, plus 값은 1로 초기화
     */
    List<String> solution(int[] nums) {
        List<String> result = new ArrayList<>();
        if (nums.length == 0)
            return result;
        int start = nums[0];
        int plus = 1;

        for (int i = 0; i < nums.length; i++) {
            if (i + 1 < nums.length) {
                if (start + plus == nums[i + 1]) {
                    plus++;
                } else {
                    if (plus == 1) {
                        result.add(String.valueOf(start));
                    } else {
                        result.add(String.format("%d->%d", start, nums[i]));
                    }
                    start = nums[i + 1];
                    plus = 1;
                }
            } else {
                //마지막
                if (plus == 1) {
                    result.add(String.valueOf(start));
                } else {
                    result.add(String.format("%d->%d", start, nums[i]));
                }
            }
        }

        return result;
    }

    @Test
    void test1() {
        int[] nums = {0, 1, 2, 4, 5, 7};

        List<String> result = solution(nums);

        assertThat(result).isEqualTo(List.of("0->2", "4->5", "7"));
    }


    @Test
    void test2() {
        int[] nums = {-2147483648, -2147483647, 2147483647};

        List<String> result = solution(nums);

        assertThat(result).isEqualTo(List.of("-2147483648->-2147483647", "2147483647"));
    }
}
