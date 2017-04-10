package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.LogUtil;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by miliang on 2017/3/6/0006.
 */

public class LoginActivity extends BaseActivity {

    private String TAG = LoginActivity.class.getCanonicalName();
    @BindView(R.id.return_back)
    TextView returnBack;
    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.etUserId)
    EditText etUserId;
    @BindView(R.id.etPwd)
    EditText etPwd;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.forget_password)
    TextView forgetPassword;

    @BindView(R.id.chb_tv)
    CheckBox checkBox;

    String mUserId, mPwd, mCode;

    String status;
    CookieJar cookieJar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_manager_activity);

        initView();

    }

    private void initView() {
        tabName.setText(R.string.login_title);
        etUserId.setHintTextColor(getResources().getColor(R.color.seashell));
        etPwd.setHintTextColor(getResources().getColor(R.color.seashell));

        returnBack.setVisibility(View.GONE);
        forgetPassword.setText(Html.fromHtml("<u>" + getResources().getString(R.string.login_forget) + "</u>"));
    }

    @OnClick({R.id.forget_password, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password:
                //忘记密码
                break;
            case R.id.btnLogin:
                //登录按钮
                if (verification()) {
                    login();
                }
                break;
        }
    }


    private boolean verification() {
        mUserId = etUserId.getText().toString().trim();
        mPwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mUserId)
                || TextUtils.isEmpty(mPwd)) {
            toast("用户名或密码不能为空");
            return false;
        }
        return true;
    }

    private void login() {

//        http://192.168.1.220:8080/grid/app/user/login.do?userName=WGY&password=123
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mUserId);
        params.put("password", mPwd);
//        params.put("gridStaff", Common.IS_LOGIN+"");

        OkHttpUtils.post()
                .url(Url.URL_WG + "user/login.do?")
//                .url("http://192.168.1.220:8080/grid/app/user/login.do?")
                .params(params)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
//            showLoading("正在登录中...");
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
//            dismissLoading();
            Log.e(TAG, "onResponse：complete");
            Log.i(TAG, "onResponse===" + response);
            AppData.isLogin = true;
            try {
                JSONObject obj = new JSONObject(response);
                if ("200".equals(obj.getString("status"))) {
                    //请求区域session
                    runnable.run();

                    toast(obj.getString("message"));
                    SharedPrefsUtil.putString(mActivity, "dept", obj.getString("dept"));

                    //获取cookie中的sessionId值 用于注入webView
                    CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
                    HttpUrl httpUrl = HttpUrl.parse(Url.URL_WG + "user/login.do?");
                    List<Cookie> cookies = cookieJar.loadForRequest(httpUrl);

                    AppData.Cookie=cookies.get(0).toString();
                    Log.i("TAG","--------------"+httpUrl.host() + "对应的cookie如下：" + cookies.toString());

                    startActivity(new Intent(mActivity, WsbsActivity.class)
                            .putExtra("dept", obj.getString("dept")));


                    finish();
                } else {
                    toast(obj.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }

    private final int SEND_TIME = 99;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_TIME:
                   //请求
                    getAirId();
                    break;

            }
        }
    };

    private void getAirId() {
        OkHttpUtils.get().url(Url.URL_WG+"user/saveUserAreaSession.do?").build().execute(null);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(SEND_TIME);
        }
    };

}
