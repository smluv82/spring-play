package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DynamicProgrammingTest1 {

    public int solution(int[][] triangle) {
        int len = triangle.length;
        int[][] dp = new int[len][len];

        dp[0][0] = triangle[0][0];

        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    // 왼쪽 끝
                    dp[i][j] = dp[i - 1][0] + triangle[i][0];
                } else if (j == i) {
                    // 오른쪽 끝
                    dp[i][j] = dp[i - 1][i - 1] + triangle[i][i];
                } else {
                    // 중간
                    dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + triangle[i][j];
                }
            }
        }

        int max = 0;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, dp[len - 1][i]);
        }

        return max;
    }


    @Test
    void test1() {
        int[][] triangle = new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};

        int result = solution(triangle);

        assertThat(result).isEqualTo(30);
    }
}
