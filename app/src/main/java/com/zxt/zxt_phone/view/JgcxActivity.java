package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.JgcxModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Call;

public class JgcxActivity extends BaseActivity {

    private String TAG = JgcxActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.UserName)
    EditText userName;
    @BindView(R.id.MobileNo)
    EditText mobileNo;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.noText)
    TextView noText;
    @BindView(R.id.jgList)
    ListView gvList;

    JgcxModel jgcxModel;
    CommonAdapter<JgcxModel.DataNewsModel> myAdapter;
    private List<JgcxModel.DataNewsModel> list = new ArrayList<>();


    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int page = 1; //页数
    String url;
    String Action = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jgcx);
        ButterKnife.bind(this);

        getData(page);
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_jgcx);

        myAdapter = new CommonAdapter<JgcxModel.DataNewsModel>(mActivity, list, R.layout.yjfkcx_list_item1) {
            @Override
            public void convert(ViewHolder holder, JgcxModel.DataNewsModel item) {
                TextView auditName = holder.getView(R.id.AuditName);
                holder.setText(R.id.OpinionId, "受理编号：" + String.valueOf(item.getOpinionId()));
                holder.setText(R.id.EditDate, "诉求时间：" + String.valueOf(item.getEditDate()));
                holder.setText(R.id.Title, item.getTitle());

                TextView reDate = holder.getView(R.id.ReDate);
                if (item.getReDate().equals("")) {
                    reDate.setText("正在办理");
                } else {
                    reDate.setText("办结时间：" + item.getReDate());
                }

                ImageView img = holder.getView(R.id.img);

                if (item.getAuditName().equals("已办结")) {
                    auditName.setText("已办结");
                    auditName.setBackgroundColor(Color.parseColor("#ff7d7d"));
                } else if (item.getAuditName().equals("已受理")) {
                    auditName.setText("已受理");
                    auditName.setBackgroundColor(Color.parseColor("#00cc55"));
                }

                if (item.getOpinionClassName().equals("咨询")) {
                    img.setImageResource(R.drawable.cxgs_class_zx);
                } else if (item.getOpinionClassName().equals("建议")) {
                    img.setImageResource(R.drawable.cxgs_class_jy);
                } else if (item.getOpinionClassName().equals("投诉")) {
                    img.setImageResource(R.drawable.cxgs_class_ts);
                } else if (item.getOpinionClassName().equals("救助")) {
                    img.setImageResource(R.drawable.cxgs_class_jz);
                } else if (item.getOpinionClassName().equals("其他")) {
                    img.setImageResource(R.drawable.cxgs_class_qt);
                }
            }
        };

        gvList.setAdapter(myAdapter);

        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                page++;
                getData(page);
            }
        });
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                list.clear();
                getData(page);
            }
        });
    }

    @OnItemClick(R.id.jgList)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getApplicationContext(), JgcxDetailActivity.class)
                .putExtra("OpinionId", list.get(position).getOpinionId()+"")
                .putExtra("AuditState", list.get(position).getAuditState())
                .putExtra("Action", Action));
//                .putExtra("OpinionClassId", getIntent().getStringExtra("OpinionClassId")));

    }

    @OnClick(R.id.search)
    public void onClick() {
        list.clear();
        page = 1;
        myAdapter.notifyDataSetChanged();
        getData(page);
    }

    private void getData(int page) {

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "OpinionList");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("Page", "" + page);
        params.put("PageSize", "10");
        params.put("UserName", userName.getText().toString());
        params.put("MobileNo", mobileNo.getText().toString());
        params.put("DeptId", SharedPrefsUtil.getString(mActivity, "DeptId"));
//        if(getIntent().getStringExtra("OpinionClassId").equals("6")) {
//            params.put("OpinionClassId",getIntent().getStringExtra("OpinionClassId"));
//        }else {
        params.put("Action", Action);
//        }
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                if (null != mRefreshView) {
                    mRefreshView.onFooterLoadFinish();
                    mRefreshView.onHeaderRefreshFinish();
                }
            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (!"0".equals(object.getString("Status"))) {

                        JSONArray array = object.getJSONArray("Data");
                        Gson gson = new Gson();
                        jgcxModel = gson.fromJson(response, JgcxModel.class);

                        if (!object.getString("Data").equals("[]")) {
//                                    list.clear();
                            list.addAll(jgcxModel.getData());
                            myAdapter.notifyDataSetChanged();
                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();
                        } else {
                            noText.setVisibility(View.VISIBLE);
                            noText.setText("暂无数据");
                        }
                    }
                    Log.i("TAG", "==================" + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
