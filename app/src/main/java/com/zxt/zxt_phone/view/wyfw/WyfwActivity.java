package com.zxt.zxt_phone.view.wyfw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.AboutModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.NewsDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class WyfwActivity extends BaseActivity {

    private String TAG = WyfwActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.gv_view)
    GridView myGridView;

    private int imgWy[] = {R.drawable.shfw_gywy, R.drawable.shfw_wygg, R.drawable.shfw_aqgl, R.drawable.shfw_gzbx,
            R.drawable.shfw_jftj, R.drawable.shfw_pjgl};
    private String titleWy[] = {"关于物业", "物业公告", "安全管理", "故障报修",
            "纠纷调解", "评价管理"};

    CommonModel model;
    CommonAdapter<CommonModel> myAdapter;
    List<CommonModel> mDatas = new ArrayList<>();

    Intent mIntent;

    int choseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shfw);

        getDatas();
        getData();
        initView();
    }


    private void initView() {
        tabName.setText("社会服务");

        myAdapter = new CommonAdapter<CommonModel>(mContext, mDatas, R.layout.item_common) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {
                holder.setImageResource(R.id.iv_icon, item.getIcon());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        myGridView.setAdapter(myAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mIntent = new Intent();
                switch (position) {
                    case 0:
//                    {"关于物业", "物业公告", "安全管理", "故障报修", "纠纷调解", "评价管理"};
//                        toast("点击了" + "关于物业");
                        mIntent.setClass(mActivity, NewsDetailActivity.class);
                        mIntent.putExtra("title", "关于物业");
                        mIntent.putExtra("url", Url.BASE_URL_HTML + "GyPro.aspx"+"?id="+choseId);

                        break;
                    case 1:
//                        toast("点击了" + "物业公告");
                        mIntent.setClass(mActivity, WyggActivity.class);
                        break;
                    case 2:
//                        toast("点击了" + "安全管理");
                        mIntent.setClass(mActivity, SafetyActivity.class);
                        break;
                    case 3:
//                        toast("点击了" + "故障报修");
                        mIntent.setClass(mActivity, RepairsActivity.class);
                        break;
                    case 4:
//                        toast("点击了" + "纠纷调解");
                        mIntent.setClass(mActivity, JftjActivity.class);
                        break;
                    case 5:
//                        toast("点击了" + "评价管理");
                        mIntent.setClass(mActivity, PjglActivity.class);
                        break;
                }
                startActivity(mIntent);
            }
        });
    }

    private void getDatas() {

        for (int i = 0; i < titleWy.length; i++) {
            model = new CommonModel();
            model.setIcon(imgWy[i]);
            model.setName(titleWy[i]);

            mDatas.add(model);
        }
    }

    private void getData() {
//        http://localhost:15494/api/APP1.0.aspx?method=grproerty&TVInfoId=4&Key=21218CCA77804D2BA1922C33E0151105&deptId=

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "grproerty");
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("deptId", SharedPrefsUtil.getString(mActivity, "DeptId"));


        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {
                                AboutModel model = new Gson().fromJson(response, AboutModel.class);

                                choseId = model.getData().get(0).getEid();

                            } else {
                                toast(obj.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
