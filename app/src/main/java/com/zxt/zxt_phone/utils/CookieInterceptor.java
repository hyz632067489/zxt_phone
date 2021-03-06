package com.zxt.zxt_phone.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.view.WsbsActivity;
import com.zxt.zxt_phone.view.ZwfwActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * cookie拦截器
 */
public class CookieInterceptor implements Interceptor {

    private Context mContext;
    public CookieInterceptor(Context context){
        this.mContext = context;
    }

    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Response originalResponse = chain.proceed(originalRequest);
        //每次请求过滤一次返回值，，，如果session过期就去登录一次
        Log.i("intercept","intercept=1==="+originalResponse.code()+"---------"+originalResponse.body().string());
        if (originalResponse.code() == 600 ) {
            originalResponse.body().close();
            originalResponse.body().close();

            Log.i("intercept","getLoginRequest==========_____________"  );

            mContext.startActivity(new Intent(mContext, ZwfwActivity.class));
            SharedPrefsUtil.putString(mContext, "roleLevel", " ");
            AppData.isLogin = false;
//            Request loginRequest = getLoginRequest();
//            Response loginResponse = chain.proceed(loginRequest);
//            if (loginResponse.isSuccessful()) {
//                loginResponse.body().close();
//                return chain.proceed(originalRequest);
//            }
        }
//        Log.i("intercept","intercept=2==="+originalResponse.code()+"---------"+originalResponse.body().string());

        return chain.proceed(originalRequest);
    }

    private Request getLoginRequest() {

        Log.i("intercept","getLoginRequest===="  );
        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.addFormDataPart("userName",SharedPrefsUtil.getString(mContext,"userName"));
        builder.addFormDataPart("password",SharedPrefsUtil.getString(mContext,"password"));
        MultipartBody requestBody = builder.build();

        return new Request.Builder()
                .url(Url.URL_WG+"user/login.do?")//
                .post( requestBody )
                .build();
    }
}
