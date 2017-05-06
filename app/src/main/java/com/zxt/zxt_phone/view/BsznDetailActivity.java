package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BsznDetailActivity extends BaseActivity {


    private String TAG = BsznDetailActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.tv_blcx)
    TextView tvBlcx;
    @BindView(R.id.tv_sbcl)
    TextView tvSbcl;
    @BindView(R.id.tv_zrks)
    TextView tvZrks;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    BsznInfoModel model;
    List<BsznInfoModel.DataBean> mDatas=new ArrayList<>();

    Intent mIntent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bszn_detail);
        ButterKnife.bind(this);


        mIntent = getIntent();
        model = (BsznInfoModel) mIntent.getSerializableExtra("allData");
        position= Integer.parseInt(mIntent.getStringExtra("position"));

        initView();
    }

    private void initView() {
        tabName.setText(R.string.bszn_info);


        tvRightTitle.setText(model.getData().get(position).getServe());
        tvBlcx.setText(model.getData().get(position).getProcedures());
        tvSbcl.setText(model.getData().get(position).getMaterials());
        tvZrks.setText(model.getData().get(position).getResponsible());
        tvMoney.setText(model.getData().get(position).getCategoryid() + "");
        tvPhone.setText(model.getData().get(position).getNumber());
    }
}
