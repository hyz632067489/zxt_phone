package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.zxt.zxt_phone.adapter.GridviewAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznListModel;
import com.zxt.zxt_phone.bean.model.BsznModel;
import com.zxt.zxt_phone.bean.model.WyggModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.Call;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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

    private QueryType selectTab ;

    private enum QueryType {
        gerenHandle, qiyeHandle;
    }

    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int page = 1;
    private int pageSize = 10;

    CommonAdapter<BsznListModel.DataBean> myAdapter, myAdapter1;
    BsznListModel model;
    List<BsznListModel.DataBean> mList1 = new ArrayList<>();
    List<BsznListModel.DataBean> mList2 = new ArrayList<>();
    List<BsznListModel.DataBean> mDatas = new ArrayList<>();

    Intent mIntent;

    private boolean isChecked = true;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mDatas.clear();
                    if(isChecked){
                        mDatas.addAll(mList1);
                    }else {
                        mDatas.addAll(mList2);

                    }
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
        mRd_g.setOnCheckedChangeListener(listener);


        myAdapter = new CommonAdapter<BsznListModel.DataBean>(mContext,mDatas,R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, BsznListModel.DataBean item) {
                Glide.with(mContext).load(Url.BASE_L + item.getPic())//
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存修改过的图片
                        .override(120,120)
                        .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                        .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                        .error(R.drawable.ic_default_color)//
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                        .into((ImageView) holder.getView(R.id.iv_icon));


                holder.setText(R.id.tv_title, item.getCategory());
            }
        };
        gridView.setAdapter(myAdapter);

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
                page++;
                getData();
            }
        });

    }

    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == rbGeren.getId()) {
                isChecked=true;
                mDatas.clear();
                mDatas.addAll(mList1);
                myAdapter.notifyDataSetChanged();
            }else if(checkedId == rbQiye.getId()){
                isChecked=false;
                mDatas.clear();
                mDatas.addAll(mList2);
                myAdapter.notifyDataSetChanged();
            }
        }
    };

    @OnItemClick(R.id.grid_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if("1".equals(mDatas.get(position).getEnabled())){
            mIntent = new Intent(mActivity,BsznInfoActivity.class);
            mIntent.putExtra("id", mDatas.get(position).getId());
            mIntent.putExtra("title", mDatas.get(position).getCategory());
            startActivity(mIntent);
        }else{
            toast("此功能持续更新中,敬请关注");
        }

    }



    private void getData() {
        showProgressDialog();

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "bszls")
                .addParams("Key", SharedPrefsUtil.getString(mContext,"Key"))
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG,"response=="+response);
                        closeProgressDialog();

                        try {
                            JSONObject obj = new JSONObject(response);
                            if("1".equals(obj.getString("Status"))){
                                model = new Gson().fromJson(response,BsznListModel.class);
                                mDatas.addAll(model.getData());

                                mList2.clear();
                                mList1.clear();
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (1 == mDatas.get(i).getPid()) {
                                        mList1.add(mDatas.get(i));
                                    } else if (2 == mDatas.get(i).getPid()) {
                                        mList2.add(mDatas.get(i));
                                    }
                                }

                                mHandler.sendEmptyMessage(0);

                            }else {
                                gridView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 添加一个得到数据的方法，方便使用
     */
    private ArrayList<BsznModel> getDate() {
        ArrayList<BsznModel> listItem = new ArrayList<BsznModel>();
        BsznModel model = null;
        /**为动态数组添加数据*/
        for (int i = 0; i < 20; i++) {
            model = new BsznModel();
            model.setImage(R.drawable.m_bsbx);
            model.setText("个人办事" + i);
            listItem.add(model);
        }
        return listItem;
    }
    private ArrayList<BsznModel> getDate1() {
        ArrayList<BsznModel> listItem = new ArrayList<BsznModel>();
        BsznModel model = null;
        /**为动态数组添加数据*/
        for (int i = 0; i < 15; i++) {
            model = new BsznModel();
            model.setImage(R.drawable.m_bsbx);
            model.setText("政府办事" + i);
            listItem.add(model);
        }
        return listItem;
    }

    private void addFirstData(){
        BsznModel model = new BsznModel();
        model.setText("全部");
        model.setImage(R.drawable.m_gscx);
        getDate().add(0,model);
        getDate1().add(0,model);
    }



}
