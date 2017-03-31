package com.zxt.zxt_phone.view.zwfw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.utils.DateTimePickDialogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AddGzrzActivity extends BaseActivity {


    private String TAG = AddGzrzActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;



    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.rd_g)
    RadioGroup rdG;
    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.image_pic)
    ImageView imagePic;

    private final int NEED_CAMERA = 200;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case NEED_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCapture();
                } else {
                    toast("请打开相机权限");
                }
                break;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gzrz);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        tabName.setText(R.string.add_gzrz);
    }


    @OnClick({ R.id.take_photo,R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.take_photo:
                //检测是否有相机和读写文件权限
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    startCapture();
                }
                break;
            case R.id.submit_btn://提交日志
                break;
        }
    }

    private void startCapture() {

    }
}
