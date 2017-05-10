package com.zxt.zxt_phone.view.zwfw.pasq;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.WgrxModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SqmjActivity extends BaseActivity {


    private String TAG = SqmjActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;



    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_jwt)
    TextView tvJwt;
    @BindView(R.id.tv_bj)
    TextView tvBj;

    CommonAdapter<WgrxModel> mAdapter;

    String myName, myPhone, BJphone, JWSPhone;


    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:


                    tvName.setText( "姓名：\t\t" + myName);
                    tvPhone.setText( "电话：\t\t" + myPhone);
                    tvJwt.setText( "警务室电话：\t\t" + BJphone);
                    tvBj.setText( "报警电话：\t\t" + JWSPhone);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqmj);
        ButterKnife.bind(this);

        getData();
        initView();
    }


    private void initView() {
        tabName.setText("社区民警");

//        newList.setAdapter(mAdapter);
    }


    private void getData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "pasc");
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


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        MLog.i(TAG, "response===" + response);

                        try {
                            JSONObject obj = null;
                            obj = new JSONObject(response);
                            if ("1".equals(obj.getString("Status"))) {

                                JSONArray array = obj.getJSONArray("Data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    myName = jsonObject.getString("name");
                                    myPhone = jsonObject.getString("SJphone");
                                    JWSPhone = jsonObject.getString("JWSPhone");
                                    BJphone = jsonObject.getString("BJphone");
                                }
                                mHandle.sendEmptyMessage(0);
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
