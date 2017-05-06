package com.zxt.zxt_phone.view.zwfw.dqfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.SdxfModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class FfclActivity extends BaseActivity {


    private String TAG = FfclActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.lv_List_View)
    ListView lvListView;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;
    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;

    CommonAdapter<SdxfModel.DataBean> myAdapter;
    List<SdxfModel.DataBean> mDatas = new ArrayList<>();

    SdxfModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffcl);

        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        tabName.setText(R.string.zwfw_ffcl);
        setAdapter();
        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = Url.BASE_L + model.getPioneerUrl() + "?method=corruption"
                        + "&id=" + mDatas.get(position).getId()
                        + "&TVInfoId=" + SharedPrefsUtil.getString(mActivity, "TVInfoId")
                        + "&Key=" + SharedPrefsUtil.getString(mActivity, "Key")
                        + "&DeptId=" + SharedPrefsUtil.getString(mContext, "DeptId");

                startActivity(new Intent(mActivity, NewsDetailActivity.class)
                        .putExtra("url", url)
                        .putExtra("title", "反腐倡廉详情"));
            }
        });
    }

    private void setAdapter() {
        if (myAdapter == null) {
            myAdapter = new CommonAdapter<SdxfModel.DataBean>(mContext, mDatas, R.layout.item_sdxf_icon) {
                @Override
                public void convert(ViewHolder holder, SdxfModel.DataBean item) {
                    holder.setImageByUrl(R.id.iv_icon, item.getCoverImg());

//                    Glide.with(mContext).load(Url.BASE_L + item.getCoverImg())//
//                            .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
//                            .error(R.drawable.ic_default_color)//
////                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
//                            .into((ImageView) holder.getView(R.id.iv_icon));

                    holder.setText(R.id.tv_title, item.getTitle());
                    holder.setText(R.id.tv_coutent, item.getIntro());
                }
            };
            lvListView.setAdapter(myAdapter);
        } else {
            myAdapter.notifyDataSetChanged();
        }

    }


    private void getData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "corruption");
        params.put("TVInfoId", SharedPrefsUtil.getString(mContext, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mContext, "Key"));
        params.put("DeptId", SharedPrefsUtil.getString(mContext, "DeptId"));
        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        if (null != mRefreshView) {
                            mRefreshView.onFooterLoadFinish();
                            mRefreshView.onHeaderRefreshFinish();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        closeProgressDialog();

                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                model = new Gson().fromJson(response, SdxfModel.class);
                                mDatas.addAll(model.getData());


                                myAdapter.notifyDataSetChanged();
//                                mHandler.sendEmptyMessage(0);

                                mRefreshView.onFooterLoadFinish();
                                mRefreshView.onHeaderRefreshFinish();
                            } else {
                                lvListView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

