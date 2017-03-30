package com.zxt.zxt_phone.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.PopBean;
import com.zxt.zxt_phone.utils.DateTimePickDialogUtil;
import com.zxt.zxt_phone.view.widget.ListPopwindow;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假页面
 */
public class LevelActivity extends BaseActivity implements ListPopwindow.OnPopItemClickListener,ListPopwindow.OnBottomTextviewClickListener{

    private String TAG = LevelActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_lindao)
    TextView tvLindao;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.submit_btn)
    Button submitBtn;


    public static final int QING_JIA = 51;
    public String initEndDateTime;


    ListPopwindow popWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tabName.setText(R.string.qjtx);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
//        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss  ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        initEndDateTime = formatter.format(curDate);
    }

    public void show(){
        List<PopBean> pops = new ArrayList<>();
        for (int i=0;i<5;i++){
            PopBean pop = new PopBean("item"+i,0);
            pops.add(pop);
        }
        popWindow = new ListPopwindow(mActivity,this,this,tvLindao,pops,"cancel","title");
        popWindow.showAtLocation(tvLindao, Gravity.CENTER| Gravity.BOTTOM,0,0);
    }

    public void onBottomClick() {
        popWindow.dismiss();
    }

    public void onPopItemClick(View view, int position) {
        switch (position){
            case 0:
                Log.d("tag>>>>>","click index:"+position);
                break;
            case 1:
                Log.d("tag>>>>>","click index:"+position);
                break;
            case 2:
                Log.d("tag>>>>>","click index:"+position);
                break;
            case 3:
                Log.d("tag>>>>>","click index:"+position);
                break;
            case 4:
                Log.d("tag>>>>>","click index:"+position);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.type_layout, R.id.lindao_layout, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_layout://选择类型
                show();
                break;
            case R.id.lindao_layout://选择领导
                show();
                break;
            case R.id.start_time_layout://开始时间
//                startActivityForResult(new Intent(mActivity, YybsInfoActivity.class).putExtra("title", tabName.getText().toString()), QING_JIA);
                DateTimePickDialogUtil startLog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                startLog.dateTimePicKDialog(tvStartTime);
                break;
            case R.id.end_time_layout://结束时间
                DateTimePickDialogUtil endLog = new DateTimePickDialogUtil(
                        mActivity, initEndDateTime);
                endLog.dateTimePicKDialog(tvEndTime);

                break;
        }
    }

}
