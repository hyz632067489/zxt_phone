package com.zxt.zxt_phone.view.zwfw.dqfc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.ZyzfwModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SjxxActivity extends BaseActivity {


    private String TAG = SjxxActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.name_layout)
    LinearLayout nameLayout;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.phone_layout)
    LinearLayout phoneLayout;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.deptName)
    TextView deptName;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.mtitle)
    EditText mtitle;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.rb_dwgs)
    RadioButton rbDwgs;
    @BindView(R.id.rb_bdwgs)
    RadioButton rbBdwgs;
    @BindView(R.id.rg_gs)
    RadioGroup rgGs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjxx);
        ButterKnife.bind(this);

        getTypeData();

    }



    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        tabName.setText(R.string.zwfw_sjxx);
    }


    @OnClick({R.id.typeLayout, R.id.deptLayout, R.id.time_Layout, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.typeLayout:
//                new AlertDialog.Builder(this)
//                        .setTitle("请选择")
//                        .setIcon(android.R.drawable.ic_dialog_info)
//                        .setSingleChoiceItems(OpinionClassName, checkedItem,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        checkedItem = which;
//                                        typeName.setText("" + listDatas.get(which).getOpinionClassName());
//                                        dialog.dismiss();
//                                    }
//                                }
//                        ).show();
                break;
            case R.id.deptLayout:
//                new AlertDialog.Builder(this)
//                        .setTitle("请选择")
//                        .setIcon(android.R.drawable.ic_dialog_info)
//                        .setSingleChoiceItems(DeptName, ocheckedItem,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ocheckedItem = which;
//                                        deptName.setText("" + listNames.get(which).getDeptName());
//                                        dialog.dismiss();
//                                    }
//                                }
//                        ).show();
                break;
            case R.id.time_Layout:
                break;
            case R.id.submit_btn:
                break;
        }
    }

    /**
     * 获取接收人跟事项
     */
    private void getTypeData() {


        HashMap<String ,String > params1 = new HashMap<>();
        params1.put("method","docket");
        params1.put("DeptId",SharedPrefsUtil.getString(mContext,"DeptId"));
        params1.put("Key",SharedPrefsUtil.getString(mContext,"Key"));
        params1.put("TVInfoId","TVInfoId");

        //获取事项
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=1=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
//                                typeDepartment = new Gson().fromJson(response, TypeDepartment.class);
//
//                                OpinionClassName = new String[typeDepartment.getData().size()];
//                                OpinionClassId = new String[typeDepartment.getData().size()];
//
//                                for (int i = 0; i < OpinionClassName.length; i++) {
//
//                                    OpinionClassName[i] = typeDepartment.getData().get(i).getOpinionClassName();
//                                    OpinionClassId[i] = String.valueOf(typeDepartment.getData().get(i).getOpinionClassId());
//                                }
//                                listDatas.addAll(typeDepartment.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });



        //获取接收人
        HashMap<String ,String > params2 = new HashMap<>();
        params2.put("method","secretary");
        params2.put("DeptId",SharedPrefsUtil.getString(mContext,"DeptId"));
        params2.put("Key",SharedPrefsUtil.getString(mContext,"Key"));
        params2.put("TVInfoId","TVInfoId");

        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=2=" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
//                                deptNameModel = new Gson().fromJson(response, DeptNameModel.class);
//
//                                DeptName = new String[deptNameModel.getData().size()];
//                                Deptid = new String[deptNameModel.getData().size()];
//
//                                for (int i = 0; i < DeptName.length; i++) {
//
//                                    DeptName[i] = deptNameModel.getData().get(i).getDeptName();
//                                    Deptid[i] = String.valueOf(deptNameModel.getData().get(i).getDeptid());
//                                }
//                                listNames.addAll(deptNameModel.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
