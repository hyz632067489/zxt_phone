package com.zxt.zxt_phone.view.shfw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;

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
    }
}
