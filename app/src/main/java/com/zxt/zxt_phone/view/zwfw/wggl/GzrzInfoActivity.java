package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.EvaluationPic;
import com.zxt.zxt_phone.bean.model.GzrzListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;

import java.util.ArrayList;

import butterknife.BindView;

public class GzrzInfoActivity extends BaseActivity {

    private String TAG = GzrzInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    GzrzListModel.DataBean.ListBean model;
    Intent mIntent;
    private String mBlogId;

    @BindView(R.id.tv_biaoti)
    TextView myBiaoti;
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.nineGrid)
    NineGridView myPicView;

    private ArrayList<EvaluationPic> imageItems;//当前选择的所有图片
    private String picString, myType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzrz_info);

        mIntent = getIntent();
        model = (GzrzListModel.DataBean.ListBean) mIntent.getSerializableExtra("list");
        initView();
    }

    private void initView() {
        tabName.setText(R.string.gzrz_info);

        tvName.setText(mIntent.getStringExtra("GridStaffName"));
        myBiaoti.setText(model.getBlogName());
        myType = model.getBlogType();
        mContent.setText(model.getBlogContent());

        if (myType.length() != 0) {
            if (myType.contains("1")) {
                findViewById(R.id.tv_xuncha).setVisibility(View.VISIBLE);
            }
            if (myType.contains("2")) {
                findViewById(R.id.tv_xuanchuan).setVisibility(View.VISIBLE);
            }
            if (myType.contains("3")) {
                findViewById(R.id.tv_zoufang).setVisibility(View.VISIBLE);
            }
            if (myType.contains("4")) {
                findViewById(R.id.tv_chuli).setVisibility(View.VISIBLE);
            }
        }

        imageItems = new ArrayList<>();
        picString = model.getBlogPic();
        if (picString.length() > 0 && picString != null) {
            String[] pics = picString.split(";");
            EvaluationPic picModel;
            for (int i = 0; i < pics.length; i++) {
                picModel = new EvaluationPic();
                picModel.setImageUrl(Url.URL_TU_PIAN + pics[i]);
                imageItems.add(picModel);
                MLog.i(TAG, "=1====" + Url.URL_TU_PIAN + pics[i]);
            }
        }

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        if (imageItems != null) {
            for (EvaluationPic imageDetail : imageItems) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail.imageUrl);
                info.setBigImageUrl(imageDetail.imageUrl);
                MLog.i(TAG, "=2====" + info.getBigImageUrl());
                MLog.i(TAG, "==3===" + info.getThumbnailUrl());
                imageInfo.add(info);
            }
        }

        myPicView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}
