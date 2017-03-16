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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.model.BannerModel;
import com.zxt.zxt_phone.view.BmfwActivity;
import com.zxt.zxt_phone.view.BsznActivity;
import com.zxt.zxt_phone.view.CzjfActivity;
import com.zxt.zxt_phone.view.SqgwActivity;
import com.zxt.zxt_phone.view.ZczxActivity;
import com.zxt.zxt_phone.view.ZwfwActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;


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
    @BindView(R.id.tv_yjtj)
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
    TextView tvZwfw;
    @BindView(R.id.tv_bmfw)
    TextView tvBmfw;
    @BindView(R.id.tv_wyfw)
    TextView tvWyfw;

    Intent mIntent;
    private List<BannerModel> mDatas = new ArrayList<>();

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
    }



    private void getData() {
        mDatas.clear();
        BannerModel model = null;
        model = new BannerModel();
        model.setImageUrl("https://gma.alicdn.com/simba/img/TB1FS.AJpXXXXc_XpXXSutbFXXX.jpg_q50.jpg");
        model.setTips("这是页面1");
        mDatas.add(model);
        model = new BannerModel();
        model.setImageUrl("https://gw.alicdn.com/tps/i3/TB1J9GqJXXXXXcZaXXXdIns_XXX-1125-352.jpg_q50.jpg");
        model.setTips("这是页面2");
        mDatas.add(model);
        model = new BannerModel();
        model.setImageUrl("https://gma.alicdn.com/simba/img/TB1txffHVXXXXayXVXXSutbFXXX.jpg_q50.jpg");
        model.setTips("这是页面3");
        mDatas.add(model);
        model = new BannerModel();
        model.setImageUrl("https://gw.alicdn.com/tps/TB1fW3ZJpXXXXb_XpXXXXXXXXXX-1125-352.jpg_q50.jpg");
        model.setTips("这是页面4");
        mDatas.add(model);
        model = new BannerModel();
        model.setImageUrl("https://gw.alicdn.com/tps/i2/TB1ku8oMFXXXXciXpXXdIns_XXX-1125-352.jpg_q50.jpg");
        model.setTips("这是页面5");
        mDatas.add(model);
//        mBanner.notifiDataHasChanged();
    }


    @OnClick({R.id.tv_zczx, R.id.tv_yjtj, R.id.tv_gscx, R.id.tv_bszn, R.id.tv_sqhd, R.id.tv_czjf, R.id.tv_sqds, R.id.tv_gzbx, R.id.tv_zwfw, R.id.tv_bmfw, R.id.tv_wyfw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zczx://政策信息
                mIntent = new Intent(getActivity(), ZczxActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_yjtj://意见提交
                break;
            case R.id.tv_gscx://查询公示
                break;
            case R.id.tv_bszn://办事指南
                mIntent = new Intent(getActivity(), BsznActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_sqhd://社区活动
                break;
            case R.id.tv_czjf://充值缴费
                mIntent = new Intent(getActivity(), CzjfActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_sqds://社区电商
                mIntent = new Intent(getActivity(), SqgwActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_gzbx://故障报修
                break;
            case R.id.tv_zwfw://政务服务
                mIntent = new Intent(getActivity(), ZwfwActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_bmfw://便民服务
                mIntent = new Intent(getActivity(), BmfwActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_wyfw://物业服务
                break;
        }
    }
}
