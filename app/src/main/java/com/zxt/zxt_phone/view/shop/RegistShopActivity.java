package com.zxt.zxt_phone.view.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.http.ImageCodeLoader;
import com.zxt.zxt_phone.utils.MLog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegistShopActivity extends BaseActivity {


    private String TAG = RegistShopActivity.class.getCanonicalName();

    @BindView(R.id.title_relayout)
    RelativeLayout topLayout;
    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.et_username)
    EditText registName;
    @BindView(R.id.iv_username_del)
    ImageView ivUsernameDel;

    @BindView(R.id.et_pwd)
    EditText registPwd;
    @BindView(R.id.iv_pwd_del)
    ImageView ivPwdDel;

    @BindView(R.id.et_email)
    EditText registEmail;
    @BindView(R.id.iv_email_del)
    ImageView ivEmailDel;

    @BindView(R.id.et_code)
    EditText registCode;
    @BindView(R.id.iv_code_del)
    ImageView ivCodeDel;
    @BindView(R.id.iv_code)
    ImageView ivCodeImage;


    @BindView(R.id.read_xieyi)
    CheckBox readXieyi;

    String regName, rwgPwd, regEmail, regCode;

    ImageCodeLoader imageCodeLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_regist_shop);

        ButterKnife.bind(this);
        topLayout.getBackground().setAlpha(0);


        //加载验证码图片
//        imageCodeLoader = new ImageCodeLoader(ivCodeImage,Url.URL_SHOP+"verify.htm") {
//            @Override
//            protected void onError(String errMessage) {
//                toast(errMessage);
//            }
//        };
//        imageCodeLoader.loadImageCode();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initView();
    }

    private void initView() {
        tabName.setText("注册");

        getCode();

        setOnClick();
    }


    private void setTextView() {
        regName = registName.getText().toString();
        rwgPwd = registPwd.getText().toString();
        regEmail = registEmail.getText().toString();
        regCode = registCode.getText().toString();
    }

    private void setOnClick() {

        registName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registName.setTextColor(getResources().getColor(R.color.white));

                } else {

                }

            }
        });

        registPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if ("".equals(registName.getText().toString())) {
                        toast("请输入用户名");
                    } else {
                        checkUserName();
                    }

                } else {

                }

            }
        });


        registCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if ("".equals(registEmail.getText().toString())) {
                        toast("请输入邮箱");
                    } else {
                        checkEmail();
                    }

                } else {

                }

            }
        });

    }

    @OnClick({R.id.iv_code, R.id.regist_btn})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_code:
//                imageCodeLoader.loadImageCode();
                getCode();
                registCode.setText("");
                break;
            case R.id.regist_btn:
                if (TextUtils.isEmpty(regName)
                        || TextUtils.isEmpty(rwgPwd)
                        || TextUtils.isEmpty(regEmail)
                        || TextUtils.isEmpty(regCode)) {
                    toast("用户名或密码不能为空");
                    return ;
                }
                checkCode();
                break;
        }
    }

    /**
     * \
     * 注册信息
     */
    private void setRegistData() {

//        http://192.168.1.223:8080/shopping/api/register_finish.htm?userName=*&password=*&email=* &code=*

        setTextView();

        OkHttpUtils.get()
                .url(Url.URL_SHOP + "api/register_finish.htm?")
                .addParams("userName", regName)
                .addParams("password", rwgPwd)
                .addParams("email", regEmail)
                .addParams("code", regCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "setRegistData==" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {
                                Intent intent = new Intent();
                                intent.putExtra("userName",regName);
                                intent.putExtra("password",rwgPwd);
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                toast(obj.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 检测姓名是否被申请
     */
    private void checkUserName() {

        //192.168.1.223:8080/shopping/verify_username.htm?userName=*
        OkHttpUtils.get()
                .url(Url.URL_SHOP + "verify_username.htm?")
                .addParams("userName", registName.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "checkUserName==" + response);

                        if ("false".equals(response.toString())) {
                            toast("账户已被使用");
                            registName.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            MLog.i(TAG, "checkUserName====");
                        }

                    }
                });
    }

    /**
     * 检测邮箱是否被申请
     */
    private void checkEmail() {

//        http://192.168.1.223:8080/shopping/verify_email.htm?email=*

        OkHttpUtils.get()
                .url(Url.URL_SHOP + "verify_email.htm?")
                .addParams("email", regEmail)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "checkEmail==" + response);

                        if ("false".equals(response.toString())) {
                            toast("邮箱已经被注册");
                            registEmail.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            MLog.i(TAG, "checkEmail====");
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
                .addParams("code",registCode.getText().toString())
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
                            registCode.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            setRegistData();
                        }

                    }
                });
    }
}
