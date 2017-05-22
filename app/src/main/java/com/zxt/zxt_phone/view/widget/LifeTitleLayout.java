package com.zxt.zxt_phone.view.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxt.zxt_phone.R;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class LifeTitleLayout extends LinearLayout {
    public LifeTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.life_title_layout, this);

    }
}
