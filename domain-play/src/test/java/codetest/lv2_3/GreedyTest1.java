package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42885
 */
public class GreedyTest1 {


    public int solution(int[] people, int limit) {
        Arrays.sort(people);

        int count = 0;
        int left = 0;
        int right = people.length - 1;

        while (left <= right) {
            if (left < right && people[left] + people[right] <= limit) {
                left++;
                right--;
            } else {
                right--;
            }
            count++;
        }

        return count;
    }


    @Test
    void test1() {
        int[] people = new int[]{70, 50, 80, 50};
        int limit = 100;

        int result = solution(people, limit);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void test2() {
        int[] people = new int[]{70, 80, 50};
        int limit = 100;

        int result = solution(people, limit);

        assertThat(result).isEqualTo(3);
    }
}
