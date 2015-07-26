package com.robinzhou.tree;

import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by N550 on 2015/7/23.
 */
public class BSTTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    int[] array = {4, 65, 5, 96, 43, 13, 76, 49, 67, 34, 11, 5, 78, 65};
    Integer[] sorted = {4, 5, 5, 11, 13, 34, 43, 49, 65, 65, 67, 76, 78, 96};
    public BST bst;

    @Before
    public void setUp() throws Exception {
        bst = new BST(array);
    }

    @Test
    public void testInorderTreeWalk() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bst.inorderTreeWalk();
        Assert.assertEquals(Joiner.on("\r\n").join(sorted), outContent.toString().trim());
    }

    @Test
    public void testSearch() throws Exception {
        Assert.assertEquals(null, bst.search(3));
        Assert.assertEquals(5, bst.search(5).getKey());
    }

    @Test
    public void testIterativeSearch() throws Exception {
        Assert.assertNull(bst.iterativeSearch(3));
        Assert.assertEquals(5, bst.iterativeSearch(5).getKey());
    }

    @Test
    public void testMin() throws Exception {
        Assert.assertEquals(4, bst.min().getKey());
    }

    @Test
    public void testMax() throws Exception {
        Assert.assertEquals(96, bst.max().getKey());

    }

    @Test
    public void testSuccessor() throws Exception {
        Assert.assertEquals(49, bst.successor(bst.iterativeSearch(43)).getKey());
    }

    @Test
    public void testInsert() throws Exception {
        bst.insert(33);
        bst.insert(66);
        bst.insert(99);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bst.inorderTreeWalk();
        Integer[] newAray = {4, 5, 5, 11, 13, 33, 34, 43, 49, 65, 65, 66, 67, 76, 78, 96, 99};
        Assert.assertEquals(Joiner.on("\r\n").join(newAray), outContent.toString().trim());
    }

    @Test
    public void testDelete() throws Exception {
        bst.delete(bst.search(5));
        bst.delete(bst.search(49));
        bst.delete(bst.search(96));
        bst.delete(bst.search(67));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bst.inorderTreeWalk();
        Integer[] newAray = {4, 5, 11, 13, 34, 43, 65, 65, 76, 78};
        Assert.assertEquals(Joiner.on("\r\n").join(newAray), outContent.toString().trim());
    }
}