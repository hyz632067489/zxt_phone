package com.zxt.zxt_phone.view.zwfw.sqgk;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.TitleAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.NewsModel;
import com.zxt.zxt_phone.bean.model.TitleModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SqgkActivity extends BaseActivity {


    private String TAG = SqgkActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    Intent mIntent;

    private List<NewsModel.DataNewsModel> mDatas = new ArrayList<>();

    CommonAdapter<NewsModel.DataNewsModel> myAdapter;
    NewsModel newsModel = null;

    private int page = 1;
    private int pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqgk);
        ButterKnife.bind(this);

        getData(page);
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_sqgk);

        //新闻列表
        myAdapter = new CommonAdapter<NewsModel.DataNewsModel>(mActivity, mDatas, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, NewsModel.DataNewsModel item) {
                holder.setText(R.id.Title, "· [" + item.getDeptName() + "]" + item.getTitle());
                holder.setText(R.id.EditDate, item.getEditDate());

            }
        };
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newsUrl = Url.BASE_L + newsModel.getNewsShowUrl() + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                        + "&id=" + mDatas.get(position).getNewsId();

                Log.i("TAG", "==================" + newsUrl);
                startActivity(new Intent(mActivity, NewsDetailActivity.class)
                        .putExtra("title", mDatas.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                mDatas.clear();
                getData(page);
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                page++;
                getData(page);
            }
        });

    }


    private void getData(int page) {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("ClassId","3");
        params.put("TVInfoId",SharedPrefsUtil.getString(mContext,"TVInfoId"));
        params.put("method","IndexNews");
        params.put("Page", String.valueOf(page));
        params.put("Key",SharedPrefsUtil.getString(mContext,"Key"));
        params.put("PageSize", String.valueOf(pageSize));
        params.put("Deptid","3");
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
                        MLog.i(TAG, "response=" + response);
                        closeProgressDialog();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if ("1".equals(jsonObject.getString("Status"))) {
                                newsModel = new Gson().fromJson(response, NewsModel.class);
                                if (newsModel.getData() != null || newsModel.getData().size() != 0) {
                                    mDatas.clear();
                                    mDatas.addAll(newsModel.getData());
                                }
                            }
                            myAdapter.notifyDataSetChanged();

                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @OnClick({R.id.layout_sqjs, R.id.layout_rkxx, R.id.layout_zzjg})
    public void onViewClicked(View view) {

        mIntent = new Intent();

        switch (view.getId()) {
            case R.id.layout_sqjs:
                mIntent.setClass(mActivity, NewsDetailActivity.class);

                mIntent.putExtra("url", Url.URL_HTML_2 + "TVInfoId=" + SharedPrefsUtil.getString(mContext, "TVInfoId") + "&Key=" + SharedPrefsUtil.getString(mContext, "Key")
                        + "&ResoId=85");
                mIntent.putExtra("title", "社区介绍");
                break;
            case R.id.layout_rkxx:
                mIntent.setClass(mActivity, NewsDetailActivity.class);
                mIntent.putExtra("url", Url.URL_HTML_2 + "TVInfoId=" + SharedPrefsUtil.getString(mContext, "TVInfoId") + "&Key=" + SharedPrefsUtil.getString(mContext, "Key")
                        + "&ResoId=82");
                mIntent.putExtra("title", "人口信息");
                break;
            case R.id.layout_zzjg:
                mIntent.setClass(mActivity, NewsDetailActivity.class);
                mIntent.putExtra("url", Url.URL_HTML_2 + "TVInfoId=" + SharedPrefsUtil.getString(mContext, "TVInfoId") + "&Key=" + SharedPrefsUtil.getString(mContext, "Key")
                        + "&ResoId=87");
                mIntent.putExtra("title", "组织架构");
                break;
        }
        startActivity(mIntent);
    }
}
