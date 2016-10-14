package com.android.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ais.app.R;
import com.android.app.entity.NqzxInfo;
import com.android.app.entity.Type;

public class NqzxMainAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NqzxInfo> mArticles = new ArrayList<NqzxInfo>();

	public NqzxMainAdapter(Context context) {
		this.context = context;
	}

	public void addData(ArrayList<NqzxInfo> articles) {
		if (articles != null) {
			this.mArticles.addAll(articles);
		}
		this.notifyDataSetChanged();
	}
	
	public void ClearData() {
		mArticles.clear();
	}

	public ArrayList<NqzxInfo> getData() {
		return mArticles;
	}

	@Override
	public int getCount() {
		return mArticles.size();
	}

	@Override
	public NqzxInfo getItem(int position) {
		return mArticles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler viewholder = null;
		if (convertView == null) {
			viewholder = new ViewHodler();
			convertView = View.inflate(context,
					R.layout.univs_itme_article_view, null);
			viewholder.type = (TextView) convertView
					.findViewById(R.id.article_itme_type);
			viewholder.time = (TextView) convertView
					.findViewById(R.id.article_itme_time);
			viewholder.title = (TextView) convertView
					.findViewById(R.id.article_itme_title);
			viewholder.content = (TextView) convertView
					.findViewById(R.id.article_itme_content);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHodler) convertView.getTag();
		}
		NqzxInfo item = getItem(position);
		
	    //设置农情资讯的类型
     	viewholder.type.setText(new Type(Integer.parseInt(item.getModelid().toString())).toString());

		viewholder.title.setText(item.getTitle());
		viewholder.content.setText(item.getDescription());
	/*	viewholder.time.setText(DateUtils.formatDateDifference(new Date(item
				.getTime() * 1000)));*/
		viewholder.time.setText(item
				.getTime());
		return convertView;
	}

	class ViewHodler {
		//type：设置农情资讯的类型     time：农情资讯时间     title：农情资讯标题    content：农情资讯内容
 		public TextView type, time, title, content;
	}
}
