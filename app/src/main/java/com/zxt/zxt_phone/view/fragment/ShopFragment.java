package com.zxt.zxt_phone.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.TipDataModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.bean.model.EvaluationPic;
import com.zxt.zxt_phone.bean.model.ShopTypeModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.shop.ShopListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;
import okhttp3.Call;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class ShopFragment extends BaseFragment {

    View view;
    private String TAG = "ShopFragment";
    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.return_back)
    TextView retBtn;

    @BindView(R.id.banner_holder)
    BannerHolderView bannerHolder;
    @BindView(R.id.grid_view_1)
    HomeGridView gridView1;

    Unbinder unbinder;

    CommonAdapter<ShopTypeModel.DataBean> shopAdapter;
    List<ShopTypeModel.DataBean> shopDatas = new ArrayList<>();
    ShopTypeModel model;


    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.nineGrid)
    NineGridView nineGrid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_fragment_view, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //获取功能分类
        getDatas();
        //初始化数据源
//        initDatas();

        initView();
    }


    private void initView() {
        retBtn.setVisibility(View.GONE);
        tabName.setText(R.string.main_shop);

        //轮播图
        setBanner();

        //设置adapter
        setAdapter();

        setShopIng();
    }


    /**
     * 轮播图
     */
    private void setBanner() {
        HolderAttr.Builder builder = bannerHolder.getHolerAttr();//获取Holder配置参数构建对象
        builder.setSwitchDuration(900)//设置切换Banner的持续时间
                .setAutoLooper(true)//开启自动轮播
                .setLooperTime(1000)//设置轮播间隔时间
                .setBannerClickListenenr(new BannerClickListenenr() {//Banner图片点击事件
                    @Override
                    public void onBannerClick(int p) {
                        //p: 页面索引
                    }
                });
        bannerHolder.setHolerAttr(builder);
        List<Bitmap> mpas = new ArrayList<>();

//测试Bitmap
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao1));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao2));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao3));

//设置图片集合
        bannerHolder.setHolderBitmaps(mpas);
    }


    /**
     * 功能类型展示
     */
    private void setAdapter() {

        //功能展示
        shopAdapter = new CommonAdapter<ShopTypeModel.DataBean>(getContext(), shopDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, ShopTypeModel.DataBean item) {

//                holder.setImageResource(R.id.im_item, item.getIcon());
                holder.setText(R.id.tv_item, item.getClassName());
            }
        };
        gridView1.setAdapter(shopAdapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getActivity(), ShopListActivity.class)
                        .putExtra("datas", model)
                        .putExtra("position", position + "")
                        .putExtra("title", shopDatas.get(position).getClassName()));

            }
        });

    }

    /**
     * 设置二手交易展示
     */
    private void setShopIng() {
        tvUsername.setText("商品：" + "西门子 272L 三门冰箱");

        tvPrice.setText("价格：" + "¥2000");

        tvContent.setText("描述：" + "购于2014年6月新世纪百货，无坏无修无磕碰无暗病，因置换转让。");

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        String[] icons = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495852402&di=d723d6dd16a68d67c0d1a37bcff1ac08&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.talkandroid.com%2Fuploads%2F2013%2F02%2FGet_Free_Apps_Splash_Banner.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495257672401&di=41de6a7852ae0f66746b917d4010e166&imgtype=0&src=http%3A%2F%2Fimages.cnblogs.com%2Fcnblogs_com%2Fcaiqinghua%2F123214234.PNG",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495257672401&di=1c7af332077884f197dc29df88cc8250&imgtype=0&src=http%3A%2F%2Fcdn8.staztic.com%2Fapp%2Fa%2F2755%2F2755840%2Fwoodstovepro-1372114351-0-s-307x512.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495257672400&di=a4e2e593a3a04e181055fad436598fbb&imgtype=0&src=http%3A%2F%2Fcdn9.staztic.com%2Fapp%2Fa%2F5512%2F5512836%2Fpanama-city-fl-10000-1-s-307x512.jpg"};
        for (int i = 0; i < icons.length; i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(icons[i]);
            info.setBigImageUrl(icons[i]);
            imageInfo.add(info);
        }
//        List<EvaluationPic> imageDetails = item.getAttachments();

//        if (imageDetails != null) {
//            for (EvaluationPic imageDetail : imageDetails) {
//                ImageInfo info = new ImageInfo();
//                info.setThumbnailUrl(imageDetail.smallImageUrl);
//                info.setBigImageUrl(imageDetail.imageUrl);
//                imageInfo.add(info);
//            }
//        }

        nineGrid.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));

    }

    /**
     * 功能分类
     */
    private void getDatas() {
        OkHttpUtils.get()
                .url(Url.URL_SHOP + "api/loadLevelOne.htm")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            if ("200".equals(obj.getString("statusCode"))) {

                                model = new Gson().fromJson(response, ShopTypeModel.class);

                                shopDatas.clear();
                                shopDatas.addAll(model.getData());

                                shopAdapter.notifyDataSetChanged();

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 初始化数据源
     */
    private void initDatas() {


//        shopDatas.clear();
//        shopDatas.addAll(TipDataModel.getShop());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
