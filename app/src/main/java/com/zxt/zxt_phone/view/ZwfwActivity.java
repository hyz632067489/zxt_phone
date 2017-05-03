package com.zxt.zxt_phone.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.ZwfwModel;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.zwfw.DqfcActivity;
import com.zxt.zxt_phone.view.zwfw.sqgk.SqgkActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;

public class ZwfwActivity extends BaseActivity {

    private String TAG = ZwfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.sign_in)
    TextView singLogin;

    @BindView(R.id.banner_holder)
    BannerHolderView holder;

    @BindView(R.id.gv_list)
    GridView gvList;

    private List<ZwfwModel> mDatas;


int []icons = {R.drawable.zwfw_sqgk, R.drawable.zwfw_dqfc,R.drawable.zwfw_sqtj,R.drawable.zwfw_jgcx,
        R.drawable.zwfw_zcxx,R.drawable.zwfw_yybs,R.drawable.zwfw_bszn,R.drawable.zwfw_pasq,
        R.drawable.zwfw_qtfw,R.drawable.zwfw_qgpt,R.drawable.zwfw_jzbf, R.drawable.zwfw_wggl};



    private String[] titles = {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事",
            "办事指南", "平安社区", "群团服务", "群工平台","精准帮扶"};
    private String[] titleName = {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事",
            "办事指南", "平安社区", "群团服务", "群工平台", "精准帮扶","网格管理"};
    Intent mIntent;

    String Dept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwfw);


    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        tabName.setText(R.string.m_zwfw);
        Dept = SharedPrefsUtil.getString(mActivity, "roleLevel");
        Log.i(TAG, "Dept============" + Dept);
        if (" ".equals(Dept)) {
            singLogin.setVisibility(View.VISIBLE);
        } else {
            singLogin.setVisibility(View.GONE);
        }


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
        int i = parent.getSelectedItemPosition();
//       {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事",
//        "办事指南", "平安社区", "群团服务", "群工平台", "精准帮扶","网格管理"};
        mIntent = new Intent();
        switch (position) {
            case 0:
//                toast("社区概况");
                mIntent.setClass(ZwfwActivity.this, SqgkActivity.class);
                break;
            case 1:
//                toast("党群风采");
                mIntent.setClass(ZwfwActivity.this, DqfcActivity.class);
                break;
            case 2:
//                toast("诉求提交");
                mIntent.setClass(ZwfwActivity.this, SqtjActivity.class);
                break;
            case 3:
//                toast("结果查询");
                mIntent.setClass(ZwfwActivity.this, JgcxActivity.class);
                break;
            case 4:
//                toast("政策信息");
                mIntent.setClass(ZwfwActivity.this, ZczxActivity.class);
                break;
            case 5:
//                toast("预约办事");
                mIntent.setClass(ZwfwActivity.this, YybsInfoActivity.class);
                mIntent.putExtra("title",titles[5]);
                break;
            case 6:
//                toast("办事指南");
                mIntent.setClass(ZwfwActivity.this, BsznActivity.class);
                break;
            case 7:
                toast("平安社区");
                mIntent.setClass(ZwfwActivity.this, SqgkActivity.class);
                break;
            case 8:
//                toast("群团服务");
//                mIntent = new Intent(mActivity,QtfwActivity.class);
//                startActivity(mIntent);
                mIntent.setClass(ZwfwActivity.this, WebViewActivity.class);
                mIntent .putExtra("title", R.string.zwfw_qtfw);
                mIntent.putExtra("ClassId", "3");
                break;
            case 9:
//                toast("群工平台");
                mIntent.setClass(ZwfwActivity.this, WebViewActivity.class);
                mIntent .putExtra("title", R.string.zwfw_qtpt);
                mIntent .putExtra("ClassId", "2");
                break;
            case 10://精准帮扶
                mIntent.setClass(ZwfwActivity.this, WsbsActivity.class);
                break;
            case 11://网格管理
                mIntent.setClass(ZwfwActivity.this, WsbsActivity.class);
                break;
        }
        startActivity(mIntent);
    }



    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<ZwfwModel>();
        ZwfwModel zwfwModel = null;
        if (" ".equals(Dept)) {
            mDatas.clear();
            for (int i = 0; i < titles.length; i++) {
                //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
                zwfwModel = new ZwfwModel();
                zwfwModel.setName(titles[i]);
                zwfwModel.setImage(icons[i]);
                mDatas.add(zwfwModel);
            }
        } else {
            mDatas.clear();

            for (int i = 0; i < titleName.length; i++) {
                //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
                zwfwModel = new ZwfwModel();
                zwfwModel.setName(titleName[i]);
                zwfwModel.setImage(icons[i]);
                mDatas.add(zwfwModel);
            }
        }
    }

    @OnClick(R.id.sign_in)
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.sign_in:
//                mIntent = new Intent(mActivity, WsbsActivity.class);
//                startActivity(mIntent);
                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setTitle("管理员登录");
                dialog.setMessage("您是否确定登录网格管理页面");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mIntent = new Intent(mActivity, LoginActivity.class);
                        startActivity(mIntent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }
}
