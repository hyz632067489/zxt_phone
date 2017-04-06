package com.zxt.zxt_phone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class LtjdActivity extends BaseActivity {

    private String TAG = LtjdActivity.class.getCanonicalName();
    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.webview)
    WebView mWebView;

    String newsUrl;
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

//        requestWeather();
        initView();
    }

//    private void setCookies(String url){
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.setCookie(url, AppData.Cookie);
//    }

    private void initView() {
        tabName.setText(R.string.ltjd);

        mWebView = (WebView) findViewById(R.id.webview);
//        mWebView.setInitialScale(100);
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.setBackgroundColor(0);
        settings.setDisplayZoomControls(false);


        newsUrl = Url.URL_WG+"info/getOneAreaIntrInfo.do";
//        if(AppData.isLogin)
//            setCookies(newsUrl);
        //WebView加载web资源

        mWebView.loadUrl(newsUrl);
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    //请求
    public void requestWeather() {
//        String url = "http://192.168.1.125:8080/grid/user/validateUser.do?userName=admin&password=1234";
        String url = Url.BASE_URL + "/user/getOneUserInfo.do?userId=2";
        OkHttpUtils.post()
                .url("http://192.168.1.220:8080/grid/app/info/getOneAreaIntrInfo.do")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            newsUrl = obj.getString("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
