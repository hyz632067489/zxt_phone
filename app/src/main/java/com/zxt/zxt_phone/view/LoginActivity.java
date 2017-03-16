package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.UserBean;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.forgetPwd)
    TextView forgetPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;

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
        returnBack.setVisibility(View.GONE);
    }

    @OnClick({R.id.forgetPwd, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetPwd:
                //忘记密码
                break;
            case R.id.btnLogin:
                //登录按钮
                if (verification()) {
//                    login();
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
