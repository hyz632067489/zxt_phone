package com.zxt.zxt_phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.DeptNameModel;
import com.zxt.zxt_phone.bean.model.TypeDepartment;

import java.util.List;

/**
 * Created by hyz on 2017/3/20.
 * powered by company
 */

public class PopNameAdapter extends BaseAdapter {


    private Context context;
    private List<DeptNameModel.DataNewsModel> list;

    public PopNameAdapter(Context context, List<DeptNameModel.DataNewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pop_list_item, null);
            vh.textview = (TextView) convertView.findViewById(R.id.tv_pop_list);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textview.setText(list.get(position).getDeptName());
        return convertView;
    }

    static class ViewHolder {
        TextView textview;
    }

}
