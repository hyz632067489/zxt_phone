package com.zxt.zxt_phone.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

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
    @BindView(R.id.sign_in)
    TextView signLogin;

    @BindView(R.id.ltjd_Layout)
    LinearLayout ltjdLayout;
    @BindView(R.id.kqdk_layout)
    LinearLayout kqdkLayout;
    @BindView(R.id.wgry_layout)
    LinearLayout wgryLayout;
    @BindView(R.id.kqqj_layout)
    LinearLayout kqqjLayout;

    @BindView(R.id.jcsu_Layout)
    LinearLayout jcsuLayout;
    @BindView(R.id.tjfx_Layout)
    LinearLayout tjfxLayout;
    @BindView(R.id.gzgl_Layout)
    LinearLayout gzglLayout;
    @BindView(R.id.rkgl_Layout)
    LinearLayout rkglLayout;
    @BindView(R.id.jzbf_Layout)
    LinearLayout jzbfLayout;
    @BindView(R.id.txl_Layout)
    LinearLayout txlLayout;


    private String Dept;
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

    private void initView() {
        tabName.setText(R.string.wggl);
        Dept = SharedPrefsUtil.getString(mActivity, "dept");

        signLogin.setVisibility(View.VISIBLE);
        signLogin.setText("注销");

        if ("网格员".equals(Dept)) {
            ltjdLayout.setVisibility(View.VISIBLE);
            kqdkLayout.setVisibility(View.VISIBLE);
            kqqjLayout.setVisibility(View.VISIBLE);
            wgryLayout.setVisibility(View.VISIBLE);
            gzglLayout.setVisibility(View.VISIBLE);
            txlLayout.setVisibility(View.VISIBLE);
        } else {
            ltjdLayout.setVisibility(View.VISIBLE);
            kqdkLayout.setVisibility(View.VISIBLE);
            wgryLayout.setVisibility(View.VISIBLE);
            jcsuLayout.setVisibility(View.VISIBLE);
            tjfxLayout.setVisibility(View.VISIBLE);
            gzglLayout.setVisibility(View.VISIBLE);
            rkglLayout.setVisibility(View.VISIBLE);
            jzbfLayout.setVisibility(View.VISIBLE);
            txlLayout.setVisibility(View.VISIBLE);
        }

    }

    private void getData() {
        OkHttpUtils.get()
                .url(Url.URL_WG + "echarts-barData.html")
//                .addParams()
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i(TAG, "response====" + response);
                    }
                });
    }


    @OnClick({R.id.sign_in, R.id.ltjd_Layout,R.id.kqqj_layout, R.id.kqdk_layout, R.id.jcsu_Layout, R.id.tjfx_Layout, R.id.gzgl_Layout, R.id.wgry_layout, R.id.rkgl_Layout, R.id.jzbf_Layout, R.id.txl_Layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                toast("注销网格登录");

                new AlertDialog.Builder(this)
                        .setTitle("注销网格登录")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPrefsUtil.putString(mActivity, "dept", " ");
                                        finish();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();

                break;
            case R.id.ltjd_Layout:
                toast("点击了龙塔街道");
                LtjdActivity.actionStart(mActivity);
                break;
            case R.id.kqdk_layout: // 考勤打卡
                mIntent = new Intent(mActivity, KqdkActivityCopy.class);
                startActivity(mIntent);
                break;
            case R.id.kqqj_layout: // 请假
                mIntent = new Intent(mActivity, LevelActivity.class);
                startActivity(mIntent);
                break;
            case R.id.jcsu_Layout:
//                toast("点击了基础数据");
                mIntent = new Intent(mActivity, JcsjActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tjfx_Layout:
                toast("点击了统计分析");
                break;

            case R.id.gzgl_Layout:
//                toast("点击了工作管理");
                mIntent = new Intent(mActivity, GzglActivity.class);
                startActivity(mIntent);
                break;
            case R.id.wgry_layout:
                toast("网格人员信息");
                break;
            case R.id.rkgl_Layout:
                toast("点击了人口管理");
                break;
            case R.id.jzbf_Layout:
                toast("精准帮扶");
                break;
            case R.id.txl_Layout:
                toast("点击了通讯录");
                mIntent = new Intent(mActivity, LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}

