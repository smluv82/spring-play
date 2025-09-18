package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42626?language=java
 */
public class HeapTest1 {

    public int solution(int[] scoville, int K) {
        int answer = 0;

        PriorityQueue<Long> pq = new PriorityQueue<>();
        Arrays.stream(scoville).mapToLong(l -> l).forEach(pq::offer);

        while (pq.size() > 1 && pq.peek() < K) {
            long first = pq.poll();
            long second = pq.poll();
            pq.offer(first + (second * 2));
            answer++;
        }

        return pq.peek() >= K ? answer : -1;
    }


    @Test
    void test1() {
        int[] scoville = {1, 2, 3, 9, 10, 12};
        int K = 7;

        int result = solution(scoville, K);

        assertThat(result).isEqualTo(2);
    }
}
