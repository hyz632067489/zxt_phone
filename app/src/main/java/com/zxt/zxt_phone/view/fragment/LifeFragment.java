package com.zxt.zxt_phone.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.utils.DensityUtil;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.view.fragment.life.ShenghuoFragment;
import com.zxt.zxt_phone.view.fragment.life.serveFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.R.attr.offset;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class LifeFragment extends BaseFragment {

    View view;
    Unbinder unbinder;

    @BindView(R.id.viewpager)
    ViewPager mPager;

    @BindView(R.id.tv_tab_1)
    TextView tvTab_1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab_2;

    @BindView(R.id.iv_bottom_line)
    ImageView ivBottomLine;

    private ArrayList<Fragment> fragmentsList;
    private int currIndex = 0;

    private int bottomLineWidth;
    private int offset = 0;
    private int position_one, position_two;
    public final static int num = 2;

    ShenghuoFragment shenghuoFrag;
    serveFragment serveFra;

    Resources resources;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.life_fragment_view, null);
        unbinder = ButterKnife.bind(this, view);
        resources = getResources();
        initWidth();

        return view;
    }

    private void initWidth() {
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;


        offset = (int) (screenW / num - bottomLineWidth);
        int avg = (int) (screenW / num);
        position_one = avg  + DensityUtil.dip2px(getContext(), 5);
        position_two = avg - bottomLineWidth - DensityUtil.dip2px(getContext(), 5);

    }


    @Override
    public void onStart() {
        super.onStart();
        initView();
        initViewPager();
        TranslateAnimation animation = new TranslateAnimation(position_one, position_two, 0, 0);
        tvTab_1.setTextColor(resources.getColor(R.color.red));
        animation.setFillAfter(true);
        animation.setDuration(300);
        ivBottomLine.startAnimation(animation);
    }


    private void initView() {


        tvTab_1.setOnClickListener(new MyOnClickListener(0));
        tvTab_2.setOnClickListener(new MyOnClickListener(1));
    }


    private void initViewPager() {
        fragmentsList = new ArrayList<>();
        shenghuoFrag = new ShenghuoFragment();
        serveFra = new serveFragment();
        fragmentsList.add(shenghuoFrag);
        fragmentsList.add(serveFra);
        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));

        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);

    }


    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    ;

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        tvTab_1.setTextColor(getResources().getColor(R.color.red));
                        MLog.i("initWidth", "currIndex==" + currIndex);
                    }
                    tvTab_2.setTextColor(getResources().getColor(R.color.white));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        tvTab_2.setTextColor(getResources().getColor(R.color.red));
                        MLog.i("initWidth", "currIndex==" + currIndex);
                    }
                    tvTab_1.setTextColor(getResources().getColor(R.color.white));
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentsList;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
