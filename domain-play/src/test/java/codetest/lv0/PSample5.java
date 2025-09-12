package codetest.lv0;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/340203
 */
public class PSample5 {

    @Test
    public void solution() {
        String[] cpr ={"call", "respiration", "repeat", "check", "pressure"};

        int[] answer = {0, 0, 0, 0, 0};
        String[] basic_order = {"check", "call", "pressure", "respiration", "repeat"};

        for (int i = 0; i < cpr.length; i++) {
            for (int j = 0; j < basic_order.length; j++) {
                if (cpr[i].equals(basic_order[j])) {
                    answer[i] = j + 1;
                    break;
                }
            }
        }

        System.out.println(Arrays.toString(answer));
        assertThat(answer).contains(2, 4, 5, 1, 3);
    }
}
