package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BmfwModel;
import com.zxt.zxt_phone.bean.model.ZwfwModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;


/**
 * by hyz
 * 便民服务
 */
public class BmfwActivity extends BaseActivity {

    private String TAG = BmfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.banner_holder)
    BannerHolderView holder;

    @BindView(R.id.gv_list)
    GridView gvList;

    private List<BmfwModel> mDatas;

    private String[] titles = {"社区电商", "社区活动", "社区交流", "家庭维修", "家教", "二手交易",
            "就业服务", "职能家居", "快递代收", "公交路线", "话费查询", "家居保洁"};

    Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmfw);

        initView();
    }

    private void initView() {

        tabName.setText(R.string.bmfw);
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

        gvList.setAdapter(new CommonAdapter<BmfwModel>(getApplication(), mDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, BmfwModel item) {

                holder.setImageResource(R.id.im_item, item.getImage());
                holder.setText(R.id.tv_item, item.getName());
            }
        });
    }

    @OnItemClick(R.id.gv_list)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0://社区电商
                mIntent = new Intent(mActivity, SqgwActivity.class);
                startActivity(mIntent);
                break;
            case 1://社区活动
                toast("社区活动");
                break;
            case 2:
                toast("社区交流");
                break;
            case 3:
                toast("家庭维修");
                break;
            case 4:
                toast("家教");
//                mIntent = new Intent(mActivity,WsbsActivity.class);
//                startActivity(mIntent);
                break;
            case 5:
                toast("二手交易");
                break;
            case 6:
                toast("就业服务");
                break;
            case 7:
                toast("智能家居");
                break;
            case 8:
//                toast("快递代收");
                mIntent = new Intent(mActivity, WebViewActivity.class);
                mIntent.putExtra("type", "5");
                mIntent.putExtra("title", "快递代收");
                mIntent.putExtra("url", "http://wap.guoguo-app.com/");
                startActivity(mIntent);
                break;
            case 9:
                toast("公交路线");
                break;
            case 10://话费查询

                break;
            case 11://家居保洁

                break;
        }
    }


    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<BmfwModel>();
        BmfwModel bwfwModel = null;
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            bwfwModel = new BmfwModel();
            bwfwModel.setName(titles[i]);
            bwfwModel.setImage(R.drawable.m_zczx);
            mDatas.add(bwfwModel);
        }
    }
}
