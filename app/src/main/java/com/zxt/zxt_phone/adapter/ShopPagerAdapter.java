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
import com.zxt.zxt_phone.bean.model.ShopTypeModel;

import java.util.List;


/**
 * Created by hyz on 2017/3/11.
 * powered by company
 */

public class ShopPagerAdapter extends BaseAdapter {

    private List<ShopTypeModel.DataBean.ChildsBeanX> mDatas;
    private LayoutInflater mInflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;


    public ShopPagerAdapter(Context context, List<ShopTypeModel.DataBean.ChildsBeanX> mDatas, int curIndex, int pageSize) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.im_item);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mDatas.get(position).getClassName());
//        holder.mImageView.setImageResource(mDatas.get(position).getImage());

        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize
         */
        int pos = position + curIndex * pageSize;
        holder.mTextView.setText(mDatas.get(pos).getClassName());
//        holder.mImageView.setImageResource(mDatas.get(pos).getImage());


        return convertView;
    }


    private final class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
