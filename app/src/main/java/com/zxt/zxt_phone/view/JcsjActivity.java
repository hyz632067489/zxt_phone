package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class JcsjActivity extends BaseActivity {

    @BindView(R.id.tab_name)
    TextView tabName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jcsj_activity);
        initView();
    }

    private void initView() {
        tabName.setText(R.string.jcsj);
    }
}
