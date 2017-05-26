package com.zxt.zxt_phone.view.shop;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.GridviewAdapter;
import com.zxt.zxt_phone.adapter.ShopPagerAdapter;
import com.zxt.zxt_phone.adapter.ViewPagerAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.ShopTypeModel;
import com.zxt.zxt_phone.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopListActivity extends BaseActivity {

    private String TAG = ShopListActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_dot)
    LinearLayout mLlDot;


    List<ShopTypeModel.DataBean.ChildsBeanX> mDatas = new ArrayList<>();

    ShopTypeModel shopTypeModel;
    int position;

    private LayoutInflater mInflater;
    private List<View> mPagerList;
    private int page = 1; //页数

    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;


    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        mIntent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        shopTypeModel = (ShopTypeModel) mIntent.getSerializableExtra("datas");
        position = Integer.parseInt(mIntent.getStringExtra("position"));
        tabName.setText(mIntent.getStringExtra("title"));



        MLog.i(TAG,"========="+mDatas.get(0).getClassName());
    }

    private void setDatas() {
        mDatas.clear();
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        } else {
            for (int i = 0; i < shopTypeModel.getData().size(); i++) {
                mDatas.addAll(shopTypeModel.getData().get(i).getChilds());
            }

        }
    }

    /**
     * 仿美团功能选择切换
     */
    private void setPagerFunction() {

//        rePager.setVisibility(View.VISIBLE);

        //初始化数据源
        setDatas();


        mInflater = LayoutInflater.from(mActivity);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) mInflater.inflate(R.layout.gridview_1, mViewPager, false);
            gridView.setAdapter(new ShopPagerAdapter(mContext, mDatas, i, pageSize));
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    toast(mDatas.get(pos).getClassName() + "==" + pos + "==" + position);
                    String name = mDatas.get(pos).getClassName();

//                    goActivity(name);

                }
            });
        }

        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
//        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        mLlDot.clearFocus();
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
