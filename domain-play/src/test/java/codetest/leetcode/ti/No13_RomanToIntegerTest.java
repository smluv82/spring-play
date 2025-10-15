package codetest.leetcode.ti;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://leetcode.com/problems/roman-to-integer/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class No13_RomanToIntegerTest {

    /**
     * idea
     * - 문자를 입력받으면 미리 지정된 int의 값으로 변환하여 최종적으로 더 한 값을 전달
     * hashmap : Map<Character, Integer> : key : 문자, Integer : 대응하는 숫자 값
     * 단 다음 값이랑 현재 값이랑 비교해서 더하는 값이 변경 될 수있는 조건 필요함. 다음단어를 사용했으면 다음 단어는 건너뛰기
     * 현재 값 : I 다음값 V, X : 4, 9
     * result : 최종적으로 더한 결과 값
     * skip : 문자 조합으로 다음 char를 skip을 하기 위한 변수
     */
    int solution(String s) {
        int result = 0;
        boolean skip = false;

        Map<Character, Integer> romanToNum = Map.of(
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
        );

        for (int i = 0; i < s.length(); i++) {
            if (skip) {
                skip = false;
                continue;
            }

            char c = s.charAt(i);
            System.out.println("c : " + c);
            if (romanToNum.containsKey(c)) {
                int plusValue = romanToNum.get(c);
                int afterPlusValue = 0;

                if (i + 1 < s.length()) {
                    if (c == 'I') {
                        if (s.charAt(i + 1) == 'V' || s.charAt(i + 1) == 'X') {
                            afterPlusValue = romanToNum.get(s.charAt(i + 1));
                            result += afterPlusValue - 1;
                            skip = true;
                        } else {
                            result += plusValue;
                        }
                    } else if (c == 'X') {
                        if (s.charAt(i + 1) == 'L' || s.charAt(i + 1) == 'C') {
                            afterPlusValue = romanToNum.get(s.charAt(i + 1));
                            result += afterPlusValue - 10;
                            skip = true;
                        } else {
                            result += plusValue;
                        }
                    } else if (c == 'C') {
                        if (s.charAt(i + 1) == 'D' || s.charAt(i + 1) == 'M') {
                            afterPlusValue = romanToNum.get(s.charAt(i + 1));
                            result += afterPlusValue - 100;
                            skip = true;
                        } else {
                            result += plusValue;
                        }
                    } else {
                        result += plusValue;
                    }
                } else {
                    result += plusValue;
                }
            }
        }

        return result;
    }

    @Test
    void test1() {
        String s = "III";
        int result = solution(s);
        assertThat(result).isEqualTo(3);
    }


    @Test
    void test2() {
        String s = "MCMXCIV";
        int result = solution(s);
        assertThat(result).isEqualTo(1994);
    }
}
