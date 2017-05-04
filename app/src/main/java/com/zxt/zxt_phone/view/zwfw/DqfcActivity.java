package com.zxt.zxt_phone.view.zwfw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.zwfw.dqfc.DqhdActivity;
import com.zxt.zxt_phone.view.zwfw.dqfc.FfclActivity;
import com.zxt.zxt_phone.view.zwfw.dqfc.SdxfActivity;
import com.zxt.zxt_phone.view.zwfw.dqfc.SjxxActivity;
import com.zxt.zxt_phone.view.zwfw.dqfc.ZsdxActivity;
import com.zxt.zxt_phone.view.zwfw.dqfc.ZyzfwActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 党群风采
 */
public class DqfcActivity extends BaseActivity {

    private String TAG = DqfcActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;


    @BindView(R.id.gv_list)
    HomeGridView gvList;


    CommonAdapter<CommonModel> myAdapter;
    private List<CommonModel> listData = new ArrayList<>();

    private CommonModel model;
    int[] icons = {R.drawable.dqfc_dqhd, R.drawable.dqfc_zyzfw, R.drawable.dqfc_zsdx,
            R.drawable.dqfc_sjxx, R.drawable.dqfc_sdxf, R.drawable.dqfc_ffcl};
    String[] titles = {"党群活动", "志愿者服务", "掌上党校", "书记信箱", "时代先锋", "反腐倡廉"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dqfc);

        getData();
        initView();
    }


    public void initView() {
        tabName.setText(R.string.zwfw_dqfc);


        myAdapter = new CommonAdapter<CommonModel>(mContext, listData, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
//                Glide.with(mContext).load(item.getCoverImg())//
//                        .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
//                        .error(R.drawable.ic_default_color)//
////                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
//                        .into((ImageView) holder.getView(R.id.repairs_img));
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        gvList.setAdapter(myAdapter);
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(DqfcActivity.this, DqhdActivity.class);
                        break;
                    case 1:
                        intent.setClass(DqfcActivity.this, ZyzfwActivity.class);
                        break;
                    case 2:
                        intent.setClass(DqfcActivity.this, ZsdxActivity.class);
                        break;
                    case 3:
                        intent.setClass(DqfcActivity.this, SjxxActivity.class);
                        break;
                    case 4:
                        intent.setClass(DqfcActivity.this, SdxfActivity.class);
                        break;
                    case 5:
                        intent.setClass(DqfcActivity.this, FfclActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }


    private void getData() {

        for (int i = 0; i < icons.length; i++) {
            model = new CommonModel();
            model.setIcon(icons[i]);
            model.setName(titles[i]);
            listData.add(model);
        }
    }

}
