package org.algorithms;

import org.interview.algorithms.ListNode;
import org.interview.algorithms.MergeTwoSortedList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTwoSortedListTest {

    private static final ListNode LIST_NODE_1;
    private static final ListNode LIST_NODE_2;
    private static final ListNode LIST_NODE_3;
    private static final ListNode LIST_NODE_4;
    private static final ListNode LIST_NODE_RESULT;
    private static final ListNode LIST_NODE_RESULT_2;

    static {
        LIST_NODE_1 = new ListNode(1);
        LIST_NODE_1.next = new ListNode(2);
        LIST_NODE_1.next.next = new ListNode(4);

        LIST_NODE_2 = new ListNode(1);
        LIST_NODE_2.next = new ListNode(3);
        LIST_NODE_2.next.next = new ListNode(4);

        LIST_NODE_RESULT = new ListNode(1);
        LIST_NODE_RESULT.next = new ListNode(1);
        LIST_NODE_RESULT.next.next = new ListNode(2);
        LIST_NODE_RESULT.next.next.next = new ListNode(3);
        LIST_NODE_RESULT.next.next.next.next = new ListNode(4);
        LIST_NODE_RESULT.next.next.next.next.next = new ListNode(4);

        LIST_NODE_3 = new ListNode(2);

        LIST_NODE_4 = new ListNode(1);

        LIST_NODE_RESULT_2 = new ListNode(1);
        LIST_NODE_RESULT_2.next = new ListNode(2);
    }

    @Test
    void mergeTwoLists() {
        assertEquals(LIST_NODE_RESULT, MergeTwoSortedList.mergeTwoLists(LIST_NODE_1, LIST_NODE_2));
        //assertEquals(LIST_NODE_RESULT_2, MergeTwoSortedList.mergeTwoLists(LIST_NODE_3, LIST_NODE_4));
    }
}