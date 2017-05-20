package com.zxt.zxt_phone.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.GridviewAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.adapter.ViewPagerAdapter;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.TipDataModel;
import com.zxt.zxt_phone.bean.model.BsznModel;
import com.zxt.zxt_phone.bean.model.MarqueeModel;
import com.zxt.zxt_phone.bean.model.NewsModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.BmfwActivity;
import com.zxt.zxt_phone.view.BsznActivity;
import com.zxt.zxt_phone.view.CaptureActivity;
import com.zxt.zxt_phone.view.JgcxActivity;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.SqgwActivity;
import com.zxt.zxt_phone.view.TestActivity;
import com.zxt.zxt_phone.view.ViewCustomActivity;
import com.zxt.zxt_phone.view.WsbsActivity;
import com.zxt.zxt_phone.view.ZczxActivity;
import com.zxt.zxt_phone.view.ZwfwActivity;
import com.zxt.zxt_phone.view.bmfw.CzjfActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.MyListView;
import com.zxt.zxt_phone.view.customview.MyMarqueeView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;
import com.zxt.zxt_phone.view.wyfw.JftjActivity;
import com.zxt.zxt_phone.view.wyfw.PjglActivity;
import com.zxt.zxt_phone.view.wyfw.RepairsActivity;
import com.zxt.zxt_phone.view.wyfw.SafetyActivity;
import com.zxt.zxt_phone.view.wyfw.WyfwActivity;
import com.zxt.zxt_phone.view.wyfw.WyggActivity;
import com.zxt.zxt_phone.view.zwfw.DqfcActivity;
import com.zxt.zxt_phone.view.zwfw.pasq.PasqActivity;
import com.zxt.zxt_phone.view.zwfw.sqgk.SqgkActivity;
import com.zxt.zxt_phone.view.zwfw.sqtj.SqtjActivity;
import com.zxt.zxt_phone.view.zwfw.yybs.YybsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;
import okhttp3.Call;

import static com.zxt.zxt_phone.constant.Common.REQUEST_CODE;
import static com.zxt.zxt_phone.constant.Common.RESULT_OK;


/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class mMainFragment extends BaseFragment {

    @BindView(R.id.return_back)
    TextView retBtn;
    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.sign_in)
    TextView zxing;
    //    @BindView(R.id.searchView)
//    SearchView searchView;
    BannerHolderView holder;
    //    @BindView(R.id.id_banner)
//    Banner mBanner;
    int num = 1;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindView(R.id.tv_zczx)
    TextView tvZczx;
    @BindView(R.id.tv_sqtj)
    TextView tvYjtj;
    @BindView(R.id.tv_gscx)
    TextView tvGscx;
    @BindView(R.id.tv_bszn)
    TextView tvBszn;
    @BindView(R.id.tv_sqhd)
    TextView tvSqhd;
    @BindView(R.id.tv_czjf)
    TextView tvCzjf;
    @BindView(R.id.tv_sqds)
    TextView tvSqds;
    @BindView(R.id.tv_gzbx)
    TextView tvGzbx;


    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_dot)
    LinearLayout mLlDot;


    Intent mIntent;


    List<MarqueeModel.DataNewsModel> mDataNews = new ArrayList<>();
    String url;
    MarqueeModel marqueeModel;
    CommonAdapter<MarqueeModel.DataNewsModel> myAdapter;

    @BindView(R.id.dynic_marquee)
    MyMarqueeView marqueeView;


    @BindView(R.id.re_pager)
    RelativeLayout rePager;
    @BindView(R.id.rb_zc_news)
    RadioButton rbZcNews;
    @BindView(R.id.rb_tong_zhi)
    RadioButton rbTongZhi;
    @BindView(R.id.rb_jx_news)
    RadioButton rbJxNews;
    @BindView(R.id.rd_g)
    RadioGroup rdG;

    @BindView(R.id.list_view)
    MyListView listview;
//    @BindView(R.id.refreshView)
//    PullToRefreshView mRefreshView;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;


    @BindView(R.id.grid_view)
    GridView gridView;

    CommonAdapter<MarqueeModel.DataNewsModel> gridAdapter;
    List<MarqueeModel.DataNewsModel> mDataNewsList = new ArrayList<>();
    List<MarqueeModel.DataNewsModel> mDataNewsGrid = new ArrayList<>();




    private List<View> mPagerList;
    private List<BsznModel> mDatas;

    private LayoutInflater mInflater;

    private int page = 1; //页数

    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;


    public mMainFragment() {

    }

    public static mMainFragment newInstance() {
        return new mMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.m_main_activity, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holder = (BannerHolderView) view.findViewById(R.id.banner_holder);

        //获取垂直轮播数据
        getData();

        //获取新闻数据
        getDataNewsList(page);

        initView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  //这个super可不能落下，否则可能回调不了
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    MLog.i("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    MLog.i("解析失败:");
                }
            }
        } else if (resultCode == RESULT_OK) {
            Bundle b = data.getExtras(); //data为B中回传的Intent
//            String str=b.getString("str1");//str即为回传的值
        }
    }


    private void initView() {

        retBtn.setVisibility(View.GONE);
        tabName.setText(R.string.m_main_tab_name);

        zxing.setVisibility(View.VISIBLE);
        zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        //轮播图
        setBanner();
        //仿美团功能选择切换
        setPagerFunction();
        //垂直轮播图
        setMarqueeView();
//设置adapter
        setAdapter();

        //点击事件
        setOnListener();
//        listview.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    scrollView.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    scrollView.requestDisallowInterceptTouchEvent(true);
//                }
//                return false;
//            }
//        });

//        //下来刷新，上啦加载跟多
//        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
//            @Override
//            public void onHeaderRefresh(PullToRefreshView view) {
//                page = 1;
//                mDataNews.clear();
//                getDataNewsList(page);
//            }
//        });
//        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
//            @Override
//            public void onFooterLoad(PullToRefreshView view) {
//                page++;
//                getDataNewsList(page);
//            }
//        });

    }

    private void setAdapter() {

        myAdapter = new CommonAdapter<MarqueeModel.DataNewsModel>(getContext(), mDataNewsList, R.layout.news_list_item) {
            @Override
            public void convert(ViewHolder holder, MarqueeModel.DataNewsModel item) {
                holder.setText(R.id.Title, "· [" + item.getDeptName() + "]" + item.getTitle());
                holder.setText(R.id.EditDate, item.getEditDate());
            }
        };
        listview.setAdapter(myAdapter);


        gridAdapter = new CommonAdapter<MarqueeModel.DataNewsModel>(getContext(), mDataNewsGrid, R.layout.news_grid_remen_item) {
            @Override
            public void convert(ViewHolder holder, MarqueeModel.DataNewsModel item) {
                holder.setImageByUrl(R.id.iv_icon, item.getImageIndex());
                holder.setText(R.id.tv_title, item.getEditDate());
            }
        };
        gridView.setAdapter(gridAdapter);
    }

    /**
     * 点击事件
     */
    private void setOnListener() {
        rdG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rbZcNews.getId() == checkedId) {
                    getDataNewsList(page);

                } else if (rbTongZhi.getId() == checkedId) {
                    getDataNewsList(page);

                } else if (rbJxNews.getId() == checkedId) {
                    getDataNewsList(page);

                }
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newsUrl = Url.URL + marqueeModel.getNewsShowUrl() + "?&TVInfoId=" + SharedPrefsUtil.getString(getActivity(), "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(getActivity(), "Key")
                        + "&id=" + mDataNewsList.get(position).getNewsId();

                MLog.i("TAG", "==================" + newsUrl);

                //名字要修改
                startActivity(new Intent(getActivity(), NewsDetailActivity.class)
                        .putExtra("title", mDataNewsList.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newsUrl = Url.URL + marqueeModel.getNewsShowUrl() + "?&TVInfoId=" + SharedPrefsUtil.getString(getActivity(), "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(getActivity(), "Key")
                        + "&id=" + mDataNewsGrid.get(position).getNewsId();

                MLog.i("TAG", "==================" + newsUrl);
                startActivity(new Intent(getActivity(), NewsDetailActivity.class)
                        .putExtra("title", mDataNewsGrid.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });
    }


    /**
     * 轮播图
     */
    private void setBanner() {
        HolderAttr.Builder builder = holder.getHolerAttr();//获取Holder配置参数构建对象
        builder.setSwitchDuration(900)//设置切换Banner的持续时间
                .setAutoLooper(true)//开启自动轮播
                .setLooperTime(1000)//设置轮播间隔时间
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
    }


    /**
     * 仿美团功能选择切换
     */
    private void setPagerFunction() {

        rePager.setVisibility(View.VISIBLE);

        //初始化数据源
        initDatas();
        mInflater = LayoutInflater.from(getActivity());
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) mInflater.inflate(R.layout.gridview_1, mViewPager, false);
            gridView.setAdapter(new GridviewAdapter(getContext(), mDatas, i, pageSize));
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    toast(mDatas.get(pos).getText() + "==" + pos + "==" + position);
                    String name = mDatas.get(pos).getText();

                    goActivity(name);

                }
            });
        }

        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }




    /**
     * 垂直轮播图
     */
    private void setMarqueeView() {
        marqueeView.setOnItemClickListener(new MyMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {

            }

            @Override
            public void onItemClick(int position, MarqueeModel.DataNewsModel item) {
//                    toast("点击"+position);
                String newsUrl = Url.URL + url + "?&TVInfoId=" + SharedPrefsUtil.getString(getActivity(), "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(getActivity(), "Key")
                        + "&id=" + item.getNewsId();

                MLog.i("TAG", "==================" + item.getNewsId());
                startActivity(new Intent(getActivity(), NewsDetailActivity.class)
                        .putExtra("title", mDataNews.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });
    }


    /**
     * 切换功能界面
     *
     * @param name
     */
    private void goActivity(String name) {
        //                    {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事", "办事指南"
//                                , "平安社区", "群团服务", "群攻平台", "精准帮扶"
//                                , "网格管理", "社区电商", "社区活动", "社区交流"
//                                , "家庭维修", "家教", "二手交易", "就业服务"
//                                , "智能家居", "快递代收", "公交路线", "充值缴费"
//                                , "家政保洁", "关于物业", "物业公告", "安全管理"
//                                , "故障报修", "纠纷调解", "评价调研"} ;

        mIntent = new Intent();
        if ("社区概况".equals(name)) {
            mIntent.setClass(getActivity(), SqgkActivity.class);
        } else if ("党群风采".equals(name)) {
            mIntent.setClass(getActivity(), DqfcActivity.class);
        } else if ("诉求提交".equals(name)) {
            mIntent.setClass(getActivity(), SqtjActivity.class);
        } else if ("结果查询".equals(name)) {
            mIntent.setClass(getActivity(), JgcxActivity.class);
        } else if ("政策信息".equals(name)) {
            mIntent.setClass(getActivity(), ZczxActivity.class);

        } else if ("预约办事".equals(name)) {
            mIntent.setClass(getActivity(), YybsActivity.class);
        } else if ("办事指南".equals(name)) {
            mIntent.setClass(getActivity(), BsznActivity.class);
        } else if ("平安社区".equals(name)) {
            mIntent.setClass(getActivity(), PasqActivity.class);
        } else if ("群团服务".equals(name)) {

        } else if ("群攻平台".equals(name)) {

        } else if ("精准帮扶".equals(name)) {

        } else if ("网格管理".equals(name)) {
            mIntent.setClass(getActivity(), WsbsActivity.class);
        } else if ("社区电商".equals(name)) {
            mIntent.setClass(getActivity(), SqgwActivity.class);
        } else if ("社区活动".equals(name)) {
            mIntent.setClass(getActivity(), TestActivity.class);
        } else if ("社区交流".equals(name)) {

        } else if ("家庭维修".equals(name)) {

        } else if ("家教".equals(name)) {

        } else if ("二手交易".equals(name)) {

        } else if ("就业服务".equals(name)) {

        } else if ("智能家居".equals(name)) {

        } else if ("快递代收".equals(name)) {

        } else if ("公交路线".equals(name)) {

        } else if ("充值缴费".equals(name)) {
            mIntent.setClass(getActivity(), CzjfActivity.class);
        } else if ("家政保洁".equals(name)) {

        } else if ("关于物业".equals(name)) {
//            mIntent.setClass(getActivity(), NewsDetailActivity.class);
//            mIntent.putExtra("title", "关于物业");
//            mIntent.putExtra("url", Url.BASE_URL_HTML + "GyPro.aspx"+"?id="+choseId);
        } else if ("物业公告".equals(name)) {
            mIntent.setClass(getActivity(), WyggActivity.class);
        } else if ("安全管理".equals(name)) {
            mIntent.setClass(getActivity(), SafetyActivity.class);
        } else if ("故障报修".equals(name)) {
            mIntent.setClass(getActivity(), RepairsActivity.class);
        } else if ("纠纷调解".equals(name)) {
            mIntent.setClass(getActivity(), JftjActivity.class);
        } else if ("评价调研".equals(name)) {
            mIntent.setClass(getActivity(), PjglActivity.class);
        } else if ("全部分类".equals(name)) {
            mIntent.setClass(getActivity(), ViewCustomActivity.class);
//            mIntent.putExtra("title", (Serializable) mDatas);
            startActivityForResult(mIntent, 200);
            return;
        }
        startActivity(mIntent);

    }


    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<BsznModel>();
        mDatas.clear();
        mDatas.addAll(TipDataModel.getAll());

    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    /**
     * 获取垂直轮播数据
     */
    private void getData() {

//        http://oa.ybqtw.org.cn/api/APP1.0.aspx?
// &TVInfoId=19&method=IndexNews&PageSize=6&Page=1&Key=21218CCA77804D2BA1922C33E0151105
        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(getActivity(), "TVInfoId"));
        params.put("method", "IndexNews");
        params.put("PageSize", "6");
        params.put("Page", String.valueOf(page));
        params.put("Key", SharedPrefsUtil.getString(getActivity(), "Key"));
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("Data");
                            url = obj.getString("NewsShowUrl");
                            marqueeModel = new Gson().fromJson(response, MarqueeModel.class);

                            mDataNews.addAll(marqueeModel.getData());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        marqueeView.startWithList(mDataNews);
                    }
                });
    }

    /**
     * 获取新闻数据
     */
    private void getDataNewsList(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(getActivity(), "TVInfoId"));
        params.put("method", "IndexNews");
        params.put("PageSize", "6");
        params.put("Page", String.valueOf(page));
        params.put("Key", SharedPrefsUtil.getString(getActivity(), "Key"));
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        if (null != mRefreshView) {
//                            mRefreshView.onFooterLoadFinish();
//                            mRefreshView.onHeaderRefreshFinish();
//                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(getTag(), "response==" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            marqueeModel = new Gson().fromJson(response, MarqueeModel.class);
                            if ("1".equals(obj.getString("Status"))) {
                                JSONArray array = obj.getJSONArray("Data");
                                url = obj.getString("NewsShowUrl");
                                mDataNewsList.clear();
                                mDataNewsList.addAll(marqueeModel.getData());

                                mDataNewsGrid.clear();
                                for (int i = 0; i < 2; i++) {

                                    mDataNewsGrid.add(mDataNewsList.get(i));
                                }


                                myAdapter.notifyDataSetChanged();
                                gridAdapter.notifyDataSetChanged();


                            } else {
                                toast(obj.getString("Message"));
                                listview.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }


    @OnClick({R.id.tv_zczx, R.id.tv_sqtj, R.id.tv_gscx, R.id.tv_bszn, R.id.tv_sqhd, R.id.tv_czjf, R.id.tv_sqds, R.id.tv_gzbx})
    public void onClick(View view) {
        mIntent = new Intent();
        switch (view.getId()) {
            case R.id.tv_zczx://政策信息
                mIntent.setClass(getActivity(), ZczxActivity.class);
                break;
            case R.id.tv_sqtj://诉求提交
                mIntent.setClass(getActivity(), SqtjActivity.class);
                break;
            case R.id.tv_gscx://结果查询
                mIntent.setClass(getActivity(), JgcxActivity.class);
                break;
            case R.id.tv_bszn://办事指南
                mIntent.setClass(getActivity(), BsznActivity.class);
                break;
            case R.id.tv_sqhd://社区活动
                mIntent.setClass(getActivity(), TestActivity.class);
                break;
            case R.id.tv_czjf://充值缴费
                mIntent.setClass(getActivity(), CzjfActivity.class);
                break;
            case R.id.tv_sqds://社区电商
                mIntent.setClass(getActivity(), SqgwActivity.class);
                break;
            case R.id.tv_gzbx://故障报修
                mIntent.setClass(getActivity(), RepairsActivity.class);
                break;

        }
        startActivity(mIntent);

    }


}
