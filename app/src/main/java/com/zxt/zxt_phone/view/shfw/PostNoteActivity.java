package com.zxt.zxt_phone.view.shfw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.OrageBtnAdapter;
import com.zxt.zxt_phone.adapter.PjglInfoAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.TimeModel;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.view.customview.HomeGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发帖
 */
public class PostNoteActivity extends BaseActivity {

    private String TAG = PostNoteActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;
    @BindView(R.id.title_relayout)
    RelativeLayout topLayout;

    @BindView(R.id.gv_list_button)
    HomeGridView gvListBtn;

    private List<TimeModel> mDatas = new ArrayList<>();
    String[] times = {"社区活动", "生活百科", "二手交易", "选举投票" };
    OrageBtnAdapter myAdapter;

    String dayTime, hourTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_note);


    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        tabName.setText("发帖");
        topLayout.setBackgroundColor(getResources().getColor(R.color.white));


        setTypeChose();
    }

    /**
     * 选择话题类型
     */
    private void setTypeChose() {
        TimeModel timeModel = null;
        for (int i = 0; i < times.length; i++) {
            timeModel = new TimeModel();
            timeModel.setTime(times[i]);
            mDatas.add(timeModel);
        }

        myAdapter = new OrageBtnAdapter(mContext, mDatas);
        gvListBtn.setAdapter(myAdapter);
        gvListBtn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("点击了" + position);

                hourTime = mDatas.get(position).getTime();
                myAdapter.setSelectItem(position);
                myAdapter.notifyDataSetChanged();
                MLog.i(TAG, "onItemClick 1 =============" + hourTime);
//                for (int i = 0; i < parent.getCount(); i++) {
//                    View v = parent.getChildAt(i);
//                    if (position == i) {//当前选中的Item改变背景颜色
//                        view.setBackgroundResource(R.color.topcolor);
//                    } else {
//                        v.setBackgroundResource(R.color.white);
//                    }
//                }
            }
        });
    }
}
