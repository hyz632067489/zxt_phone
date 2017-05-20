package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.TipDataModel;
import com.zxt.zxt_phone.bean.model.BsznModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.view.bmfw.CzjfActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.easytagdragview.EasyTipDragView;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.SimpleTitleTip;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.Tip;
import com.zxt.zxt_phone.view.customview.easytagdragview.widget.TipItemView;
import com.zxt.zxt_phone.view.wyfw.JftjActivity;
import com.zxt.zxt_phone.view.wyfw.PjglActivity;
import com.zxt.zxt_phone.view.wyfw.RepairsActivity;
import com.zxt.zxt_phone.view.wyfw.SafetyActivity;
import com.zxt.zxt_phone.view.wyfw.WyggActivity;
import com.zxt.zxt_phone.view.zwfw.DqfcActivity;
import com.zxt.zxt_phone.view.zwfw.pasq.PasqActivity;
import com.zxt.zxt_phone.view.zwfw.sqgk.SqgkActivity;
import com.zxt.zxt_phone.view.zwfw.sqtj.SqtjActivity;
import com.zxt.zxt_phone.view.zwfw.yybs.YybsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewCustomActivity extends BaseActivity {


    private String TAG = ViewCustomActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    private EasyTipDragView easyTipDragView;


    @BindView(R.id.grid_view_1)
    HomeGridView gridView1;
    @BindView(R.id.grid_view_2)
    HomeGridView gridView2;
    @BindView(R.id.grid_view_3)
    HomeGridView gridView3;

    CommonAdapter<CommonModel> mAdapter1, mAdapter2, mAdapter3;
    CommonModel model;
    List<CommonModel> mList1, mList2, mList3;

    private static String[] names1 = {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事", "办事指南"
            , "平安社区", "群团服务", "群工平台", "精准帮扶", "网格管理"};
    private static int[] icon1 = {R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6, R.drawable.ic_category_7,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6, R.drawable.ic_category_7, R.drawable.ic_category_7};


    private static String[] names2 = {"社区电商", "社区活动", "社区交流", "家庭维修", "家教", "二手交易", "就业服务"
            , "智能家居", "快递代收", "公交路线", "话费查询", "家居保洁"};

    private static int[] icon2 = {R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6, R.drawable.ic_category_7
            , R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6, R.drawable.ic_category_7};


    private static String[] names3 = {"关于物业", "物业公告", "安全管理", "故障报修", "纠纷调解", "评价调研"};
    private static int[] icon3 = {R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6};


    Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_custom);
        ButterKnife.bind(this);

        getData();
        initView();
    }

    private void getData() {

        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
        mList3 = new ArrayList<>();

        for (int i = 0; i < names1.length; i++) {
            model = new CommonModel();
            model.setIcon(icon1[i]);
            model.setName(names1[i]);
            mList1.add(model);
        }

        for (int i = 0; i < names2.length; i++) {
            model = new CommonModel();
            model.setIcon(icon2[i]);
            model.setName(names2[i]);
            mList2.add(model);
        }

        for (int i = 0; i < names3.length; i++) {
            model = new CommonModel();
            model.setIcon(icon3[i]);
            model.setName(names3[i]);
            mList3.add(model);
        }
    }


    private void initView() {

        tabName.setText("自定义目录");


        mAdapter1 = new CommonAdapter<CommonModel>(mContext, mList1, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        gridView1.setAdapter(mAdapter1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = mList1.get(position).getName();
                goActivity(name);
            }
        });


        mAdapter2 = new CommonAdapter<CommonModel>(mContext, mList2, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        gridView2.setAdapter(mAdapter2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = mList2.get(position).getName();
                goActivity(name);
            }
        });


        mAdapter3 = new CommonAdapter<CommonModel>(mContext, mList3, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        gridView3.setAdapter(mAdapter3);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = mList3.get(position).getName();
                goActivity(name);
            }
        });
    }

    private void goActivity(String name) {
        //                    {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事", "办事指南"
//                                , "平安社区", "群团服务", "群攻平台", "精准帮扶"
//                                , "网格管理", "社区电商", "社区活动", "社区交流"
//                                , "家庭维修", "家教", "二手交易", "就业服务"
//                                , "智能家居", "快递代收", "公交路线", "充值缴费"
//                                , "家政保洁", "关于物业", "物业公告", "安全管理"
//                                , "故障报修", "纠纷调解", "评价调研"} ;

        mIntent = new Intent();
        if ("社区概况".equals(name)) {
            mIntent.setClass(mActivity, SqgkActivity.class);
        } else if ("党群风采".equals(name)) {
            mIntent.setClass(mActivity, DqfcActivity.class);
        } else if ("诉求提交".equals(name)) {
            mIntent.setClass(mActivity, SqtjActivity.class);
        } else if ("结果查询".equals(name)) {
            mIntent.setClass(mActivity, JgcxActivity.class);
        } else if ("政策信息".equals(name)) {
            mIntent.setClass(mActivity, ZczxActivity.class);

        } else if ("预约办事".equals(name)) {
            mIntent.setClass(mActivity, YybsActivity.class);
        } else if ("办事指南".equals(name)) {
            mIntent.setClass(mActivity, BsznActivity.class);
        } else if ("平安社区".equals(name)) {
            mIntent.setClass(mActivity, PasqActivity.class);
        } else if ("群团服务".equals(name)) {

        } else if ("群攻平台".equals(name)) {

        } else if ("精准帮扶".equals(name)) {

        } else if ("网格管理".equals(name)) {
            mIntent.setClass(mActivity, WsbsActivity.class);
        } else if ("社区电商".equals(name)) {
            mIntent.setClass(mActivity, SqgwActivity.class);
        } else if ("社区活动".equals(name)) {
            mIntent.setClass(mActivity, TestActivity.class);
        } else if ("社区交流".equals(name)) {

        } else if ("家庭维修".equals(name)) {

        } else if ("家教".equals(name)) {

        } else if ("二手交易".equals(name)) {

        } else if ("就业服务".equals(name)) {

        } else if ("智能家居".equals(name)) {

        } else if ("快递代收".equals(name)) {

            return;
        } else if ("公交路线".equals(name)) {

        } else if ("充值缴费".equals(name)) {
            mIntent.setClass(mActivity, CzjfActivity.class);
        } else if ("家政保洁".equals(name)) {

        } else if ("关于物业".equals(name)) {
//            mIntent.setClass(mActivity, NewsDetailActivity.class);
//            mIntent.putExtra("title", "关于物业");
//            mIntent.putExtra("url", Url.BASE_URL_HTML + "GyPro.aspx"+"?id="+choseId);
        } else if ("物业公告".equals(name)) {
            mIntent.setClass(mActivity, WyggActivity.class);
        } else if ("安全管理".equals(name)) {
            mIntent.setClass(mActivity, SafetyActivity.class);
        } else if ("故障报修".equals(name)) {
            mIntent.setClass(mActivity, RepairsActivity.class);
        } else if ("纠纷调解".equals(name)) {
            mIntent.setClass(mActivity, JftjActivity.class);
        } else if ("评价调研".equals(name)) {
            mIntent.setClass(mActivity, PjglActivity.class);
        } else if ("全部分配".equals(name)) {
            mIntent.setClass(mActivity, ViewCustomActivity.class);
//            mIntent.putExtra("title", (Serializable) mDatas);
            startActivityForResult(mIntent, 200);
            return;
        }
        startActivity(mIntent);

    }

    /**
     * 选择标签
     */
    private void choseTip() {
        easyTipDragView = (EasyTipDragView) findViewById(R.id.easy_tip_drag_view);
        //设置已包含的标签数据
        easyTipDragView.setAddData(TipDataModel.getAddTips());
        //设置可以添加的标签数据
        easyTipDragView.setDragData(TipDataModel.getDragTips());
        //在easyTipDragView处于非编辑模式下点击item的回调（编辑模式下点击item作用为删除item）
        easyTipDragView.setSelectedListener(new TipItemView.OnSelectedListener() {
            @Override
            public void onTileSelected(Tip entity, int position, View view) {
                toast(((SimpleTitleTip) entity).getTip());
            }
        });
        //设置每次数据改变后的回调（例如每次拖拽排序了标签或者增删了标签都会回调）
        easyTipDragView.setDataResultCallback(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                Log.i("heheda", tips.toString());
            }
        });
        //设置点击“确定”按钮后最终数据的回调
        easyTipDragView.setOnCompleteCallback(new EasyTipDragView.OnCompleteCallback() {
            @Override
            public void onComplete(ArrayList<Tip> tips) {
                toast("最终数据：" + tips.toString());
                //   btn.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.putExtra("data", tips);
                setResult(RESULT_OK, intent);

            }
        });
        easyTipDragView.open();
    }
}
