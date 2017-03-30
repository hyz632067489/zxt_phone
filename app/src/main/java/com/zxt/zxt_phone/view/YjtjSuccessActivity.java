package com.zxt.zxt_phone.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

public class YjtjSuccessActivity extends BaseActivity {

    @BindView(R.id.tab_name)
    TextView tabName;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yjtj_success);

        tabName.setText("提交成功");
    }
}
