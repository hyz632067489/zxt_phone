package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.zxt.zxt_phone.ActivityManager;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.GzrzListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.ZwfwActivity;
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
 * Created by Administrator on 2017/3/31.
 */

public class GzrzActivity extends BaseActivity {


    private String TAG = GzrzActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.sign_in)
    TextView addText;

    @BindView(R.id.search_lay)
    LinearLayout searchLay;


    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.MobileNo)
    EditText MobileNo;
    @BindView(R.id.search)
    TextView tvSearch;


    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize = 30;
    private int page = 1;
    CommonAdapter<GzrzListModel.DataBean.ListBean> myAdapter;
    private List<GzrzListModel.DataBean.ListBean> list = new ArrayList<>();
    GzrzListModel newsModel = null;

    Intent mIntent;

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
            list.clear();
            getData(page);
            myAdapter.notifyDataSetChanged();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {

            case RESULT_OK:
                page = 1;
                list.clear();
                getData(page);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzrz);
        ButterKnife.bind(this);

        getData(page);
        initView();
    }

    private void initView() {
        tabName.setText(R.string.gzrz);

        roleLevel = Integer.parseInt(SharedPrefsUtil.getString(mActivity, "roleLevel"));
        //根据权限角色，判断添加或者收索
        if (roleLevel >= 6) {
            addText.setText(R.string.add_gzrz);
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
                    startActivityForResult(new Intent(mActivity, AddGzrzActivity.class), 1);
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


        myAdapter = new CommonAdapter<GzrzListModel.DataBean.ListBean>(mActivity, list, R.layout.gzrz_list_item) {
            @Override
            public void convert(ViewHolder holder, GzrzListModel.DataBean.ListBean item) {
                holder.setText(R.id.Title, "· [" + item.getBlogName() + "]");
//                holder.setText(R.id.EditDate, item.getEditBlogDate());
                TextView tvEditDate = holder.getView(R.id.EditDate);
                tvEditDate.setText("编辑时间：" + item.getEditBlogDate().substring(10, item.getEditBlogDate().length()));

                holder.setText(R.id.tv_title, item.getGridStaffApp().getGridStaffName());

                String myType = item.getBlogType();
                if (myType.length() != 0) {
                    if (myType.contains("1")) {
                        holder.getView(R.id.tv_xuncha).setVisibility(View.VISIBLE);
                    }
                    if (myType.contains("2")) {
                        holder.getView(R.id.tv_xuanchuan).setVisibility(View.VISIBLE);
                    }
                    if (myType.contains("3")) {
                        holder.getView(R.id.tv_zoufang).setVisibility(View.VISIBLE);
                    }
                    if (myType.contains("4")) {
                        holder.getView(R.id.tv_chuli).setVisibility(View.VISIBLE);
                    }
                }
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

        GzrzListModel.DataBean.ListBean model = new GzrzListModel.DataBean.ListBean();
        int count = newsModel.getData().getList().size();
        model.setBlogContent(newsModel.getData().getList().get(position).getBlogContent());
        model.setBlogName(newsModel.getData().getList().get(position).getBlogName());
        model.setBlogPic(newsModel.getData().getList().get(position).getBlogPic());
        model.setBlogType(newsModel.getData().getList().get(position).getBlogType());

        mIntent = new Intent(mActivity, GzrzInfoActivity.class);
        mIntent.putExtra("list", model);
        mIntent.putExtra("GridStaffName", list.get(position).getGridStaffApp().getGridStaffName());
        startActivity(mIntent);
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(int page) {
        OkHttpUtils.get()
                .url(Url.URL_WG + "blog/getOneStaffBlogById.do?")
                .addParams("pageSize", pageSize + "")
                .addParams("pageCurrent", page + "")
                .addParams("field", data)
//                .addHeader("cookie", AppData.Cookie)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response==" + response);
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ("200".equals(jsonObject.getString("statusCode"))) {
                            newsModel = new Gson().fromJson(response, GzrzListModel.class);
                            if (newsModel.getData().getList().size() > 0) {
//                                    list.clear();
                                list.addAll(newsModel.getData().getList());

                            }
                        }
//                        else if ("403".equals(jsonObject.getString("statusCode"))) {
//                            toast("登录过期，请重新登录");
//                            ActivityManager.getActivityManager().popAllActivityExceptOne(getClass());
//                            startActivity(new Intent(mActivity, ZwfwActivity.class));
//                        }
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
