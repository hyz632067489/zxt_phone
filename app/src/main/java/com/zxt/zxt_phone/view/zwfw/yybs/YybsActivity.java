package com.zxt.zxt_phone.view.zwfw.yybs;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznListModel;
import com.zxt.zxt_phone.bean.model.TypeYybsModel;
import com.zxt.zxt_phone.bean.model.listYybsModel;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.YybsInfoActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class YybsActivity extends BaseActivity {

    private String TAG = YybsActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.grid_view)
    HomeGridView gridView;
    @BindView(R.id.rd_g)
    RadioGroup mRd_g;
    @BindView(R.id.rb_geren)
    RadioButton rbGeren;
    @BindView(R.id.rb_qiye)
    RadioButton rbQiye;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;


    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;


    Intent mIntent;
    listYybsModel model;
    TypeYybsModel typeModel;
    List<TypeYybsModel.DataBean> typeList = new ArrayList<>();

    List<listYybsModel.DataBeanX> allList = new ArrayList<>();
    List<listYybsModel.DataBeanX.DataBean> allList1 = new ArrayList<>();

    CommonAdapter<listYybsModel.DataBeanX> myAdapter;

    int personageId = -1;
    int companyId = -1;

    int tag = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Common.TYPE_S:
                    for (int i = 0; i < typeList.size(); i++) {
                        if (1 == typeList.get(i).getId()) {
                            rbGeren.setText(typeList.get(i).getGenre());
                            personageId = typeList.get(i).getId();
                        } else if (2 == typeList.get(i).getId()) {
                            rbQiye.setText(typeList.get(i).getGenre());
                            companyId = typeList.get(i).getId();
                        }
                    }
                    getListInfoData(personageId);
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yybs);

        getTypeData();
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_yybs);

        mRd_g.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbGeren.getId() == checkedId) {
                    getListInfoData(personageId);
                    tag = 1;
                } else if (rbQiye.getId() == checkedId) {
                    getListInfoData(companyId);
                    tag = 2;
                }
            }
        });

        myAdapter = new CommonAdapter<listYybsModel.DataBeanX>(mContext, allList, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, listYybsModel.DataBeanX item) {
                holder.getView(R.id.iv_icon).setVisibility(View.GONE);

                holder.setText(R.id.tv_title, item.getGenre());
            }
        };

        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(mActivity, YybsListActivity.class)
                        .putExtra("position", position+"")
                        .putExtra("list", (Serializable) allList));
            }
        });


        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                allList.clear();
                if (tag == 1) {
                    getListInfoData(personageId);
                } else if (tag == 2) {
                    getListInfoData(companyId);
                }
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                allList.clear();
                if (tag == 1) {
                    getListInfoData(personageId);
                } else if (tag == 2) {
                    getListInfoData(companyId);
                }

            }
        });

    }

    private void getListInfoData(int id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "booking");
        params.put("id", String.valueOf(id));
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        if (null != mRefreshView) {
                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        closeProgressDialog();

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                model = new Gson().fromJson(response, listYybsModel.class);
                                allList.clear();
                                allList.addAll(model.getData());



                                myAdapter.notifyDataSetChanged();

                                mRefreshView.onFooterLoadFinish();
                                mRefreshView.onHeaderRefreshFinish();
                            } else {
                                gridView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void getTypeData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "booking");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                typeModel = new Gson().fromJson(response, TypeYybsModel.class);
                                typeList.addAll(typeModel.getData());

                                handler.sendEmptyMessage(Common.TYPE_S);

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