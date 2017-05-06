package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by hkc on 2017/5/6.
 */

public class SuccessActivity extends BaseActivity {

    @BindView(R.id.tab_name)
    TextView tabName;

    Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yjtj_success);

        mIntent = getIntent();
        initView();
    }

    private void initView() {
        tabName.setText(mIntent.getStringExtra("title"));
    }
}