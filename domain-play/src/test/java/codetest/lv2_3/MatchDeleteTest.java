package codetest.lv2_3;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MatchDeleteTest {

    // 정확성은 맞지만, 효율성 통과하지 못함.
//    public int solution(String s) {
//        return dfs(s);
//    }
//
//
//    private int dfs(String input) {
//        for (int i = 0; i < input.length(); i++) {
//            if (i + 1 >= input.length())
//                return 0;
//
//            if (input.charAt(i) == input.charAt(i + 1)) {
//                StringBuilder sb = new StringBuilder(input);
//                sb.delete(i, i + 2);
//
//                String newInput = sb.toString();
//                if (newInput.isEmpty()) {
//                    return 1;
//                }
//
//                return dfs(sb.toString());
//            }
//        }
//        return -1;
//    }


    public int solution(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty() ? 1 : 0;
    }


    @Test
    void test1() {
        String s = "baabaa";
        int result = solution(s);

        System.out.println(result);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void test2() {
        String s = "cdcd";
        int result = solution(s);

        System.out.println(result);
    }
}
