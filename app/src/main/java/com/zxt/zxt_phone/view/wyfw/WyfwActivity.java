package com.zxt.zxt_phone.view.wyfw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.view.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WyfwActivity extends BaseActivity {

    private String TAG = WyfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.gv_view)
    GridView myGridView;

    private int imgWy[] = {R.drawable.shfw_gywy, R.drawable.shfw_wygg, R.drawable.shfw_aqgl, R.drawable.shfw_gzbx,
            R.drawable.shfw_jftj, R.drawable.shfw_pjgl};
    private String titleWy[] = {"关于物业", "物业公告", "安全管理", "故障报修",
            "纠纷调解", "评价管理"};

    CommonModel model;
    CommonAdapter<CommonModel> myAdapter;
    List<CommonModel> mDatas = new ArrayList<>();

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shfw);

        getDatas();
        initView();
    }

    private void initView() {
        tabName.setText("社会服务");

        myAdapter = new CommonAdapter<CommonModel>(mContext, mDatas, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        myGridView.setAdapter(myAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mIntent = new Intent();
                switch (position) {
                    case 0:
//                    {"关于物业", "物业公告", "安全管理", "故障报修", "纠纷调解", "评价管理"};
//                        toast("点击了" + "关于物业");
                        mIntent.setClass(mActivity, NewsDetailActivity.class);
                        mIntent.putExtra("title", "关于物业");
                        mIntent.putExtra("url", Url.BASE_URL_HTML + "GyPro.aspx");

                        break;
                    case 1:
//                        toast("点击了" + "物业公告");
                        mIntent.setClass(mActivity, WyggActivity.class);
                        break;
                    case 2:
//                        toast("点击了" + "安全管理");
                        mIntent.setClass(mActivity, SafetyActivity.class);
                        break;
                    case 3:
//                        toast("点击了" + "故障报修");
                        mIntent.setClass(mActivity, RepairsActivity.class);
                        break;
                    case 4:
//                        toast("点击了" + "纠纷调解");
                        mIntent.setClass(mActivity, JftjActivity.class);
                        break;
                    case 5:
//                        toast("点击了" + "评价管理");
                        mIntent.setClass(mActivity, PjglActivity.class);
                        break;
                }
                startActivity(mIntent);
            }
        });
    }

    private void getDatas() {

        for (int i = 0; i < titleWy.length; i++) {
            model = new CommonModel();
            model.setIcon(imgWy[i]);
            model.setName(titleWy[i]);

            mDatas.add(model);
        }
    }
}
