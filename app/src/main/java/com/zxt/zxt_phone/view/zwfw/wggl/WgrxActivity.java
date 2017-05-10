package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class WgrxActivity extends BaseActivity {


    private String TAG = WgrxActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.sign_in)
    TextView addText;

    @BindView(R.id.newList)
    ListView newList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.MobileNo)
    EditText MobileNo;
    @BindView(R.id.search)
    TextView tvSearch;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;

    private int pageSize = 30;
    private int page = 1;


    WgrxModel model;
    CommonAdapter<WgrxModel.DataBean.ListBean> mAdapter;
    List<WgrxModel.DataBean.ListBean> mDatas = new ArrayList<>();


    int tag, roleLevel;
    String data = "";
    private boolean isButton = true;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

            }

        }
    };

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            data = UserName.getText().toString();

            page = 1;
            mDatas.clear();
            getData(page);
            mAdapter.notifyDataSetChanged();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgrx);
        ButterKnife.bind(this);

        getData(page);
        initView();
    }


    private void initView() {
        tabName.setText(R.string.wgryxx);

        roleLevel = Integer.parseInt(SharedPrefsUtil.getString(mActivity, "roleLevel"));
        //根据权限角色，判断添加或者收索
        if (roleLevel >= 6) {
            addText.setText(R.string.add_gzrz);
            addText.setVisibility(View.GONE);
            tag = 1;
        } else {
            addText.setText("搜索");
            addText.setVisibility(View.VISIBLE);
//            addText.setBackgroundResource(R.drawable.search);
            tag = 2;
        }

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag == 1) {
//                    startActivityForResult(new Intent(mActivity, AddGzrzActivity.class), 1);
                } else if (tag == 2) {
//                    toast("弹出搜索框");
//                    searchLay.setVisibility(View.VISIBLE);
                    if(isButton){
                        searchLayout.setVisibility(View.VISIBLE);
                        isButton = false;
                    }else {
                        searchLayout.setVisibility(View.GONE);
                        isButton = true;
                    }
                }
            }
        });

        //
        UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */

//                data = UserName.getText().toString();
//                if(s.length() == 0){
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
//                }
//                else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
                mHandler.post(eChanged);
//                }
            }
        });


        mAdapter = new CommonAdapter<WgrxModel.DataBean.ListBean>(mContext,mDatas,R.layout.wgry_item_layout) {
            @Override
            public void convert(ViewHolder holder, WgrxModel.DataBean.ListBean item) {

                holder.setText(R.id.tv_title,"网格员：" +item.getGridStaffName());
                holder.setText(R.id.tv_gender,"性别：" +item.getGridStaffSex());
                holder.setText(R.id.tv_content,"负责地区：" +item.getGridStaffScope());
                holder.setText(R.id.tv_phone,"手机号：" +item.getGridStaffTel());

            }
        };
        newList.setAdapter(mAdapter);


        setRefreshView();

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
        OkHttpUtils.get()
                .url(Url.URL_WG + "staff/getAllGridStaffById.do?")
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageCurrent", String.valueOf(page))
                .addParams("field", data)
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
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {

                                model = new Gson().fromJson(response,WgrxModel.class);
                                mDatas.addAll(model.getData().getList());
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

}
