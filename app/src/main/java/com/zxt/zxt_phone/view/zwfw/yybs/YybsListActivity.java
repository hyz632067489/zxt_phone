package com.zxt.zxt_phone.view.zwfw.yybs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.TypeYybsModel;
import com.zxt.zxt_phone.bean.model.listYybsModel;
import com.zxt.zxt_phone.view.YybsInfoActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

public class YybsListActivity extends BaseActivity {

    private String TAG = YybsListActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;


    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;
    @BindView(R.id.newList)
    ListView gridView;

    List<listYybsModel.DataBeanX> allList = new ArrayList<>();
    List<listYybsModel.DataBeanX.DataBean> allList1 = new ArrayList<>();
    CommonAdapter<listYybsModel.DataBeanX.DataBean> myAdapter;

    Intent mIntent;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yybs_list);

        mIntent = getIntent();

        allList.addAll((Collection<? extends listYybsModel.DataBeanX>) mIntent.getSerializableExtra("list"));
        position = Integer.parseInt(mIntent.getStringExtra("position"));
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_yybslb);


        //获取数据
        getData();

        //设置adapter
        setAdapter();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(mActivity, YybsInfoActivity.class)
                        .putExtra("eid", allList1.get(position).getId()));
            }
        });

        //刷新
        getRefresh();

    }

    private void getRefresh() {
        //下来刷新，上啦加载跟多
        mRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                allList1.clear();
                getData();
            }
        });
        mRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                allList1.clear();
                getData();
            }
        });
    }

    private void setAdapter() {
        myAdapter = new CommonAdapter<listYybsModel.DataBeanX.DataBean>(mActivity, allList1, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, listYybsModel.DataBeanX.DataBean item) {

                holder.getView(R.id.iv_icon).setVisibility(View.GONE);

                holder.setText(R.id.tv_title, item.getGenre());
            }
        };

        gridView.setAdapter(myAdapter);
    }

    private void getData() {
        if (allList.size() > 0 || allList != null) {
            allList1.clear();
            if (!(allList.get(position).getData() == null)) {
                allList1.addAll(allList.get(position).getData());
            } else {
                gridView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }

        }

        mRefreshView.onFooterLoadFinish();
        mRefreshView.onHeaderRefreshFinish();
    }
}
