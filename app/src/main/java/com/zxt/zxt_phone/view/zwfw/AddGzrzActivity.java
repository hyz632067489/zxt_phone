package com.zxt.zxt_phone.view.zwfw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.ImagePickerAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.utils.GlideImageLoader;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AddGzrzActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{


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

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView ;

    private ArrayList<ImageItem> imageItems;//当前选择的所有图片
    private ImagePickerAdapter adapter;
    private int maxImgCount = 8;               //允许选择图片最大数
    private final int NEED_CAMERA = 200;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case NEED_CAMERA:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    selectImage();
//                } else {
//                    toast("请打开相机权限");
//                }
//                break;
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gzrz);
        initImagePicker();
        initView();
    }


    private void initView() {
        tabName.setText(R.string.add_gzrz);


        imageItems = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, imageItems, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - imageItems.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    @OnClick({  R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.submit_btn://提交日志
                formUpload();
                break;
        }
    }

    public void selectImage( ) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);   //多选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(9);    //最多选择9张
        imagePicker.setCrop(false);       //不进行裁剪
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == 100) {
//                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (imageItems != null && imageItems.size() > 0) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < imageItems.size(); i++) {
//                        if (i == imageItems.size() - 1) sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
//                        else sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
//                    }
////                    tvImages.setText(sb.toString());
//                } else {
////                    tvImages.setText("--");
//                }
//            } else {
//                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
////                tvImages.setText("--");
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1) sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                        else sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                    }
//                    imageItems.addAll(imageItems);
                    adapter.setImages(imageItems);
                    Log.i(TAG,"files.size======"+imageItems.size());
//                    tvImages.setText(sb.toString());
                } else {
//                    tvImages.setText("--");
                }
//            if (data != null && requestCode == REQUEST_CODE_SELECT) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                imageItems.addAll(images);
//                adapter.setImages(imageItems);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                imageItems.clear();
                imageItems.addAll(images);
                adapter.setImages(imageItems);
            }
        }
    }
    public void formUpload( ) {
        ArrayList<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }

        Log.i(TAG,"files.size======"+files.size());
    }
}
