package com.zxt.zxt_phone.view.zwfw.wggl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.SjclListModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SjclInfoActivity extends BaseActivity {


    private String TAG = SjclInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.tv_importent)
    TextView tvImportent;
    @BindView(R.id.tv_come)
    TextView tvCome;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_jin_ji)
    TextView tvJinJi;
    @BindView(R.id.tv_time)
    TextView tvTime;


    Intent mIntent;
    String eventId;
    int position;

    SjclListModel model;

    String strTitle, strName, strCome, strType, strJinJi, strAddPeople, strStatus, strTime, strCount;
    String strArea, strGrid;    //所属区域，所属网格
    String strImportent;        //重点督办

    String startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjcl_info);
        ButterKnife.bind(this);

        mIntent = getIntent();
//        Bundle bun = mIntent.getExtras();
//        model = (SjclListModel) bun.getSerializable("mData");
        model = (SjclListModel) mIntent.getSerializableExtra("mData");
        position = Integer.parseInt(mIntent.getStringExtra("position"));

        getdata();
        getdataInfo();
        initView();
    }


    private void getdata() {
        eventId = model.getData().getList().get(position).getEventId();
        MLog.i(TAG, "eventId===" + eventId);
//        strTitle, strName, strCome, strType, strJinJi, strAddPeople, strStatus, strTime, strCount;
        strTitle = model.getData().getList().get(position).getEventTitle();
        strName = model.getData().getList().get(position).getGridStaffApp().getGridStaffName();
        strCome = model.getData().getList().get(position).getSourceType().getSourceTypeName();
        strType = model.getData().getList().get(position).getEventType().getEventTypeName();
        strJinJi = model.getData().getList().get(position).getEventLevel().getEventLevelName();
//        strAddPeople = model.getData().getList().get(position).get
//        strStatus = model.getData().getList().get(position).getIsFinished();   //0是未办结，1是已办结
        strTime = model.getData().getList().get(position).getEditEventDate();
        strCount = model.getData().getList().get(position).getEventContent();
    }

    private void initView() {
        tabName.setText("事件处理详情");


        setView();
    }

    /**
     * 设置数据
     */
    private void setView() {
        tvTitle.setText(strTitle);
        tvPeople.setText(strName);
        tvImportent.setText(strCome);//重点
        tvCome.setText(strCome);
        tvType.setText(strType);
        tvJinJi.setText(strJinJi);
        tvTime.setText("编辑时间：" + strTime.substring(10, strTime.length()));


    }


    private void getdataInfo() {

        showProgressDialog();
        /**
         * solveStatusId
         * 1  ==>暂受理
         * 2  ==>已办结
         */
        HashMap<String, String> params = new HashMap<>();
        params.put("eventId", eventId);
        OkHttpUtils.post()
                .url(Url.URL_WG + "event/getOneEventLogInfoById.do?")
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "respone===" + response);
                        closeProgressDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (200 == obj.getInt("statusCode")) {


                            } else if (300 == obj.getInt("statusCode")) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
