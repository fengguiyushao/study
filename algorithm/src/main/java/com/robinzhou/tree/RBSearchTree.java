package com.robinzhou.tree;

/**
 * Created by robinzhou on 2015/7/26.
 */
public class RBSearchTree {

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    RBNode nil = new RBNode(0, BLACK);
    RBNode root = nil;

    public RBSearchTree(int[] arr) {
        this.build(arr);
    }

    public void build(int[] arr) {
        for (int x : arr) {
            insert(x);
        }
    }

    public void inorderTreeWalk() {
        inorderTreeWalk(root);
    }

    private void inorderTreeWalk(RBNode x) {
        if (x != nil) {
            inorderTreeWalk(x.left);
            System.out.println(x.getKey());
            inorderTreeWalk(x.right);
        }
    }

    public RBNode search(int k) {
        return search(root, k);
    }

    private RBNode search(RBNode x, int k) {
        if (x == nil) {
            return null;
        }
        if (k == x.getKey()) {
            return x;
        }
        if (k < x.getKey()) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    public RBNode iterativeSearch(int k) {
        RBNode x = root;
        while (x != nil) {
            if (k == x.getKey()) {
                return x;
            } else if (k < x.getKey()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return null;
    }

    public RBNode min() {
        return min(root);
    }

    private RBNode min(RBNode x) {
        if (x == nil) {
            return null;
        }
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    public RBNode max() {
        if (root == nil) {
            return null;
        }
        RBNode x = root;
        while (x.right != nil) {
            x = x.right;
        }
        return x;
    }

    public RBNode successor(RBNode x) {
        if (x.right != nil) {
            return min(x.right);
        }

        RBNode y = x.p;
        while (y != nil && x == y.right) {
            x = y;
            y = x.p;
        }
        return y;
    }

    public void insert(int k) {
        RBNode z = new RBNode(k, RED);
        RBNode y = nil;
        RBNode x = root;
        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == nil) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        insertFixUp(z);
    }

    private void insertFixUp(RBNode z) {
        while (z.p.color == RED) {
            if (z.p == z.p.p.left) {
                RBNode y = z.p.p.right;
                if (y.color == RED) {
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        z = z.p;
                        leftRotate(z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    rightRotate(z.p.p);
                }
            } else {
                RBNode y = z.p.p.left;
                if (y.color == RED) {
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.left) {
                        z = z.p;
                        rightRotate(z);
                    }
                    z.p.color = BLACK;
                    z.p.p.color = RED;
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = BLACK;
    }

    public void delete(RBNode z) {
        RBNode y = z;
        boolean originColor = y.color;
        RBNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = min(z.right);
            originColor = y.color;
            x = y.right;
            if (y.p == z) {
                x.p = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }
        if(originColor == BLACK) {
            deleteFixUp(x);
        }
    }

    private void deleteFixUp(RBNode x) {
        while(x != root && x.color == BLACK) {
            RBNode w;
            if(x == x.p.left) {
                w = x.p.right;
                if(w.color == RED) {
                    w.color = BLACK;
                    x.p.color = RED;
                    leftRotate(x.p);
                    w = x.p.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                }else {
                    if(w.right.color == BLACK){
                        w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.p);
                    x = root;
                }
            }else {
                w = x.p.left;
                if(w.color == RED) {
                    w.color = BLACK;
                    x.p.color = RED;
                    rightRotate(x.p);
                    w = x.p.left;
                }
                if(w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.p;
                }else {
                    if(w.left.color == BLACK){
                        w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.p);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    private void transplant(RBNode u, RBNode v) {
        if (u.p == nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }


    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != nil) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p == nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;
    }

    private void rightRotate(RBNode x) {
        RBNode y = x.left;
        x.left = y.right;
        if (y.right != nil) {
            y.right.p = x;
        }
        y.p = x.p;
        if (x.p == nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.right = x;
        x.p = y;
    }

    public class RBNode {

        private int key;
        boolean color = BLACK;
        private RBNode p;
        private RBNode left;
        private RBNode right;

        public RBNode(int key, boolean color) {
            this.key = key;
            this.color = color;
            p = nil;
            left = nil;
            right = nil;
        }

        public int getKey() {
            return key;
        }


    }
}
