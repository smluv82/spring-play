package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/majority-element/?envType=study-plan-v2&envId=top-interview-150
 */
public class No169_MajorityElementTest {

    /**
     * idea
     * - n / 2 보다 많은게 메이저 원소 : 전체길이의 반으로 한 값을 후보로 놓자.
     * - 각 원소 값마다 여러개가 나올수 있다.
     * - Brute Force? 보다는 hashMap을 이용하여 Key, Value로 하는게 더 괜찮을듯.
     * - Key : 원소
     * - Value : 나온 횟수
     */
    public int solution(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        int majority = nums.length / 2;
        int result = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);

            if (map.get(num) > majority) {
                result = num;
                break;
            }

        }

        return result;
    }

    @Test
    void test1() {
        int[] nums = {3, 2, 3};

        int result = solution(nums);

        assertThat(result).isEqualTo(3);
    }

}
