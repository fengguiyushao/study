package com.robinzhou.ITA;

import java.util.*;

/**
 * Created by robinzhou on 2016/7/13.
 */
public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        List<List<Integer>> list = new ArrayList<>();


//        System.out.println(s.isValidSudoku("barfoothefoobarman", new String[]{"foo", "bar"}));

    }

    public static ListNode buildList(int[] a) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        for (int num : a) {
            cur.next = new ListNode(num);
            cur = cur.next;
        }
        return head.next;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}

class Tuple implements Comparable<Tuple> {
    int height, index;

    public Tuple(int height, int index) {
        this.height = height;
        this.index = index;
    }

    @Override
    public int compareTo(Tuple o) {
        return this.height - o.height;
    }
}

class Unit implements Comparable<Unit> {
    int num, times;

    public Unit(int num, int times) {
        this.num = num;
        this.times = times;
    }

    @Override
    public int compareTo(Unit o) {
        return o.times - this.times;
    }
}
