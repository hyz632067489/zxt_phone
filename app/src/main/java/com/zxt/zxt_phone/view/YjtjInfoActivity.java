package com.zxt.zxt_phone.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.PopAdapter;
import com.zxt.zxt_phone.adapter.PopNameAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.DeptNameModel;
import com.zxt.zxt_phone.bean.model.TypeDepartment;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class YjtjInfoActivity extends BaseActivity {


    private String TAG = YjtjInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.typeLayout)
    LinearLayout typeLayout;
    @BindView(R.id.deptName)
    TextView deptName;
    @BindView(R.id.deptLayout)
    LinearLayout deptLayout;
    @BindView(R.id.mtitle)
    EditText mtitle;
    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.submit_btn)
    Button subBtn;

    int checkedItem = 0;
    int ocheckedItem = 0;
    String[] DeptName = null;
    String[] Deptid = null;
    String[] OpinionClassId = null;
    String[] OpinionClassName = null;


    private List<TypeDepartment.DataNewsModel> listDatas = new ArrayList<>();
    private List<DeptNameModel.DataNewsModel> listNames = new ArrayList<>();
    DeptNameModel deptNameModel = null;
    TypeDepartment typeDepartment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yjtj_info);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initData();
        initView();

    }


    private void initView() {
        tabName.setText(R.string.zwfw_sqtj);


    }


    @OnClick({R.id.submit_btn, R.id.typeLayout, R.id.deptLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if ("".equals(name.getText().toString())) {
                    toast("姓名不能为空!");
                    return;
                } else if ("".equals(phone.getText().toString())) {
                    toast("电话不能为空!");
                    return;
                } else if (phone.getText().toString().trim().length() != 11) {
                    toast("请输入正确的电话号码!");
                    return;
                } else if ("".equals(typeName.getText().toString())) {
                    toast("类型不能为空!");
                    return;
                } else if ("".equals(deptName.getText().toString())) {
                    toast("部门不能为空!");
                    return;
                } else if ("".equals(mtitle.getText().toString())) {
                    toast("标题不能为空!");
                    return;
                } else if ("".equals(content.getText().toString())) {
                    toast("内容不能为空!");
                    return;
                }

                sentData();
                break;
            case R.id.typeLayout://类型
                new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(OpinionClassName, checkedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkedItem = which;
                                        typeName.setText("" + listDatas.get(which).getOpinionClassName());
                                        dialog.dismiss();
                                    }
                                }
                        ).show();
                break;
            case R.id.deptLayout://部门
                new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(DeptName, ocheckedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ocheckedItem = which;
                                        deptName.setText("" + listNames.get(which).getDeptName());
                                        dialog.dismiss();
                                    }
                                }
                        ).show();
                break;
        }
    }


    private void initData() {

        //类型TVInfoId=19&method=Opinionclass&Key=21218CCA77804D2BA1922C33E0151105
//        HashMap<String, String> params = new HashMap<>();
//        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
//        params.put("method", "Opinionclass");
//        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));

        OkHttpUtils.get()
                .url(Url.URL_PT)
                .addParams("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"))
                .addParams("method", "Opinionclass")
                .addParams("Key", SharedPrefsUtil.getString(mActivity, "Key"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!"0".equals(obj.getString("Status"))) {
                                typeDepartment = new Gson().fromJson(response, TypeDepartment.class);

                                OpinionClassName = new String[typeDepartment.getData().size()];
                                OpinionClassId = new String[typeDepartment.getData().size()];

                                for (int i = 0; i < OpinionClassName.length; i++) {

                                    OpinionClassName[i] = typeDepartment.getData().get(i).getOpinionClassName();
                                    OpinionClassId[i] = String.valueOf(typeDepartment.getData().get(i).getOpinionClassId());
                                }
                                listDatas.addAll(typeDepartment.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .addParams("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"))
                .addParams("method", "DeptList")
                .addParams("Key", SharedPrefsUtil.getString(mActivity, "Key"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response==" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!"0".equals(obj.getString("Status"))) {
                                deptNameModel = new Gson().fromJson(response, DeptNameModel.class);

                                DeptName = new String[deptNameModel.getData().size()];
                                Deptid = new String[deptNameModel.getData().size()];

                                for (int i = 0; i < DeptName.length; i++) {

                                    DeptName[i] = deptNameModel.getData().get(i).getDeptName();
                                    Deptid[i] = String.valueOf(deptNameModel.getData().get(i).getDeptid());
                                }
                                listNames.addAll(deptNameModel.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 提交诉求
     */
    private void sentData() {

   HashMap<String ,String> params = new HashMap<>();
        params.put("method", "OpinionAdd");
        params.put("TVInfoId",SharedPrefsUtil.getString(mActivity,"TVInfoId"));
        params.put("Key",SharedPrefsUtil.getString(mActivity,   "Key" ));
        params.put("UserName",name.getText().toString());
        params.put("MobileNo",phone.getText().toString());
        params.put("OpinionClassId",listDatas.get(checkedItem).getOpinionClassId()+"");
        params.put("deptId",listNames.get(ocheckedItem).getDeptid()+"");
        params.put("Title",mtitle.getText().toString());
        params.put("Content",content.getText().toString());

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

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!"0".equals(obj.getString("Status"))){
                                startActivity(new Intent(mActivity, YjtjSuccessActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}
