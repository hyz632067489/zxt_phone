package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 述求提交
 */
public class SqtjActivity extends BaseActivity {

    private String TAG = SqtjActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    String Agreement = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqtj);
        ButterKnife.bind(this);


        initData();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_sqtjxy);


    }
    @OnClick({R.id.btn_agreed, R.id.btn_dont})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dont:
                finish();
                break;
            case R.id.btn_agreed:
                startActivity(new Intent(this, YjtjInfoActivity.class));
                finish();
                break;

        }
    }

    private void initData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("method", "OpinionAgreement");
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i(TAG, "response===" + response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!"0".equals(obj.getString("Status"))) {
                                Agreement = obj.getString("Agreement").toString();
                                tvAgreement.setText(Agreement);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}
