package com.zxt.zxt_phone;

import android.app.Application;
import android.content.Context;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by hyz on 2017/3/6.
 * powered by company
 */

public class MyApplication extends Application {


    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
