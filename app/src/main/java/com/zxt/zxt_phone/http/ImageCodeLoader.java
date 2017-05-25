package com.zxt.zxt_phone.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;


import com.zxt.zxt_phone.bean.AppData;
import com.zxt.zxt_phone.utils.MLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者: Idacf ,时间: 2016/6/2.9:44
 * 类说明:验证码图片加载类
 */
public abstract class ImageCodeLoader {
    private String errorMessage = null;
    ImageView ivCode;
    String url;
    public ImageCodeLoader(ImageView ivCode, String url){
        this.ivCode = ivCode;
        this.url = url;
    }

    public void loadImageCode(){
        new AsyncTask<Void,Void,Bitmap>(){

            @Override
            protected void onPreExecute() {
                ivCode.setEnabled(false);
                ivCode.setVisibility(View.INVISIBLE);
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap mBitmap = connection(url);
                return mBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                ivCode.setVisibility(View.VISIBLE);
                ivCode.setEnabled(true);
                if(bitmap != null){
                    ivCode.setImageBitmap(bitmap);
                }else{
                    onError(errorMessage);
                }
            }
        }.execute();


    }



    private Bitmap connection(String url) {

        errorMessage = null;
        URL realurl;
        HttpURLConnection conn = null;
        try {
            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            //设置连接超时
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10*1000);
            conn.connect();
            //解析返回流
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String cookie = conn.getHeaderField("Set-Cookie");
                if (null != cookie && cookie.startsWith("JSESSIONID")) {
                    MLog.e("获取的Cookie:" + cookie.split(";")[0].trim());
                    AppData.Cookie = cookie.split(";")[0].trim();
                }
                InputStream inputStream = conn.getInputStream();
                byte[] data = readInputStream(inputStream);
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                inputStream.close();
                return bitmap;

            } else {
                errorMessage = "服务器响应失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "获取数据失败";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }


    /*
     * 从数据流中获得数据
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.flush();
        bos.close();
        return bos.toByteArray();

    }


    protected abstract void onError(String errMessage);
}
