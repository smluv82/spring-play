package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42577
 */
public class HashTest1 {

    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Arrays.sort(phone_book);

        for (int i = 0; i < phone_book.length - 1; i++) {
            String phone = phone_book[i];
            if (phone_book[i + 1].startsWith(phone)) {
                answer = false;
                return answer;
            }
        }

        return answer;
    }


    @Test
    void test1() {
        String[] phoneBook = new String[]{"119", "97674223", "1195524421"};

        boolean result = solution(phoneBook);

        assertThat(result).isFalse();
    }

    @Test
    void test2() {
        String[] phoneBook = new String[]{"123", "456", "789"};

        boolean result = solution(phoneBook);

        assertThat(result).isTrue();
    }

    @Test
    void test3() {
        String[] phoneBook = new String[]{"12", "123", "1235", "567", "88"};

        boolean result = solution(phoneBook);

        assertThat(result).isFalse();
    }
}
