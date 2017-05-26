package com.zxt.zxt_phone.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PersonalSettingActivity extends BaseActivity {

    private String TAG = PersonalSettingActivity.class.getCanonicalName();


    @BindView(R.id.tab_name)
    TextView tabName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        tabName.setText("个人设置中心");
    }

    @OnClick({R.id.changePwd, R.id.switchPasswrod, R.id.notice, R.id.about, R.id.btnOutLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changePwd:
                break;
            case R.id.switchPasswrod:
                break;
            case R.id.notice:
                break;
            case R.id.about:
                break;
            case R.id.btnOutLogin:  //退出登录

                setOutLogin();

                break;
        }
    }

    private void setOutLogin() {
//        http://192.168.1.223:8080/shopping//api/shopping_logout.htm
        OkHttpUtils.get()
                .url(Url.URL_SHOP + "api/shopping_logout.htm")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        AppData.isLoginShop="";
                        SharedPrefsUtil.putString(mActivity,"userNameShop","");

                        startActivity(new Intent(mActivity, LoginShopActivity.class));
                        mActivity.finish();
                    }
                });
    }
}
