package com.zxt.zxt_phone.view.zwfw.dqfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

public class SdxfActivity extends BaseActivity {


    private String TAG = SdxfActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdxf);


        initView();
    }

    private void initView() {
        tabName.setText(R.string.add_gzrz);
    }

}