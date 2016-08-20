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

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("D://result");

        Files.lines(Paths.get("D://宝洁鹰眼内容字典表-2016-8-2(2).csv")).forEach(s -> {
            s = s.replace("，","");
            String []tmp = s.split(",");
            if(tmp[3].equals("用户上传")){
                tmp[3] = "综艺";
                System.out.println(111);
            }
            writer.println(Joiner.on(",").join(tmp));
        });
        writer.close();

    }

}
