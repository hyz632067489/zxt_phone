package com.zxt.zxt_phone.view.wyfw;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.Mobile;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class JftjAddActivity extends BaseActivity {


    private String TAG = JftjAddActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rb_dwgs)
    RadioButton rbDwgs;
    @BindView(R.id.rb_bdwgs)
    RadioButton rbBdwgs;
    @BindView(R.id.rg_gs)
    RadioGroup rgGs;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_code)
    Button btnCode;


    String code;
    int sms = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jftj_add);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        tabName.setText(R.string.wyfw_jftj_add);

        rgGs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_dwgs:
                        sms = 0;
                        break;
                    case R.id.rb_bdwgs:
                        sms = 1;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                //验证码
                if (getEmpty(1)) {
                    sendCode();
                }
                break;
            case R.id.btn_commit:
                if (getEmpty(2)) {
                    sendData();
                }
                break;
        }
    }

    private void sendCode() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "Short");
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params.put("Phone", etPhone.getText().toString());
        params.put("YZ", 1 + "");  //验证手机号
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                        MLog.i(TAG, "response==" + response);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            String status = obj.getString("Status");
                            if ("1".equals(status)) {
                                code = obj.getString("YZM");
                                toast("验证码发送成功");
                                btnCode.setEnabled(false);
                            } else {
                                toast("验证码获取失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    private void sendData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "compostadd");
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params.put("Name", etUserName.getText().toString());
        params.put("Phone", etPhone.getText().toString());
        params.put("Explanation", etContent.getText().toString());
        params.put("dispublic", sms+"");
        params.put("Deptid", SharedPrefsUtil.getString(mContext,"DeptId"));
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                        MLog.i(TAG, "response==" + response);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            String status = obj.getString("Status");
                            if ("1".equals(status)) {
                                toast("事件提交成功");
                                finish();
                            } else {
                                toast("事件提交失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    private boolean getEmpty(int check) {

        if (check == 1) {
            if ("".equals(etPhone.getText().toString())) {
                toast("请输入手机号");
                return false;
            }
            if (!Mobile.isPhoneNum(etPhone.getText().toString())) {
                toast("请输入正确的手机号");
                return false;
            }
        } else if (check == 2) {
            getEmpty(1);
            if ("".equals(etUserName.getText().toString())) {
                toast("请输入姓名");
                return false;
            }
            if ("".equals(etCode.getText().toString())) {
                toast("请输入验证码");
                return false;
            }
            if (!code.equals(etCode.getText().toString())) {
                toast("请输入正确的验证码");
                return false;
            }

            if (sms == 100) {
                toast("请选择是否对外公示");
                return false;
            }
            if ("".equals(etContent.getText().toString())) {
                toast("请输入内容");
                return false;
            }
        }

        return true;
    }
}
