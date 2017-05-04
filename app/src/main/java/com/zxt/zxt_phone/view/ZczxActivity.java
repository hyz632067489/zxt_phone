package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.zxt.zxt_phone.bean.model.ZczxModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.HorizontalListView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;
import okhttp3.Call;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class ZczxActivity extends BaseActivity {


    private String TAG = ZczxActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.banner_holder)
    BannerHolderView holder;

    @BindView(R.id.gv_list)
    GridView gvList;

    private List<ZczxModel> mDatas;

    private String[] titles = {"渝北资讯", "龙塔资讯", "社区资讯"};
    private int[] images = {R.drawable.m_zczx, R.drawable.m_sqhd, R.drawable.m_sqds};

    Intent mIntent;

    @BindView(R.id.hlistview)
    HorizontalListView hlistview;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.newList)
    ListView newsList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    private List<NewsModel.DataNewsModel> list = new ArrayList<>();
    private List<TitleModel.DataNewsTitleModel> listTitle = new ArrayList<>();
    TitleAdapter titleAdapter;
    CommonAdapter<NewsModel.DataNewsModel> myAdapter;
    NewsModel newsModel = null;

    private int page = 1; //页数
    private int CountNum = 1; //总条数
    String Deptid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zczx_activity);
        ButterKnife.bind(this);

        initDatas();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.zczx);

        HolderAttr.Builder builder = holder.getHolerAttr();//获取Holder配置参数构建对象
        builder.setSwitchDuration(900)//设置切换Banner的持续时间
                .setAutoLooper(true)//开启自动轮播
                .setLooperTime(1500)//设置轮播间隔时间
                .setBannerClickListenenr(new BannerClickListenenr() {//Banner图片点击事件
                    @Override
                    public void onBannerClick(int p) {
                        //p: 页面索引
                    }
                });
        holder.setHolerAttr(builder);
        List<Bitmap> mpas = new ArrayList<>();
        //测试Bitmap
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao1));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao2));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao3));
        //设置图片集合
        holder.setHolderBitmaps(mpas);


        //3个功能块 列表
        gvList.setAdapter(new CommonAdapter<ZczxModel>(getApplication(), mDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, ZczxModel item) {

                holder.setImageResource(R.id.im_item, item.getImage());
                holder.setText(R.id.tv_item, item.getName());
            }
        });

        //title加载
        titleAdapter = new TitleAdapter(mActivity, listTitle);
        hlistview.setAdapter(titleAdapter);
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleAdapter.setSelectItem(position);
                titleAdapter.notifyDataSetChanged();

                Deptid = String.valueOf(listTitle.get(position).getDeptid());
                list.clear();
                page = 1;
                NewsDatas(page, Deptid);
            }
        });

        //新闻列表
        myAdapter = new CommonAdapter<NewsModel.DataNewsModel>(mActivity, list, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, NewsModel.DataNewsModel item) {
                holder.setText(R.id.Title, "· [" + item.getDeptName() + "]" + item.getTitle());
                holder.setText(R.id.EditDate, item.getEditDate());

            }
        };
        newsList.setAdapter(myAdapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newsUrl = Url.URL + newsModel.getNewsShowUrl() + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                        + "&id=" + list.get(position).getNewsId();

                Log.i("TAG", "==================" + newsUrl);
                startActivity(new Intent(mActivity, NewsDetailActivity.class)
                        .putExtra("title", list.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });

        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                list.clear();
                NewsDatas(page, Deptid);
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                page++;
                NewsDatas(page, Deptid);
            }
        });

    }

    @OnItemClick(R.id.gv_list)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                startActivity(new Intent(this, NewsListActivity.class).putExtra("title", "国际视野").putExtra("numberId", "1"));
                break;
            case 1:
                startActivity(new Intent(this, NewsListActivity.class).putExtra("title", "渝北资讯").putExtra("numberId", "4"));
                break;
            case 2:
                startActivity(new Intent(this, NewsListActivity.class).putExtra("title", "渝北资讯").putExtra("numberId", "4"));
                break;
        }
    }


    /**
     * 初始化数据源
     */
    private void initDatas() {

        //获取title数据
        TitleDatas();

        mDatas = new ArrayList<ZczxModel>();
        ZczxModel zczxModel = null;
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            zczxModel = new ZczxModel();
            zczxModel.setName(titles[i]);
            zczxModel.setImage(images[i]);
            mDatas.add(zczxModel);
        }
    }

    //-------------------------------数据获取----------------------

    /**
     * title 数据获取
     */
    public void TitleDatas() {
        OkHttpUtils
                .get()
                .url(Url.URL_PT)
                .addParams("method", "DeptNewsTab")
                .addParams("TVInfoId", SharedPrefsUtil.getString(mContext,"TVInfoId"))
                .addParams("Key", SharedPrefsUtil.getString(mContext,"Key"))
//                .addParams("TVInfoId", "19")
//                .addParams("Key", "21218CCA77804D2BA1922C33E0151105")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response=title=====" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!"0".equals(obj.getString("Status"))) {
                                TitleModel titleModel = new Gson().fromJson(response, TitleModel.class);
                                titleModel.getData().get(0).setDeptid(0);
                                titleModel.getData().get(0).setDeptName("全部");

                                listTitle.addAll(titleModel.getData());
                                titleAdapter.notifyDataSetChanged();
                                Deptid = String.valueOf(listTitle.get(0).getDeptid());
                            }

                            NewsDatas(page, Deptid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 列表数据的获取
     */
    public void NewsDatas(int page, String Deptid) {
//        showLoading("数据加载中...");
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .addParams("method", "IndexNews")
                .addParams("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"))
                .addParams("Key", SharedPrefsUtil.getString(mActivity, "Key"))
                .addParams("Page", page + "")
                .addParams("PageSize", "10")
//                .addParams("ClassId", getIntent().getStringExtra("numberId"))
                .addParams("ClassId", "4")
                .addParams("Deptid", Deptid == "0" ? "" : Deptid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        if (null != mRefreshView) {
                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response======" + response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (!"0".equals(jsonObject.getString("Status"))) {
                                newsModel = new Gson().fromJson(response, NewsModel.class);
                                if (newsModel.getData() != null || newsModel.getData().size() != 0) {
//                                    list.clear();
                                    list.addAll(newsModel.getData());
                                }
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


}
