package com.zxt.zxt_phone.view.zwfw.pasq;

import android.content.Intent;
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
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.WgrxModel;
import com.zxt.zxt_phone.bean.model.ZatbModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.WebViewActivity;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class PasqActivity extends BaseActivity {

    private String TAG = PasqActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.sign_in)
    TextView add;

    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;

    @BindView(R.id.newList)
    ListView newList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    ZatbModel model;
    CommonAdapter<ZatbModel.DataBean> mAdapter;
    List<ZatbModel.DataBean> mDatas = new ArrayList<>();

    private int pageSize = 30;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasq);

        getData();
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_pasq);
        add.setText("社区民警");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,SqmjActivity.class));
            }
        });



        mAdapter = new CommonAdapter<ZatbModel.DataBean>(mContext, mDatas, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, ZatbModel.DataBean item) {
                holder.setText(R.id.Title, item.getTitle());
                holder.setText(R.id.EditDate, item.getTime().substring(0,10));
            }
        };


        newList.setAdapter(mAdapter);
        newList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = Url.BASE_L + model.getPioneerUrl() + "?method=pasccontent"
                        + "&id=" + mDatas.get(position).getId()
                        + "&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                        + "&DeptId=" + SharedPrefsUtil.getString(mContext, "DeptId");

                startActivity(new Intent(mActivity, WebViewActivity.class)
                        .putExtra("url", url)
                        .putExtra("title", "治安通报"));

            }
        });

        setRefreshView();
    }


    private void getData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "pasccontent");
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params.put("DeptId", SharedPrefsUtil.getString(mContext, "DeptId"));

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
                        closeProgressDialog();
                        MLog.i(TAG, "response===" + response);

                        try {
                            JSONObject obj = null;
                            obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {

                                model = new Gson().fromJson(response, ZatbModel.class);
                                mDatas.addAll(model.getData());

                                mAdapter.notifyDataSetChanged();

                                mRefreshView.onFooterLoadFinish();
                                mRefreshView.onHeaderRefreshFinish();

                            } else {
                                toast(obj.getString("Message"));
                                noData.setVisibility(View.VISIBLE);
                                newList.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 刷新界面
     */
    private void setRefreshView() {
        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                mDatas.clear();
                getData();
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
//                page++;
//                getData();
                mDatas.clear();
                getData();
            }
        });
    }
}
