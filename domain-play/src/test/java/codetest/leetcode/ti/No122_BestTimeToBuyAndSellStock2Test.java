package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/?envType=study-plan-v2&envId=top-interview-150
 */
public class No122_BestTimeToBuyAndSellStock2Test {

    /**
     * idea : 최고의 수익을 내야 함.
     * 배열 길이중에 낮은가격에 산다음 비싸지면 팔면 됨. 즉 다음 가격이 비싸면 팔아서 수익을 남기면 됨.
     * 다음가격 - 현재가격 = + 이면 이득, - 이면 continue
     * <p>
     * 변수 result : 이윤 저장 결과
     */
    int solution(int[] prices) {

        int result = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                result += prices[i] - prices[i - 1];
            }
        }

        return result;
    }


    @Test
    void test1() {
        int[] prices = {7, 1, 5, 3, 6, 4};

        int result = solution(prices);

        assertThat(result).isEqualTo(7);
    }
}
