package com.zxt.zxt_phone.view.zwfw.wggl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
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

public class AddSjclActivity extends BaseActivity {


    private String TAG = AddSjclActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.layout_type)
    LinearLayout layoutType;
    @BindView(R.id.tv_scale)
    TextView tvScale;
    @BindView(R.id.layout_scale)
    LinearLayout layoutScale;
    @BindView(R.id.tv_come)
    TextView tvCome;
    @BindView(R.id.layout_come)
    LinearLayout layoutCome;
    @BindView(R.id.chb_1)
    CheckBox chb1;
    @BindView(R.id.chb_2)
    CheckBox chb2;

    @BindView(R.id.nineGrid)
    NineGridView mNineGrid;

    @BindView(R.id.et_content)
    EditText etContent;

    int tag = 0;

    String typeId, levelId, comeId;

    String[] typeNames = null;
    String[] typeIds = null;
    String[] LevelNames = null;
    String[] LevelIds = null;
    String[] comeNames = null;
    String[] comeIds = null;

    int checkedItem = 0;

    private ArrayList<ImageItem> selImageList;//当前选择的所有图片
    private ImagePickerAdapter adapter;
    private int maxImgCount = 8;               //允许选择图片最大数
    List<EvaluationPic> imageDetails;
    EvaluationPic picModel;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    String picString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sjcl);
        ButterKnife.bind(this);

        getTypeData();
        getLevelData();
        getComeData();
        initView();
    }


    private void initView() {
        tabName.setText(getResources().getText(R.string.add_sjcl));
        selImageList = new ArrayList<>();

    }

    @OnClick({R.id.layout_type, R.id.layout_scale, R.id.layout_come, R.id.iv_img, R.id.btn_tongyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_type:
                if (typeNames.length != 0) {
                    showdialog1();
                }else {
                    toast("暂无数据");
                }
                break;
            case R.id.layout_scale:
                showdialog2();
                break;
            case R.id.layout_come:
                showdialog3();
                break;
            case R.id.iv_img:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);

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
        //上传多张图片 表头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data");

        OkHttpUtils.post()
                .url(Url.URL_WG + "event/uploadEventImg.do")
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

        /**
         * 添加事件方法
         * @param eventLevelId 事件等级id
         * @param sourceTypeId 事件来源id
         * @param eventTypeId 事件类型id
         * @param eventTitle 事件标题
         * @param eventContent 事件内容
         * @param isImportant 重点督办 1 ==>是，0 ==>否
         * @param eventPic 事件相关图片
         * @return
         */
        HashMap<String ,String > params = new HashMap<>();
        params.put("eventTitle",etTitle.getText().toString());
        params.put("eventLevelId",levelId);
        params.put("sourceTypeId",comeId);
        params.put("eventTypeId",typeId);

        params.put("isImportant", 1+"");

        params.put("eventPic",picString);
        params.put("eventContent",etContent.getText().toString());

        OkHttpUtils.get()
                .url(Url.URL_WG+"event/addNewEventInfo.do")
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
                            if("200".equals(obj.getString("statusCode"))){

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

        if ("".equals(etTitle.getText().toString())) {
            toast("请输入标题");
            return false;
        } else if ("".equals(tvType.getText().toString())) {
            toast("请选择类型");
            return false;
        } else if ("".equals(tvCome.getText().toString())) {
            toast("请选择来源");
            return false;
        } else if ("".equals(tvScale.getText().toString())) {
            toast("请选择等级");
            return false;
        } else if ("".equals(etContent.getText().toString())) {
            toast("请输入内容");
            return false;
        } else if (0 == selImageList.size()) {
            toast("请添加图片");
            return false;
        }


        return true;
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





    public void showdialog1() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择。。。");
        dialog.setSingleChoiceItems(typeNames, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem = which;
                typeId = typeIds[which];
                tvType.setText(typeNames[which]);
                dialog.dismiss();
            }
        });


        dialog.show();//显示Dialog对话框
    }

    public void showdialog2() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择。。。");


        dialog.setSingleChoiceItems(LevelNames, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem = which;
                levelId = LevelIds[which];
                tvScale.setText(LevelNames[which]);
                dialog.dismiss();
            }
        });


        dialog.show();//显示Dialog对话框
    }

    public void showdialog3() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择。。。");

        dialog.setSingleChoiceItems(comeNames, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem = which;
                comeId = comeIds[which];
                tvCome.setText(comeNames[which]);
                dialog.dismiss();
            }
        });


        dialog.show();//显示Dialog对话框
    }

    private void getTypeData() {
        //获取所有事件类型
        OkHttpUtils.get()
                .url(Url.URL_WG + "event/getAllEventType.do")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response=1=" + response);
                try {
                    JSONObject obj = new JSONObject(response);

                    if ("200".equals(obj.getString("statusCode"))) {
                        tag = 1;
                        JSONArray array = obj.getJSONArray("data");
                        typeNames = new String[array.length()];
                        typeIds = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            typeNames[i] = array.getJSONObject(i).getString("eventTypeName");
                            typeIds[i] = array.getJSONObject(i).getString("eventTypeId");
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

    private void getComeData() {
        //获取所有事件来源
        OkHttpUtils.get()
                .url(Url.URL_WG + "event/getAllEventSourceType.do")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response=2=" + response);
                try {
                    JSONObject obj = new JSONObject(response);

                    if ("200".equals(obj.getString("statusCode"))) {
                        tag = 2;
                        JSONArray array = obj.getJSONArray("data");
                        comeNames = new String[array.length()];
                        comeIds = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            comeNames[i] = array.getJSONObject(i).getString("sourceTypeName");
                            comeIds[i] = array.getJSONObject(i).getString("sourceTypeId");
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

    private void getLevelData() {
        //获取所有事件等级
        OkHttpUtils.get()
                .url(Url.URL_WG + "event/getAllEventLevel.do")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MLog.i(TAG, "response=3=" + response);
                try {
                    JSONObject obj = new JSONObject(response);

                    if ("200".equals(obj.getString("statusCode"))) {
                        tag = 3;
                        JSONArray array = obj.getJSONArray("data");
                        LevelNames = new String[array.length()];
                        LevelIds = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            LevelNames[i] = array.getJSONObject(i).getString("eventLevelName");
                            LevelIds[i] = array.getJSONObject(i).getString("eventLevelId");
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
