package org.interview.algorithms;

import java.util.List;

public class MergeTwoSortedList {

    /**
     *
     * True solution, which faster than 100% solutions on LeetCode
     *
     * @param l1 ListNode
     * @param l2 ListNode
     * @return ListNode
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }

        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }

        return dummy.next;
    }

    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        ListNode result = new ListNode();
        ListNode headOfResult = result;
        List<ListNode> list;
        do {
            int val1 = list1.val;
            int val2 = list2.val;
            if (val1 >= val2) {
                list = fill(val1, list2, result);
                list2 = list.get(0);
            } else {
                list = fill(val2, list1, result);
                list1 = list.get(0);
            }
            result = list.get(1);
        } while (list1 == null && list2 == null);
        return headOfResult;
    }

    private static List<ListNode> fill(int biggestValue, ListNode list, ListNode result) {
        int valSmaller = list.val;
        while(biggestValue >= valSmaller) {
            result.val = valSmaller;
            list = list.next;
            valSmaller = list.val;
            var newNode = new ListNode();
            result.next = newNode;
            result = newNode;
        }
        return List.of(list, result);
    }

    public static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        ListNode result = new ListNode();
        ListNode headOfResult = result;
        int val1 = -1;
        int val2 = -1;
        if (list1 == null && list2 == null) {
            return null;
        }
        if (list1 != null) {
            val1 = list1.val;
        }
        if (list2 != null) {
            val2 = list2.val;
        }
        if (list1 == null && list2 != null && list2.next == null) {
            return new ListNode(list2.val);
        }
        if (list2 == null && list1 != null && list1.next == null) {
            return new ListNode(list1.val);
        }
        while (true) {
            if (list1 == null && list2 == null) {
                break;
            }
            if (val1 > val2 && val2 != -1) {
                result.val = val2;
                if (list2 != null) {
                    list2 = list2.next;
                    if (list2 != null) {
                        val2 = list2.val;
                    }
                }
//                else {
//                    val2 = -1;
//                }
            } else if (val1 == val2) {
                result.val = val1;
                var newNode = new ListNode();
                result.next = newNode;
                result = newNode;
                result.val = val2;
                if (list1 != null) {
                    list1 = list1.next;
                    if (list1 != null) {
                        val1 = list1.val;
                    }
                }
//                else {
//                    val1 = -1;
//                }
                if (list2 != null) {
                    list2 = list2.next;
                    if (list2 != null) {
                        val2 = list2.val;
                    }
                }
//                else {
//                    val2 = -1;
//                }
            } else {
                result.val = val1;
                if (list1 != null) {
                    list1 = list1.next;
                    if (list1 != null) {
                        val1 = list1.val;
                    }
                }
//                else {
//                    val1 = -1;
//                }
            }
            if (list1 != null || list2 != null) {
                var newNode2 = new ListNode();
                result.next = newNode2;
                result = newNode2;
            }
            if (list1 == null) {
                val1 = -1;
            }
            if (list2 == null) {
                val2 = -1;
            }
        }
        return headOfResult;
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
