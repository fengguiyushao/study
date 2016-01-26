package com.robinzhou.proxy;

import com.robinzhou.common.base.Splitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by robinzhou on 2015/12/15.
 */
public class FileDeal {

    private static Map<Integer, String> data = new HashMap<>();

    public static void main(String[] args) {
        File file = new File("D:/2.fld");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                splitLine(line);
            }
            reader.close();

            TreeMap<Integer, String> sorted_map = new TreeMap<>(new KeyComparator());
            sorted_map.putAll(data);

            for (Map.Entry<Integer, String> entry : sorted_map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    private static void splitLine(String line) {
        List<String> list = Splitter.onPattern("\t").splitToList(line);
        if(!data.containsKey(Integer.valueOf(list.get(0)))) {
            data.put(Integer.valueOf(list.get(0)), list.get(1) + "\t" + list.get(2) + "\t" + list.get(3));
        }
    }
}

class KeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}
