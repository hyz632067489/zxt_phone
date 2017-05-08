package com.zxt.zxt_phone.view.wyfw;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.ImagePickerAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.EvaluationPic;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.Mobile;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RepairsActivity extends BaseActivity {


    private String TAG = RepairsActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_adress)
    EditText etAddress;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.nineGrid)
    NineGridView mNineGrid;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_tongyi)
    Button btnTongyi;


    private ArrayList<ImageItem> selImageList;//当前选择的所有图片
    private ImagePickerAdapter adapter;
    private int maxImgCount = 8;               //允许选择图片最大数
    List<EvaluationPic> imageDetails;
    EvaluationPic picModel;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    String[] DeptName = null;
    String[] Deptid = null;
    String deptId = null;
    int checkedItem = 0;

    String picString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs);


        getDataDept();
        initView();
    }


    private void initView() {


        tabName.setText(getResources().getText(R.string.wyfw_gzbx));
        selImageList = new ArrayList<>();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (selImageList != null && selImageList.size() > 0) {
                    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                    imageDetails = new ArrayList<>();
                    for (int i = 0; i < selImageList.size(); i++) {
                        picModel = new EvaluationPic();
                        picModel.setImageUrl(selImageList.get(i).path);
                        picModel.setSmallImageUrl(selImageList.get(i).path);
                        imageDetails.add(picModel);
                    }

                    for (EvaluationPic imageDetail : imageDetails) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(imageDetail.imageUrl);
                        info.setBigImageUrl(imageDetail.imageUrl);
                        imageInfo.add(info);
                    }
                    mNineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
                } else {
//                    tvImages.setText("--");
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
//                tvImages.setText("--");
            }
        }
    }

    @OnClick({R.id.iv_img, R.id.btn_tongyi, R.id.layout_dept})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_img:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.layout_dept:
                showDialog();
                break;
            case R.id.btn_tongyi:

                if (verification()) {
                    setPic();
                }
                break;
        }
    }

    private void setPic() {

        showProgressDialog();

        HashMap<String, File> fileList = new HashMap<>();
        if (selImageList != null && selImageList.size() > 0) {
            for (int i = 0; i < selImageList.size(); i++) {
                fileList.put(selImageList.get(i).name, new File(selImageList.get(i).path));
            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "tp");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));

        //上传多张图片 表头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data");

        OkHttpUtils.post()
                .url(Url.BASE_URL)
                .params(params)
                .files("LJ", fileList)
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
                            if ("1".equals(obj.getString("Status"))) {
                                picString = obj.getString("URL");
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

        HashMap<String ,String > params = new HashMap<>();
        params.put("method","breakdownadd");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));
        params.put("Name",etName.getText().toString());
        params.put("Phone",etPhone.getText().toString());
        params.put("img",picString);
        params.put("level",deptId);
        params.put("Address",etAddress.getText().toString());
        params.put("Title",etTitle.getText().toString());
        params.put("Content",etContent.getText().toString());

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                            MLog.i(TAG,"response="+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if("1".equals(obj.getString("Status"))){

                                toast("提交成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private boolean verification() {

        if ("".equals(etName.getText().toString())) {
            toast("请输入姓名");
            return false;
        } else if ("".equals(etPhone.getText().toString())) {
            toast("请输入电话");
            return false;
        } else if ("".equals(tvDept.getText().toString())) {
            toast("请选择紧急程度");
            return false;
        } else if ("".equals(etAddress.getText().toString())) {
            toast("请输入地址");
            return false;
        } else if ("".equals(etTitle.getText().toString())) {
            toast("请输入大致描述");
            return false;
        } else if ("".equals(etContent.getText().toString())) {
            toast("请输入内容");
            return false;
        } else if (0 == selImageList.size()) {
            toast("请添加图片");
            return false;
        }

        //判断手机号
        if (!Mobile.isPhoneNum(etPhone.getText().toString())) {
            toast("请输入正确的手机号");
            return false;
        }

        return true;
    }


    private void showDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择。。。");

        //设置正面按钮，并做事件处理
        dialog.setSingleChoiceItems(DeptName, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem = which;
                deptId = Deptid[which];
                tvDept.setText(DeptName[which]);
                dialog.dismiss();
            }
        });
        //设置反面按钮，并做事件处理
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();//显示Dialog对话框
    }


    /**
     * 获取重要程度
     */
    private void getDataDept() {
        Map<String, String> params = new HashMap<>();
        params.put("method", "faulthow");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response==" + response);
                try {
                    JSONObject obj = new JSONObject(response);

                    if ("1".equals(obj.getString("Status"))) {
                        JSONArray array = obj.getJSONArray("Data");
                        DeptName = new String[array.length()];
                        Deptid = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            DeptName[i] = array.getJSONObject(i).getString("level");
                            Deptid[i] = array.getJSONObject(i).getString("id");
                        }
                    } else {
                        toast(obj.getString("Message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
