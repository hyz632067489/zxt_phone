package com.zxt.zxt_phone.view.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zxt.zxt_phone.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class TextViewLayout extends LinearLayout {
    public TextViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.text_view_layout, this);
    }
}
