package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No121_BestTimeBuySellStockTest {

    /**
     * idea
     * - 최대 이윤이 남기도록 해야함.
     * - maxProfit : 최대 이윤이 남는것에 대한 변수
     * - 2중 for문으로 될꺼 같은데 시간복잡도가 초과됨.
     */
    int solution(int[] prices) {

        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[i] < prices[j]) {
                    maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
                }
            }
        }

        return maxProfit;
    }

    /**
     * idea
     * - 최소값을 저장하고 최소값이 더 작은게 있으면 해당값으로 변경. 아니면 현재까지 최대이윤의 저장값과, 다음 값이 비교했을 때 더 이윤이 높은거 저장
     * - input array가 비었으면 0으로 return
     * - 최소값을 저장하는 변수 . 최초의 값은 첫번째 배열의 값
     * - 최대 이윤 저장하는 변수.
     */
    int solution2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int minValue = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minValue) {
                minValue = prices[i];
            } else {
                maxProfit = Math.max(maxProfit, prices[i] - minValue);
            }
        }

        return maxProfit;
    }


    @Test
    void test1() {
        int[] prices = {7, 1, 5, 3, 6, 4};

        int result = solution2(prices);

        assertThat(result).isEqualTo(5);
    }
}
