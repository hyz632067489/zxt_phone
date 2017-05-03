package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.PjglInfoModel;

import java.util.List;


/**
 * Created by hkc on 2017/4/12.
 */

public class PjglInfoAdapter extends BaseAdapter {

    Context mContext;
    List<PjglInfoModel.DataBeanX.DataBean> mLists;

    public PjglInfoAdapter(Context context, List<PjglInfoModel.DataBeanX.DataBean> lists) {
        this.mContext = context;
        this.mLists = lists;

    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public int getSelectItem( int selectItem) {
        return selectItem;
    }
    private int selectItem = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_common_1, null);
            holder = new ViewHolder();

            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_button = (TextView) convertView.findViewById(R.id.tv_button);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar_horizontal);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_icon.setVisibility(View.GONE);
        holder.tv_des.setVisibility(View.GONE);
//        holder.ll_bg.setBackgroundResource();
        holder.tv_button.setText(mLists.get(position).getAname());
        holder.tv_button.setVisibility(View.VISIBLE);
        holder.progressBar.setProgress(Integer.parseInt(mLists.get(position).getCount()));
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.tv_count.setText(mLists.get(position).getCount()+"票");
        holder.tv_count.setVisibility(View.VISIBLE);

        if (position == selectItem) {
            holder.tv_button.setTextColor(Color.BLUE);
        } else {
            holder.tv_button.setTextColor(Color.RED);
        }

        return convertView;
    }


    class ViewHolder {

        ImageView iv_icon;
        TextView tv_des;
        TextView tv_button;
        ProgressBar progressBar;
        TextView tv_count;
    }

}
