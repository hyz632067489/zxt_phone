package com.zxt.zxt_phone.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;


import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.base.BaseFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UploadPicHelper {

    /**
     * 选择拍照的request_code
     */
    public static final int TAKEPHOTO_REQUESTCODE = 100;
    /**
     * 裁剪图片
     */
    public static final int CROP = 102;
    /**
     * 选择从图库选择图片的request_code
     */
    public static final int SELECT_PIC = 101;
    public static String takePicturePath = null;// 调用相机拍摄照片的名字
    /**
     * 拍照后图片保存路径
     */
    private static String IMAGE_FILE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    public static void showPicDialog(final BaseActivity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("请选择操作")
//                .setItems(new String[]{"拍照", "从图库选择"},
                .setItems(new String[]{"拍照"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                switch (which) {
                                    case 0:
                                        String sdStatus = Environment
                                                .getExternalStorageState();
                                        if (!sdStatus
                                                .equals(Environment.MEDIA_MOUNTED)) {
                                            Toast.makeText(activity, "未检测到内存卡",
                                                    Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        // 下面这句指定调用相机拍照后的照片存储的路径
                                        takePicturePath = IMAGE_FILE_PATH + "/IMG" + System.currentTimeMillis() + ".jpg";
                                        File image = new File(takePicturePath);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                                        activity.startActivityForResult(intent, TAKEPHOTO_REQUESTCODE);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(
                                                Intent.ACTION_PICK);
                                        intent1.setDataAndType(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
                                        activity.startActivityForResult(intent1,
                                                SELECT_PIC);
                                        break;

                                }

                            }
                        }).show();

    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Uri uri, BaseActivity activity) {

        int  dp = 200;
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1 // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);
        intent.putExtra("outputY", dp);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, CROP);
    }


    //    public static void upPic(String picString){
//
//        HttpUtil<BaseData> httpUtil = new HttpUtil<BaseData>(BaseData.class, Url.picUpload) {
//            @Override
//            protected void onStart() {
//                //fragment.showLoading("加载中...");
//            }
//
//            @Override
//            protected void onError(String errMessage) {
////                dismissLoading();
////                toast(errMessage);
//            }
//
//            @Override
//            protected void onSucceed(BaseData data) {
//               // dismissLoading();
//
//            }
//        };
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("userId", String.valueOf(AppData.userInfo.getUserId()));
//        params.put("imgData", picString);
//        httpUtil.setParams(params);
//        httpUtil.sendRequest();
//    }
    public static void saveMyBitmap(Bitmap mBitmap, File dir, Context context) {
        String name = "header.jpg";
        File file = new File(dir, name);
        try {
            Runtime.getRuntime().exec("chmod 705 " + dir.getPath());
            Runtime.getRuntime().exec("chmod 604 " + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), name, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        LogUtil.e("保存成功了");
    }

}
