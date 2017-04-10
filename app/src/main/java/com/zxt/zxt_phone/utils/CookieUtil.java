package com.zxt.zxt_phone.utils;

import android.webkit.CookieManager;

import com.zxt.zxt_phone.bean.AppData;

/**
 * Created by hyz on 2017/4/7.
 * powered by company
 */

public   class CookieUtil {

    public static void setCookies(String url){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, AppData.Cookie);
    }
}
