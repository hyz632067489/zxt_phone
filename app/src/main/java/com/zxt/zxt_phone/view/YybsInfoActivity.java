package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.TimeModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.KCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class YybsInfoActivity extends BaseActivity {

    private String TAG = YybsInfoActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.time_sign)
    TextView timeSign;

    @BindView(R.id.popupwindow_calendar)
    KCalendar calendar;
    @BindView(R.id.gv_list)
    HomeGridView gvList;

    private String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式
    private List<String> list = new ArrayList<String>(); //设置标记列表

    private List<TimeModel> mDatas = new ArrayList<>();
    String[] times = {"9:00-10:00", "10:00-11:00", "11:00-12:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00",};
    CommonAdapter<TimeModel> myAdapter;

    String dayTime, hourTime;

    Intent mIntent;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yybs_info);
        gvList.setFocusable(false);
        mIntent = getIntent();
        initView();
    }

    private void initView() {
//        tabName.setText(R.string.zwfw_yybsinfo);
        tabName.setText(mIntent.getStringExtra("title"));

        Log.i(TAG,"===================="+mIntent.getStringExtra("title"));
        timeSign.setText(calendar.getCalendarYear() + "年"
                + calendar.getCalendarMonth() + "月");


        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                timeSign.setText(year + "年" + month + "月");
            }
        });

        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    list.add(dateFormat);
                    for (String str : list) {

                        dayTime = str;
                        Log.i("TAG", "str===========" + str.toString());
                    }
                    calendar.addMarks(list, 0);
                    calendar.removeAllBgColor();
                    calendar.setCalendarDayBgColor(dateFormat,
                            R.drawable.calendar_date_focused);
                    date = dateFormat;//最后返回给全局 date
                }
            }
        });

        TimeModel timeModel = null;
        for (int i = 0; i < times.length; i++) {
            timeModel = new TimeModel();
            timeModel.setTime(times[i]);
            mDatas.add(timeModel);
        }

        myAdapter = new CommonAdapter<TimeModel>(mActivity, mDatas, R.layout.pop_list_item) {
            @Override
            public void convert(ViewHolder holder, TimeModel item) {
                holder.setText(R.id.tv_pop_list, item.getTime());
                LinearLayout mLayout = holder.getView(R.id.yuyue_layout);

            }
        };

        gvList.setAdapter(myAdapter);
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("点击了" + position);

                hourTime = mDatas.get(position).getTime();
                Log.i(TAG, "onItemClick 1 =============" + hourTime);
                for (int i = 0; i < parent.getCount(); i++) {
                    View v = parent.getChildAt(i);
                    if (position == i) {//当前选中的Item改变背景颜色
                        view.setBackgroundResource(R.color.topcolor);
                    } else {
                        v.setBackgroundResource(R.color.white);
                    }
                }
            }
        });
    }

    @OnClick({R.id.igv_left, R.id.igv_right, R.id.submit_btn})
    public void OnCilck(View v) {
        switch (v.getId()) {
            case R.id.igv_left:
                calendar.lastMonth();
                break;

            case R.id.igv_right:
                calendar.nextMonth();
                break;
            case R.id.submit_btn:
                if (verification()) {
                    sendData();
                }
                break;
        }
    }

    private boolean verification() {
        if (TextUtils.isEmpty(hourTime)
                || TextUtils.isEmpty(dayTime)) {
            toast("请选择日期跟时间段");
            return false;
        }

        return true;
    }

    private void sendData() {
        HashMap<String, String> params = new HashMap<>();
        OkHttpUtils.get()
                .url(Url.URL_PT)
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }
}
