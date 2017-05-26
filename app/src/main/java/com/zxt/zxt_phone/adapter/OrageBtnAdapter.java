package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.PjglInfoModel;
import com.zxt.zxt_phone.bean.model.TimeModel;

import java.util.List;


/**
 * Created by hkc on 2017/4/12.
 */

public class OrageBtnAdapter extends BaseAdapter {

    Context mContext;
    List<TimeModel> mLists;

    public OrageBtnAdapter(Context context, List<TimeModel> lists) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.orage_list_item, null);
            holder = new ViewHolder();


            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_pop_list);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_des.setText(mLists.get(position).getTime());

        if (position == selectItem) {
//            holder.tv_des.setTextColor(Color.BLUE);
            holder.tv_des.setBackgroundResource(R.drawable.orage_btn_shape_click);
//            Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.orage_btn_shape_click);
//            drawable1.setBounds(0,0,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
//            holder.tv_des.setCompoundDrawables(drawable1, null, null, null);
        } else {
//            holder.tv_des.setTextColor(Color.RED);
            holder.tv_des.setBackgroundResource(R.drawable.orage_btn_shape);
//            Drawable drawable2 = mContext.getResources().getDrawable(R.drawable.orage_btn_shape);
//            drawable2.setBounds(0,0,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
//            holder.tv_des.setCompoundDrawables(drawable2, null, null, null);
        }

        return convertView;
    }


    class ViewHolder {


        TextView tv_des;
    }

}
