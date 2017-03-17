package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.UserInfo;
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
