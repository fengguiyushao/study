package com.robinzhou.tree;

/**
 * Created by N550 on 2015/7/23.
 */
public class BST {
    TreeNode root;

    public BST() {}

    public BST(int[] arr) {
        this.build(arr);
    }

    public void build(int[] arr) {
        for (int key : arr) {
            TreeNode node = new TreeNode(key);
            TreeNode p = null;
            TreeNode x = root;
            while (x != null) {
                p = x;
                if(node.getKey() < x.getKey()) {
                    x = x.getLeft();
                }else {
                    x = x.getRight();
                }
            }
            node.setParent(p);
            if(p == null) {
                root = node;
            } else if(node.getKey() < p.getKey()) {
                p.setLeft(node);
            } else {
                p.setRight(node);
            }
        }
    }

    public void inorderTreeWalk() {
        inorderTreeWalk(root);
    }

    private void inorderTreeWalk(TreeNode x) {
        if(x != null) {
            inorderTreeWalk(x.getLeft());
            System.out.println(x.getKey());
            inorderTreeWalk(x.getRight());
        }
    }


}
