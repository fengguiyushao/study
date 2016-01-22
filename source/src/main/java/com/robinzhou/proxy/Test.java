package com.robinzhou.proxy;

import com.robinzhou.common.base.Splitter;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by robinzhou on 2015/9/21.
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {


        String fileName = "D:/result.txt";
        System.setOut(new PrintStream(new File(fileName)));

        File file = new File("D:/2.fld");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                dealData(line);
            }
            reader.close();


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

    public static void dealData(String str) throws IOException {
        List<String> data = Splitter.onPattern("\\s+").omitEmptyStrings().trimResults().splitToList(str);

        List<String> second = Splitter.on("e-").splitToList(data.get(2));

        BigDecimal num = new BigDecimal(second.get(0));
        for (int i = 0; i < Integer.valueOf(second.get(1)); i++) {
            num = num.divide(BigDecimal.valueOf(10));
        }
        if (num.compareTo(BigDecimal.valueOf(0.001)) <= 0) {
            System.out.println(str);
        }
    }
}


