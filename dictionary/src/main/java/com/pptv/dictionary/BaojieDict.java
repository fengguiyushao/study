package com.pptv.dictionary;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by robinzhou on 2016/6/20.
 */
public class BaojieDict {
    public static void main(String[] args) throws Exception {

        Map<String, String> map = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] tmp = line.split(",");
//                if (!tmp[3].equals("电影") && !tmp[3].equals("电视剧") && !tmp[3].equals("综艺") && !tmp[3].equals("体育")) {
//                    tmp[3] = "综艺";
//                }
                if (tmp[3].equals("体育") && !tmp[4].equals("足球德甲") && !tmp[4].equals("足球英超") && !tmp[4].equals("足球欧冠"))
                    tmp[4] = "足球英超";
                if (!tmp[3].equals("体育") && (tmp[4].equals("足球英超") || tmp[4].equals("足球德甲") || tmp[4].equals("足球欧冠")))
                    tmp[3] = "体育";
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

        for (int i = 1; i < args.length; i++) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[i])), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    line = line.replace("NULL", "电视剧.内地");
                    line = replacelast(line, ".", "\t");
                    String[] tmp = line.split("\t");
                    if (!tmp[3].equals("电影") && !tmp[3].equals("电视剧") && !tmp[3].equals("综艺") && !tmp[3].equals("体育")) {
                        tmp[3] = "综艺";
                    }
                    if (tmp[3].equals("体育") && !tmp[4].equals("足球德甲") && !tmp[4].equals("足球英超") && !tmp[4].equals("足球欧冠"))
                        tmp[4] = "足球英超";
                    if (!tmp[3].equals("体育") && (tmp[4].equals("足球英超") || tmp[4].equals("足球德甲") || tmp[4].equals("足球欧冠")))
                        tmp[3] = "体育";
                    map.put(tmp[0], tmp[1].replaceAll(",|，", "") + "," + tmp[2].replaceAll(",|，", "") + "," + tmp[3] + "," + tmp[4]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PrintWriter writer = new PrintWriter("result.csv", "utf-8");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            writer.println(entry.getKey() + "," + entry.getValue().replace(" ", ""));
        }

        writer.close();

    }

    public static String replacelast(String str, String a, String b) {
        StringBuilder buf = new StringBuilder(str);
        buf.replace(str.lastIndexOf(a), str.lastIndexOf(a) + 1, b);
        return buf.toString();
    }

}
