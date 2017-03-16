package com.zxt.zxt_phone.view.fragment.shequdianshang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.ChoseTypeAdapter;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.model.SqdsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import me.militch.widget.bannerholder.BannerClickListenenr;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.HolderAttr;

import static com.zxt.zxt_phone.R.id.listview;

/**
 * Created by hyz on 2017/3/10.
 * powered by company
 */

public class SqdsMainFragment extends BaseFragment {


    @BindView(R.id.return_back)
    TextView rtBtn;

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.type_grid)
    GridView typeGrid;

    BannerHolderView holder;
    private List<SqdsModel> mDatas = new ArrayList<>();

    private int mImageSelected[] = {R.drawable.guanggao1, R.drawable.guanggao2, R.drawable.guanggao3};

    //    List<> data = new ArrayList();
    private String[] tests = {"部门1", "部门2", "部门3", "部门4", "部门5"};
    ChoseTypeAdapter choseTypeAdapter;
    MyAdapter myAdapter;

    public SqdsMainFragment() {

    }

    public static SqdsMainFragment newInstance() {

        return new SqdsMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_sqds_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        holder = (BannerHolderView) view.findViewById(R.id.banner_holder);
        choseTypeAdapter = new ChoseTypeAdapter();
        myAdapter = new MyAdapter(getContext());
        Log.i("TAG", "=============================");
        initView();
    }

    private void initView() {
        rtBtn.setVisibility(View.GONE);
        tabName.setText(R.string.sqgw_tabname);

//        mBannerHolder = (BannerHolderView) findViewById(R.id.banner_holder);

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
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao1));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao2));
        mpas.add(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao3));
//设置图片集合
        holder.setHolderBitmaps(mpas);



        int length = 150;
        int size = getDate().size();
        //获得屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 10) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        typeGrid.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        typeGrid.setColumnWidth(itemWidth); // 设置列表项宽
        typeGrid.setHorizontalSpacing(15); // 设置列表项水平间距
        typeGrid.setStretchMode(GridView.NO_STRETCH);
        typeGrid.setNumColumns(size); // 设置列数量=列表集合数
        typeGrid.setAdapter(myAdapter);


    }
    @OnItemClick(R.id.type_grid)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        String isCheck = eventTypeList.getData().get(position).isCheck;
        int count = getDate().size();

//        MLog.i(TAG, "是否获得分类数据 1 =============" + count);
//        for(int i=0; i<count; i++){
//            eventTypeList.getData().get(i).isCheck = "";
//        }
//        if(null == isCheck || isCheck.equals("")){
//            getDate().get(position).isCheck = "true";
//
//        }else if(isCheck.equals("true")){
//            getDate().get(position).isCheck = "";
//        }
//        eventTypeAdapter.notifyDataSetChanged();
//
//        typeValue =  eventTypeList.getData().get(position).typeName;

    }


    /**
     * 添加一个得到数据的方法，方便使用
     */
    private ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        /**为动态数组添加数据*/
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemText", "这是第" + i + "行");
            listItem.add(map);
        }
        return listItem;
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        /**
         * 构造函数
         */
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getDate().size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * 书中详细解释该方法
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.chose_type_item, null);
                holder = new ViewHolder();
                /**得到各个控件的对象*/
                holder.title = (TextView) convertView.findViewById(R.id.type_id);
                holder.text = (TextView) convertView.findViewById(R.id.type_item);
//                holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
//            holder.title.setText(getDate().get(position).get("ItemTitle").toString());
            holder.text.setText(getDate().get(position).get("ItemText").toString());

//            /**为Button添加点击事件*/
//            holder.bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.v("MyListViewBase", "你点击了按钮" + position);//打印Button的点击信息
//                }
//            });

            return convertView;
        }

        /**
         * 存放控件
         */
        public class ViewHolder {
            public TextView title;
            public TextView text;

        }

    }


}
