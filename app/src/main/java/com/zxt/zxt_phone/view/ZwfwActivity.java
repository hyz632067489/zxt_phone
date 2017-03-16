package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.ZwfwModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;

public class ZwfwActivity extends BaseActivity {

    private String TAG = ZwfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.banner_holder)
    BannerHolderView holder;

    @BindView(R.id.gv_list)
    GridView gvList;

    private List<ZwfwModel> mDatas;

      private String[] titles = {"社区概况", "党群风采", "诉求提交", "结果查询", "网格管理", "预约办事",
            "网上办事", "满意度查询", "群团服务", "群工平台", "政策资讯"};

    Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwfw);

        initView();
    }

    private void initView() {

        tabName.setText(R.string.m_zwfw);
        //获得数据
        initDatas();

        HolderAttr.Builder builder = holder.getHolerAttr();//获取Holder配置参数构建对象
        builder.setSwitchDuration(900)//设置切换Banner的持续时间
                .setAutoLooper(true)//开启自动轮播
                .setLooperTime(1000)//设置轮播间隔时间
                .setBannerClickListenenr(new BannerClickListenenr() {//Banner图片点击事件
                    @Override
                    public void onBannerClick(int p) {
                        //p: 页面索引
                    }
                });
        holder.setHolerAttr(builder);
        List<Bitmap> mpas = new ArrayList<>();
        //测试Bitmap
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao1));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao2));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao3));
        //设置图片集合
        holder.setHolderBitmaps(mpas);

        gvList.setAdapter(new CommonAdapter<ZwfwModel>(getApplication(), mDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, ZwfwModel item) {

                holder.setImageResource(R.id.im_item, item.getImage());
                holder.setText(R.id.tv_item, item.getName());
            }
        });
    }

    @OnItemClick(R.id.gv_list)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
       int i =  parent.getSelectedItemPosition();
//        {"社区概况", "党群风采", "诉求提交", "结果查询", "网格管理", "预约办事",
//                "网上办事", "满意度查询", "群团服务", "群工平台", "政策资讯"};
        switch (position) {
            case 0:
                toast("社区概况");
                break;
            case 1:
                toast("党群风采");
                break;
            case 2:
                toast("诉求提交");
                break;
            case 3:
                toast("结果查询");
                break;
            case 4:
                toast("网格管理");
                mIntent = new Intent(mActivity,WsbsActivity.class);
                startActivity(mIntent);
                break;
            case 5:
                toast("预约办事");
                break;
            case 6:
                toast("网上办事");
                break;
            case 7:
                toast("满意度查询");
                break;
            case 8:
                toast("群团服务");
                mIntent = new Intent(mActivity,QtfwActivity.class);
                startActivity(mIntent);
                break;
            case 9:
                toast("群工平台");
                break;
            case 10:
                mIntent = new Intent(mActivity,ZczxActivity.class);
                startActivity(mIntent);
                break;
        }
    }


    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<ZwfwModel>();
        ZwfwModel zwfwModel = null;
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            zwfwModel = new ZwfwModel();
            zwfwModel.setName(titles[i]);
            zwfwModel.setImage(R.drawable.m_zwfw);
            mDatas.add(zwfwModel);
        }
    }
}
