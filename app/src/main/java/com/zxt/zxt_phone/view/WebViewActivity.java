package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

public class WebViewActivity extends BaseActivity {

    private String TAG = WebViewActivity.class.getCanonicalName();

    TextView rtback;
    TextView tabName;
    WebView mWebView;

    Intent mIntent;
    String newsUrl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mIntent = getIntent();
//        Log.i(TAG, mIntent.getStringExtra("url"));
        initView();

    }


    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private void initView() {
        rtback = (TextView) findViewById(R.id.return_back);
        rtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });

        tabName = (TextView) findViewById(R.id.tab_name);
        tabName.setText(mIntent.getStringExtra("title"));

        newsUrl = Url.URL_HTML + "?&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                + "&ClassId=" + mIntent.getStringExtra("ClassId");

        mWebView = (WebView) findViewById(R.id.webview);
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.setBackgroundColor(0);
        settings.setDisplayZoomControls(false);
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
}
