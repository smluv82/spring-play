package leetcode.linkedlist;

/**
 * https://leetcode.com/problems/reverse-linked-list/description/
 */
public class ReverseLinkedList {
    /**
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        // 변수를 하나 줘서 임시 저장 현재값을 저장
        // 현재값을 next로 이동
        // next의 값을 그다음 next로 이동
        // 시간 복잡도 O(n) : while, 공간복잡도 : O(n) : ListNode가 new로 반복문에서 생성
//        ListNode prev = null;
//
//        while (head != null) {
//            int temp = head.val;
//            head = head.next;
//            prev = new ListNode(temp, prev);
//        }
//
//        return prev;


        // 공간복잡도 : O(1)

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
