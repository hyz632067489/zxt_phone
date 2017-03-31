package com.zxt.zxt_phone.view.zwfw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.GzrzListModel;
import com.zxt.zxt_phone.constant.Url;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

public class GzrzInfoActivity extends BaseActivity {

    private String TAG = GzrzInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    GzrzListModel.ListBean model ;
    Intent mIntent;
    private String mBlogId;

    @BindView(R.id.tv_content)
    TextView mContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzrz_info);

        mIntent = getIntent();
        model = (GzrzListModel.ListBean) mIntent.getSerializableExtra("list");
        initView();
    }

    private void initView() {
        tabName.setText(R.string.gzrz_info);

        mContent.setText(model.getBlogContent());
    }

    @Override
    protected void onStart() {
        super.onStart();

    }




}
