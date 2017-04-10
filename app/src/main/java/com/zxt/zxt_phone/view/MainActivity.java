package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.view.fragment.ByCarFragment;
import com.zxt.zxt_phone.view.fragment.mMainFragment;
import com.zxt.zxt_phone.view.fragment.mMeFragment;
import com.zxt.zxt_phone.view.fragment.zXingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class MainActivity extends AppCompatActivity {


    private mMainFragment mainFragment;
    private ByCarFragment byCarFragment;
    private zXingFragment xingFragment;
    private mMeFragment meFragment;

    @BindView(R.id.rb_main)
    RadioButton rbMain;
    @BindView(R.id.rb_car)
    RadioButton rbCar;
    @BindView(R.id.rb_zxing)
    RadioButton rbZxing;
    @BindView(R.id.rb_me)
    RadioButton rbMe;

    @BindView(R.id.rg)
    RadioGroup mRg;
    //记录底部选中的按钮
    byte mCurrentIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //解决软件盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mainFragment = mMainFragment.newInstance();
        addFragment(mainFragment);

        initView();

    }

    private void initView() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_main:
                          mCurrentIndex = 0;
                        showFragment(mainFragment);
                        break;
                    case R.id.rb_car:
                        mCurrentIndex = 1;
                        if(byCarFragment == null){
                            byCarFragment = new ByCarFragment();
                            addFragment(byCarFragment);
                        }
                        showFragment(byCarFragment);
                        break;
                    case R.id.rb_zxing:
                        mCurrentIndex = 2;
                        if(xingFragment == null){
                            xingFragment = new zXingFragment();
                            addFragment(xingFragment);
                        }
                        showFragment(xingFragment);
                        break;
                    case R.id.rb_me:
                        mCurrentIndex = 3;
                        if(meFragment == null){
                            meFragment = new mMeFragment();
                            addFragment(meFragment);
                        }
                        showFragment(meFragment);
                        break;
                }
            }
        });
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.flComtent, fragment);
        transaction.commit();
    }

    private  void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(mainFragment);
        if(byCarFragment != null){
            transaction.hide(byCarFragment);
        }
        if(xingFragment != null){
            transaction.hide(xingFragment);
        }
        if(meFragment !=null){
            transaction.hide(meFragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

}

