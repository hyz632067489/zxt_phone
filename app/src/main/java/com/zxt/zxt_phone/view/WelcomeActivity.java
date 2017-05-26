package com.zxt.zxt_phone.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.bean.UserInfo;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.PermissionsChecker;
import com.zxt.zxt_phone.utils.SPUtils;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by hyz on 2017/3/16.
 * powered by company
 */

public class WelcomeActivity extends BaseActivity {

    private String TAG = WelcomeActivity.class.getCanonicalName();


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int key = msg.what;
            switch (key) {
                case 0:
                    if (" ".equals(SharedPrefsUtil.getString(mActivity, "Key"))) {

                        startActivity(new Intent(getApplicationContext(), CheckActivity.class));
                    } else {
                        startActivity(new Intent(mActivity, MainActivity.class));
                    }

                    finish();
                    break;
            }
            return true;
        }
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= 23) {
//            checkPermission();
        }

        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "Key"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "DeptId"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "Address"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "UserId"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "RealName"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "Mobile"));
        Log.i(TAG, "USERNAME==" + SharedPrefsUtil.getString(mActivity, "JobTel"));


        getData();

        AppData.isLoginShop = SharedPrefsUtil.getString(mActivity,"userNameShop");

    }


    public void getData() {

//        private static final String DOWN_APK_URL = "http://192.168.1.222:8099/api/appFile/ZxtPhone.apk";
        OkHttpUtils.get()
                .url(Url.BASE_L+"/api/GetAppVar.aspx?method=getpadvar&typeVer=3")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                MLog.i(TAG, "response=" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("1".equals(obj.getString("Status"))) {
                        SPUtils.put(mContext,SPUtils.APK_VERSION,obj.getString("VersionName"));
//                        SharedPrefsUtil.putString(mContext, SharedPrefsUtil.APK_VERSION, obj.getString("VersionCode"));

                        MLog.i(TAG, "SPUtils=" + SPUtils.get(mContext, SPUtils.APK_VERSION,""));
                    }

                    handler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        new Thread() {
//            @Override
//            public void run() {
//
//                try {
//                    Thread.sleep(2000);
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }
}
