package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

import butterknife.BindView;

public class NewsDetailActivity extends BaseActivity {

    private  String TAG = NewsListActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    WebView mWebView;

    Intent mIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mIntent = getIntent();
        Log.i(TAG,mIntent.getStringExtra("url"));
        initView();
    }

    private void initView() {

        tabName.setText(mIntent.getStringExtra("title"));
        mWebView = (WebView) findViewById(R.id.webView);

        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.setBackgroundColor(0);
        settings.setDisplayZoomControls(false);
        //WebView加载web资源
        mWebView.loadUrl( mIntent.getStringExtra("url"));
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
