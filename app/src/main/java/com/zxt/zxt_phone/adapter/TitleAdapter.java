package com.zxt.zxt_phone.adapter;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.TitleModel;


public class TitleAdapter  extends BaseAdapter {

	private int selectItem = 0;
	private LayoutInflater layoutInflater;
	private List<TitleModel.DataNewsTitleModel> list;
	Context context;
	
	public TitleAdapter(Context context, List<TitleModel.DataNewsTitleModel> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	public int getCount() {
		return this.list != null ? this.list.size() : 0;
	}

	public Object getItem(int position) {
		return this.list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	// 根据位置得到View对象
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		convertView = layoutInflater.inflate(R.layout.horizontal_viewitem,
					null);
        
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText("" + list.get(position).getDeptName().toString());
		
		if (position == selectItem) {
			title.setBackgroundResource(R.drawable.tab_bg_click); 
			title.setTextColor(context.getResources().getColor(R.color.topcolor));
		} else {
			title.setBackgroundResource(R.drawable.tab_bg);
			title.setTextColor(context.getResources().getColor(R.color.maop_text_color));
		}
		return convertView;
	}

}
