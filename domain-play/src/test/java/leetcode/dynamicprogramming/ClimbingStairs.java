package leetcode.dynamicprogramming;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/climbing-stairs/description/
 */
public class ClimbingStairs {
    public int climbStairs(int n) {
        // 시간복잡도 : O(n), 공간복잡도 : O(n)
//        int[] dp = new int[n + 1];
//        dp[0] = 1;
//        dp[1] = 1;
//        for (int i = 2; i <= n; i++) {
//            dp[i] = dp[i - 1] + dp[i - 2];
//        }
//        return dp[n];


        // 재귀 타임아웃 발생
        // 시간복잡도 : O(2^n), 공간복잡도 : O(n)
        int[] dp = new int[n + 1];

        return memorize(n, dp);
    }

    private int memorize(int n, int[] dp) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        if (dp[n] != 0) return dp[n];
        dp[n] = memorize(n - 1, dp) + memorize(n - 2, dp);

        return dp[n];
    }


    @Test
    void test() {
        int n = 3;
        int result = climbStairs(n);
        System.out.println(result);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void test2() {
        int n = 4;
        int result = climbStairs(n);
        System.out.println(result);
        assertThat(result).isEqualTo(5);
    }

}
