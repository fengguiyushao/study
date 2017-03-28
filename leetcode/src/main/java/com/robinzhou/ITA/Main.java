package com.robinzhou.ITA;

import com.google.common.base.Joiner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Created by robinzhou on 2016/5/25.
 */
public class Main {

    public static void main (String[] args) throws java.lang.Exception
    {
        UnionFindSet ufs = new UnionFindSet(10);
        ufs.union(1, 2);
        ufs.union(3, 4);
        ufs.union(5, 6);
        ufs.union(3, 6);
        ufs.union(7, 9);
        System.out.println(ufs.count());
        System.out.println(ufs.find(6));
        System.out.println(ufs.connected(4, 5));
        System.out.println(ufs.connected(8, 9));
    }
}
class UnionFindSet
{
    private int[] set;
    private int[] size;
    private int count;

    public UnionFindSet(int n)
    {
        count = n;
        set = new int[n];
        size = new int[n];
        for (int i=0; i<n; ++i)
        {
            set[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q)
    {
        int x = find(p);
        int y = find(q);
        if (x == y)
        {
            return;
        }
        if (size[x] < size[y])
        {
            set[x] = y;
            size[y] += size[x];
        }
        else
        {
            set[y] = x;
            size[x] += size[y];
        }
        count--;
    }
    public int find(int p)
    {
        while (p != set[p])
        {
            p = set[p];
        }
        return p;
        /*// path compression
         if (p != set[p])
         {
             set[p] = find(set[p]);
         }
         return set[p];
         */
    }
    public boolean connected(int p, int q)
    {
        return find(p) == find(q);
    }
    public int count()
    {
        return count;
    }

}
