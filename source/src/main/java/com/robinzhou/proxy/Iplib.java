package com.robinzhou.proxy;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by robinzhou on 2016/9/22.
 */
public class Iplib {

    public static void main(String[] args) throws Exception {

        Set<String> titleSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:/ipData/title.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                titleSet.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new FileReader("D:/ipData/ad-iplib-youku"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] tmp = line.split("\t");
                String[] area = tmp[2].split("/");
                for (int i = area.length - 1; i >= 0; i--) {
                    if(area[i].equals("未知")) continue;
                    if(!titleSet.contains(area[i])){
                        System.out.println(line);
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
