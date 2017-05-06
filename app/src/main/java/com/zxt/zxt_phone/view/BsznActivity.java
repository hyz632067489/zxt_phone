package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.Call;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class BsznActivity extends BaseActivity {

    private String TAG = BsznActivity.class.getCanonicalName();
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

    private int page = 1;
    private int pageSize = 10;

    CommonAdapter<BsznListModel.DataBean> myAdapter, myAdapter1;
    BsznListModel model;
    List<BsznListModel.DataBean> mList1= new ArrayList<>();
    List<BsznListModel.DataBean> mList2= new ArrayList<>();
    List<BsznListModel.DataBean> mDatas = new ArrayList<>();
    List<BsznListModel.DataBean> allDatas = new ArrayList<>();

    Intent mIntent;

    private boolean isChecked = true;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mDatas.clear();
                    if (isChecked) {
                        mDatas.addAll(mList1);
                    } else {
                        mDatas.addAll(mList2);

                    }
//                    setAdapter();
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bazn_activity);


        getData();
        initView();

    }

    private void initView() {
        tabName.setText(R.string.bszn_tabname);


        mRd_g.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mDatas.clear();
                if (checkedId == rbGeren.getId()) {
                    isChecked = true;
                    mDatas.addAll(mList1);
                } else if (checkedId == rbQiye.getId()) {
                    isChecked = false;
                    mDatas.addAll(mList2);
                }
                myAdapter.notifyDataSetChanged();
            }
        });


        myAdapter = new CommonAdapter<BsznListModel.DataBean>(mContext, mDatas, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, BsznListModel.DataBean item) {

                holder.setImageByUrlIcon(R.id.iv_icon, item.getPic());
                holder.setText(R.id.tv_title, item.getCategory());
            }

        };
        gridView.setAdapter(myAdapter);

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                mDatas.clear();
                allDatas.clear();
                getData();
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                mDatas.clear();
                allDatas.clear();
                getData();
            }
        });

    }


    @OnItemClick(R.id.grid_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if ("1".equals(mDatas.get(position).getEnabled())) {
            mIntent = new Intent(mActivity, BsznInfoActivity.class);
            mIntent.putExtra("id", mDatas.get(position).getId());
            mIntent.putExtra("title", mDatas.get(position).getCategory());
            startActivity(mIntent);
        } else {
            toast("此功能持续更新中,敬请关注");
        }
    }

    private void getData() {
        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "bszls")
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
                                model = new Gson().fromJson(response, BsznListModel.class);
                                allDatas.addAll(model.getData());

                                mDatas.clear();
                                mList2.clear();
                                mList1.clear();
                                for (int i = 0; i < allDatas.size(); i++) {
                                    if (1 == allDatas.get(i).getPid()) {
                                        mList1.add(allDatas.get(i));
                                    } else if (2 == allDatas.get(i).getPid()) {
                                        mList2.add(allDatas.get(i));
                                    }
                                }

//                                mDatas.clear();
//                                if (isChecked) {
//                                    mDatas.addAll(mList1);
//                                } else {
//                                    mDatas.addAll(mList2);
//                                }
//                                myAdapter.notifyDataSetChanged();
                                mHandler.sendEmptyMessage(0);

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

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
