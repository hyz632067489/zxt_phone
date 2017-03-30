package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.util.Log;
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
import com.zxt.zxt_phone.bean.model.NewsModel;
import com.zxt.zxt_phone.bean.model.TitleModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 工作管理
 */
public class GzglActivity extends BaseActivity {

    private  String TAG = GzglActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize=30;
    private int page  = 1;
    CommonAdapter<GzrzListModel.ListNewsModel> myAdapter;
    private List<GzrzListModel.ListNewsModel> list  = new ArrayList<>();
    GzrzListModel newsModel=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzgl);

        initView();
        getData(page);
    }


    private void initView() {

        tabName.setText(R.string.gzgl);
        myAdapter = new CommonAdapter<GzrzListModel.ListNewsModel>(mActivity, list, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, GzrzListModel.ListNewsModel item) {
                holder.setText(R.id.Title, "· [" + item.getBlogName() + "]" );
                holder.setText(R.id.EditDate, item.getEditBlogDate());

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

    private void getData(int page) {
//        http://192.168.1.220:8080/grid/app/blog/getOneStaffBlogById.do?pageSize=30&pageCurrent=1
        OkHttpUtils.get()
                .url(Url.URL_WG+"blog/getOneStaffBlogById.do?")
                .addParams("pageSize",pageSize+"")
                .addParams("pageCurrent",page+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                Log.i(TAG,"response=="+response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    if (!"0".equals(jsonObject.getString("Status"))) {
                        newsModel = new Gson().fromJson(response, GzrzListModel.class);
                        if (newsModel.getList() != null || newsModel.getList().size() != 0) {
//                                    list.clear();
                            list.addAll(newsModel.getList());
                        }else {

                        }
//                    }
                    myAdapter.notifyDataSetChanged();

                    mRefreshView.onFooterLoadFinish();
                    mRefreshView.onHeaderRefreshFinish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
