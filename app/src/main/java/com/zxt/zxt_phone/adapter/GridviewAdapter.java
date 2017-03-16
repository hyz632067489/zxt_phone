package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.BsznModel;

import java.util.List;


/**
 * Created by hyz on 2017/3/11.
 * powered by company
 */

public class GridviewAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    Context mContext;
    List<BsznModel> mDatas;
    public GridviewAdapter(Context context, List<BsznModel> datas){

        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.grid_item_layout,parent,false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.im_item);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mDatas.get(position).getText());
        holder.mImageView.setImageResource(mDatas.get(position).getImage());

        return convertView;
    }
    private final class ViewHolder
    {
        ImageView mImageView;
        TextView mTextView;
    }
}
