package com.zxt.zxt_phone.view.zwfw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.zxt.zxt_phone.bean.AppData;
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
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

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

    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize = 30;
    private int page = 1;
    CommonAdapter<GzrzListModel.ListNewsModel> myAdapter;
    private List<GzrzListModel.ListNewsModel> list = new ArrayList<>();
    GzrzListModel newsModel = null;

    Intent mIntent;

    int tag;

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

        getData(page);
        initView();
    }

    private void initView() {
        tabName.setText(R.string.gzrz);
        if (SharedPrefsUtil.getString(mContext, "dept").equals("社区网格员") ||
                SharedPrefsUtil.getString(mContext, "Dept").equals("社区网格长")) {
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
                    toast("弹出搜索框");
                    searchLay.setVisibility(View.VISIBLE);

                }

            }
        });

        myAdapter = new CommonAdapter<GzrzListModel.ListNewsModel>(mActivity, list, R.layout.gzrz_list_item) {
            @Override
            public void convert(ViewHolder holder, GzrzListModel.ListNewsModel item) {
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

        GzrzListModel.ListNewsModel model = new GzrzListModel.ListNewsModel();
        int count = newsModel.getList().size();
        model.setBlogContent(newsModel.getList().get(position).getBlogContent());
        model.setBlogName(newsModel.getList().get(position).getBlogName());
        model.setBlogPic(newsModel.getList().get(position).getBlogPic());
        model.setBlogType(newsModel.getList().get(position).getBlogType());

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
//                .addHeader("cookie", AppData.Cookie)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "response==" + response);
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                    if (!"0".equals(jsonObject.getString("Status"))) {
                        newsModel = new Gson().fromJson(response, GzrzListModel.class);
                        if (newsModel.getList().size() != 0) {
//                                    list.clear();
                            list.addAll(newsModel.getList());

                        }
                    }else if("304".equals(jsonObject.getString("Status"))){
                            toast("登录过期，请重新登录");
                        ActivityManager.getActivityManager().popAllActivityExceptOne(getClass());
                        startActivity(new Intent(mActivity, ZwfwActivity.class));
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
