package com.zxt.zxt_phone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by hyz on 2017/3/15.
 * powered by company
 */

public class SharedPrefsUtil {

    /**
     * 数据存储的xml名称
     */

    private static final String SHARED_PATH = "zxt_phone";

    public static final String APK_VERSION = "apk_version";//apk版本 默认值是""
    public static final String WIFI_DOWNLOAD_SWITCH = "wifi_download_switch";//apk版本 默认值是""
    public static final String KEY = "download_ID";


    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
//        if(!TextUtils.isEmpty(value)){
//            edit.putString(key, pbe.encrypt(value));
//        }else{
//            edit.putString(key,null);
//        }
        edit.putString(key,value);
        edit.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        String value = sharedPreferences.getString(key, " ");
//        if(!TextUtils.isEmpty(value)){
//            value = pbe.decrypt(value);
//        }
        return sharedPreferences.getString(key,value);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defValue);
    }
}
