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
import android.widget.TextView;

import com.android.ais.app.Constants;
import com.android.ais.app.R;
import com.android.app.entity.NqltInfo;
import com.android.app.util.AsyncImageLoader;
import com.android.app.util.AsyncImageLoader.ImageCallback;
import com.android.app.util.DateUtils;

public class NqltMainAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NqltInfo>  entitys = new ArrayList<NqltInfo>();
    LayoutInflater inflater ;
    XListView mListView ; 
    
    AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
			Constants.ITEM_IMG_WIDTH, Constants.ITEM_IMG_HEIGHT);
    
    public NqltMainAdapter(Context context,XListView mListView){
    	super();
		// TODO Auto-generated constructor stub
    	this.context = context;
		this.inflater=LayoutInflater.from(context);
		this.mListView = mListView;
    }
    
    
    public void addData(ArrayList<NqltInfo> entitys) {
		if (entitys != null) {
			this.entitys.addAll(entitys);
		}
		notifyDataSetChanged();
	}

    public void ClearData(){
    	entitys.clear();
    }

	public ArrayList<NqltInfo> getData() {
		return entitys;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entitys.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return entitys.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		NewsViewsHolderWithImg holderWithImg = null;

		if (convertView == null) {
				holderWithImg = new NewsViewsHolderWithImg();
				convertView = inflater.inflate(
						R.layout.nqlt_itme_with_img, null);
				holderWithImg.tv_title = (TextView) convertView
						.findViewById(R.id.nqlt_item_main_title);
				holderWithImg.iv_imgs = (ImageView) convertView
						.findViewById(R.id.nqlt_item_main_touxiang);
				holderWithImg.tv_nicheng = (TextView) convertView
						.findViewById(R.id.nqlt_item_main_nicheng);
				holderWithImg.tv_date = (TextView) convertView
						.findViewById(R.id.nqlt_item_main_date);
				holderWithImg.tv_comment = (TextView) convertView
						.findViewById(R.id.nqlt_item_main_pinglun);
				holderWithImg.tv_zan = (TextView) convertView
						.findViewById(R.id.nqlt_item_main_zan);
				convertView.setTag(holderWithImg);
			
			} else {
				holderWithImg = (NewsViewsHolderWithImg) convertView
						.getTag();
			}
		    NqltInfo item = (NqltInfo) getItem(position);
		    
			holderWithImg.tv_title.setText(item.getTitle());
			holderWithImg.tv_zan.setText("赞："+item.getZancount());
			holderWithImg.tv_comment.setText("评论："+item.getComments());
			holderWithImg.tv_date.setText(item
					.getTime());
			holderWithImg.tv_nicheng.setText(item.getUser().getNick());

			// 异步加载图片
			holderWithImg.iv_imgs.setTag(item.getUser().getIcon());
			Drawable cachedImage = asyncImageLoader.loadDrawable(
					item.getUser().getIcon(),  mListView, new ImageCallback() {
							
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl,ListView mListView) {
							ImageView imageViewByTag = null;
							imageViewByTag = (ImageView) mListView
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null
									&& imageDrawable != null) {
								imageViewByTag
										.setImageDrawable(imageDrawable);
								// Log.e("在回调里面设置好图片", "liushuai");
							} else {
								try {
									imageViewByTag
											.setImageResource(R.drawable.user_default_icon);
									// Log.e("在回调里面设置了默认的图片", "liushuai");
								} catch (Exception e) {
								}
							}
						}

					});
			holderWithImg.iv_imgs.setImageResource(R.drawable.user_default_icon);
			if (cachedImage != null) {
				// Log.e("没在回调里设置好图片", "liushuai");
				holderWithImg.iv_imgs.setImageDrawable(cachedImage);
			}
		
		return convertView;
	}

	public class NewsViewsHolderWithImg {
		TextView tv_title;
		TextView tv_nicheng;
		ImageView iv_imgs;
		TextView tv_date;
		TextView tv_zan;
		TextView tv_comment;
	}
}




	
	

