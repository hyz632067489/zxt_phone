package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.view.zwfw.wggl.GzrzActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工作管理
 */
public class GzglActivity extends BaseActivity {

    private String TAG = GzglActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzgl);

        initView();

    }


    private void initView() {

        tabName.setText(R.string.gzgl);

    }

    @OnClick({R.id.gzrz_Layout,R.id.sjcl_Layout})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.gzrz_Layout://工作日志
                startActivity(new Intent(mActivity, GzrzActivity.class));
            break;
            case R.id.sjcl_Layout://时间处理

                break;
        }
    }
}