package leetcode.stack;

import java.util.*;

/**
 * https://leetcode.com/problems/valid-parentheses/
 */
public class ValidParentheses {
    private static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
    }

    public boolean isValid(String s) {
        // 시간 복잡도 O(n) -> for, 공간복잡도 O(n) -> stack
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 키에 있으면 오픈이니까 stack에 저장
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                //close랑 일치하는지 비교
                if (stack.isEmpty())
                    return false;
                char another = stack.pop();
                if (map.get(another) != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
