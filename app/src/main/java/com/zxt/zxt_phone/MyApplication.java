package com.zxt.zxt_phone;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zxt.zxt_phone.utils.CookieInterceptor;
import com.zxt.zxt_phone.utils.GlideImageLoader;
import com.zxt.zxt_phone.utils.LoggerInterceptor1;
import com.zxt.zxt_phone.utils.MLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by hyz on 2017/3/6.
 * powered by company
 */

public class MyApplication extends Application {


    private Context mContext;
    private int maxImgCount = 8;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ZXingLibrary.initDisplayOpinion(this);
        initImagePicker();
        NineGridView.setImageLoader(new GlideImageLoader());

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor1("TAG",mContext))
//                .addInterceptor(new LoggerInterceptor("TAG"))
// 过滤 session过期
//                .addInterceptor(new CookieInterceptor(mContext))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        MLog.i("MyApplication","cookieJar====="+cookieJar.toString());
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
}
