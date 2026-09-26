package leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/contains-duplicate/
 */
public class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
//        // 시간복잡도 O(n), 공간복잡도 O(n)
//        Set<Integer> set = new HashSet<>();
//
//        for (int num : nums) {
//            // set은 add의 return 값이 false인 경우 이미 존재하는 값이므로 true를 반환한다.
//            if (!set.add(num)) {
//                return true;
//            }
//        }
//
//        return false;


        // 시간복잡도 O(n log n), 공간복잡도 O(log n)
        Arrays.sort(nums);
        for (int i = 0; i < nums.length -1; i++) {
            if (nums[i] == nums[i+1]) {
                return true;
            }
        }
        return false;
    }


    @Test
    void test1() {
        int[] nums = {1,2,3,1};

        boolean result = containsDuplicate(nums);
        assertThat(result).isTrue();
    }

    @Test
    void test2() {
        int[] nums = {1,2,3,4};
        boolean result = containsDuplicate(nums);
        System.out.println(result);
        assertThat(result).isFalse();
    }
}
