package com.zxt.zxt_phone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class LtjdActivity extends BaseActivity {

    private  String TAG = LtjdActivity.class.getCanonicalName();
    @BindView(R.id.tab_name)
    TextView tabName;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LtjdActivity.class);
//        intent.putExtra("param1",data1);
//        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ltjd_activity);

        initView();
    }

    private void initView() {
        tabName.setText(R.string.ltjd);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        requestWeather();
    }

    //请求
//    public void requestWeather(){
////        String url = "http://192.168.1.125:8080/grid/user/validateUser.do?userName=admin&password=1234";
//        String url = Url.BASE_URL+"/user/getOneUserInfo.do?userId=2";
//        HttpUtil.sendOkHttpRequest(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//               e.printStackTrace();
//                toast("获取数据失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//
//                Log.i(TAG,responseText.toString());
//            }
//        });
//    }
}
