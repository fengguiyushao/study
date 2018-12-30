package com.robinzhou.ITA;

import com.google.common.collect.Lists;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PicTest {

    public static void main(String[] args) throws IOException {
        File dir = new File("D://pic");

        File[] files=dir.listFiles();

        for (File file : files) {
            aaa(file);
        }


    }

    public static void aaa(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                aaa(file1);
            }
            return;
        }

        if (!file.getName().toLowerCase().endsWith("jpg")) {
            return;
        }

        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long size = file.length();
        double quality = 100 * 1024d / size;
        if (quality <= 0d || quality >= 1d) {
            quality = 0.3d;
        }
        Thumbnails.of(file.getAbsolutePath())
                .scale(1f)
                .outputQuality(0.3f)
                .toFile(file.getAbsolutePath());
    }
}
