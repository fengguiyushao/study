package com.robinzhou.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by N550 on 2015/7/16.
 */
public class DeleteNodeInALinkedListTest {

    @Test
    public void testDeleteNode() throws Exception {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        DeleteNodeInALinkedList.deleteNode(node3);
        Assert.assertEquals(node4.val, node2.next.val);
    }
}