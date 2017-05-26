package com.zxt.zxt_phone.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.NetworkTypeUtils;
import com.zxt.zxt_phone.utils.SPUtils;
import com.zxt.zxt_phone.utils.VersionUtils;
import com.zxt.zxt_phone.view.fragment.GovernmentFragment;
import com.zxt.zxt_phone.view.fragment.ShopFragment;
import com.zxt.zxt_phone.view.fragment.mMainFragment;
import com.zxt.zxt_phone.view.fragment.mMeFragment;
import com.zxt.zxt_phone.view.fragment.LifeFragment;
import com.zxt.zxt_phone.view.shop.LoginShopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    private Boolean isCheck = false;


    private static final int REQUEST_CODE = 0; // 请求码


    private final int LOGIN_CODE = 100;


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


        //解决软件盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mainFragment = mMainFragment.newInstance();
        addFragment(mainFragment);


        initView();

        progressVersion();
    }

    /**
     * 说明:
     * 比较服务器版本与当前apk版本,如果低于服务器版本
     * 1.wifi下自动更新开关打开 启动服务,后台静默下载apk,下载完毕后自动弹出安装界面
     * 2.wifi下自动更新开关关闭 启动服务,下载apk,并用notification通知栏显示下载进度等,下载完毕后自动弹出安装界面
     * --
     * 目前我没有线程的接口,去比较服务的版本,所以写一个假的服务器版本 用到的朋友自行显示比较即可
     */
    private void progressVersion() {

        //VersionUtils.getVersionCode(this)工具类里获取当前安装的apk版本号
        int version = VersionUtils.compareVersion(String.valueOf(VersionUtils.getVersionName(this)),
                String.valueOf(SPUtils.get(mContext, SPUtils.APK_VERSION, "")));//这里 1.2.0使我们伪造的 你完全可以得到自己服务器接口里的版本号 然后进行比对

        MLog.e(TAG, "获取当前安装的apk版本号==" + version);

        /**
         * 比较版本大小 version1为当前所安装的版本
         * version1 < version2  则  返回 -1
         * version1 > version2  则  返回 1
         * version1 == version2 则 返回  0
         */
        if (version == -1) {
            //判断 用户是否进入app主页面,
            Intent intent = new Intent();
            intent.setClassName("com.zxt.zxt_phone.view", "MainActivity");

            if (intent.resolveActivity(getPackageManager()) == null) {
                MLog.e(TAG, "不存在MainActivity");
                // 说明系统MainActivity没有被打开
                return;
            } else {
                MLog.e(TAG, "存在MainActivity");

                /**
                 * wifi状态下自动下载
                 */
                if ((boolean) SPUtils.get(this, SPUtils.WIFI_DOWNLOAD_SWITCH, false)
                        && NetworkTypeUtils.getCurrentNetType(MainActivity.this).equals("wifi")) {

                    MLog.e(TAG, "wifi状态下自动下载");

//                    startService(new Intent(MainActivity.this, DownloadService2.class));
                    //Log.e("TAG", "startService");
                } else { //提示dialog

                    //判断 忽略的版本sp信息是否与当前版本相等 如果不相等 则显示更新的dialog
//                    String spVersion = (String) SPUtils.get(this, SPUtils.APK_VERSION, "");
                    String spVersion = String.valueOf(VersionUtils.getVersionName(this));
                    MLog.e(TAG, "spVersion===" + spVersion);

                    if (!spVersion.equals(String.valueOf(SPUtils.get(mContext, SPUtils.APK_VERSION, "")))) {//服务器版本 依旧填假数据 1.2.0

                        //下面是自定义dialog
                        View view = View.inflate(this, R.layout.download_layout, null);
                        final Dialog dialog = new AlertDialog.Builder(this).create();
                        dialog.show();

                        dialog.setContentView(view);
                        TextView content = (TextView) view.findViewById(R.id.tv_content);
                        content.setText("解决不能及时..." + "等其它版本信息");
                        //取消
                        TextView cancel = (TextView) view.findViewById(R.id.btn_cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //当true时 保存版本信息
                                if (isCheck) {
                                    SPUtils.put(MainActivity.this, SPUtils.APK_VERSION, String.valueOf(SPUtils.get(mContext, SPUtils.APK_VERSION, "")));
                                }

                                MLog.e("TAG", "取消 == " + isCheck);

                                dialog.dismiss();
                            }
                        });

                        //确定
                        TextView Sure = (TextView) view.findViewById(R.id.btn_ok);
                        Sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startService(new Intent(MainActivity.this, DownloadService2.class));
                                //当true时 保存版本信息
                                if (isCheck) {
                                    SPUtils.put(MainActivity.this, SPUtils.APK_VERSION, String.valueOf(SPUtils.get(mContext, SPUtils.APK_VERSION, "")));
                                }
                                MLog.e("TAG", "确定 == " + isCheck);
                                dialog.dismiss();
                                //Log.e("TAG", "isCheck == " + isCheck);
                            }
                        });


                        //忽略该版本
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_ignore);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    isCheck = true;
                                } else {
                                    isCheck = false;
                                }

                            }
                        });
                    }
                }
            }
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        //用户退出登录后显示首页
        if ("".equals(AppData.isLoginShop) && mCurrentIndex == 4) {
            ((RadioButton) mRg.getChildAt(0)).setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != RESULT_OK) {
            MLog.i(TAG, "activity=====");
            return;
        }

        MLog.i(TAG, "activity=====" + requestCode);

        switch (requestCode) {
            case REQUEST_CODE:
                break;
            case Common.OUT_LOGIN_CODE:
                MLog.i("mRg", "mCurrentIndex==");
                if (resultCode == RESULT_OK && !"".equals(AppData.isLoginShop)) {
                    mCurrentIndex = 4;
                    if (meFragment == null) {
                        meFragment = new mMeFragment();
                        addFragment(meFragment);
                    }
                    showFragment(meFragment);
                } else {
                    ((RadioButton) mRg.getChildAt(mCurrentIndex)).setChecked(true);
                    MLog.i("mRg", "mCurrentIndex==" + mCurrentIndex);
                }
                break;
            case LOGIN_CODE:
                if (resultCode == RESULT_OK && !"".equals(AppData.isLoginShop)) {
                    mCurrentIndex = 4;
                    if (meFragment == null) {
                        meFragment = new mMeFragment();
                        addFragment(meFragment);
                    }
                    showFragment(meFragment);
                } else {
                    ((RadioButton) mRg.getChildAt(mCurrentIndex)).setChecked(true);
                    MLog.i("mRg", "mCurrentIndex==" + mCurrentIndex);
                }
                break;
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

                        if (!"".equals(AppData.isLoginShop)) {
                            mCurrentIndex = 4;
                            if (meFragment == null) {
                                meFragment = new mMeFragment();
                                addFragment(meFragment);
                            }
                            showFragment(meFragment);
                        } else {
                            startActivityForResult(new Intent(mActivity, LoginShopActivity.class), LOGIN_CODE);
                        }


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


    private long time = 0;

    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}

