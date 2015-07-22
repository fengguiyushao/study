package com.robinzhou.sort;

/**
 * Created by N550 on 2015/7/8.
 */
public class PriorityQueue {
    private int[] a;

    private int queueSize;

    public PriorityQueue(int[] a) {
        this.a = a;
        this.queueSize = a.length;
        HeapSort.buildMaxHeap(a);
    }

    public int heapMaximum() {
        return a[0];
    }

    public int heapExtractMax() {
        queueSize--;
        int max = a[0];
        a[0] = a[queueSize];
        a[queueSize] = max;
        HeapSort.maxHeapify(a, queueSize - 1, 0);

        if (queueSize == a.length / 2) {
            reduceQueueSize();
        }

        return max;

    }

    public void heapIncreaseKey(int i, int key) {
        a[i] = key;
        while (i != 0 && a[HeapSort.parent(i)] < a[i]) {
            int tmp = a[i];
            a[i] = a[HeapSort.parent(i)];
            a[HeapSort.parent(i)] = tmp;
            i = HeapSort.parent(i);
        }
    }

    public void insert(int key) {
        if (queueSize == a.length) {
            addQueueSize();
        }
        a[queueSize] = Integer.MIN_VALUE;
        heapIncreaseKey(queueSize, key);

        queueSize++;
    }

    private void addQueueSize() {
        int[] tmp = new int[queueSize * 2];
        System.arraycopy(a, 0, tmp, 0, queueSize);
        a = tmp;
    }

    private void reduceQueueSize() {
        int[] tmp = new int[queueSize];
        System.arraycopy(a, 0, tmp, 0, queueSize);
        a = tmp;
    }
}
