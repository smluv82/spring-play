package leetcode.array;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 */
public class BestTimeToBuyAndSellStock {


    public int maxProfit(int[] prices) {
        int answer = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            answer = Math.max(prices[i] - min, answer);
        }

        return answer;
    }
}
