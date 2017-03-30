package com.zxt.zxt_phone.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;

public class JgcxDetailActivity extends BaseActivity {

    private String TAG = JgcxDetailActivity.class.getCanonicalName();


    TextView btn1, btn2, tabName, noText;

    TextView OpinionId, OpinionClassName, EditDate, ReDate, AuditState, Content, deptName, ReContent;
    private List<Map<String, String>> list = null;
    private SimpleAdapter adapter;
    MyListView listView;
    LinearLayout pjLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jgcx_detail);
        initData();
    }

    public void initData() {
        list = new ArrayList<Map<String, String>>();


        tabName = (TextView) findViewById(R.id.tab_name);
        tabName.setText(R.string.zwfw_jgcxxq);
        pjLayout = (LinearLayout) findViewById(R.id.pjLayout);
//        if(getIntent().getStringExtra("OpinionClassId").equals("6")){
//            title.setText("物业公示");
//        }else if(getIntent().getStringExtra("OpinionClassId").equals("7")){
//            title.setText("事项评价");
//            if(getIntent().getStringExtra("AuditState").equals("1")) {
//                pjLayout.setVisibility(View.VISIBLE);
//            }else{
//                pjLayout.setVisibility(View.GONE);
//            }
//        }

        btn1 = (TextView) findViewById(R.id.btn1);
        btn2 = (TextView) findViewById(R.id.btn2);


        OpinionId = (TextView) findViewById(R.id.OpinionId);
        OpinionClassName = (TextView) findViewById(R.id.OpinionClassName);
        EditDate = (TextView) findViewById(R.id.EditDate);
        ReDate = (TextView) findViewById(R.id.ReDate);
        AuditState = (TextView) findViewById(R.id.AuditState);
        Content = (TextView) findViewById(R.id.Content);
        deptName = (TextView) findViewById(R.id.deptName);
        ReContent = (TextView) findViewById(R.id.ReContent);
        noText = (TextView) findViewById(R.id.noText);


        OpinionId.setFocusable(true);
        OpinionId.setFocusableInTouchMode(true);
        OpinionId.requestFocus();

        listView = (MyListView) findViewById(R.id.listview);
        adapter = new SimpleAdapter(this, list, R.layout.yjfkcx_detail_list_item,
                new String[]{"ReContent"},
                new int[]{R.id.ReContent}) {

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {

                View superView = super.getView(position, convertView, parent);
                TextView ReContent = (TextView) superView.findViewById(R.id.ReContent);
                ReContent.setText(list.get(position).get("EditDate") + "  " + list.get(position).get("ReContent"));

                return superView;
            }
        };
        listView.setAdapter(adapter);

        OpinionShow();

    }


    /**
     * 数据列表
     */
    public void OpinionShow() {
//        &TVInfoId=19&Action=&method=OpinionShow&id=100606&Key=21218CCA77804D2BA1922C33E0151105
        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(mActivity, "TVInfoId"));
        params.put("Key", SharedPrefsUtil.getString(mActivity, "Key"));
        params.put("method", "OpinionShow");
        params.put("id", getIntent().getStringExtra("OpinionId"));
        params.put("Action", getIntent().getStringExtra("Action"));

        Log.i(TAG,"params="+SharedPrefsUtil.getString(mActivity, "TVInfoId").toString()+"&"+getIntent().getStringExtra("Action").toString()
                        +"&"+"OpinionShow"+"&"+getIntent().getStringExtra("OpinionId").toString()+"&"+SharedPrefsUtil.getString(mActivity, "Key").toString());
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "yjfk===" + response);
                JSONArray rootJson;
                JSONObject obj;
                try {
                    obj = new JSONObject(response);
                    if (!"0".equals(obj.getString("Status"))) {

                        OpinionId.setText("受理编号：" + obj.getString("OpinionId"));
                        OpinionClassName.setText("分类性质：" + obj.getString("OpinionClassName"));
                        EditDate.setText("诉求时间：" + obj.getString("EditDate"));
                        ReDate.setText("办结时间：" + obj.getString("ReDate"));
                        AuditState.setText("办结状态：" + obj.getString("AuditName"));
                        Content.setText(obj.getString("Content"));
                        deptName.setText("回复单位：" + obj.getString("deptName"));
                        ReContent.setText(obj.getString("ReContent"));


                        if (!obj.getString("Data").equals("[]")) {
                            rootJson = obj.getJSONArray("Data");
                            for (int i = 0; i < rootJson.length(); i++) {
//                                list.add(JsonUtil.jsonObj2Map(rootJson.getJSONObject(i)));
                            }
                        } else {
                            noText.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {

                }
            }
        });
    }

    @OnClick()
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.backBtn:
                finish();
                break;
            case R.id.btn1:
//                startActivity(new Intent(this, YjfkcxWypjActivity.class).putExtra("OpinionId", getIntent().getStringExtra("OpinionId")).putExtra("number", "1"));
                break;
            case R.id.btn2:
//                startActivity(new Intent(this, YjfkcxWdpjActivity.class).putExtra("OpinionId", getIntent().getStringExtra("OpinionId"))
//                        .putExtra("Action", getIntent().getStringExtra("Action"))
//                        .putExtra("number", "1")
//                );
                break;

            default:
                break;
        }
    }

}

