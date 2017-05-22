package com.zxt.zxt_phone.view.fragment.life;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.TipDataModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.bean.model.MarqueeModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.GlideImage;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.fragment.LifeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by hkc on 2017/5/22.
 */

public class ShenghuoFragment extends BaseFragment implements OnBannerListener {

    View view;

    Unbinder unbinder;

    @BindView(R.id.banner3)
    Banner banner3;

    public static List<String> images = new ArrayList<>();
    public static List<String> titles = new ArrayList<>();


    List<MarqueeModel.DataNewsModel> mDataNews = new ArrayList<>();
    String url;
    MarqueeModel marqueeModel;

    private int page = 1; //页数


    @BindView(R.id.grid_view_1)
    HomeGridView gridView1;

    CommonAdapter<CommonModel> lifeAdapter;
    List<CommonModel> lifeDatas = new ArrayList<>();


    // Fragment管理对象
    private FragmentManager manager;
    private FragmentTransaction ft;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sheng_huo_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        getDatas();
        initView();
        //开始轮播
        banner3.startAutoPlay();
    }


    private void initView() {

        lifeDatas.clear();
        lifeDatas.addAll(TipDataModel.getLife());

        lifeAdapter = new CommonAdapter<CommonModel>(getContext(), lifeDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.im_item, item.getIcon());
                holder.setText(R.id.tv_item, item.getName());
            }
        };
        gridView1.setAdapter(lifeAdapter);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("全部分类".equals(lifeDatas.get(position).getName())){

                }
            }
        });
    }

    /**
     * 设置轮播图Banner
     */
    private void setBanner() {
        banner3.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImage())
                .setOnBannerListener(this)
                .start();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    setBanner();
                    break;
            }
        }
    };

    @Override
    public void OnBannerClick(int position) {
        toast("你点击了：" + titles.get(position));

    }


    /**
     * 获取垂直轮播数据
     */
    private void getDatas() {

        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(getActivity(), "TVInfoId"));
        params.put("method", "IndexNews");
        params.put("PageSize", "6");
        params.put("Page", String.valueOf(page));
        params.put("Key", SharedPrefsUtil.getString(getActivity(), "Key"));
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        MLog.i(getTag(), "response==" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("Data");
                            url = obj.getString("NewsShowUrl");
                            marqueeModel = new Gson().fromJson(response, MarqueeModel.class);

                            mDataNews.addAll(marqueeModel.getData());

                            titles.clear();
                            images.clear();
                            for (int i = 0; i < array.length(); i++) {
                                titles.add(mDataNews.get(i).getTitle());
                                images.add(mDataNews.get(i).getImageIndex());
                            }

                            mHandler.sendEmptyMessage(0);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner3.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
