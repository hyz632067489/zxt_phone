package com.zxt.zxt_phone.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CheckActivity extends BaseActivity {

    private String TAG = CheckActivity.class.getCanonicalName();

    @BindView(R.id.deptName)
    TextView deptName;
    @BindView(R.id.checkLayout)
    LinearLayout checkLayout;
    @BindView(R.id.btnLayout)
    LinearLayout btnLayout;

    String[] DeptName = null;
    String[] Deptid = null;
    int checkedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);

        getData();
    }


    /**
     * 获取数据
     */
    private void getData() {

        OkHttpUtils.get()
                .url(Url.URL_PT)
                .addParams("method", "Community")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }
            @Override
            public void onResponse(String response, int id) {

                try {
                    JSONObject object = new JSONObject(response);
                    if ("0".equals(object.getString("Status"))) {
                        toast(object.getString("Message"));
                    } else {
                        JSONArray jsonArray = object.getJSONArray("Data");
                        if (jsonArray.length() != 0) {
                            DeptName = new String[jsonArray.length()];
                            Deptid = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                DeptName[i] = jsonArray.getJSONObject(i).get("DeptName").toString();
                                Deptid[i] = jsonArray.getJSONObject(i).getString("Deptid");
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @OnClick({R.id.checkLayout, R.id.btnLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkLayout://点击弹出对话框选择登录地区

                new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(DeptName, checkedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkedItem = which;
                                        deptName.setText(""+DeptName[which]);

                                        dialog.dismiss();
                                    }
                                }
                        ).show();

                break;
            case R.id.btnLayout:
                if("".equals(deptName.getText().toString())){
                    toast("请选择社区");
                }else {
                    LoginUp(Deptid[checkedItem]);
                }
                break;
        }
    }

    public void LoginUp(String deptId){
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .addParams("method", "UserAppLogin")
                .addParams("DeptId",deptId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    JSONObject object = new JSONObject(response);
                    if("0".equals(object.getString("Status"))){
                        toast(object.getString("Message"));
                    }else if("1".equals(object.getString("Status"))){
                        toast(object.getString("Message"));
                        SharedPrefsUtil.putString(mActivity,"Key",object.getString("Key"));
                        SharedPrefsUtil.putString(mActivity,"TVInfoId",object.getString("TVInfoId"));
                        SharedPrefsUtil.putString(mActivity,"DeptId",object.getString("DeptId"));
                        SharedPrefsUtil.putString(mActivity,"Address",object.getString("Address"));
                        SharedPrefsUtil.putString(mActivity,"UserId",object.getString("UserId"));
                        SharedPrefsUtil.putString(mActivity,"RealName",object.getString("RealName"));
                        SharedPrefsUtil.putString(mActivity,"Mobile",object.getString("Mobile"));
                        SharedPrefsUtil.putString(mActivity,"JobTel",object.getString("JobTel"));

                        startActivity(new Intent(mActivity,MainActivity.class));
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
