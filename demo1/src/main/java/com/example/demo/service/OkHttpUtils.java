package com.example.demo.service;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * okhttp请求
 */
public class OkHttpUtils {

    private static Logger LOG = Logger.getLogger(OkHttpUtils.class);
    private static OkHttpClient client = new OkHttpClient();

    static {
        //超时配置
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 普通的get方法
     *
     */
    public static String get(String url) {
        return getMethod(url);
    }

    private static String getMethod(String url) {
        Long start = System.currentTimeMillis();
        String result = null;
        Request request = new Request.Builder().url(url).build();
        try {
            result = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            LOG.error("调用异常:url:" + url);
            e.printStackTrace();
        } finally {
            Long end = System.currentTimeMillis();
            LOG.info("开始时间：" + start + " 结束时间：" + end + " 此次调用时长为" + (end - start));
        }
        return result;
    }
}
