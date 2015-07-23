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
    public void buildBST() {
        bst = new BST(array);
    }

    @Test
    public void testInorderTreeWalk() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bst.inorderTreeWalk();
        Assert.assertEquals(Joiner.on("\r\n").join(sorted), outContent.toString().trim());
    }
}