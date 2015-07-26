package com.robinzhou.tree;

/**
 * Created by N550 on 2015/7/23.
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
                    x = x.getLeft();
                } else {
                    x = x.getRight();
                }
            }
            node.setParent(p);
            if (p == null) {
                root = node;
            } else if (node.getKey() < p.getKey()) {
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
        if (x != null) {
            inorderTreeWalk(x.getLeft());
            System.out.println(x.getKey());
            inorderTreeWalk(x.getRight());
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
            return search(x.getLeft(), k);
        } else {
            return search(x.getRight(), k);
        }
    }

    public TreeNode iterativeSearch(int k) {
        TreeNode x = root;
        while (x != null && k != x.getKey()) {
            if (k < x.getKey()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
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
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    public TreeNode max() {
        if (root == null) {
            return null;
        }
        TreeNode x = root;
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x;
    }

    public TreeNode successor(TreeNode x) {
        if (x.getRight() != null) {
            return min(x.getRight());
        }

        TreeNode y = x.getParent();
        while (y != null && x == y.getRight()) {
            x = y;
            y = x.getParent();
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
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        z.setParent(y);
        if (y == null) {
            root = z;
        } else if (k < y.getKey()) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
    }

    private void transplant(TreeNode u, TreeNode v) {
        if (u == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public void delete(TreeNode z) {
        if (z.getLeft() == null) {
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            transplant(z, z.getLeft());
        } else {
            TreeNode y = min(z.getRight());
            if (y.getParent() != z) {
                transplant(y, y.getLeft());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
        }

    }

}
