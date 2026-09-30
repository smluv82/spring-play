package leetcode.linkedlist;

/**
 * https://leetcode.com/problems/merge-two-sorted-lists/description/
 */
public class MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 시간 복잡도 : O(n+m) : l1 + l2, 공간 복잡도 : O(1) 밖에서 head로 new 1개 변수 생성

        // 시작점 기억
        ListNode head = new ListNode();
        // 움직이면서 붙이기
        ListNode result = head;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                result.next = l1;
                l1 = l1.next;
            } else {
                result.next = l2;
                l2 = l2.next;
            }
            result = result.next;
        }
        // 남은거 뒤에다가 다 붙이기
        result.next = l1 != null ? l1 : l2;
        return head.next;
    }
}


