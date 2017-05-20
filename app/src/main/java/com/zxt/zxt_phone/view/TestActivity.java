package com.zxt.zxt_phone.view;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BottomTabBaseActivity;
import com.zxt.zxt_phone.base.bottom.BottomTabView;
import com.zxt.zxt_phone.view.fragment.GovernmentFragment;
import com.zxt.zxt_phone.view.fragment.mMainFragment;
import com.zxt.zxt_phone.view.fragment.mMeFragment;
import com.zxt.zxt_phone.view.fragment.LifeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hkc on 2017/5/15.
 */

public class TestActivity extends BottomTabBaseActivity {


    @Override
    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_back));
        tabItemViews.add(new BottomTabView.TabItemView(this, "政务服务", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_back));
        tabItemViews.add(new BottomTabView.TabItemView(this, "电商", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_back));
        tabItemViews.add(new BottomTabView.TabItemView(this, "个人中心", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_back));
        return tabItemViews;
    }




    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new mMainFragment());
        fragments.add(new GovernmentFragment());
        fragments.add(new mMeFragment());
        fragments.add(new LifeFragment());
        return fragments;
    }

    @Override
    protected View getCenterView() {
        ImageView centerView = new ImageView(this);
        centerView.setImageResource(R.mipmap.ic_del);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        layoutParams.leftMargin = 60;
        layoutParams.rightMargin = 60;
        layoutParams.bottomMargin = 0;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return centerView;
    }
}
