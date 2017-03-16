package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.BsznInfoModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hyz on 2017/3/12.
 * powered by company
 */

public class MyAdapter<T> extends CommonAdapter<T> {

    public MyAdapter(Context context, List<T> dates,int itemLayoutId) {
        super(context, dates,itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, T item) {

    }

    //    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if(convertView == null){
//            convertView = mInflater.inflate(R.layout.bszn_liebiao_item,parent,false);
//            holder = new ViewHolder();
//            holder.iv_image = (ImageView) convertView.findViewById(R.id.img_item);
//            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//            holder.tv_all = (TextView) convertView.findViewById(R.id.tv_all);
//            holder.tv_sp = (TextView) convertView.findViewById(R.id.tv_shengpi);
//            holder.tv_fw = (TextView) convertView.findViewById(R.id.tv_fuwu);
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//
//        holder.iv_image.setImageResource(mDates.get(position).getImage());
//        holder.tv_title.setText(mDates.get(position).getTitle());
//        holder.tv_all.setText(mDates.get(position).getTv_all());
//        holder.tv_sp.setText(mDates.get(position).getTv_sp());
//        holder.tv_fw.setText(mDates.get(position).getTv_fw());
//        return convertView;
//    }
//
//    private final  class ViewHolder{
//        private ImageView iv_image;
//        private TextView tv_title;
//        private  TextView tv_all;
//        private  TextView tv_sp;
//        private TextView tv_fw;
//    }
}
