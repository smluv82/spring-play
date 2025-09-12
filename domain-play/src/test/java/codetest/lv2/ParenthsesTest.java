package codetest.lv2;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12909?language=java
 */
public class ParenthsesTest {



    boolean solution(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0)
                    return false;
            }
        }
        return  count == 0;

        // 스택으로 처리
//        Stack<Character> stack = new Stack<Character>();
//
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (s.charAt(i) == '(') {
//                stack.push(c);
//
//                if (s.length() != i + 1) {
//                    continue;
//                } else {
//                    answer = false;
//                    break;
//                }
//            }
//            if (s.charAt(i) == ')') {
//                if (stack.isEmpty()) {
//                    answer = false;
//                    break;
//                }
//                if (stack.pop() != '(') {
//                    answer = false;
//                    break;
//                }
//            }
//        }
//
//        return answer;
    }

    public static void main(String[] args) {
        ParenthsesTest p = new ParenthsesTest();
        String input1 = "()()";
        String input2 = "(())()";
        String input3 = ")()(";
        String input4 = "(()(";

        boolean result1 = p.solution(input1);
        boolean result2 = p.solution(input2);
        boolean result3 = p.solution(input3);
        boolean result4 = p.solution(input4);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }
}
