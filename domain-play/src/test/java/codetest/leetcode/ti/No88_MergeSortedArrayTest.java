package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/merge-sorted-array/?envType=study-plan-v2&envId=top-interview-150
 */
public class No88_MergeSortedArrayTest {

    public int[] solution(int[] nums1, int m, int[] nums2, int n) {

        int i = m - 1;
        int j = n - 1;
        int nums1Len = m + n - 1;

        while (i >= 0 && j >= 0) {
            System.out.println("first after i=" + i + ", j=" + j + ", len=" + nums1Len);

            if (nums1[i] > nums2[j]) {
                nums1[nums1Len] = nums1[i];
                i--;
            } else {
                nums1[nums1Len] = nums2[j];
                j--;
            }
            nums1Len--;

            System.out.println("first before i=" + i + ", j=" + j + ", len=" + nums1Len);
        }

        while (j >= 0) {
            System.out.println("second before i=" + i + ", j=" + j + ", len=" + nums1Len);
            nums1[nums1Len] = nums2[j];
            j--;
            nums1Len--;
            System.out.println("second after i=" + i + ", j=" + j + ", len=" + nums1Len);
        }

        System.out.println("result : " + Arrays.toString(nums1));

        return nums1;
    }

    @Test
    void test1() {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        int m = 3;
        int n = 3;

        int[] result = solution(nums1, m, nums2, n);

        assertThat(result).isEqualTo(new int[]{1, 2, 2, 3, 5, 6});
    }

    @Test
    void test2() {
        int[] nums1 = {0};
        int[] nums2 = {1};
        int m = 0;
        int n = 1;

        int[] result = solution(nums1, m, nums2, n);

        assertThat(result).isEqualTo(new int[]{1});
    }
}
