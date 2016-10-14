package com.android.app.adapter;

import com.android.ais.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {
	int count;
	Context context;

	int[] res;

	String[] name;

	public MyGridViewAdapter(Context context, int[] res, String[] name) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.count = res.length;
		this.res = res;
		this.name = name;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stud
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_item, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.tv = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv.setImageResource(res[position]);
		holder.tv.setText(name[position]);
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}

}
