package com.zxt.zxt_phone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class WsbsActivity extends BaseActivity {

    private final String TAG = WsbsActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.ltjd_Layout)
    LinearLayout ltjdLayout;
    @BindView(R.id.jcsu_Layout)
    LinearLayout jcsuLayout;
    @BindView(R.id.tjfx_Layout)
    LinearLayout tjfxLayout;
    @BindView(R.id.gzgl_Layout)
    LinearLayout gzglLayout;
    @BindView(R.id.rkgl_Layout)
    LinearLayout rkglLayout;
    @BindView(R.id.txl_Layout)
    LinearLayout txlLayout;


    private Intent mIntent;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WsbsActivity.class);
//        intent.putExtra("param1",data1);
//        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wsbs_activity);

        getData();
        initView();


    }

    private void getData() {
        OkHttpUtils.get()
                .url(Url.URL_WG+"echarts-barData.html")
//                .addParams()
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i(TAG,"response===="+response);
                    }
                });
    }

    private void initView() {
        tabName.setText(R.string.wggl);
    }


    @OnClick({R.id.ltjd_Layout, R.id.jcsu_Layout, R.id.tjfx_Layout, R.id.gzgl_Layout, R.id.rkgl_Layout, R.id.txl_Layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ltjd_Layout:
                toast("点击了龙塔街道");
                LtjdActivity.actionStart(mActivity);
                break;
            case R.id.jcsu_Layout:
                toast("点击了基础数据");
                break;
            case R.id.tjfx_Layout:
                toast("点击了统计分析");
                break;

            case R.id.gzgl_Layout:
                toast("点击了工作管理");

                break;
            case R.id.rkgl_Layout:
                toast("点击了人口管理");
                break;
            case R.id.txl_Layout:
                toast("点击了通讯录");
                mIntent = new Intent(mActivity,LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}

