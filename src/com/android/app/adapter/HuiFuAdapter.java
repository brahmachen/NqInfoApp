package com.android.app.adapter;

import java.util.ArrayList;

import me.maxwin.view.XListView;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.ais.app.Constants;
import com.android.ais.app.R;
import com.android.app.adapter.ZhuTieAdapter.NoticeItem;
import com.android.app.entity.MyCommentedTrans;
import com.android.app.entity.MyNqltCommentedTrans;
import com.android.app.util.AsyncImageLoader;
import com.android.app.util.AsyncImageLoader.ImageCallback;

public class HuiFuAdapter extends BaseAdapter {
	private Context context = null;
	private ArrayList<MyCommentedTrans> mZhuTieTrans = new ArrayList<MyCommentedTrans>();
	private XListView xListView = null;
	
	AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
			Constants.ITEM_IMG_WIDTH, Constants.ITEM_IMG_HEIGHT);

	public HuiFuAdapter(Context context, XListView xListView) {
		super();
		this.context = context;
		this.xListView = xListView;
	}

	public void addData(ArrayList<MyCommentedTrans> entitys) {
		if (entitys != null) {
			mZhuTieTrans.addAll(entitys);
		}
		notifyDataSetChanged();
	}

	public void ClearData() {
		mZhuTieTrans.clear();
	}

	public ArrayList<MyCommentedTrans> getData() {
		return mZhuTieTrans;
	}

	@Override
	public int getCount() {
		return mZhuTieTrans.size();
		// return 10;
	}

	@Override
	public Object getItem(int arg0) {
		return mZhuTieTrans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		NoticeItem noticeItem = null;
		if (convertView == null) {
			noticeItem = new NoticeItem();
			convertView = layoutInflater.inflate(R.layout.nitice_item, null);
			noticeItem.userName = (TextView) convertView
					.findViewById(R.id.id_text_username);
			noticeItem.userTime = (TextView) convertView
					.findViewById(R.id.id_text_user_time);
			noticeItem.usercomment = (TextView) convertView
					.findViewById(R.id.id_text_content);
			noticeItem.selfContent = (TextView) convertView
					.findViewById(R.id.id_text_selfcontent);
			noticeItem.huifu = (TextView) convertView
					.findViewById(R.id.id_text_huifu);
			noticeItem.userHead = (ImageView) convertView
					.findViewById(R.id.id_img_userhead);
			convertView.setTag(noticeItem);
		} else {
			noticeItem = (NoticeItem) convertView.getTag();
		}
		MyCommentedTrans zhuTieTrans = (MyCommentedTrans) getItem(position);
		noticeItem.userName.setText(zhuTieTrans.getCommentUser().getNick());
		noticeItem.userTime.setText(zhuTieTrans.getTime());
		noticeItem.usercomment.setText(zhuTieTrans.getContent());
		noticeItem.selfContent.setText("�ң�"+zhuTieTrans.getCommentedUser().getContent());

		noticeItem.userHead.setTag(zhuTieTrans.getCommentUser().getIcon());
		Drawable cachedImg = asyncImageLoader.loadDrawable(zhuTieTrans.getCommentUser().getIcon(),
				xListView, new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl, ListView mListView) {
						ImageView imageViewByTag = null;
						imageViewByTag = (ImageView) mListView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null && imageDrawable != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
							// Log.e("�ڻص��������ú�ͼƬ", "liushuai");
						} else {
							try {
								imageViewByTag
										.setImageResource(R.drawable.user_default_icon);
								// Log.e("�ڻص�����������Ĭ�ϵ�ͼƬ", "liushuai");
							} catch (Exception e) {
							}
						}
					}
				});

		noticeItem.userHead.setImageResource(R.drawable.default_user_head_img);
		if (cachedImg != null) {
			noticeItem.userHead.setImageDrawable(cachedImg);
		}
		return convertView;
	}

	public class NoticeItem {
		TextView userName;
		TextView userTime;
		TextView usercomment;
		TextView selfContent;
		TextView huifu;
		ImageView userHead;
	}
}