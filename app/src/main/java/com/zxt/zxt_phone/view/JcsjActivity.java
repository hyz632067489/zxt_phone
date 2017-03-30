package com.zxt.zxt_phone.view;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.JcsjModel;
import com.zxt.zxt_phone.bean.model.JgcxModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hyz on 2017/3/7.
 * powered by company
 */

public class JcsjActivity extends BaseActivity {

    private String TAG = JcsjActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.gv_list)
    GridView gvList;

    private List<JcsjModel> mLists = new ArrayList<>();
    CommonAdapter<JcsjModel> myAdapter;

    private String[] names = {"小区信息", "楼栋信息", "房屋信息", "公共设施", "道路信息", "企业信息", "商铺信息"};
    private int[] images = {R.drawable.zydj_lgy_ico, R.drawable.zydj_lgy_ico, R.drawable.zydj_lgy_ico,
            R.drawable.zydj_lgy_ico, R.drawable.zydj_lgy_ico,
            R.drawable.zydj_lgy_ico, R.drawable.zydj_lgy_ico,};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jcsj_activity);
        initView();

        initData();
    }


    private void initView() {
        tabName.setText(R.string.jcsj);

        myAdapter = new CommonAdapter<JcsjModel>(mActivity,mLists,R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, JcsjModel item) {

                holder.setImageResource(R.id.im_item, item.getImage());
                holder.setText(R.id.tv_item, item.getName());
            }
        };
        gvList.setAdapter(myAdapter);
    }

    private void initData() {

        JcsjModel jcsjModel=null;
        for(int i = 0;i<names.length;i++){
            jcsjModel = new JcsjModel();
            jcsjModel.setName(names[i]);
            jcsjModel.setImage(images[i]);

            mLists.add(jcsjModel);
        }
    }

}
