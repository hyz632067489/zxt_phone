package com.zxt.zxt_phone.view.zwfw.sqtj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.zxt.zxt_phone.bean.model.DeptNameModel;
import com.zxt.zxt_phone.bean.model.TypeDepartment;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.Mobile;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.SuccessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SqtjInfoActivity extends BaseActivity {


    private String TAG = SqtjInfoActivity.class.getCanonicalName();

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


    @BindView(R.id.rb_code)
    RadioButton rbCode;
    @BindView(R.id.rb_noCode)
    RadioButton rbNoCode;
    @BindView(R.id.rg_sms)
    RadioGroup rgSms;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.code_layout)
    LinearLayout codeLayout;
    @BindView(R.id.rb_dwgs)
    RadioButton rbDwgs;
    @BindView(R.id.rb_bdwgs)
    RadioButton rbBdwgs;
    @BindView(R.id.rg_gs)
    RadioGroup rgGs;


    int checkedItem = 0;
    int ocheckedItem = 0;

    private int visible = -1;//1对外公示 0不公示
    private int sms = -1;//1验证 0不验证
    String codeNum="321";

    String[] DeptName = null;
    String[] Deptid = null;
    String[] OpinionClassId = null;
    String[] OpinionClassName = null;


    private List<TypeDepartment.DataNewsModel> listDatas = new ArrayList<>();
    private List<DeptNameModel.DataNewsModel> listNames = new ArrayList<>();
    DeptNameModel deptNameModel = null;
    TypeDepartment typeDepartment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqtj_info);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_sqtj);

        //选择是否短信验证
        rgSms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbCode.getId() == checkedId) {
                    codeLayout.setVisibility(View.VISIBLE);
                    sms = 1;
                } else if (rbNoCode.getId() == checkedId) {
                    codeLayout.setVisibility(View.GONE);
                    sms = 0;
                }

            }
        });
        //选择是否对外公示
        rgGs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbDwgs.getId() == checkedId) {
                    visible = 1;
                } else if (rbBdwgs.getId() == checkedId) {
                    visible = 0;
                }
            }
        });


    }


    @OnClick({R.id.submit_btn, R.id.typeLayout, R.id.deptLayout, R.id.btn_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (verification()) return;

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
            case R.id.btn_code:

                sentCode();
                break;
        }
    }


    /**
     * 判断是否有值
     *
     * @return
     */
    private boolean verification() {
        if ("".equals(name.getText().toString())) {
            toast("姓名不能为空!");
            return true;
        } else if ("".equals(phone.getText().toString())) {
            toast("电话不能为空!");
            return true;
        } else if (!Mobile.isPhoneNum(phone.getText().toString())) {
            toast("请输入正确的手机号");
            return true;
        } else if ("".equals(typeName.getText().toString())) {
            toast("类型不能为空!");
            return true;
        } else if ("".equals(deptName.getText().toString())) {
            toast("部门不能为空!");
            return true;
        } else if ("".equals(mtitle.getText().toString())) {
            toast("标题不能为空!");
            return true;
        } else if ("".equals(content.getText().toString())) {
            toast("内容不能为空!");
            return true;
        } else if (sms == -1) {
            toast("请选择是否短信验证!");
            return true;
        }
        if (sms == 1) {
            if ("".equals(etCode.getText().toString())) {
                toast("请输入验证码");
                return true;
            }
            if (!codeNum.equals(etCode.getText().toString())) {
                toast("请输入正确的验证码");
                return true;
            }

        }
        if (visible == -1) {
            toast("请选择是否对外公示");
            return true;
        }
        return false;
    }

    /**
     * 获取验证码
     */
    private void sentCode() {
        //判断手机号
        if (!Mobile.isPhoneNum(phone.getText().toString())) {
            toast("请输入正确的手机号");
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("method", "Short");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("Phone", phone.getText().toString());
        params.put("YZ", String.valueOf(sms));//是否需要短信验证
//        params.put("DeptId", SharedPrefsUtil.getString(mActivity, "DeptId"));

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
                        MLog.i(TAG, "response=0=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                codeNum = obj.getString("YZM");

                                toast("已发送验证码");
                            } else {
                                toast("获取验证码出错");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
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
                        MLog.i(TAG, "response=1=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
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
                        MLog.i(TAG, "response=2=" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
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

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "OpinionAdd1");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("DeptId", SharedPrefsUtil.getString(mActivity, "DeptId"));
        params.put("UserName", name.getText().toString());
        params.put("MobileNo", phone.getText().toString());
        params.put("OpinionClassId", listDatas.get(checkedItem).getOpinionClassId() + "");
        params.put("deptId", listNames.get(ocheckedItem).getDeptid() + "");
        params.put("Title", mtitle.getText().toString());
        params.put("Content", content.getText().toString());

        params.put("Visible", String.valueOf(visible));//是否公示
        params.put("YZ", String.valueOf(sms));//是否需要短信验证


        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=3=" + response);
                        closeProgressDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                startActivity(new Intent(mActivity, SuccessActivity.class)
                                .putExtra("title","诉求提交"));
                                finish();
                            } else {
                                toast(obj.getString("Message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}
