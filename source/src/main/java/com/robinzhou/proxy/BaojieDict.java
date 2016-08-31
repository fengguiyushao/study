package com.robinzhou.proxy;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by robinzhou on 2016/6/20.
 */
public class BaojieDict {
    public static void main(String[] args) throws Exception {

        Map<String, String> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("E:/epg/201608.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] tmp = line.split(",");
                if (!tmp[3].equals("电影") && !tmp[3].equals("电视剧") && !tmp[3].equals("综艺") && !tmp[3].equals("体育")) {
                    tmp[3] = "综艺";
                }
                String str = "";
                for (int i = 1; i < tmp.length; i++) {
                    str = str + tmp[i].trim() + ",";
                }
                str = str.substring(0, str.length() - 1);
                map.put(tmp[0], str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new FileReader("E:/epg/dic"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                line = line.replace(".", "\t");
                String[] tmp = line.split("\t");
                if (!map.containsKey(tmp[0])) {
                    if (!tmp[3].equals("电影") && !tmp[3].equals("电视剧") && !tmp[3].equals("综艺") && !tmp[3].equals("体育")) {
                        tmp[3] = "综艺";
                    }
                    String area = "大陆";
                    if (tmp.length >= 5 && !tmp[4].equals("") && !tmp[4].equals("未知/其他/忽略/NA")) area = tmp[4];
                    map.put(tmp[0], tmp[1].replaceAll(",|，", "") + "," + tmp[2].replaceAll(",|，", "") + "," + tmp[3] + "," + area);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        PrintWriter writer = new PrintWriter("E:/epg/result", "UTF-8");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            writer.println(entry.getKey() + "," + entry.getValue().replace(" ", ""));
//            System.out.println(entry.getKey() + "," + entry.getValue());
        }

        writer.close();

    }

}
