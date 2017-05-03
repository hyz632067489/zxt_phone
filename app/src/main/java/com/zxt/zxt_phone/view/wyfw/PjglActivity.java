package com.zxt.zxt_phone.view.wyfw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.PjglModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class PjglActivity extends BaseActivity {


    private String TAG = PjglActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_view)
    TextView tvView;
    @BindView(R.id.newList)
    ListView newList;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    List<PjglModel.DataBean> mListData = new ArrayList<>();
    PjglModel pjglModel;
    CommonAdapter<PjglModel.DataBean> myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pjgl);
        ButterKnife.bind(this);

        getData();
        initView();
    }


    private void initView() {

        tabName.setText(getResources().getString(R.string.wyfw_pjgl));

        myAdapter = new CommonAdapter<PjglModel.DataBean>(mActivity, mListData, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, PjglModel.DataBean item) {
                holder.getView(R.id.iv_icon).setVisibility(View.GONE);
                holder.setText(R.id.tv_title, item.getTitle());

            }
        };
        newList.setAdapter(myAdapter);
        newList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                startActivity(new Intent(mActivity, PjglInfoActivity.class)
                        .putExtra("choseId", mListData.get(position).getId() + "")
                        .putExtra("title", mListData.get(position).getTitle()));

            }
        });
    }

    private void getData() {
        showProgressDialog();
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .addParams("method", "sort")
                .addParams("Deptid", SharedPrefsUtil.getString(mContext, "DeptId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response=" + response);
                        closeProgressDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {

                                pjglModel = new Gson().fromJson(response, PjglModel.class);
                                mListData.clear();
                                mListData.addAll(pjglModel.getData());

                                myAdapter.notifyDataSetChanged();
                            } else {
                                mRefreshView.setVisibility(View.GONE);
                                tvView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
