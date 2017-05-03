package com.zxt.zxt_phone.view.wyfw;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.PjglInfoAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.PjglInfoModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.Mobile;
import com.zxt.zxt_phone.view.customview.HomeGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PjglInfoActivity extends BaseActivity {


    private String TAG = PjglActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.tv_title_content)
    TextView tvTitleContent;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_tongyi)
    Button btnTongyi;
    @BindView(R.id.btn_butongyi)
    Button btnButongyi;
    @BindView(R.id.tv_yijian)
    EditText tvYijian;
    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.gv_list)
    HomeGridView gvList;
    @BindView(R.id.btn_touyipiao)
    Button btnTouyipiao;
    @BindView(R.id.layout_2)
    RelativeLayout layout2;


    PjglInfoModel pjglInfoModel;
    List<PjglInfoModel.DataBeanX.DataBean> mDatas = new ArrayList<>();
    PjglInfoAdapter myAdapter;

    int typeNum, typeId;

    String mCount, etPhone, reason, title;
    String choseId = "";

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pjgl_info);
        ButterKnife.bind(this);

        mIntent = getIntent();
        choseId = mIntent.getStringExtra("choseId");
        title = mIntent.getStringExtra("title");
        getData();
        initView();

    }

    private void initView() {
        tabName.setText(R.string.wyfw_pjgl_info);

        tvTitleContent.setText(title);

        myAdapter = new PjglInfoAdapter(mContext, mDatas);
        gvList.setAdapter(myAdapter);
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                typeId = Integer.parseInt(mDatas.get(position).getId());
                myAdapter.setSelectItem(position);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getData() {

        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "votes")
                .addParams("id", String.valueOf(choseId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        MLog.i(TAG, "response=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            if ("1".equals(obj.getString("Status"))) {
                                closeProgressDialog();
                                JSONArray array1 = obj.getJSONArray("Data");
                                for (int i = 0; i < array1.length(); i++) {
                                    JSONObject obj2 = array1.getJSONObject(i);
                                    typeNum = Integer.parseInt(obj2.getString("pjlx"));
                                    mCount = obj2.getString("content");
                                }
                                pjglInfoModel = new PjglInfoModel();
                                pjglInfoModel = new Gson().fromJson(response, PjglInfoModel.class);

                                mDatas.addAll(pjglInfoModel.getData().get(0).getData());

                                myAdapter.notifyDataSetChanged();

                                tvContent.setText(mCount);

                                btnTongyi.setText(mDatas.get(0).getAname());
                                btnButongyi.setText(mDatas.get(1).getAname());

                                if (2 == typeNum) {
                                    layout1.setVisibility(View.GONE);
                                    layout2.setVisibility(View.VISIBLE);

                                } else {
                                    layout1.setVisibility(View.VISIBLE);
                                    layout2.setVisibility(View.GONE);
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @OnClick({R.id.btn_tongyi, R.id.btn_butongyi, R.id.btn_touyipiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tongyi:
                if ("赞成".equals(mDatas.get(0).getAname())) {
                    typeId = Integer.parseInt(mDatas.get(0).getId());
                } else {
                    typeId = Integer.parseInt(mDatas.get(1).getId());
                }
                showDialog();
                break;
            case R.id.btn_butongyi:
                reason = tvYijian.getText().toString();
                if ("不赞成".equals(mDatas.get(0).getAname())) {
                    typeId = Integer.parseInt(mDatas.get(0).getId());
                } else {
                    typeId = Integer.parseInt(mDatas.get(1).getId());
                }
                if ("".equals(reason)) {
                    toast("请输入理由");
                    return;
                }
                showDialog();
                break;
            case R.id.btn_touyipiao:

                if (typeId == 0) {
                    toast("请选择投票");
                    return;
                }

                showDialog();

                break;
        }
    }

    private void showDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("投票");
        //通过布局填充器获login_layout
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        //获取两个文本编辑框（密码这里不做登陆实现，仅演示）
        final EditText et_Phone = (EditText) view.findViewById(R.id.et_phone);
        dialog.setView(view);//设置login_layout为对话提示框
        dialog.setCancelable(false);//设置为不可取消
        //设置正面按钮，并做事件处理
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etPhone = et_Phone.getText().toString().trim();
                //判断手机号
                if (!Mobile.isPhoneNum(etPhone)) {
                    toast("请输入正确的手机号");
                    return;
                }

                sentInfoData();

            }
        });
        //设置反面按钮，并做事件处理
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();//显示Dialog对话框
    }

    private void sentInfoData() {
        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "voting")
                .addParams("id", typeId+"")
                .addParams("reason", reason)
                .addParams("phone", etPhone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "jsonStr====" + response);
                        closeProgressDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {

                                finish();
                            }else {
                                toast(obj.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
