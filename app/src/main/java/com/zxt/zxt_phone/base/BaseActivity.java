package com.zxt.zxt_phone.base;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.ActivityManager;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.view.widget.LoadingDialog;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class BaseActivity extends FragmentActivity  {

    private String TAG = BaseActivity.class.getCanonicalName();

    protected Unbinder mUnbinder;
    protected LoadingDialog mLoadingDialog;

    protected Activity mActivity;
    protected Context mContext;

    public String initEndDateTime;
    protected Date curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        initView1();
    }

    private void initView1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
//        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss  ");
        curDate = new Date(System.currentTimeMillis());//获取当前时间
        initEndDateTime = formatter.format(curDate);
        MLog.i(TAG, "initEndDateTime==" + initEndDateTime + "===curDate===" + curDate);
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


    private ProgressDialog progressDialog;

    /**
     * 显示进度对话框
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
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

//    7.0权限管理
//    private OnBooleanListener onPermissionListener;
//
//    /**
//     * 权限请求     * @param permission Manifest.permission.CAMERA     * @param onBooleanListener 权限请求结果回调，true-通过  false-拒绝
//     */
//    public void permissionRequests(Activity activity, String permission, OnBooleanListener onBooleanListener) {
//        onPermissionListener = onBooleanListener;
//        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
////            Should we show an explanation ?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    Manifest.permission.READ_CONTACTS)) {
//                //权限通过
//                onPermissionListener.onClick(true);
//            } else {
//                //没有权限，申请一下
//                ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
//            }
//        } else {            //权限已有
//            if (onPermissionListener != null) {
//                onPermissionListener.onClick(true);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //权限通过
//                if (onPermissionListener != null) {
//                    onPermissionListener.onClick(true);
//                }
//            } else {
//                //权限拒绝
//                if (onPermissionListener != null) {
//                    onPermissionListener.onClick(false);
//                }
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

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
