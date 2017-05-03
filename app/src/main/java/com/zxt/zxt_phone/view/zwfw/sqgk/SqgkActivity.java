package com.zxt.zxt_phone.view.zwfw.sqgk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

public class SqgkActivity extends BaseActivity {

    private  String TAG = SqgkActivity.class.getCanonicalName();

@BindView(R.id.tab_name)
    TextView tabName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqgk);

        initView();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_sqgk);
    }
}
