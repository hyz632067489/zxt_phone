package com.zxt.zxt_phone.utils;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by hyz on 2017/4/6.
 * powered by company
 */

public abstract class StringCallBack1 extends Callback<String> {



    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {

       Headers headers = response.headers();

        int i = 0;
        while (i < headers.size()) {

            LogUtil.e("cookie=="+headers.name(i) + ":  " + headers.value(i));
            i++;
        }
        LogUtil.e("============================");
        return response.body().string();
    }
}
