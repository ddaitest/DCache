package com.sunny.net;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DHttpClient {

    public void downloadInFile(String urlString, File file, Context context)
            throws IOException {
        /**
         * Workaround for bug pre-Froyo, see here for more info: http://android
         * -developers.blogspot.com/2011/09/androids-http-clients.html
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
        HttpURLConnection urlConnection = null;
        FileOutputStream out = null;
        File cacheFile = file;
        if (!cacheFile.exists()) {
            cacheFile.getParentFile().mkdirs();
        }
        URL url;
        InputStream is = null;
        try {
            url = new URL(urlString);
            java.net.Proxy proxy = ProxyUtil.getProxy(context);
            urlConnection = (proxy != null ? (HttpURLConnection) url
                    .openConnection(proxy) : (HttpURLConnection) url
                    .openConnection());
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            is = urlConnection.getInputStream();
            out = new FileOutputStream(cacheFile);
            byte[] buffer = new byte[8 * 1024];
            int b = -1;
            while ((b = is.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        cacheFile.setLastModified(System.currentTimeMillis());
    }

    public InputStream downloadInMemory(String urlString, Context context)
            throws IOException {
        /**
         * Workaround for bug pre-Froyo, see here for more info: http://android
         * -developers.blogspot.com/2011/09/androids-http-clients.html
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
        HttpURLConnection urlConnection = null;
        URL url;
        InputStream is = null;
        try {
            url = new URL(urlString);
            java.net.Proxy proxy = ProxyUtil.getProxy(context);
            urlConnection = (proxy != null ? (HttpURLConnection) url
                    .openConnection(proxy) : (HttpURLConnection) url
                    .openConnection());
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            is = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return is;
    }


}
