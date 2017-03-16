package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.GridviewAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznModel;
import com.zxt.zxt_phone.view.customview.HomeGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class BsznActivity extends BaseActivity {

    private String TAG = BsznActivity.class.getCanonicalName();
    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.grid_view)
    HomeGridView gridView;
    @BindView(R.id.rd_g)
    RadioGroup mRd_g;
    @BindView(R.id.rb_geren)
    RadioButton rbGeren;
    @BindView(R.id.rb_qiye)
    RadioButton rbQiye;
    GridviewAdapter gridviewAdapter,gridviewAdapter1;
    private List<BsznModel> lists = new ArrayList();

    private QueryType selectTab ;

    private enum QueryType {
        gerenHandle, qiyeHandle;
    }

    Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bazn_activity);


        initView();

    }

    private void initView() {
        tabName.setText(R.string.bszn_tabname);
        mRd_g.setOnCheckedChangeListener(listener);

//        for(int i=0;i<20;i++){
//            lists.add("个人办事"+i);
//            Log.i(TAG,"办事指南"+lists.get(i).toString());
//        }
        addFirstData();
        gridviewAdapter = new GridviewAdapter(this,getDate());
        gridviewAdapter1 = new GridviewAdapter(this,getDate1());
        gridView.setAdapter(gridviewAdapter);
    }

    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == rbGeren.getId()) {
                    toast("点击个人");
                selectTab = QueryType.gerenHandle;
                getAdapter(selectTab);
            }else if(checkedId == rbQiye.getId()){
                toast("点击企业");
                selectTab = QueryType.qiyeHandle;
                getAdapter(selectTab);
            }
        }
    };


    private void getAdapter(QueryType selectTab){

        switch (selectTab){
            case  gerenHandle:
//                lists.add(getDate());
                gridviewAdapter = new GridviewAdapter(this,getDate());
                break;
            case qiyeHandle:
                gridviewAdapter = new GridviewAdapter(this,getDate1());
                break;
        }
        gridView.setAdapter(gridviewAdapter);
        gridviewAdapter.notifyDataSetChanged();
    }
    /**
     * 添加一个得到数据的方法，方便使用
     */
    private ArrayList<BsznModel> getDate() {
        ArrayList<BsznModel> listItem = new ArrayList<BsznModel>();
        BsznModel model = null;
        /**为动态数组添加数据*/
        for (int i = 0; i < 20; i++) {
            model = new BsznModel();
            model.setImage(R.drawable.m_bsbx);
            model.setText("个人办事" + i);
            listItem.add(model);
        }
        return listItem;
    }
    private ArrayList<BsznModel> getDate1() {
        ArrayList<BsznModel> listItem = new ArrayList<BsznModel>();
        BsznModel model = null;
        /**为动态数组添加数据*/
        for (int i = 0; i < 15; i++) {
            model = new BsznModel();
            model.setImage(R.drawable.m_bsbx);
            model.setText("政府办事" + i);
            listItem.add(model);
        }
        return listItem;
    }

    private void addFirstData(){
        BsznModel model = new BsznModel();
        model.setText("全部");
        model.setImage(R.drawable.m_gscx);
        getDate().add(0,model);
        getDate1().add(0,model);
    }
    @OnItemClick(R.id.grid_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        mIntent = new Intent(this,BsznInfoActivity.class);
        startActivity(mIntent);
    }

}
