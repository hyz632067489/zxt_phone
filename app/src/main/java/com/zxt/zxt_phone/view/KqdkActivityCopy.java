package com.zxt.zxt_phone.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.BitmapUtil;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.WaterUtils;
import com.zxt.zxt_phone.view.customview.WrapHeightGridView;
import com.zxt.zxt_phone.view.photo.ClipImageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class KqdkActivityCopy extends BaseActivity {

    private String TAG = KqdkActivityCopy.class.getCanonicalName();

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


    private final int CAMERA_WITH_DATA = 2;
    private final int CROP_RESULT_CODE = 3;
    public static final String TMP_PATH = "clip_temp.jpg";

    Bitmap mImage;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            Log.i(TAG, "000000000000" + "市：" + location.getLongitude() + "\n" + "区：" + location.getLatitude() + "\n" + "街道：" + location.getStreet());
            //获取定位结果
            address = location.getAddrStr();
            mHandler.sendEmptyMessage(ADDRESS);
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
                    startCapture();
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
                    startCapture();
                }
                break;
            case R.id.btn_tjkq:
                if ("".equals(etContent.getText().toString())) {
                    toast("请输入备注");
                    return;
                } else if (null == mImage) {
                    toast("请拍摄照片");
                    return;
                }
                sendImages();
                break;
        }
    }

    final String CACHE_IMG = Environment.getExternalStorageDirectory()+"/zxtPhone/";

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径

        File file = new File(CACHE_IMG, TMP_PATH);
        Uri imageUri=FileProvider.getUriForFile(mActivity,"me.xifengwanzhao.fileprovider", file);//这里进行替换uri的获得方式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                Environment.getExternalStorageDirectory(), TMP_PATH)));

        startActivityForResult(intent, CAMERA_WITH_DATA);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // String result = null;
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
//            case START_ALBUM_REQUESTCODE:
//                startCropImageActivity(getFilePath(data.getData()));
//                break;
            case CAMERA_WITH_DATA:// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
//
                //FileProvider 7.0文件读取共享权限
                String path1 = CACHE_IMG + TMP_PATH;

                ClipImageActivity.startActivity(this, path1, CROP_RESULT_CODE);
                break;
            case CROP_RESULT_CODE: // 裁剪
                String path = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                Bitmap photo = BitmapFactory.decodeFile(path);

                //添加水印保存图片
                Bitmap mWater = BitmapFactory.decodeResource(getResources(), R.drawable.left1);
                mImage = WaterUtils.createBitmap(photo, mWater, newTime);

//                BitmapUtil.saveBitmap(mImage, path);
                BitmapUtil.saveBitmapFile(mImage);
//                ClipImageActivity.saveBitmap(mImage,path);
                MLog.i(TAG, "photo===========" + photo.getByteCount());
                MLog.i(TAG, "img===========" + mImage);

                imagePic.setImageBitmap(mImage);

                break;
        }
    }


    // 裁剪图片的Activity
//    private void startCropImageActivity(String path) {
//        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
//    }


    private void sendImages() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + TMP_PATH);
//        Log.e(TAG, "file=====sendImages" + file.getPath().toString());
        if (!file.exists()) {
            toast("文件不存在，请修改文件路径");
            return;
        }
        //上传多张图片 表头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data");

        OkHttpUtils.post()//
                .url(Url.URL_WG + "work/uploadClockInImg.do?")//
                .addFile("mFile", TMP_PATH, file)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {

//            showLoading("正在加载...");
            MLog.e(TAG, "loading.....======");
        }

        @Override
        public void onAfter(int id) {
//            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            MLog.e(TAG, "onError：======");

        }

        @Override
        public void onResponse(String response, int id) {
            MLog.e(TAG, "onResponse：complete" + response);
            try {
                JSONObject obj = new JSONObject(response);
                if ("200".equals(obj.getString("statusCode"))) {
                    picString = obj.getString("path");
                }
                sendData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            MLog.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    private void sendData() {
        OkHttpUtils.get()
                .url(Url.URL_WG+"work/addStaffClockInInfo.do?")
                .addParams("start_time", newTime)
                .addParams("start_site", positionText.getText().toString())
                .addParams("start_memo", etContent.getText().toString())
                .addParams("attendance_pic", picString)
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
                    if ("200".equals(obj.getString("statusCode"))) {
                        toast(" " + obj.getString("message"));

                        finish();
                    } else if ("201".equals(obj.getString("statusCode"))) {
                        toast(" " + obj.getString("message"));
                        btnTjkq.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MLog.i(TAG, "response=====" + response);

            }
        });
    }


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
