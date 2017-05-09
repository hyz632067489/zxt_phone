package com.zxt.zxt_phone.view.zwfw.yybs;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.TypeYybsModel;
import com.zxt.zxt_phone.constant.Common;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class YybsActivity extends BaseActivity {

    private  String TAG = YybsActivity.class.getCanonicalName();

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
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.process_bar)
    ProgressBar processBar;


    @BindView(R.id.refreshView)
    PullToRefreshView mRefreshView;


    Intent mIntent;
    TypeYybsModel typeModel;
    List<TypeYybsModel.DataBean> typeList=new ArrayList<>();


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Common.TYPE_S:
                    for(int i=0;i<typeList.size();i++){
                        if(1==typeList.get(i).getId()){
                            rbGeren.setText(typeList.get(i).getGenre());
                        }else if(2==typeList.get(i).getId()){
                            rbQiye.setText(typeList.get(i).getGenre());
                        }
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yybs);

        getTypeData();
        initView();
    }


    private void initView() {
        tabName.setText(R.string.zwfw_yybs);
    }


    private void getTypeData() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("method", "booking");
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
                        MLog.i(TAG,"response=="+response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            if("1".equals(obj.getString("Status"))){
                                typeModel=new Gson().fromJson(response,TypeYybsModel.class);
                                typeList.addAll(typeModel.getData());

                                handler.sendEmptyMessage(Common.TYPE_S);

                            }else {
                                toast(obj.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}