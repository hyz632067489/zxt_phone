package com.zxt.zxt_phone.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.constant.AppConstant;


/**
 * 作者: Idacf ,时间: 2016/5/11.17:09
 * 类说明:
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int LayoutId) {
        super(context, R.style.dialog);
        Window window = getWindow();
        setContentView(LayoutId);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.width = (int) (AppConstant.Screen.WIDTH * 0.9);
        window.setAttributes(lp);

    }
}
