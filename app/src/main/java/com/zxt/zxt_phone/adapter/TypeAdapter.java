package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.TypeModel;

import java.util.List;


/**
 * Created by hkc on 2017/5/8.
 */

public class TypeAdapter extends BaseAdapter {


    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<TypeModel.DataBean> mDatas;


    public TypeAdapter(Context context, List<TypeModel.DataBean> mDatas)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;

    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_common,parent,false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mDatas.get(position).getContent());
        holder.mTextView.setTextSize(R.dimen.px_40);
        holder.mImageView.setVisibility(View.GONE);

        return convertView;
    }
    private final class ViewHolder
    {
        ImageView mImageView;
        TextView mTextView;
    }
}
