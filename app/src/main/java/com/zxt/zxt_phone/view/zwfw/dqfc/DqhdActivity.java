package com.zxt.zxt_phone.view.zwfw.dqfc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.bean.model.DqhdModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;
import com.zxt.zxt_phone.view.zwfw.DqfcActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DqhdActivity extends BaseActivity {


    private String TAG = DqhdActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.gv_list)
    HomeGridView gvList;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int page = 1;
    private int pageSize = 10;

    CommonAdapter<DqhdModel.DataNewsModel> myAdapter;
    private List<DqhdModel.DataNewsModel> listData = new ArrayList<>();

    private DqhdModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dqhd);
        ButterKnife.bind(this);

        getData();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_dqhd);


        myAdapter = new CommonAdapter<DqhdModel.DataNewsModel>(mContext, listData, R.layout.pop_list_img_item) {
            @Override
            public void convert(ViewHolder holder, DqhdModel.DataNewsModel item) {
                Glide.with(mContext).load(Url.BASE_L + item.getCoverImg())//
                        .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                        .error(R.drawable.ic_default_color)//
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                        .into((ImageView) holder.getView(R.id.item_img));

                holder.setText(R.id.tv_pop_list, item.getTitle());
            }
        };
        gvList.setAdapter(myAdapter);
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DqhdActivity.this, NewsDetailActivity.class);
                intent.putExtra("url", Url.BASE_URL + model.getPioneerUrl() + "?method=Activities"
                        + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                        + "&id=" + listData.get(position).getId());
                intent.putExtra("title", "党群活动");
                startActivity(intent);
            }
        });

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                listData.clear();
                getData();
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                page++;
                getData();
            }
        });
    }


    private void getData() {
//        params.put("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"));
//        params.put("Key",SharedPrefsUtil.getString(mContext,"Key"));

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "Activities")
                .addParams("Key", SharedPrefsUtil.getString(mContext,"Key"))
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"))
                .addParams("page", String.valueOf(page))
                .addParams("pageSize", String.valueOf(pageSize))
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
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                model = new Gson().fromJson(response, DqhdModel.class);
                                listData.addAll(model.getData());

                                myAdapter.notifyDataSetChanged();

                                mRefreshView.onFooterLoadFinish();
                                mRefreshView.onHeaderRefreshFinish();

                            } else {
                                mRefreshView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
