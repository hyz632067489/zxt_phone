package com.zxt.zxt_phone.view.zwfw.dqfc;

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
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.WyggModel;
import com.zxt.zxt_phone.bean.model.ZyzfwModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ZyzfwActivity extends BaseActivity {


    private String TAG = ZyzfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rd_g)
    RadioGroup rdG;
    @BindView(R.id.grid_view)
    HomeGridView gridView;

    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;


    CommonAdapter<ZyzfwModel.DataBean> myAdapter, myAdapter1;
    ZyzfwModel model;
    List<ZyzfwModel.DataBean> mDatas = new ArrayList<>();
    List<ZyzfwModel.DataBean> mList1 = new ArrayList<>();
    List<ZyzfwModel.DataBean> mList2 = new ArrayList<>();

    private boolean isChecked = true;
    private int tag = -1;

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
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zyzfw);
        ButterKnife.bind(this);

        getZyzxData();
        getZyhdData();

        initView();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_zyzfw);

        rdG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                mDatas.clear();
                if (rb1.getId() == checkedId) {
                    isChecked = true;
                    mDatas.addAll(mList1);
                } else if (rb2.getId() == checkedId) {
                    isChecked = false;
                    mDatas.addAll(mList2);
                }
                gridView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                myAdapter.notifyDataSetChanged();
            }
        });
        myAdapter = new CommonAdapter<ZyzfwModel.DataBean>(mContext, mDatas, R.layout.pop_list_img_item) {
            @Override
            public void convert(ViewHolder holder, ZyzfwModel.DataBean item) {
                Glide.with(mContext).load(Url.BASE_L + item.getCoverImg())//
                        .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                        .error(R.drawable.ic_default_color)//
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                        .into((ImageView) holder.getView(R.id.item_img));

                holder.setText(R.id.tv_pop_list, item.getTitle());
            }
        };
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ZyzfwActivity.this, NewsDetailActivity.class);

                if (isChecked) {
                    intent.putExtra("url", Url.BASE_L + model.getUrl() + "?volunteer&id="
                            + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                            + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                            + mDatas.get(position).getId());
                    intent.putExtra("title", "志愿资讯");
                } else {
                    intent.putExtra("url", Url.BASE_L + model.getUrl() + "?voluntary&id="
                            + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                            + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                            + mDatas.get(position).getId());
                    intent.putExtra("title", "志愿活动");
                }
                startActivity(intent);

            }
        });
    }

    private void getZyzxData() {
        showProgressDialog();
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "volunteer")
                .addParams("Key", SharedPrefsUtil.getString(mContext, "Key"))
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=1=" + response);
                        closeProgressDialog();
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            String status = object.getString("Status");
                            if (("1".equals(status))) {
                                model = new Gson().fromJson(response, ZyzfwModel.class);
                                mList1.clear();
                                mList1.addAll(model.getData());

                                mHandler.sendEmptyMessage(0);
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

    private void getZyhdData() {
        showProgressDialog();
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "voluntary")
                .addParams("Key", SharedPrefsUtil.getString(mContext, "Key"))
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=2=" + response);
                        closeProgressDialog();
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            String status = object.getString("Status");
                            if (("1".equals(status))) {
                                model = new Gson().fromJson(response, ZyzfwModel.class);
                                mList2.clear();
                                mList2.addAll(model.getData());
                                mHandler.sendEmptyMessage(0);
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


    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rd_g})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_1:
                break;
            case R.id.rb_2:
                break;
            case R.id.rd_g:
                break;
        }
    }
}
