package leetcode.string;

/**
 * https://leetcode.com/problems/valid-palindrome/description/
 */
public class ValidPalindrome {

    public boolean isPalindrome(String s) {
        // 시간복잡도 O(n) 공간복잡도 O(1)
        int start = 0;
        int end = s.length() -1;

        while (start < end) {
            if (!Character.isLetterOrDigit(s.charAt(start))) {
                start++;
                continue;
            }
            if (!Character.isLetterOrDigit(s.charAt(end))) {
                end--;
                continue;
            }
            if (Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
