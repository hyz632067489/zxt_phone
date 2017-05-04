package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.MyAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BsznInfoActivity extends BaseActivity {

    private String TAG = BsznInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.lv_liebiao)
    ListView lv_Liebiao;


    CommonAdapter<BsznInfoModel> myAdapter;
    List<BsznInfoModel> mDatas;
    Intent mIntent;

    private int id;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bszn_info);

        mIntent = getIntent();

        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");

        initView();
//        getData();
    }

    private void initView() {
        tabName.setText(R.string.bszn_liebiao);
        mDatas = new ArrayList<>();

        myAdapter = new CommonAdapter<BsznInfoModel>(mContext, mDatas, R.layout.bszn_liebiao_item) {
            @Override
            public void convert(ViewHolder helper, BsznInfoModel item) {

                helper.setImageResource(R.id.img_item,item.getImage());
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_all, item.getTv_all());
                helper.setText(R.id.tv_shengpi, item.getTv_sp());
                helper.setText(R.id.tv_fuwu, item.getTv_fw());
            }
        };
         lv_Liebiao.setAdapter(myAdapter);
    }


}
