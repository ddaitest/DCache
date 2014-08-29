package com.ddai.mice.manager;

import java.util.ArrayList;

/**
 * Created by ningdai on 14-8-7.
 */
public enum TestManager {
    INSTANCE;

    private static final String base = "http://daivp.com/files/480/";
    private static final String suffix = ".jpg";

    static ArrayList<Source> urls;

    TestManager() {

    }


    public static ArrayList<Source> getURL() {
        if (urls == null) {
            urls = new ArrayList<Source>();
            Source s;
            for (int i = 2; i < 390; i++) {
                s = new Source();
                s.title = i + suffix;
                s.url = base + s.title;
                urls.add(s);
            }
        }
        return urls;
    }


}
