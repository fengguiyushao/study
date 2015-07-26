package com.robinzhou.tree;

import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by N550 on 2015/7/26.
 */
public class RBSearchTreeTest {

    int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
    Integer[] sorted = {4, 5, 5, 11, 13, 34, 43, 49, 65, 65, 67, 76, 78, 96};
    public RBSearchTree rbTree;

    @Before
    public void setUp() throws Exception {
        rbTree = new RBSearchTree(array);
    }

    @Test
    public void testInorderTreeWalk() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        rbTree.inorderTreeWalk();
        Assert.assertEquals(Joiner.on("\r\n").join(sorted), outContent.toString().trim());
    }

    @Test
    public void testSearch() throws Exception {
        Assert.assertEquals(null, rbTree.search(3));
        Assert.assertEquals(5, rbTree.search(5).getKey());
    }

    @Test
    public void testIterativeSearch() throws Exception {
        Assert.assertNull(rbTree.iterativeSearch(3));
        Assert.assertEquals(5, rbTree.iterativeSearch(5).getKey());
    }

    @Test
    public void testMin() throws Exception {
        Assert.assertEquals(4, rbTree.min().getKey());
    }

    @Test
    public void testMax() throws Exception {
        Assert.assertEquals(96, rbTree.max().getKey());

    }

    @Test
    public void testSuccessor() throws Exception {
        Assert.assertEquals(49, rbTree.successor(rbTree.iterativeSearch(43)).getKey());
    }

    @Test
    public void testInsert() throws Exception {
        rbTree.insert(33);
        rbTree.insert(66);
        rbTree.insert(99);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        rbTree.inorderTreeWalk();
        Integer[] newAray = {4, 5, 5, 11, 13, 33, 34, 43, 49, 65, 65, 66, 67, 76, 78, 96, 99};
        Assert.assertEquals(Joiner.on("\r\n").join(newAray), outContent.toString().trim());
    }

    @Test
    public void testDelete() throws Exception {
        rbTree.delete(rbTree.search(5));
        rbTree.delete(rbTree.search(49));
        rbTree.delete(rbTree.search(96));
        rbTree.delete(rbTree.search(67));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        rbTree.inorderTreeWalk();
        Integer[] newAray = {4, 5, 11, 13, 34, 43, 65, 65, 76, 78};
        Assert.assertEquals(Joiner.on("\r\n").join(newAray), outContent.toString().trim());
    }

    @Test
    public void testDeleteAll() throws Exception {
        for (int x : array) {
            rbTree.delete(rbTree.search(x));
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        rbTree.inorderTreeWalk();
        Integer[] newAray = {};
        Assert.assertEquals(Joiner.on("\r\n").join(newAray), outContent.toString().trim());
    }
}