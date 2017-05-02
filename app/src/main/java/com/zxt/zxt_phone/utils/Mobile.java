package com.zxt.zxt_phone.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by miliang on 2017/4/13/0013.
 */

public class Mobile {


    /**
     * 验证手机号
     * @param str
     * @return
     */
    public static boolean isPhoneNum(String str) {
        // 如果字符串为空，直接返回false
        String regExp = "^1[3-9]\\d{9}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(str);
        return m.find();
    }


}
