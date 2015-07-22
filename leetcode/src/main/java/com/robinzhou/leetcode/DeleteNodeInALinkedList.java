package com.robinzhou.leetcode;

/**
 * Created by N550 on 2015/7/16.
 */
public class DeleteNodeInALinkedList {
    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

}
