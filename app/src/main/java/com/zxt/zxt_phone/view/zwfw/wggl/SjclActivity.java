package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class SjclActivity extends BaseActivity {

    private String TAG = SjclActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize = 30;
    private int page = 1;

    CommonAdapter<GzrzListModel.DataBean.ListBean> myAdapter;
    private List<GzrzListModel.DataBean.ListBean> list = new ArrayList<>();
    GzrzListModel newsModel = null;

    String data = "";
    private boolean isButton = true;

    Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjcl);


        getData(page);
        initView();
    }

    private void initView() {
        tabName.setText("事件处理");


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
                Log.i(TAG, "response==" + response);
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
