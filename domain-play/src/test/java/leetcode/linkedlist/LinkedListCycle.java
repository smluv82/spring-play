package leetcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 */
public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        // 시간복잡도 : O(n) : head 만큼, 공간복잡도 : O(n) - Set
//
//        Set<ListNode> set = new HashSet<>();
//
//        while (head != null) {
//            if (set.contains(head))
//                return true;
//            set.add(head);
//            head = head.next;
//        }
//        //반복문이 끝났다는건 head가 null이 됐다는것
//        return false;


        // 시간복잡도 O(n), 공간복잡도 : O(1) 단순 변수만 추가
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
