package com.zxt.zxt_phone.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.shop.LoginShopActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ModifyPasswordActivity extends BaseActivity {


    private String TAG = ModifyPasswordActivity.class.getCanonicalName();


    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.old_pasword)
    EditText oldPasword;
    @BindView(R.id.input_password)
    EditText newPassword;
    @BindView(R.id.affirm_password)
    EditText sureNewPassword;

    String oldPwd, newPwd, surePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        initView();
    }

    private void initView() {
        tabName.setText("密码修改");

    }


    @OnClick(R.id.btnOutLogin)
    public void onViewClicked() {

        if (verification()) {
            changeMima();
        }

    }

    private boolean verification() {

        oldPwd = oldPasword.getText().toString().trim();
        newPwd = newPassword.getText().toString().trim();
        surePwd = sureNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPasword.getText().toString())
                || TextUtils.isEmpty(newPwd)
                || TextUtils.isEmpty(surePwd)) {
            toast("用户名或密码不能为空");
            return false;
        }
        if (!newPwd.equals(surePwd)) {
            toast("2次密码输入不相同");
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
     * 修改密码
     */
    private void changeMima() {
//        http://192.168.1.223:8080/shopping/api/account_password_save.htm?old_password=*&new_password=*
        showProgressDialog();
        OkHttpUtils.get()
                .url(Url.URL_SHOP + "api/account_password_save.htm?")
                .addParams("old_password", oldPwd)
                .addParams("new_password", newPwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast(e.getMessage());
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {
                                AppData.isLoginShop = "";
                                SharedPrefsUtil.putString(mActivity, "userNameShop", "");

                                startActivity(new Intent(mActivity, LoginShopActivity.class));

                                setResult(RESULT_OK);
                                mActivity.finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
