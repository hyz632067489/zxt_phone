package com.zxt.zxt_phone.view.customview.easytagdragview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.SimpleTitleTip;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.Tip;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27 0027.
 */
public class AddTipAdapter extends BaseAdapter {

    private List<Tip> tips;

    public AddTipAdapter() {
    }

    @Override
    public int getCount() {
        if(tips ==null){
            return 0;
        }
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.view_add_item,null);
        ((TextView)view.findViewById(R.id.tagview_title)).setText((((SimpleTitleTip)(tips.get(position))).getTip()));
        ((ImageView)view.findViewById(R.id.iv_icon)).setImageResource((((SimpleTitleTip)(tips.get(position))).getIcon()));
        return view;
    }

    public List<Tip> getData() {
        return tips;
    }

    public void setData(List<Tip> iDragEntities) {
        this.tips = iDragEntities;
    }
    public void refreshData(){
        notifyDataSetChanged();
    }
}
