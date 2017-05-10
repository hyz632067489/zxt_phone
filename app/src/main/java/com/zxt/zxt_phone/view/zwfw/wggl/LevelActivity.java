package com.zxt.zxt_phone.view.zwfw.wggl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.PopBean;
import com.zxt.zxt_phone.bean.model.DeptNameModel;
import com.zxt.zxt_phone.bean.model.LinDaoModel;
import com.zxt.zxt_phone.bean.model.TypeDepartment;
import com.zxt.zxt_phone.bean.model.TypeQjModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.DateTimePickDialogUtil;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.Mobile;
import com.zxt.zxt_phone.view.SuccessActivity;
import com.zxt.zxt_phone.view.widget.ListPopwindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by hkc on 2017/5/9.
 */

public class LevelActivity extends BaseActivity implements ListPopwindow.OnPopItemClickListener, ListPopwindow.OnBottomTextviewClickListener {

    private String TAG = LevelActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_lindao)
    TextView tvLindao;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.submit_btn)
    Button submitBtn;


    public static final int QING_JIA = 51;
    int checkedItem = 0;
    int ocheckedItem = 0;

    String[] DeptName = null;
    String[] Deptid = null;
    String[] OpinionClassId = null;
    String[] OpinionClassName = null;


    private List<TypeQjModel.DataBean> listDatas = new ArrayList<>();
    private List<LinDaoModel.DataBean> listNames = new ArrayList<>();
    LinDaoModel deptNameModel = null;
    TypeQjModel typeDepartment = null;


    ListPopwindow popWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ButterKnife.bind(this);

        getTypeData();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.qjtx);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verification()) return;

                sendData();
            }
        });

    }

    private void sendData() {
        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("leaves_type_id", listDatas.get(checkedItem).getLeaves_type_id() + "");
        params.put("leaves_auditor", listNames.get(ocheckedItem).getGridStaffId() + "");
        params.put("leaves_begin_time", DateTimePickDialogUtil.chonseTime);
        params.put("leaves_end_time",DateTimePickDialogUtil.chonseTime);

        params.put("leaves_reason", content.getText().toString());


        OkHttpUtils.get()
                .url(Url.URL_WG + "leaves/addOneLeavesInfo.do?")
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeProgressDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response==3==-=" + response);

                closeProgressDialog();
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("200".equals(obj.getString("statusCode"))) {
                        startActivity(new Intent(mActivity, SuccessActivity.class)
                                .putExtra("title", "请假"));
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
        if ("".equals(tvType.getText().toString())) {
            toast("请选择请假类型!");
            return true;
        } else if ("".equals(tvLindao.getText().toString())) {
            toast("请选择请假领导!");
            return true;
        } else if ("".equals(tvStartTime.getText().toString())) {
            toast("请选择开始时间!");
            return true;
        } else if ("".equals(tvEndTime.getText().toString())) {
            toast("请选择结束时间!");
            return true;
        } else if ("".equals(content.getText().toString())) {
            toast("内容不能为空!");
            return true;
        }

        return false;
    }


    @OnClick({R.id.type_layout, R.id.lindao_layout, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_layout://选择类型
                new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(OpinionClassName, checkedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkedItem = which;
                                        tvType.setText("" + listDatas.get(which).getLeaves_type_name());
                                        dialog.dismiss();
                                    }
                                }
                        ).show();
                break;
            case R.id.lindao_layout://选择领导
                new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(DeptName, ocheckedItem,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ocheckedItem = which;
                                        tvLindao.setText("" + listNames.get(which).getGridStaffName());
                                        dialog.dismiss();
                                    }
                                }
                        ).show();
                break;
            case R.id.start_time_layout://开始时间
//                startActivityForResult(new Intent(mActivity, YybsInfoActivity.class).putExtra("title", tabName.getText().toString()), QING_JIA);
                DateTimePickDialogUtil startLog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                startLog.dateTimePicKDialog(tvStartTime);
                break;
            case R.id.end_time_layout://结束时间
                DateTimePickDialogUtil endLog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                endLog.dateTimePicKDialog(tvEndTime);

                break;
        }
    }


    private void getTypeData() {

        //获得类型
        OkHttpUtils.post()
                .url(Url.URL_WG + "leaves/getAllLeavesType.do")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=1===" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {
                                typeDepartment = new Gson().fromJson(response, TypeQjModel.class);

                                OpinionClassName = new String[typeDepartment.getData().size()];
                                OpinionClassId = new String[typeDepartment.getData().size()];

                                for (int i = 0; i < OpinionClassName.length; i++) {

                                    OpinionClassName[i] = typeDepartment.getData().get(i).getLeaves_type_name();
                                    OpinionClassId[i] = String.valueOf(typeDepartment.getData().get(i).getLeaves_type_id());
                                }
                                listDatas.addAll(typeDepartment.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        //获得领导
        OkHttpUtils.post()
                .url(Url.URL_WG + "leaves/getLeavesAuditor.do")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=2===" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {
                                deptNameModel = new Gson().fromJson(response, LinDaoModel.class);

                                DeptName = new String[deptNameModel.getData().size()];
                                Deptid = new String[deptNameModel.getData().size()];

                                for (int i = 0; i < DeptName.length; i++) {

                                    DeptName[i] = deptNameModel.getData().get(i).getGridStaffName();
                                    Deptid[i] = String.valueOf(deptNameModel.getData().get(i).getGridStaffId());
                                }
                                listNames.addAll(deptNameModel.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void show() {
        List<PopBean> pops = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PopBean pop = new PopBean("item" + i, 0);
            pops.add(pop);
        }
        popWindow = new ListPopwindow(mActivity, this, this, tvLindao, pops, "cancel", "title");
        popWindow.showAtLocation(tvLindao, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }

    public void onBottomClick() {
        popWindow.dismiss();
    }

    public void onPopItemClick(View view, int position) {
        switch (position) {
            case 0:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 1:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 2:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 3:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 4:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            default:
                break;
        }
    }
}
