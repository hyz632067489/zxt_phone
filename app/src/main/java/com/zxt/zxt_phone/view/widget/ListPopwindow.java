package com.zxt.zxt_phone.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.PopWindowAdapter;
import com.zxt.zxt_phone.bean.PopBean;

import java.util.List;

/**
 * Created by hyz on 2017/3/29.
 * powered by company
 */

public class ListPopwindow extends PopupWindow {

    private Context mContext; //上下文
    private View parentView;    //父视图
    private List<PopBean> dataList; //item数据源
    private OnPopItemClickListener listener;
    private ListView lv;
    private View viewTop;   //title视图
    private String topText,bottomText;  //title文字，bottom文字
    private TextView tvTop,tvBottom;    //title文本，bottom文本
    private PopWindowAdapter adapter;   //适配器
    private OnBottomTextviewClickListener bottomListener;//底部点击接口


    public interface  OnPopItemClickListener{
        void onPopItemClick(View view,int position);
    }
    public interface OnBottomTextviewClickListener{
        void onBottomClick();
    }
    public ListPopwindow(Context context,OnPopItemClickListener listener,OnBottomTextviewClickListener bottomListener,
                         View parentView,List<PopBean> dataList,String bottomText,String topText){
        this.mContext = context;
        this.listener = listener;
        this.parentView = parentView;
        this.dataList = dataList;
        this.bottomListener = bottomListener;
        this.topText = topText;
        this.bottomText = bottomText;

        initView();
    }

    private void initView() {
        parentView = LayoutInflater.from(mContext).inflate(R.layout.list_popwindow,null);
        setContentView(parentView);
        lv = (ListView) parentView.findViewById(R.id.lv_popwindow);
        //设置弹出窗体的高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //view添加OnTouchListener监听判断获取触屏位置如果在布局外面则销毁弹出框
        parentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              int height = parentView.findViewById(R.id.ll_bottom).getTop();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y>height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        update();
        viewTop = parentView.findViewById(R.id.view_line1);
        tvBottom = (TextView) parentView.findViewById(R.id.tv_popwindow_bottom);
        tvTop = (TextView) parentView.findViewById(R.id.tv_popwindow_first);
        adapter = new PopWindowAdapter(mContext,dataList,false);
        lv.setAdapter(adapter);

        if (!TextUtils.isEmpty(topText)){
            tvTop.setVisibility(View.VISIBLE);
            tvTop.setText(topText);
            viewTop.setVisibility(View.VISIBLE);
        }
        else {
            tvTop.setVisibility(View.GONE);
            viewTop.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bottomText)){
            tvBottom.setVisibility(View.VISIBLE);
            tvBottom.setText(bottomText);
        }
        else {
            tvBottom.setVisibility(View.GONE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onPopItemClick(view, i);
            }
        });

        tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomListener.onBottomClick();
            }
        });

    }

}
