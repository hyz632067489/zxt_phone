package com.zxt.zxt_phone.view.wyfw;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class JftjActivity extends BaseActivity {


    private String TAG = JftjActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.sign_in)
    TextView addText;

    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_phone1)
    EditText etPhone1;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.countent)
    TextView countent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jftj);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        tabName.setText(R.string.wyfw_jftj);
        addText.setVisibility(View.VISIBLE);
        addText.setText("添加");
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, JftjAddActivity.class));
            }
        });

    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        if ("".equals(etPhone1.getText().toString())) {
            toast("请输入手机号");
            return;
        }
        if (!Mobile.isPhoneNum(etPhone1.getText().toString())) {
            toast("请输入正确的手机号");
            return;
        }


        sendDataQuery();
    }

    private void sendDataQuery() {
        showProgressDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "disputeadd");
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
//        params.put("serialnumber", etId.getText().toString());
        params.put("Phone", etPhone1.getText().toString());

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        toast("连接超时。。。");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        closeProgressDialog();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            String status = obj.getString("Status");
                            if ("1".equals(status)) {
                                countent.setText(obj.getString(""));
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
