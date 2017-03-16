package com.zxt.zxt_phone.view;

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
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.TitleAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.NewsModel;
import com.zxt.zxt_phone.bean.model.TitleModel;
import com.zxt.zxt_phone.view.customview.HorizontalListView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

import static com.zxt.zxt_phone.R.id.refreshView;

public class NewsListActivity extends BaseActivity {


    private String TAG = NewsListActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(refreshView)
    PullToRefreshView mRefreshView;

    @BindView(R.id.newList)
    ListView mListView;
    @BindView(R.id.hlistview)
    HorizontalListView hListview;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;

    Intent mIntent;

    private List<NewsModel.DataNewsModel> list = new ArrayList<>();
    private List<TitleModel.DataNewsTitleModel> listTitle = new ArrayList<>();
    TitleAdapter titleAdapter;
    CommonAdapter<NewsModel.DataNewsModel> myAdapter;

    private int page = 1; //页数
    private int CountNum = 1; //总条数
    String Deptid;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mIntent = getIntent();
        //获取数据
        titleNews();
//        IndexNews(page);
        initView();
    }

    private void initView() {
        tabName.setText(mIntent.getStringExtra("title"));

        //选择title
        titleAdapter = new TitleAdapter(mActivity, listTitle);
        hListview.setAdapter(titleAdapter);
        hListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                titleAdapter.setSelectItem(position);
                titleAdapter.notifyDataSetChanged();
                page = 1;
                list.clear();
                Deptid = String.valueOf(listTitle.get(position).getDeptid());
                IndexNews(page,Deptid);
            }
        });


        //列表
        myAdapter = new CommonAdapter<NewsModel.DataNewsModel>(mActivity, list, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, NewsModel.DataNewsModel item) {
                holder.setText(R.id.Title, item.getTitle());
                holder.setText(R.id.EditDate, item.getEditDate());
            }
        };

        mListView.setAdapter(myAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String newsUrl = getString(R.string.Url)+url+"?&TVInfoId="+SharedPrefsUtil.getValue(NewsListActivity.this, "userinfo", "TVInfoId", "")
//                        +"&Key="+SharedPrefsUtil.getValue(NewsListActivity.this, "userinfo", "Key", "")+"&id="+list.get(arg2).get("NewsId");
//
////                MyLogger.showloge("==="+newsUrl);
//
//                startActivity(new Intent(getApplicationContext(), NewsDetailActivity.class)
//                        .putExtra("title", list.get(position).getModuName()).putExtra("url", newsUrl));
//            }
//        });



        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                list.clear();
                IndexNews(page,Deptid);
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                page ++;
                IndexNews(page,Deptid);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    /**
     * 数据table列表
     */
    public void titleNews() {
        url = "http://oa.ybqtw.org.cn/api/APP1.0.aspx?";
        OkHttpUtils.get()
                .url(url)
                .addParams("method", "DeptNewsTab")
//                .addParams("TVInfoId", SharedPrefsUtil.getValue(this, "userinfo", "TVInfoId", ""))
//                .addParams("Key", SharedPrefsUtil.getValue(this, "userinfo", "Key", ""))
                .addParams("TVInfoId", "19")
                .addParams("Key", "21218CCA77804D2BA1922C33E0151105")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "response==title====" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!"0".equals(jsonObject.getString("Status"))) {

                        Gson gson = new Gson();
                        TitleModel titleModel = gson.fromJson(response, TitleModel.class);
                        titleModel.getData().get(0).setDeptid(0);
                        titleModel.getData().get(0).setDeptName("所有部门");

                        //数据添加到list<>中
                        listTitle.addAll(titleModel.getData());

                        titleAdapter.notifyDataSetChanged();

                        Deptid = String.valueOf(titleModel.getData().get(0).getDeptid());
                        Log.i(TAG, "Deptid======" + Deptid);
                    }


                    IndexNews(page, Deptid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 数据列表
     */
    public void IndexNews(final int page, String Deptid) {

        url = "http://oa.ybqtw.org.cn/api/APP1.0.aspx?";
        // &TVInfoId=19
        // &method=IndexNews
        // &PageSize=10
        // &Page=1
        // &ClassId=1
        // &Deptid=
        // &Key=21218CCA77804D2BA1922C33E0151105
        OkHttpUtils.get()
                .url(url)
                .addParams("method", "IndexNews")
//                .addParams("TVInfoId", SharedPrefsUtil.getValue(this, "userinfo", "TVInfoId", ""))
//                .addParams("Key", SharedPrefsUtil.getValue(this, "userinfo", "Key", ""))
                .addParams("TVInfoId", "19")
                .addParams("Key", "21218CCA77804D2BA1922C33E0151105")
                .addParams("Page", page + "")
                .addParams("PageSize", "10")
//                .addParams("ClassId", getIntent().getStringExtra("numberId"))
                .addParams("ClassId", "4")
                .addParams("Deptid", Deptid=="0" ? "":Deptid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response======" + response);
                        try {
                            Gson gson = null;
                            NewsModel newsModel = null;
                            JSONObject jsonObject = new JSONObject(response);
                            if (!"0".equals(jsonObject.getString("Status"))) {
                                gson = new Gson();
                                 newsModel = gson.fromJson(response, NewsModel.class);

                                list.addAll(newsModel.getData());
                                Log.i(TAG, "list=1=" + list.get(0).getDeptName().toString());
                            }

                            myAdapter.notifyDataSetChanged();

                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }



    private void parseJSONWithJSONObject(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray;
            if (!"0".equals(jsonObject.getString("Status"))) {
                url = jsonObject.getString("NewsShowUrl");
                CountNum = Integer.parseInt(jsonObject.getString("CountNum"));

                if (!jsonObject.getString("Data").equals("[]")) {
                    jsonArray = jsonObject.getJSONArray("Data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gson gson = new Gson();
                        JSONObject datas = jsonArray.getJSONObject(i);
//                        list.add(gson.toJson(datas));
                        String title = datas.getString("Title");
                        Log.i(TAG, "titile====" + title);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
