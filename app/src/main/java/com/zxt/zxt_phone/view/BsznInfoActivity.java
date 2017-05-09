package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.MyAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznInfoModel;
import com.zxt.zxt_phone.bean.model.BsznListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class BsznInfoActivity extends BaseActivity {

    private String TAG = BsznInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.lv_List_View)
    ListView lv_Liebiao;

    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;

    private int page = 1;
    private int pageSize = 10;

    CommonAdapter<BsznInfoModel.DataBean> myAdapter;
    List<BsznInfoModel.DataBean> mDatas = new ArrayList<>();
    Intent mIntent;
    BsznInfoModel model;

    BsznInfoModel.DataBean myModel;
    private int id;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bszn_info);

        mIntent = getIntent();

        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");

        getData();


    }
    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.bszn_liebiao);


        myAdapter = new CommonAdapter<BsznInfoModel.DataBean>(mContext, mDatas, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, BsznInfoModel.DataBean item) {


                holder.getView(R.id.iv_icon).setVisibility(View.GONE);
                holder.setText(R.id.tv_title, item.getServe());
            }
        };
        lv_Liebiao.setAdapter(myAdapter);
        lv_Liebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(mActivity, BsznDetailActivity.class)
                        .putExtra("allData", model)
                        .putExtra("position", position+""));
            }
        });

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                mDatas.clear();

                getData();
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                mDatas.clear();
//                page++;
                getData();
            }
        });

    }

    private void getData() {

        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "bszl")
                .addParams("id", String.valueOf(id))
                .addParams("Key", SharedPrefsUtil.getString(mContext, "Key"))
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"))
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
                                model = new Gson().fromJson(response, BsznInfoModel.class);
                                mDatas.addAll(model.getData());


                                myAdapter.notifyDataSetChanged();
//                                mHandler.sendEmptyMessage(0);

                                mRefreshView.onFooterLoadFinish();
                                mRefreshView.onHeaderRefreshFinish();
                            } else {
                                lv_Liebiao.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
