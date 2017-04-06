package com.zxt.zxt_phone.view.zwfw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private int pageSize = 30;
    private int page = 1;
    CommonAdapter<GzrzListModel.ListBean> myAdapter;
    private List<GzrzListModel.ListBean> list = new ArrayList<>();
    GzrzListModel newsModel = null;

    Intent mIntent;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {

            case RESULT_OK:
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
        addText.setText(R.string.add_gzrz);
        addText.setVisibility(View.VISIBLE);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mActivity, AddGzrzActivity.class), 1);
            }
        });

        myAdapter = new CommonAdapter<GzrzListModel.ListBean>(mActivity, list, R.layout.gzrz_list_item) {
            @Override
            public void convert(ViewHolder holder, GzrzListModel.ListBean item) {
                holder.setText(R.id.Title, "· [" + item.getBlogName() + "]");
//                holder.setText(R.id.EditDate, item.getEditBlogDate());
                TextView tvEditDate = holder.getView(R.id.EditDate);
                tvEditDate.setText("编辑时间："+item.getEditBlogDate().substring(10,item.getEditBlogDate().length()));
                String myType = item.getBlogType();
                if (myType.length() != 0) {
                    if(myType.contains("1")){
                        holder.getView(R.id.tv_xuncha).setVisibility(View.VISIBLE);
                    }
                    if(myType.contains("2")){
                        holder.getView(R.id.tv_xuanchuan).setVisibility(View.VISIBLE);
                    }
                    if(myType.contains("3")){
                        holder.getView(R.id.tv_zoufang).setVisibility(View.VISIBLE);
                    }
                    if(myType.contains("4")){
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

        GzrzListModel.ListBean model = new GzrzListModel.ListBean();
        model.setBlogContent(newsModel.getList().get(position).getBlogContent());
        model.setBlogName(newsModel.getList().get(position).getBlogName());
        model.setBlogPic(newsModel.getList().get(position).getBlogPic());
        model.setBlogType(newsModel.getList().get(position).getBlogType());
        mIntent = new Intent(mActivity, GzrzInfoActivity.class);
        mIntent.putExtra("list", model);
        startActivity(mIntent);
    }

    /**
     * 获取数据
     *
     * @param page
     */
    private void getData(int page) {
//        http://192.168.1.220:8080/grid/app/blog/getOneStaffBlogById.do?pageSize=30&pageCurrent=1
        OkHttpUtils.get()
                .url(Url.URL_WG + "blog/getOneStaffBlogById.do?")
                .addParams("pageSize", pageSize + "")
                .addParams("pageCurrent", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                Log.i(TAG, "response==" + response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    if (!"0".equals(jsonObject.getString("Status"))) {
                    newsModel = new Gson().fromJson(response, GzrzListModel.class);
                    if (newsModel.getList() != null || newsModel.getList().size() != 0) {
//                                    list.clear();
                        list.addAll(newsModel.getList());
                    } else {

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
