package com.zxt.zxt_phone.view.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class LoginShopActivity extends BaseActivity {


    private String TAG = LoginShopActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.return_back)
    TextView return_back;

    @OnClick(R.id.return_back)
    public void onViewClicke(View view) {
        MLog.i(TAG, "onViewClicke");
        setResult(RESULT_OK); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法
    }

    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        MLog.i(TAG, "onBackPressed()");
        setResult(RESULT_OK); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法

        super.onBackPressed();
    }

    @BindView(R.id.et_login_username)
    EditText etUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView ivUsernameDel;
    @BindView(R.id.et_login_pwd)
    EditText etPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView ivPwdDel;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvForgetPwd;

    @BindView(R.id.et_code)
    EditText etLoginCode;
    @BindView(R.id.iv_code)
    ImageView ivCodeImage;


    String logName, logPwd;

    private final int registCode = 100;
    private final int forgetCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_shop);
        ButterKnife.bind(this);

        //获取验证码
        getCode();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initView();
    }

    private void initView() {
        tabName.setText("商城登录");
    }

    private void setData() {

        MLog.i(TAG, "setData");

        String url = "http://192.168.1.223:8080/shopping/api/verify_code.htm?code=4325";
        OkHttpUtils.get()
                .url(url)
                .build()
//                .execute(new BitmapCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        MLog.i(TAG,"onError"+e);
//                    }
//
//                    @Override
//                    public void onResponse(Bitmap response, int id) {
//                        MLog.i(TAG, "response==" + response);
//                    }
//                });
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        if ("false".equals(response.toString())) {
                            toast("验证码错误");
                        } else {
                            toast("发送登录");
                        }
                    }

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case registCode:
                MLog.i(TAG, "regist===");

                getCode();
                etLoginCode.setText("");


                break;
            case forgetCode:

                break;
        }

    }

    @OnClick({R.id.iv_code, R.id.bt_login_submit, R.id.bt_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_code://验证
                getCode();
                break;
            case R.id.bt_login_submit://登录
                if (verification()) {
                    LoginShop();
                }

                break;
            case R.id.bt_login_register://注册

                startActivityForResult(new Intent(mActivity, RegistShopActivity.class), registCode);
                break;
        }
    }

    private boolean verification() {
        logName = etUsername.getText().toString().trim();
        logPwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(logName)
                || TextUtils.isEmpty(logPwd)) {
            toast("用户名或密码不能为空");
            return false;
        }
//        mCode = mEtCode.getText().toString().trim();
//        if (TextUtils.isEmpty(mCode)) {
//            toast("请输入验证码");
//            return false;
//        }
        return true;
    }

    /**
     * 登录商铺
     */
    private void LoginShop() {

        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.URL_SHOP + "api/shopping_login.htm?")
                .addParams("userName", etUsername.getText().toString())
                .addParams("password", etPwd.getText().toString())
                .addParams("code", etLoginCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                        closeProgressDialog();
                        //验证码过期，自动刷新验证码

                        getCode();
                        etLoginCode.setText("");

//                        if("000046".equals(getCode())){
//                            imageCodeLoader.loadImageCode();
//                            mEtCode.setText("");
//                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "LoginShop==" + response);
                        closeProgressDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {



                                //获取cookie中的sessionId值 用于注入webView
                                CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
                                HttpUrl httpUrl = HttpUrl.parse(Url.URL_SHOP + "api/shopping_login.htm?");
                                List<Cookie> cookies = cookieJar.loadForRequest(httpUrl);
                                AppData.Cookie = cookies.get(0).toString();
                                MLog.i("TAG", " AppData.Cookie--------------"  +  AppData.Cookie);
                                MLog.i("TAG", "--------------" + httpUrl.host() + "对应的cookie如下：" + cookies.toString());
//                                SharedPrefsUtil.putString(mActivity,"userShopName",);


                                JSONObject obj1 = new JSONObject(obj.getString("data"));
                                if(!"".equals(obj1.getString("userName"))){
                                    SharedPrefsUtil.putString(mActivity,"userNameShop",obj1.getString("userName"));
                                    AppData.isLoginShop = SharedPrefsUtil.getString(mActivity,"userNameShop");
                                }

                                mActivity.setResult(RESULT_OK);

                                mActivity. finish();
                                toast(obj.getString("msg"));
                            } else {
                                getCode();
                                etLoginCode.setText("");

                                toast(obj.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 获取验证码
     */
    private void getCode() {

//        http://192.168.1.223:8080/shopping/verify_code.htm?code=*

        OkHttpUtils.get()
                .url(Url.URL_SHOP + "verify.htm")
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        MLog.i(TAG, "checkCode==" + response);
                        if (response != null) {
                            ivCodeImage.setImageBitmap(response);
                        }

                    }
                });
    }

    /**
     * 检测验证码
     */
    private void checkCode() {

//     http://192.168.1.223:8080/shopping/verify_code.htm?code=*

        OkHttpUtils.get()
                .url(Url.URL_SHOP + "verify_code.htm")
                .addParams("code", etLoginCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "checkCode==" + response);
                        if ("false".equals(response.toString())) {
                            toast("请输入正确的验证码");
                            etLoginCode.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            LoginShop();
                        }

                    }
                });
    }

}
