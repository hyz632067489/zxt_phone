package com.zxt.zxt_phone.view.zwfw.wggl;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.ImagePickerAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AddGzrzActivity extends BaseActivity  implements ImagePickerAdapter.OnRecyclerViewItemClickListener, CompoundButton.OnCheckedChangeListener {


    private String TAG =  AddGzrzActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;

    @BindViews({R.id.chb_1, R.id.chb_2, R.id.chb_3, R.id.chb_4})
    List<CheckBox> chBox;

    String chb1, chb2, chb3, chb4, picString;

    StringBuilder myType;
    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<ImageItem> selImageList;//当前选择的所有图片
    private ImagePickerAdapter adapter;
    private int maxImgCount = 8;               //允许选择图片最大数

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gzrz);

        //最好放到 Application oncreate执行
//        initImagePicker();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.add_gzrz);

        tvBiaoti.setText(DateUtil.getCurDateDay());

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chb_1:
                if (isChecked) {
                    toast("选择：" + "巡查");
                } else {
                    toast("取消：" + "巡查");
                }
                break;
            case R.id.chb_2:
                if (isChecked) {
                    toast("选择：" + "宣传");
                } else {
                    toast("取消：" + "宣传");
                }
                break;
            case R.id.chb_3:
                if (isChecked) {
                    toast("选择：" + "走访");
                } else {
                    toast("取消：" + "走访");
                }
                break;
            case R.id.chb_4:
                if (isChecked) {
                    toast("选择：" + "处理");
                } else {
                    toast("取消：" + "处理");
                }
                break;
        }
    }





    @OnClick({R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn://提交日志
                myType = new StringBuilder();
                if (chBox.get(0).isChecked()) {
                    myType.append("1,");
                }
                if (chBox.get(1).isChecked()) {
                    myType.append("2,");
                }
                if (chBox.get(2).isChecked()) {
                    myType.append("3,");
                }
                if (chBox.get(3).isChecked()) {
                    myType.append("4");
                }
                Log.i(TAG, "myType===" + myType);
                if ("".equals(myType.toString()) || myType.length() == 0) {
                    toast("请选择类型");
                    return;
                } else if ("".equals(content.getText().toString())) {
                    toast("请输入内容");
                    return;
                } else if (0 == selImageList.size()) {
                    toast("请添加图片");
                    return;
                }
                formUpload();
                break;
        }
    }

    /**
     * 先上传图片，在传参数
     */
    public void formUpload() {

        HashMap<String, File> fileList = new HashMap<>();
        if (selImageList != null && selImageList.size() > 0) {
            for (int i = 0; i < selImageList.size(); i++) {
                fileList.put(selImageList.get(i).name, new File(selImageList.get(i).path));
            }
        }
//        http://192.168.1.222:8099/api/APP1.0.aspx?method=tp
        //上传多张图片 表头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data");
        OkHttpUtils.post()
                .url(Url.URL_WG + "blog/uploadBlogImg.do?")
//                .url("http://192.168.1.222:8099/api/APP1.0.aspx?method=tp")
                .headers(headers)
                .files("mFile", fileList)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response======" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {

                                picString = obj.getString("path");
                                if (!"".equals(picString)) {
                                    sentData();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    private void sentData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("blogName", tvBiaoti.getText().toString());
        params.put("blogType", String.valueOf(myType));
        params.put("blogContent", content.getText().toString());
        params.put("blogPic", picString);
        OkHttpUtils.get()
                .url(Url.URL_WG + "blog/addOneBlog.do?")
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "response===" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("200".equals(obj.getString("statusCode"))) {
                                toast(obj.getString("message"));
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                toast(obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

