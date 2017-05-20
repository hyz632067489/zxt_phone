package com.zxt.zxt_phone.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.utils.BitmapUtil;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.WaterUtils;
import com.zxt.zxt_phone.view.CaptureActivity;
import com.zxt.zxt_phone.view.KqdkActivityCopy;
import com.zxt.zxt_phone.view.customview.CircleImageView;
import com.zxt.zxt_phone.view.photo.ClipImageActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.zxt.zxt_phone.constant.Common.REQUEST_CODE;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class mMeFragment extends BaseFragment {


    Unbinder unbinder;
    private View view;

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.user_photo)
    CircleImageView userPhoto;

    private final int NEED_CAMERA = 200;

    private final int CAMERA_WITH_DATA = 2;
    private final int CROP_RESULT_CODE = 3;
    public static final String TMP_PATH = "clip_temp.jpg";
    Bitmap mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_fragment_view, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
    }

    private void initView() {

        tabName.setText(R.string.about_me);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

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
    @OnClick({R.id.user_photo,R.id.tv_fu_kuan, R.id.tv_shou_huo, R.id.tv_ping_jia, R.id.tv_shou_hou, R.id.me_buy_car, R.id.me_my_address, R.id.me_shequ_communication, R.id.me_kefu, R.id.me_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_photo:
                //检测是否有相机和读写文件权限
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    startCapture();
                }

                break;
            case R.id.tv_fu_kuan:
                startActivity(new Intent(getActivity(), KqdkActivityCopy.class));
                break;
            case R.id.tv_shou_huo:
                break;
            case R.id.tv_ping_jia:
                break;
            case R.id.tv_shou_hou:
                break;
            case R.id.me_buy_car:
                break;
            case R.id.me_my_address:
                break;
            case R.id.me_shequ_communication:
                break;
            case R.id.me_kefu:
                break;
            case R.id.me_setting:
                break;
        }
    }
    final String CACHE_IMG = Environment.getExternalStorageDirectory()+"/zxtPhone/";

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径

        File file = new File(CACHE_IMG, TMP_PATH);
        Uri imageUri= FileProvider.getUriForFile(getActivity(),"me.xifengwanzhao.fileprovider", file);//这里进行替换uri的获得方式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                Environment.getExternalStorageDirectory(), TMP_PATH)));

        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    String newTime = "2017-5-19";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // String result = null;
        MLog.i("1111", "activity====="+requestCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
//            case START_ALBUM_REQUESTCODE:
//                startCropImageActivity(getFilePath(data.getData()));
//                break;
            case CAMERA_WITH_DATA:// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                //FileProvider 7.0文件读取共享权限
                String path1 = CACHE_IMG + TMP_PATH;
                MLog.i("1111","111===="+path1);
//                ClipImageActivity.startActivity(getActivity(), path1, CROP_RESULT_CODE);


                Intent intent = new Intent(getContext(),ClipImageActivity.class);
                intent.putExtra("path",path1);
                startActivityForResult(intent, CROP_RESULT_CODE);
                break;
            case CROP_RESULT_CODE: // 裁剪
                String path = data.getStringExtra(ClipImageActivity.RESULT_PATH);

                mImage = BitmapFactory.decodeFile(path);
//                BitmapUtil.saveBitmapFile(mImage);
                MLog.i("1111", "img===========" + mImage);

                userPhoto.setImageBitmap(mImage);



                break;
        }
    }
}
