package com.zxt.zxt_phone.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.PermissionsChecker;
import com.zxt.zxt_phone.view.fragment.GovernmentFragment;
import com.zxt.zxt_phone.view.fragment.ShopFragment;
import com.zxt.zxt_phone.view.fragment.mMainFragment;
import com.zxt.zxt_phone.view.fragment.mMeFragment;
import com.zxt.zxt_phone.view.fragment.LifeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zxt.zxt_phone.constant.Common.REQUEST_CODE;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class MainActivity extends BaseActivity {

    private String TAG = MainActivity.class.getCanonicalName();

    private mMainFragment mainFragment;
    private GovernmentFragment governmentFragment;
    private LifeFragment lifeFragment;
    private ShopFragment shopFragment;
    private mMeFragment meFragment;

    @BindView(R.id.rb_main)
    RadioButton rbMain;

    @BindView(R.id.rb_me)
    RadioButton rbMe;

    @BindView(R.id.rg)
    RadioGroup mRg;
    //记录底部选中的按钮
    byte mCurrentIndex = 0;



    private PermissionsChecker mPermissionsChecker; // 权限检测器


    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPermissionsChecker = new PermissionsChecker(this);

        //解决软件盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mainFragment = mMainFragment.newInstance();
        addFragment(mainFragment);



        initView();

    }


    @Override protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
//        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//            startPermissionsActivity();
//        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            MLog.i(TAG, "activity=====");
            return;
        }
        MLog.i(TAG, "activity====="+requestCode);
        if (requestCode == REQUEST_CODE) {

        }


    }


    private void initView() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main:
                        mCurrentIndex = 0;
                        showFragment(mainFragment);
                        break;
                    case R.id.rb_zw:
                        mCurrentIndex = 1;
                        if (governmentFragment == null) {
                            governmentFragment = new GovernmentFragment();
                            addFragment(governmentFragment);
                        }
                        showFragment(governmentFragment);
                        break;
                    case R.id.rb_life:
                        mCurrentIndex = 2;
                        if (lifeFragment == null) {
                            lifeFragment = new LifeFragment();
                            addFragment(lifeFragment);
                        }
                        showFragment(lifeFragment);
                        break;
                    case R.id.rb_shop:
                        mCurrentIndex = 3;
                        if (shopFragment == null) {
                            shopFragment = new ShopFragment();
                            addFragment(shopFragment);
                        }
                        showFragment(shopFragment);
                        break;
                    case R.id.rb_me:
                        mCurrentIndex = 4;
                        if (meFragment == null) {
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

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(mainFragment);
        if (governmentFragment != null) {
            transaction.hide(governmentFragment);
        }
        if (lifeFragment != null) {
            transaction.hide(lifeFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

}

