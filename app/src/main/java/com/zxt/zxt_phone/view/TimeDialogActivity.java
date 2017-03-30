package com.zxt.zxt_phone.view;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.utils.DateTimePickDialogUtil;


public class TimeDialogActivity extends BaseActivity {

    private String TAG = TimeDialogActivity.class.getCanonicalName();


    private EditText startDateTime;
    private EditText endDateTime;

    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间
    String newTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_dialog);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
//        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss  ");
         Date curDate = new  Date(System.currentTimeMillis());//获取当前时间
        initEndDateTime = formatter.format(curDate);

        startDateTime = (EditText) findViewById(R.id.inputDate);
//        endDateTime = (EditText) findViewById(R.id.inputDate2);

        startDateTime.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

//        endDateTime.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
//                        mActivity, initEndDateTime);
//                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
//            }
//        });

    }

}
