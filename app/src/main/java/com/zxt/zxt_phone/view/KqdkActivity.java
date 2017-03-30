package com.zxt.zxt_phone.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.Base64Coder;
import com.zxt.zxt_phone.utils.BitmapUtil;
import com.zxt.zxt_phone.utils.UploadPicHelper;
import com.zxt.zxt_phone.utils.WaterUtils;
import com.zxt.zxt_phone.view.customview.WrapHeightGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class KqdkActivity extends BaseActivity {

    private String TAG = KqdkActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;

    private final int SEND_TIME = 99;
    private final int ADDRESS = 98;
    String mHour;
    String mMinute;
    String mSecond;


    private final int NEED_CAMERA = 200;
    private final int BAIDU_LBS = 100;

    @BindView(R.id.photo_img_layout)
    WrapHeightGridView gvImages;
    @BindView(R.id.take_photo)
    ImageView btnPhoto;
    @BindView(R.id.image_pic)
    ImageView imagePic;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_tjkq)
    Button btnTjkq;
    String newTime;
    String picString = "";//图片地址

    File mFile;

    public LocationClient mLocationClient;
    @BindView(R.id.position_tv)
    TextView positionText;
    private String mCity, mDistrict, mStreet = "";
    String address;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_TIME:
                    setTime();
                    mHandler.postDelayed(runnable, 998);
                    break;
                case ADDRESS:
                    positionText.setText(address);
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(SEND_TIME);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_kqdk);

//        positionText = (TextView) findViewById(R.id.position_tv);
        //检测是否有定位权限
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            mActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, BAIDU_LBS);
        } else {
            requestLocation();
        }

        //初始化数据
        initView();


    }

    private void initView() {
        tabName.setText(R.string.kqdk);
        setTime();
        mHandler.postDelayed(runnable, 998);

        //查询是否签到
        sendData();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    /**
     * 定位
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
//        int span = 1000;
        option.setScanSpan(0);

        mLocationClient.setLocOption(option);

    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i(TAG, "000000000000" + "市：" + location.getCity() + "\n" + "区：" + location.getDistrict() + "\n" + "街道：" + location.getStreet());
            //获取定位结果

            address = location.getAddrStr();
            mHandler.sendEmptyMessage(ADDRESS);


//                positionText.setText(address);

//            mCity=location.getCity();
//            mDistrict = location.getDistrict();
//            mStreet = location.getStreet();
//            positionText.setText("市：" + location.getCity() + "\n" + "区：" + location.getDistrict() + "\n" + "街道：" + location.getStreet());

            //操作地图
//            if(location.getLocType() == BDLocation.TypeGpsLocation
//                    || location.getLocType() == BDLocation.TypeNetWorkLocation){
//                    navigateTo(location);
//            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }

    //显示地图出来
    private void navigateTo(BDLocation location) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case BAIDU_LBS:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            toast("必须同意所有权限才能使用本程序");
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    toast("发生未知错误");
                    finish();
                }
                break;
            case NEED_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UploadPicHelper.showPicDialog(this);
                } else {
                    toast("请打开相机权限");
                }
                break;
        }
    }


    @OnClick({R.id.take_photo, R.id.btn_tjkq})
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.take_photo:
                //检测是否有相机和读写文件权限
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    UploadPicHelper.showPicDialog(this);
                }
                break;
            case R.id.btn_tjkq:
                if ("".equals(etContent.getText().toString())) {
                    toast("请输入备注");
                    return;
                }
//                else if ("".equals(picString)) {
//                    toast("请拍摄照片");
//                    return;
//                }
                sendImages();
                break;

        }

    }

    private void sendImages() {

        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists())
        {
             toast("文件不存在，请修改文件路径");
            return;
        }
        OkHttpUtils
                .postFile()
                .url("http://192.168.1.220:8080/grid/app/work/uploadClockInImg.do?")
                .file(mFile)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
toast("点击了发送");
                        Log.i(TAG,"image+++++"+response);
                    }
                });
    }


    private void sendData() {

//        HashMap<String, String> params = new HashMap<>();
//        params.put("attendance_pic", picString);
//        params.put("start_memo", etContent.getText().toString());   //  备注
//        params.put("start_site", positionText.getText().toString());    //地址
//        params.put("start_time", newTime);
//        String url = Url.URL_WG + "work/addStaffClockInInfo.do?";
//        Log.i(TAG, "url=====" + url);
        OkHttpUtils.get()
                .url("http://192.168.1.220:8080/grid/app/work/addStaffClockInInfo.do?")
//                .params(params)
//                .addParams("start_time",newTime)
//                .addParams("start_site",positionText.getText().toString())
//                .addParams("start_memo",etContent.getText().toString())
                .addParams("attendance_pic",picString)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                toast("提交失败");
            }
            @Override
            public void onResponse(String response, int id) {
//                toast("提交成功");
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("200".equals(obj.getString("status"))) {
                        toast(" " + obj.getString("message"));

                        finish();
                    } else if ("201".equals(obj.getString("status"))) {
                        toast(" " + obj.getString("message"));
                        btnTjkq.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "response=====" + response);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        switch (requestCode) {
            case UploadPicHelper.SELECT_PIC:

                break;
            case UploadPicHelper.TAKEPHOTO_REQUESTCODE:
                if (resultCode == -1) {
                    File file1 = new File(UploadPicHelper.takePicturePath);
                    UploadPicHelper.startPhotoZoom(Uri.fromFile(file1), this);

//                    UploadPicHelper.drawTextToBitmap(this,,newTime);
                }
                break;
            case UploadPicHelper.CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");

                        //保存图片
                        String path;
                        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                            path = Environment.getExternalStorageDirectory().getPath() + "/zxt/images/";
                        } else {
                            path = "/data/data/zxt/images/";
                        }
                        Log.i(TAG, "path====" + path);
                        //加水印
                        Bitmap mWater = BitmapFactory.decodeResource(getResources(), R.drawable.left1);
                        Bitmap img = WaterUtils.createBitmap(photo, mWater, newTime);


                         mFile = new File(path,"header.jpg");
                        UploadPicHelper.saveMyBitmap(img, mFile, mActivity);
                        //圆形控件
//                        bitmap = BitmapUtil.toRoundBitmap(photo);

                        //显示水印图片
                        imagePic.setImageBitmap(img);
//                        imagePic.setImageBitmap(photo);

//                        Picasso.with(mActivity).load(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), img, null,null)))
//                                .placeholder(R.mipmap.ic_launcher)
//                                .error(R.mipmap.ic_launcher)
//                                .into(imagePic);


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //图片压缩
                        img.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//                        photo.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                        byte[] b = stream.toByteArray();
                        try {
                            stream.flush();
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //转换图片
                        picString = Base64.encodeToString(b, Base64.NO_WRAP);
//                        picString = new String(Base64Coder.encodeLines(b));
//                        imagePic.setImageBitmap(base64ToBitmap(picString));


                        Log.i(TAG, "picStrinf====" + picString);

//                        UploadPicHelper.upPic(s);
                    }
                }
                break;
        }
    }


//    public static Bitmap base64ToBitmap(String base64Data) {
//        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//    }

    public void setTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss  ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        newTime = formatter.format(curDate);
//        Log.i(TAG, "str====" + newTime.toString());
        String[] stime = newTime.split(":", 3);

        mHour = stime[0].substring(stime[0].length() - 2, stime[0].length());
        mMinute = stime[1];
        mSecond = stime[2];

        tvDay.setText(stime[0].substring(0, stime[0].length() - 2));
        tvHour.setText(mHour);
        tvMinute.setText(mMinute);
        tvSecond.setText(mSecond);
    }


    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        mLocationClient.stop();
    }


}
