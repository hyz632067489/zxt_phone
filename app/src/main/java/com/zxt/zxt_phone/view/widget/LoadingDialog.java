package com.zxt.zxt_phone.view.widget;

import android.content.Context;
import android.widget.TextView;

import com.zxt.zxt_phone.R;


/**
 * 作者: Idacf ,时间: 2016/5/18.15:02
 * 类说明:加载
 */
public class LoadingDialog extends CustomDialog {
    public TextView textView;

    public LoadingDialog(Context context, String message) {
        super(context, R.layout.dialog_loading);
        textView = (TextView) findViewById(R.id.message);
        textView.setText(message);
        setCanceledOnTouchOutside(false);
    }
}
