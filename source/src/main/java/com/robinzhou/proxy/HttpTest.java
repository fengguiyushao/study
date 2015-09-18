package com.robinzhou.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by robinzhou on 2015/9/17.
 */
public class HttpTest {

    public static void getHTML(String proxyHost) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://www.google.com/");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost + ".h.xduotai.com", 25933));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setConnectTimeout(1000);
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
    }

    //bksqca.h.xduotai.com
    public static void main(String[] args) throws Exception {
//        for (int a1 = 97; a1 <= 122; a1++) {
//            for (int a2 = 97; a2 <= 122; a2++) {
                for (int a3 = 97; a3 <= 122; a3++) {
                    for (int a4 = 97; a4 <= 122; a4++) {
                        for (int a5 = 97; a5 <= 122; a5++) {
                            for (int a6 = 97; a6 <= 122; a6++) {
                                StringBuilder builder = new StringBuilder();
                                builder.append('b').append('k').append((char)a3).append((char)a4).append((char)a5).append((char)a6);
//                                builder = new StringBuilder("bksqca");
                                try{
                                    getHTML(builder.toString());
                                }catch (Exception e) {
                                    System.out.println("fail>>>>>>" + builder);
                                    continue;
                                }
                                System.out.println("success>>>>>>" + builder);
                            }
                        }
                    }
                }
//            }
//        }

    }
}
