package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于物业
 */
public class WebViewActivity extends BaseActivity {

    private String TAG = WebViewActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tvTitle;





    @BindView(R.id.webView)
    WebView mWebView;

    Intent mIntent;
    WebSettings settings;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mIntent = getIntent();

        initView();
    }

    private void initView() {
        tvTitle.setText(mIntent.getStringExtra("title"));

         settings = mWebView.getSettings();
        type= mIntent.getStringExtra("type");

        if("公交路线".equals(mIntent.getStringExtra("title")) || "1".equals(type)|| "8".equals(type)|| "7".equals(type)|| "5".equals(type)|| "9".equals(type)|| "11".equals(type)){
            LoadUrl1(mIntent.getStringExtra("url"));
        }else {
            LoadUrl(mIntent.getStringExtra("url"));
        }
    }

    private void LoadUrl(String url) {
        //启用支持javascript
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.setBackgroundColor(0);
        settings.setDisplayZoomControls(false);
        //WebView加载web资源
        mWebView.loadUrl(url);
//        mWebView.loadUrl("http://oa.ybqtw.org.cn/api/MobileHtmlShow.ashx?method=newshtml&userid=1&Key=21218CCA77804D2BA1922C33E0151105&typeVer=&id=1267");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }


    private void LoadUrl1(String url) {
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
//        mWebView.setScrollBarStyle(0);
        mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
        mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示


        settings.setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT| WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setNeedInitialFocus(false);
        mWebView.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
        settings.setDomStorageEnabled(true); //显示全景的问题



//启用数据库
        settings.setDatabaseEnabled(true);


        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", this.MODE_PRIVATE).getPath();


        settings.setGeolocationDatabasePath(dir);


//启用地理定位
        settings.setGeolocationEnabled(true);

        mWebView.loadUrl(url);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });


        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view,String url)
            {
//                loading.dismiss();
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                loading.dismiss();
            }
        });
    }




//    private void getData() {
//
//        MyApplication.okHttpUtil.get(Constants.BASE_URL + "method=grproerty" + "&DeptId=" + SharedPreUtil.getValue(mContext, "userinfo", "DeptId", ""), new HttpCallBack() {
//            @Override
//            public void OnSuccess(String jsonStr) {
//
////                android.util.Log.e("TAG", "" + SharedPreUtil.getValue(mContext, "userinfo", "DeptId", ""));
//
//                android.util.Log.e("MyTAG", "" + jsonStr);
//
//                try {
//                    JSONObject object = new JSONObject(jsonStr);
//                    String a = object.getString("Status");
////                    android.util.Log.e("MyTAG", "a=" + a);
//                    if ("0".equals(a)) {
//                        toast("暂无数据");
//                    } else if ("1".equals(a)) {
//                        tvTitleContent.setText(object.getString("Company"));
//                        tvContent.setText(object.getString("Content"));
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void OnError(String jsonStr) {
//
//            }
//        });
//    }
}
