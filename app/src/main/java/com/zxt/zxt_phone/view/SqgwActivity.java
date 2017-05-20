package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.view.fragment.GovernmentFragment;
import com.zxt.zxt_phone.view.fragment.mMeFragment;
import com.zxt.zxt_phone.view.fragment.shequdianshang.ShoucangFragment;
import com.zxt.zxt_phone.view.fragment.shequdianshang.SqcsFragment;
import com.zxt.zxt_phone.view.fragment.shequdianshang.SqdsMainFragment;

import butterknife.BindView;


public class SqgwActivity extends BaseActivity {

    private  String TAG = SqgwActivity.class.getCanonicalName();

    @BindView(R.id.rg_sqgw)
    RadioGroup mRg_sqgw;
    //记录底部选中的按钮
    byte mCurrentIndex = 0;

    private SqdsMainFragment sqdsMainFragment;
    private SqcsFragment sqcsFragment;
    private ShoucangFragment shoucangFragment;
    private GovernmentFragment governmentFragment;
    private mMeFragment meFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqgw);//社区购物
        //解决软件盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        sqdsMainFragment = SqdsMainFragment.newInstance();
        addFragment(sqdsMainFragment);

        initView();

    }

    private void initView() {
        mRg_sqgw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.rb_sqgw_main://社区购物首页
                        mCurrentIndex = 0;
                       showFragment(sqdsMainFragment);
                       toast("社区购物");
                       break;
                   case R.id.rb_sqcs://社区超市
                       mCurrentIndex = 1;
                       if(sqcsFragment == null){
                           sqcsFragment = new SqcsFragment();
                           addFragment(sqcsFragment);
                       }
                       showFragment(sqcsFragment);
                       toast("社区超市");
                       break;
                   case R.id.rb_scdp://收藏店铺
                       mCurrentIndex = 2;
                       if(shoucangFragment == null){
                           shoucangFragment = new ShoucangFragment();
                           addFragment(shoucangFragment);
                       }
                       showFragment(shoucangFragment);
                       toast("收藏店铺");
                       break;
                   case R.id.rb_gwc://购物车
                       mCurrentIndex = 3;
                       if(governmentFragment == null){
                           governmentFragment = new GovernmentFragment();
                           addFragment(governmentFragment);
                       }
                       showFragment(governmentFragment);
                       toast("购物车");
                       break;
                   case R.id.rb_me://个人中心
                       mCurrentIndex = 4;
                       if(meFragment == null){
                           meFragment = new mMeFragment();
                           addFragment(meFragment);
                       }
                       showFragment(meFragment);
                       toast("个人中心");
                       break;
               }
           }
       });
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.flComtent,fragment);
        transaction.commit();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(sqdsMainFragment);
        if(sqcsFragment != null){
            transaction.hide(sqcsFragment);
        }
        if(shoucangFragment != null){
            transaction.hide(shoucangFragment);
        }
        if(governmentFragment != null){
            transaction.hide(governmentFragment);
        }
        if(meFragment != null){
            transaction.hide(meFragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }
}
