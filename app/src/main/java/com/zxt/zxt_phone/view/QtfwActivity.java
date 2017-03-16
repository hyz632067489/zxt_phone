package com.zxt.zxt_phone.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;

import butterknife.BindView;
import okhttp3.Call;

public class QtfwActivity extends BaseActivity {

    private String TAG = QtfwActivity.class.getCanonicalName();

    TextView rtback;
    TextView tabName;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtfw);


//        initData();

        initView();

    }


    @Override
    public void onBackPressed() {

        if(webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

    private void initView() {
        rtback = (TextView) findViewById(R.id.return_back);
        tabName = (TextView) findViewById(R.id.tab_name);
        tabName.setText(R.string.zwfw_qtfw);
         webView = (WebView) findViewById(R.id.webview);
        rtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        //WebView加载web资源
        webView.loadUrl("http://oa.ybqtw.org.cn/api/Html/ApiReso.aspx?&TVInfoId=19&Key=21218CCA77804D2BA1922C33E0151105&ClassId=3");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


    }


    private void initData() {
        //&TVInfoId=19
        // &Key=21218CCA77804D2BA1922C33E0151105
        // &ClassId=3

        OkHttpUtils.get()
                .url(Url.URL_HTML)
                .addParams("TVInfoId", "19")
                .addParams("Key", "21218CCA77804D2BA1922C33E0151105")
                .addParams("ClassId", "3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

}
