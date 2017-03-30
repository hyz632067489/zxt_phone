package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.ZwfwModel;
import com.zxt.zxt_phone.constant.Common;

import java.util.List;

import static android.R.id.list;

/**
 * Created by hyz on 2017/3/23.
 * powered by company
 */

public class ZwfwAdapter extends BaseAdapter{
    private Context mContext;
    private List<ZwfwModel> mDatas;
    private LayoutInflater mInflater;

    private  ZwfwAdapter (Context context,List<ZwfwModel> datas){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas.size()+1;
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
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.gv_item_layout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mDatas.get(position).getName());
        holder.mImageView.setImageResource(mDatas.get(position).getImage());
//        if(!Common.IS_LOGIN){
//            holder.mLayout.setVisibility(View.VISIBLE);
//        }
//        if (position < mDatas.size()) {
//            holder.mLayout.setBackgroundResource(list.get(position));
//        }else{
//            holder.mLayout.setBackgroundResource(R.drawable.pic3);//最后一个显示加号图片
//        }
        return convertView;
    }
    private final class ViewHolder
    {
        LinearLayout mLayout;
        ImageView mImageView;
        TextView mTextView;
    }
}
