package com.zxt.zxt_phone.view.zwfw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.GzrzListModel;
import com.zxt.zxt_phone.constant.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

public class GzrzInfoActivity extends BaseActivity {

    private String TAG = GzrzInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    GzrzListModel.ListBean model;
    Intent mIntent;
    private String mBlogId;

    @BindView(R.id.tv_biaoti)
    TextView myBiaoti;
    @BindView(R.id.tv_content)
    TextView mContent;

    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;

    private ArrayList<String> imageItems;//当前选择的所有图片
    private String picString, myType;

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
        myBiaoti.setText(model.getBlogName());
        myType = model.getBlogType();
        mContent.setText(model.getBlogContent());

        if (myType.length() != 0) {
            if(myType.contains("1")){
               findViewById(R.id.tv_xuncha).setVisibility(View.VISIBLE);
            }
            if(myType.contains("2")){
                findViewById(R.id.tv_xuanchuan).setVisibility(View.VISIBLE);
            }
            if(myType.contains("3")){
                findViewById(R.id.tv_zoufang).setVisibility(View.VISIBLE);
            }
            if(myType.contains("4")){
                findViewById(R.id.tv_chuli).setVisibility(View.VISIBLE);
            }
        }

        imageItems = new ArrayList<>();
        picString = model.getBlogPic();
        if (picString.length() > 0 && picString != null) {
            String[] pics = picString.split(";");
            for (int i = 0; i < pics.length; i++) {
                imageItems.add(pics[i]);
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}
