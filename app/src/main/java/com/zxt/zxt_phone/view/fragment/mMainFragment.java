package com.zxt.zxt_phone.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.model.BannerModel;
import com.zxt.zxt_phone.bean.model.MarqueeModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.BmfwActivity;
import com.zxt.zxt_phone.view.BsznActivity;
import com.zxt.zxt_phone.view.CzjfActivity;
import com.zxt.zxt_phone.view.JgcxActivity;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.SqgwActivity;
import com.zxt.zxt_phone.view.SqtjActivity;
import com.zxt.zxt_phone.view.ZczxActivity;
import com.zxt.zxt_phone.view.ZwfwActivity;
import com.zxt.zxt_phone.view.customview.MyMarqueeView;
import com.zxt.zxt_phone.view.wyfw.RepairsActivity;
import com.zxt.zxt_phone.view.wyfw.WyfwActivity;

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


/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class mMainFragment extends BaseFragment {

    @BindView(R.id.return_back)
    TextView retBtn;
    @BindView(R.id.tab_name)
    TextView tabName;

//    @BindView(R.id.searchView)
//    SearchView searchView;
BannerHolderView holder;
//    @BindView(R.id.id_banner)
//    Banner mBanner;
    int num = 1;
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

    @BindView(R.id.tv_zwfw)
    LinearLayout tvZwfw;
    @BindView(R.id.tv_bmfw)
    LinearLayout tvBmfw;
    @BindView(R.id.tv_wyfw)
    LinearLayout tvWyfw;

    Intent mIntent;
    private List<BannerModel> mDatas = new ArrayList<>();

    List<MarqueeModel.DataNewsModel> info = new ArrayList<>();
String url;
    @BindView(R.id.dynic_marquee)
    MyMarqueeView marqueeView;

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

        getData();
        initView();
    }

    private void initView() {

        retBtn.setVisibility(View.GONE);
        tabName.setText(R.string.m_main_tab_name);

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


        marqueeView.setOnItemClickListener(new MyMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {

            }

            @Override
            public void onItemClick(int position, MarqueeModel.DataNewsModel item) {
//                    toast("点击"+position);
                String newsUrl = Url.URL+url+"?&TVInfoId="+SharedPrefsUtil.getString(getActivity(),   "TVInfoId")
                        +"&Key="+SharedPrefsUtil.getString(getActivity(), "Key")
                        +"&id="+item.getNewsId();

                Log.i("TAG","=================="+item.getNewsId());
                startActivity(new Intent(getActivity(), NewsDetailActivity.class)
                        .putExtra("title", info.get(position).getModuName())
                        .putExtra("url", newsUrl));
            }
        });
    }


    @OnClick({R.id.tv_zczx, R.id.tv_sqtj, R.id.tv_gscx, R.id.tv_bszn, R.id.tv_sqhd, R.id.tv_czjf, R.id.tv_sqds, R.id.tv_gzbx, R.id.tv_zwfw, R.id.tv_bmfw, R.id.tv_wyfw})
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
            case R.id.tv_zwfw://政务服务
                mIntent.setClass(getActivity(), ZwfwActivity.class);
                break;
            case R.id.tv_bmfw://便民服务
                mIntent.setClass(getActivity(), BmfwActivity.class);
                break;
            case R.id.tv_wyfw://物业服务
                mIntent.setClass(getActivity(), WyfwActivity.class);
                break;
        }
        startActivity(mIntent);
    }

    private void getData() {

//        http://oa.ybqtw.org.cn/api/APP1.0.aspx?
// &TVInfoId=19&method=IndexNews&PageSize=6&Page=1&Key=21218CCA77804D2BA1922C33E0151105
        HashMap<String,String> params = new HashMap<>();
        params.put("TVInfoId",SharedPrefsUtil.getString(getActivity(),"TVInfoId"));
        params.put("method","IndexNews");
        params.put("PageSize","6");
        params.put("Page","1");
        params.put("Key", SharedPrefsUtil.getString(getActivity(),"Key"));
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
                            MarqueeModel marqueeModel = new Gson().fromJson(response,MarqueeModel.class);

                            info.addAll(marqueeModel.getData());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        marqueeView.startWithList(info);
                    }
                });


    }
}
