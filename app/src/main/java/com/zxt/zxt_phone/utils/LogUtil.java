package com.zxt.zxt_phone.utils;

import android.util.Log;

/**
 * 作者: Idacf ,时间: 2016/5/12.14:47
 * 类说明:
 */
public class LogUtil {

    public static final boolean isDebug = false;//log日子开关
    public static void e(String message) {
        if (isDebug) {
            Log.e("idacf", message);
        }
    }



}
