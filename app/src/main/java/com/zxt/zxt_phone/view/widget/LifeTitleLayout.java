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
        LayoutInflater.from(context).inflate(R.layout.top_navigat, this);

        TextView return_back = (TextView) findViewById(R.id.return_back);

        return_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity) getContext()).finish();
            }
        });
    }
}
