package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42584
 */
public class StackQueueTest1 {


    public int[] solution(int[] prices) {
//        Deque<Integer> stack = new ArrayDeque<>();
//        int[] answer = new int[prices.length];
//
//        for (int i = prices.length - 1; i >= 0; i--) {
//
//            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
//                stack.pop();
//            }
//            if (stack.isEmpty())
//                answer[i] = prices.length - i - 1;
//            else
//                answer[i] = stack.peek() - i;
//
//            stack.push(i);
//        }
//
//        return answer;


        int n = prices.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] > prices[i]) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {  // 루프 끝난 후 처리
            int j = stack.pop();
            answer[j] = n - j - 1;
        }

        return answer;
    }


    @Test
    void test1() {
        int[] prices = {1, 2, 3, 2, 3};

        int[] result = solution(prices);

        assertThat(result).isEqualTo(new int[]{4, 3, 1, 1, 0});
    }
}
