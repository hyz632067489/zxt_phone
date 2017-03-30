package com.zxt.zxt_phone.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.ActivityManager;
import com.zxt.zxt_phone.view.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class BaseActivity extends FragmentActivity {

    private String TAG = BaseActivity.class.getCanonicalName();

    protected Unbinder mUnbinder;
    protected LoadingDialog mLoadingDialog;

    protected Activity mActivity;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = this;
        mContext = getApplication();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //绑定
        mUnbinder = ButterKnife.bind(this);
    }

    public void toast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示加载dialog
     *
     * @param message messageIsChanged message是否已经改变
     */

    protected void showLoading(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getApplicationContext(), message);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏
     */
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
//            mTv.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(mActivity, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(mActivity, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity(this);
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
        mUnbinder.unbind();
    }

}
