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

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.UserInfo;
import com.zxt.zxt_phone.utils.PermissionsChecker;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

/**
 * Created by hyz on 2017/3/16.
 * powered by company
 */

public class WelcomeActivity extends BaseActivity {

    private  String TAG = WelcomeActivity.class.getCanonicalName();


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int key = msg.what;
            switch (key){
                case 0:
                    if( " ".equals(SharedPrefsUtil.getString(mActivity, "Key"))) {

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

        if(Build.VERSION.SDK_INT >=23){
//            checkPermission();
        }

        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"Key"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"TVInfoId"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"DeptId"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"Address"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"UserId"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"RealName"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"Mobile"));
        Log.i(TAG,"USERNAME=="+SharedPrefsUtil.getString(mActivity,"JobTel"));


        getData();


    }





    public void getData() {
        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
