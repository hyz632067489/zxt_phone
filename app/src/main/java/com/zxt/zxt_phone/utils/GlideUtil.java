package com.zxt.zxt_phone.utils;

/**
 * Created by hyz on 2017/5/4.
 */

public class GlideUtil {
    private static GlideUtil instance;

    public static GlideUtil getInstance() {
        if (instance == null) {
            synchronized (GlideUtil.class) {
                if (instance == null) {
                    instance = new GlideUtil();
                }
            }
        }
        return instance;
    }
}