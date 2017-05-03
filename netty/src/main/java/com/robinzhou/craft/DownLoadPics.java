package com.robinzhou.craft;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Created by robinzhou on 2017/5/3.
 */
public class DownLoadPics {
    public static void downLoadPics(List<String> urlList,String title,String filePath) {
        System.out.println(Arrays.toString(urlList.toArray()) + "-------" + title + "---------" + filePath);
    }
}
