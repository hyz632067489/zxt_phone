package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.UserBean;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
        if(checkBox.isChecked()){
           Common.IS_LOGIN = true;
        }else {
            Common.IS_LOGIN = false;
        }
        return true;
    }

    private void login() {
//        http://192.168.1.220:8080/grid/app/user/login.do?userName=WGY&password=123
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mUserId);
        params.put("password", mPwd);
        params.put("gridStaff", Common.IS_LOGIN+"");
        OkHttpUtils.post()
                .url(Url.URL_WG+"user/login.do?")
//                .url("http://192.168.1.220:8080/grid/app/user/login.do?")
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Log.i(TAG,"onError==="+e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG,"onResponse==="+response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if("200".equals(obj.getString("status"))){
                        toast(obj.getString("message"));
                        SharedPrefsUtil.putString(mActivity,"dept",obj.getString("dept"));
                        startActivity(new Intent(mActivity,WsbsActivity.class)
                                .putExtra("dept",obj.getString("dept")));
                        finish();
                    }else {
                        toast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    private void login() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("userName", mUserId);
//        params.put("password", mPwd);
////        http://192.168.1.125:8080/grid/user/validatesUser.do?userName=admin&password=123
//        String url = Url.BASE_URL + Url.login + "userName=" + mUserId + "&" + "password=" + mPwd;
//        HttpUtil.sendOkHttpRequest(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                dismissLoading();
//                String responseText = response.body().string();
//                UserBean stu;
//                String data;
//                try {
//                    JSONObject jsonObject = new JSONObject(responseText);
//                    status =  jsonObject.getString("status");
//                    if(Common.SUCCEED.equals(status)){
//                        Gson gson = new Gson();
//                        // 将json 转化成 java 对象
//                        //fromJson方法。参数一是json字符串。参数二是要转换的javabean
//                        //该javabean的字段名必须与json的key名字完全对应才能被正确解析。
//                          data = jsonObject.getString("data");
//                        Log.i(TAG, "userName="+data.getClass().toString());
//                          stu = gson.fromJson(data, UserBean.class);
//
//
//                        Log.i(TAG, "======================"+stu.toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                Log.i(TAG, responseText.toString());
//
//            }
//        });
//    }


}
