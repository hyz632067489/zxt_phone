package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.GzrzListModel;
import com.zxt.zxt_phone.bean.model.SjclListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.Call;

public class SjclActivity extends BaseActivity {

    private String TAG = SjclActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.sign_in)
    TextView addText;

    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.UserName)
    EditText UserName;

    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize = 30;
    private int page = 1;

    CommonAdapter<SjclListModel.DataBean.ListBean> myAdapter;
    private List<SjclListModel.DataBean.ListBean> list = new ArrayList<>();
    SjclListModel newsModel = null;

    String data = "";
    private boolean isButton = true;

    int tag,roleLevel;

    Intent mIntent;
    public  final static String SER_KEY = "com.mData";

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
            list.clear();
            getData(page);
            myAdapter.notifyDataSetChanged();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjcl);


        getData(page);
        initView();
    }

    private void initView() {
        tabName.setText("事件处理");

        roleLevel = Integer.parseInt(SharedPrefsUtil.getString(mActivity, "roleLevel"));
        //根据权限角色，判断添加或者收索
        if (roleLevel >= 6) {
            addText.setText(R.string.add_sjcl);
            addText.setVisibility(View.VISIBLE);
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
                    startActivityForResult(new Intent(mActivity, AddSjclActivity.class), 1);
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

        UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mHandler.post(eChanged);
            }
        });
        myAdapter = new CommonAdapter<SjclListModel.DataBean.ListBean>(mActivity, list, R.layout.sjcl_list_item) {
            @Override
            public void convert(ViewHolder holder, SjclListModel.DataBean.ListBean item) {
                holder.setText(R.id.tv_title, "· [" + item.getEventTitle() + "]");
                holder.setText(R.id.tv_people,"网 格 员：" + item.getGridStaffApp().getGridStaffName());

                holder.setText(R.id.tv_come,"事件来源：" + item.getSourceType().getSourceTypeName());
                holder.setText(R.id.tv_type,"事件类型：" + item.getEventType().getEventTypeName());

                holder.setText(R.id.tv_jin_ji,"紧急程度：" + item.getEventLevel().getEventLevelName());
                holder.setText(R.id.tv_time,"编辑时间：" + item.getEditEventDate().substring(10, item.getEditEventDate().length()));


//                String myType = item.getBlogType();
//                if (myType.length() != 0) {
//                    if (myType.contains("1")) {
//                        holder.getView(R.id.tv_xuncha).setVisibility(View.VISIBLE);
//                    }
//                    if (myType.contains("2")) {
//                        holder.getView(R.id.tv_xuanchuan).setVisibility(View.VISIBLE);
//                    }
//                    if (myType.contains("3")) {
//                        holder.getView(R.id.tv_zoufang).setVisibility(View.VISIBLE);
//                    }
//                    if (myType.contains("4")) {
//                        holder.getView(R.id.tv_chuli).setVisibility(View.VISIBLE);
//                    }
//                }
            }

        };
        newsList.setAdapter(myAdapter);

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                list.clear();
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


    @OnItemClick(R.id.newList)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {


        mIntent = new Intent(mActivity, SjclInfoActivity.class);
//        Bundle mBundle = new Bundle();
//        mBundle.putSerializable("mData",newsModel);
        mIntent.putExtra("mData",newsModel);
        mIntent.putExtra("position",position+"");

        startActivity(mIntent);
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(int page) {
        showProgressDialog();
        OkHttpUtils.get()
                .url(Url.URL_WG + "event/getAllEventInfoById.do?")
                .addParams("pageSize", pageSize + "")
                .addParams("pageCurrent", page + "")
                .addParams("field", data)
//                .addHeader("cookie", AppData.Cookie)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            closeProgressDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response==" + response);
                closeProgressDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ("200".equals(jsonObject.getString("statusCode"))) {
                            newsModel = new Gson().fromJson(response, SjclListModel.class);
                            if (newsModel.getData().getList().size() > 0) {
//                                    list.clear();
                                list.addAll(newsModel.getData().getList());

                            }
                        }
                        myAdapter.notifyDataSetChanged();

                        mRefreshView.onFooterLoadFinish();
                        mRefreshView.onHeaderRefreshFinish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
