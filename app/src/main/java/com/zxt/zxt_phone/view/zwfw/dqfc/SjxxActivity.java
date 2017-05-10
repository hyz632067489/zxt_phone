package com.zxt.zxt_phone.view.zwfw.dqfc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.TypeAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.PeopleModle;
import com.zxt.zxt_phone.bean.model.TypeModel;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.DateTimePickDialogUtil;
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
    Spinner typeName;
    @BindView(R.id.deptName)
    Spinner deptName;
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

    TypeModel typeModel;
    PeopleModle peopleModle;

    List<String> list1 = new ArrayList<>();//事件类型
    List<TypeModel.DataBean> typeList = new ArrayList<>();//事件类型
    List<String> list2 = new ArrayList<>();//人物
    List<PeopleModle.DataBean> peopleList = new ArrayList<>();//人物

    TypeAdapter mAdapter1;
    ArrayAdapter<PeopleModle.DataBean> mAdapter2;
    private int typeIndex = 0;      //Spinner选择的position
    private int leaderIndex = 0;      //Spinner选择的position

    private String userName = "";
    private String etPhone = "";
    int genre = -1;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Common.TYPE_S:
                    setType();
                    break;
                case Common.PROPLE_S:
                    setPeople();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjxx);
        ButterKnife.bind(this);

        getTypeData();
        getPeopleData();

    }



    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        tabName.setText(R.string.zwfw_sjxx);

        rgGs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbDwgs.getId() == checkedId) {
                    genre = 1;
                    userName = name.getText().toString();
                    etPhone = phone.getText().toString();
                    nameLayout.setVisibility(View.VISIBLE);
                    phoneLayout.setVisibility(View.VISIBLE);
                } else if (rbBdwgs.getId() == checkedId) {
                    genre = 0;
                    nameLayout.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                }
            }
        });


    }


    private void setType() {

        //绑定 Adapter到控件
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, list1);
//        mAdapter1 = new TypeAdapter(mContext, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        typeName.setAdapter(adapter1);

        //设置spinner监听
        typeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeIndex = position;
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setPeople() {
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        deptName.setAdapter(adapter2);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        deptName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                /* 将mySpinner 显示*/
                parent.setVisibility(View.VISIBLE);
                leaderIndex = position;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                myTextView.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        deptName.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        deptName.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
    }


    @OnClick({R.id.time_Layout, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.time_Layout:

                DateTimePickDialogUtil endLog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                endLog.dateTimePicKDialog(timeTv);

                break;
            case R.id.submit_btn:

                if (verification()) return;

                sendData();
                break;
        }
    }

    private void sendData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "mailbox");
        params.put("DeptId", SharedPrefsUtil.getString(mContext,"DeptId"));
        params.put("Key", SharedPrefsUtil.getString(mContext,"Key"));
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"));

        MLog.i(TAG,"time=="+timeTv.getText().toString());

        params.put("genre", String.valueOf(genre));
        params.put("docketid", String.valueOf(typeList.get(typeIndex).getId()));
        params.put("name", userName);
        params.put("phone", etPhone);
        params.put("secretaryid", String.valueOf(peopleList.get(leaderIndex).getId()));
        params.put("time", String.valueOf(curDate));
        params.put("title", mtitle.getText().toString());
        params.put("content", content.getText().toString());


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
                        try {
                            JSONObject obj = new JSONObject(response);
                            if("1".equals(obj.getString("Status"))){
                                startActivity(new Intent(mActivity, SuccessActivity.class)
                                        .putExtra("title","书记信箱"));
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


    /**
     * 判断是否有值
     *
     * @return
     */
    private boolean verification() {

        if (genre == -1) {
            toast("请选择是否匿名!");
            return true;
        }
        if (genre == 1) {
            if ("".equals(name.getText().toString())) {
                toast("姓名不能为空!");
                return true;
            } else if ("".equals(phone.getText().toString())) {
                toast("电话不能为空!");
                return true;
            } else if (!Mobile.isPhoneNum(phone.getText().toString())) {
                toast("请输入正确的手机号");
                return true;
            }
        }

//        else if (typeIndex == -1) {
//            toast("事件类型不能为空!");
//            return true;
//        } else if (leaderIndex == -1) {
//            toast("请选择书记!");
//            return true;
//        }
        if ("".equals(mtitle.getText().toString())) {
            toast("标题不能为空!");
            return true;
        } else if ("".equals(content.getText().toString())) {
            toast("内容不能为空!");
            return true;
        } else if ("".equals(timeTv.getText().toString())) {
            toast("请选择时间!");
            return true;
        }

        return false;
    }

    /**
     * 获取接收人跟事项
     */
    private void getTypeData() {

        HashMap<String, String> params1 = new HashMap<>();
        params1.put("method", "docket");
        params1.put("DeptId", SharedPrefsUtil.getString(mContext, "DeptId"));
        params1.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params1.put("TVInfoId", "TVInfoId");

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
                                typeModel = new Gson().fromJson(response, TypeModel.class);
                                typeList.addAll(typeModel.getData());

                                list1.clear();
                                for (int i = 0; i < typeModel.getData().size(); i++) {
                                    list1.add(typeList.get(i).getContent());
                                }
//                                list1.addAll(typeModel.getData());

                                mHandler.sendEmptyMessage(Common.TYPE_S);
                            } else {
                                toast(obj.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getPeopleData() {
        //获取接收人
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("method", "secretary");
        params2.put("DeptId", SharedPrefsUtil.getString(mContext, "DeptId"));
        params2.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params2.put("TVInfoId", "TVInfoId");

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
                                peopleModle = new Gson().fromJson(response, PeopleModle.class);

                                peopleList.addAll(peopleModle.getData());
                                list2.clear();
                                for (int i = 0; i < peopleModle.getData().size(); i++) {
                                    list2.add(peopleList.get(i).getType());
                                }
                                mHandler.sendEmptyMessage(Common.PROPLE_S);
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
