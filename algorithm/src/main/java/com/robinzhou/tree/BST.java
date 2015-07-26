package com.robinzhou.tree;

/**
 * Created by robinzhou on 2015/7/23.
 */
public class BST {
    TreeNode root;

    public BST() {
    }

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
                if (node.getKey() < x.getKey()) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            node.p = p;
            if (p == null) {
                root = node;
            } else if (node.getKey() < p.getKey()) {
                p.left = node;
            } else {
                p.right = node;
            }
        }
    }

    public void inorderTreeWalk() {
        inorderTreeWalk(root);
    }

    private void inorderTreeWalk(TreeNode x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x.getKey());
            inorderTreeWalk(x.right);
        }
    }


    public TreeNode search(int k) {
        return search(root, k);
    }

    private TreeNode search(TreeNode x, int k) {
        if (x == null || k == x.getKey()) {
            return x;
        }
        if (k < x.getKey()) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    public TreeNode iterativeSearch(int k) {
        TreeNode x = root;
        while (x != null && k != x.getKey()) {
            if (k < x.getKey()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public TreeNode min() {
        return min(root);
    }

    private TreeNode min(TreeNode x) {
        if (x == null) {
            return null;
        }
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public TreeNode max() {
        if (root == null) {
            return null;
        }
        TreeNode x = root;
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public TreeNode successor(TreeNode x) {
        if (x.right != null) {
            return min(x.right);
        }

        TreeNode y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = x.p;
        }
        return y;
    }

    public void insert(int k) {
        TreeNode z = new TreeNode(k);
        TreeNode y = null;
        TreeNode x = root;
        while (x != null) {
            y = x;
            if (k < x.getKey()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == null) {
            root = z;
        } else if (k < y.getKey()) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    private void transplant(TreeNode u, TreeNode v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        if (v != null) {
            v.p = u.p;
        }
    }

    public void delete(TreeNode z) {
        if (z.left == null) {
            transplant(z, z.right);
        } else if (z.right == null) {
            transplant(z, z.left);
        } else {
            TreeNode y = min(z.right);
            if (y.p != z) {
                transplant(y, y.left);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.right.p = y;
        }

    }

    static public class TreeNode {

        private int key;
        private TreeNode p;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }
    }


}
