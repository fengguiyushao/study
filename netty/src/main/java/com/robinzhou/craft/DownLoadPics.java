package com.robinzhou.craft;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by robinzhou on 2017/5/3.
 */
public class DownLoadPics {
    private static final String IMG_URL = "^https:.*";

    public static void downLoadPics(List<String> urlList,String title,String filePath) throws Exception {
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        for (String url : urlList) {
            if(!Pattern.matches(IMG_URL, url)) {
                continue;
            }
            String[] s = url.split("/");
            String filename = s[s.length - 1];
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(filePath + "\\" + filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
