package com.zxt.zxt_phone.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SPUtils;

import java.io.File;

import okhttp3.Call;

/**
 * Created by hkc on 2017/5/23.
 */

public class DownloadService extends Service {
//http://oa.ybqtw.org.cn/api/appFile/ZxtPhone.apk
    //    private static final String DOWN_APK_URL = "http://192.168.1.222:8099/api/GetAppVar.aspx?method=getpadvar&typeVer=3";

    private static final String DOWN_APK_URL = "http://192.168.1.222:8099/api/appFile/ZxtPhone.apk";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    //显示 Notification
                    showNotificationProgress(msg.arg1);
                    break;
                case 2:
                    //安装apk
                    installApk();
                    break;
            }
        }
    };

    private Boolean autoDownLoad;
    private int currentProgress = 0;

    private NotificationManager manager;
    private static final String APK_NAME = "ZxtPhone.apk";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        new Thread(new Runnable() {
            @Override
            public void run() {
                okHttpDownLoadApk(DOWN_APK_URL);
            }
        }).start();
    }

    /**
     * 联网下载最新版本apk
     */
    private void okHttpDownLoadApk(final String url) {

        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), APK_NAME) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MLog.e("onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        MLog.e("onResponse :" + response.getAbsolutePath());
                        MLog.i("OkHttpUtils===1");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        autoDownLoad = (Boolean) SPUtils.get(DownloadService.this, SPUtils.WIFI_DOWNLOAD_SWITCH, false);
                        MLog.i("OkHttpUtils===2");
                        //判断开关状态 开 则静默下载
                        if (autoDownLoad) {
                            MLog.i("OkHttpUtils===3");
                            //说明自动更新 这里服务在后台默默运行下载 只能看日志了
                            MLog.e("自动安装的进度 == " + (100 * progress));
                            if ((100 * progress) == 100.0) {
                                //Log.e(TAG, "网络请求 自动安装 当前线程 == " + Thread.currentThread().getName());

                                handler.sendEmptyMessage(2);
                            }
                        } else { // 否则 进度条下载
                            MLog.i("OkHttpUtils===4");
                            int pro = (int) (100 * progress);

                            //解决pro进度重复传递 progress的问题 这里解决UI界面卡顿问题
                            if (currentProgress < pro && pro <= 100) {

                                currentProgress = pro;

                                Message msg = new Message();
                                msg.what = 1;
                                msg.arg1 = currentProgress;
                                handler.sendMessage(msg);
                            }
                        }
                    }

                });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotificationProgress(int progress) {
        String message = "当前下载进度：" + progress + "%";

        int AllProgress = 100;

        Intent intent = null;
        if (progress == 100) {
            message = "下载完毕，点击安装";
            //Log.e(TAG, "下载完成 " + progress);

            //安装apk
            installApk();
            if (manager != null) {
                manager.cancel(0);//下载完毕 移除通知栏
            }

            //当进度为100%时 传入安装apk的intent
            File fileLocation = new File(Environment.getExternalStorageDirectory(), APK_NAME);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(fileLocation), "application/vnd.android.package-archive");
        }

        //表示返回的PendingIntent仅能执行一次，执行完后自动取消
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.icon)//App小的图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))//App大图标
                .setContentTitle("自定义内容")//设置通知的信息
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContentText(message)
                .setAutoCancel(false)//用户点击后自动删除
                //.setDefaults(Notification.DEFAULT_LIGHTS)//灯光
                .setPriority(Notification.PRIORITY_DEFAULT)//设置优先级
                .setOngoing(true)
                .setProgress(AllProgress, progress, false) //AllProgress最大进度 //progress 当前进度
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

    /**
     * 安装apk
     */
    private void installApk() {
        //Environment.getExternalStorageDirectory() 保存的路径
        MLog.e("installApk运行了");

        File fileLocation = new File(Environment.getExternalStorageDirectory(), APK_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(fileLocation), "application/vnd.android.package-archive");
        startActivity(intent);

        //停止服务
        stopSelf();
    }

    @Override
    public void onDestroy() {
        //Log.e(TAG, "onDestroy()");
        super.onDestroy();

        /*if (manager != null) {
            manager.cancel(0);//下载完毕 移除通知栏
        }*/
    }
}
