package com.zxt.zxt_phone.view.wyfw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.zxt.zxt_phone.bean.model.WyggModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class WyggActivity extends BaseActivity {


    private String TAG = WyggActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.left_list)
    ListView myListView;

    CommonAdapter<WyggModel.DataBean> myAdapter, myAdapter1;
    WyggModel model;
    List<WyggModel.DataBean> mList = new ArrayList<>();
    List<WyggModel.DataBean> mList1 = new ArrayList<>();
    List<WyggModel.DataBean> mDatas = new ArrayList<>();

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;

    Intent mIntent;

    private boolean isChecked = true;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(isChecked){
                        mDatas.clear();
                        mDatas.addAll(mList);
                        myAdapter.notifyDataSetChanged();
                    }else {
                        mDatas.clear();
                        mDatas.addAll(mList1);
                        myAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wygg);
        ButterKnife.bind(this);

        getData();
        initView();
    }


    private void initView() {
        tabName.setText("物业公告");


        getCheckId();
        setAdapterListener();

    }

    private void getCheckId() {
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb1.getId() == checkedId) {
                    isChecked=true;
                    mDatas.clear();
                    mDatas.addAll(mList);
                    myAdapter.notifyDataSetChanged();
                } else if (rb2.getId() == checkedId) {
                    isChecked=false;
                    mDatas.clear();
                    mDatas.addAll(mList1);
                    myAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    private void setAdapterListener() {

        myAdapter = new CommonAdapter<WyggModel.DataBean>(mContext, mDatas, R.layout.item_wygg) {
            @Override
            public void convert(ViewHolder holder, WyggModel.DataBean item) {
                holder.setText(R.id.tv_title, item.getAtitle());
                holder.setText(R.id.tv_editDate, item.getAtime());
            }
        };
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent = new Intent(mActivity, NewsDetailActivity.class);
                if(isChecked){
                    mIntent.putExtra("title", "公告");
                    mIntent.putExtra("url", Url.BASE_URL_HTML+"InFrom.aspx?Eid=" + mDatas.get(position).getAid());
                }else {
                    mIntent.putExtra("title", "公示");
                    mIntent.putExtra("url", Url.BASE_URL_HTML+"Announce.aspx?Eid=" + mDatas.get(position).getAid());
                }

                startActivity(mIntent);
            }
        });
    }


    private void getData() {

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method","anncounce")
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"))
                .addParams("Key", SharedPrefsUtil.getString(mContext,"Key"))
                .addParams("deptId", SharedPrefsUtil.getString(mContext,"DeptId"))
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
                            if("1".equals(obj.getString("Status"))){
                                model = new Gson().fromJson(response, WyggModel.class);
                                // newsList = mainNew.getData();
                                WyggModel.DataBean bean;

                                mDatas.clear();
                                mDatas.addAll(model.getData());

                                mList.clear();
                                mList1.clear();
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (1 == mDatas.get(i).getAid()) {
                                        mList.add(mDatas.get(i));
                                    } else if (2 == mDatas.get(i).getAid()) {
                                        mList1.add(mDatas.get(i));
                                    }
                                }

                                mHandler.sendEmptyMessage(0);
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}
