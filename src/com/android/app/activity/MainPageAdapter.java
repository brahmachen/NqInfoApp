package com.android.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ais.app.Constants;
import com.android.ais.app.MainActivity;
import com.android.ais.app.R;
import com.android.app.entity.NqzxInfo;
import com.android.app.util.AsyncImageLoader;
import com.android.app.util.AsyncImageLoader.ImageCallback;
import com.aps.l;

public class MainPageAdapter extends BaseAdapter {

	private Context context = null;
	private List<NqzxInfo> adatperData0 = new ArrayList<NqzxInfo>();
	private List<NqzxInfo> adatperData1 = new ArrayList<NqzxInfo>();
	private List<NqzxInfo> adatperData2 = new ArrayList<NqzxInfo>();
	AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
			Constants.ITEM_IMG_WIDTH, Constants.ITEM_IMG_HEIGHT);
	private ListView listView = null;

	public MainPageAdapter(Context context, List<NqzxInfo> adatperData0,
			List<NqzxInfo> adatperData1, List<NqzxInfo> adatperData2,
			ListView listView) {
		super();
		this.context = context;
		this.adatperData0 = adatperData0;
		this.adatperData1 = adatperData1;
		this.adatperData2 = adatperData2;
		this.listView = listView;
	}

	public MainPageAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return adatperData0.size() + adatperData1.size() + adatperData2.size()
				+ 4;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = null;

		if (adatperData0 != null && adatperData1 != null
				&& adatperData2 != null) {
			System.out.println("-=-=-=-=-=-=  " + position + " "
					+ adatperData0.size() + " " + adatperData1.size() + "  "
					+ adatperData2.size());

			if (position == 0) {
				view = layoutInflater.inflate(R.layout.layout_slideshow, null);
				final ImageView imgBcfz = (ImageView) view
						.findViewById(R.id.id_img_bcfz);
				final ImageView imgZzjs = (ImageView) view
						.findViewById(R.id.id_img_zzjs);
				final ImageView imgSchq = (ImageView) view
						.findViewById(R.id.id_img_schq);
				final ImageView imgGyxq = (ImageView) view
						.findViewById(R.id.id_img_gyxq);

				imgZzjs.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							imgZzjs.setImageResource(R.drawable.zzjs_pressed);
						}
						if (event.getAction() == MotionEvent.ACTION_UP) {
							imgZzjs.setImageResource(R.drawable.zzjs_normal);
						}
						return false;
					}
				});
				imgSchq.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							imgSchq.setImageResource(R.drawable.schq_pressed);
						}
						if (event.getAction() == MotionEvent.ACTION_UP) {
							imgSchq.setImageResource(R.drawable.schq_normal);
						}
						return false;
					}
				});
				imgBcfz.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							imgBcfz.setImageResource(R.drawable.bcfh_pressed);
						}
						if (event.getAction() == MotionEvent.ACTION_UP) {
							imgBcfz.setImageResource(R.drawable.bcfh_normal);
						}
						return false;
					}
				});

				imgGyxq.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							imgGyxq.setImageResource(R.drawable.gyxq_pressed);
						}
						if (event.getAction() == MotionEvent.ACTION_UP) {
							imgGyxq.setImageResource(R.drawable.gyxq_normal);
						}
						return false;
					}
				});

				imgBcfz.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						NqzxMainActivity.currentPosition = 0;
						((MainActivity) context).setSelect(1);
					}
				});

				imgZzjs.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						NqzxMainActivity.currentPosition = 1;
						((MainActivity) context).setSelect(1);
					}
				});
				imgSchq.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						NqzxMainActivity.currentPosition = 3;
						((MainActivity) context).setSelect(1);
					}
				});
				imgGyxq.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						NqzxMainActivity.currentPosition = 4;
						((MainActivity) context).setSelect(1);
					}
				});

			} else if (position == 1) {
				view = layoutInflater.inflate(R.layout.item_main_listview_head,
						null);
				LinearLayout layout = (LinearLayout) view
						.findViewById(R.id.id_layout_item_head);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(10, 0, 0, 10);// 4个参数按顺序分别是左上右下
				// layout.setLayoutParams(layoutParams);

				TextView textViewHead = (TextView) view
						.findViewById(R.id.id_text_head);
				textViewHead.setText("新闻资讯");
				TextView moreTextView = (TextView) view
						.findViewById(R.id.id_text_more);
				moreTextView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						MainActivity mainActivity = (MainActivity) context;
						NqzxMainActivity.currentPosition = 2;
						mainActivity.setSelect(1);
					}
				});
			} else if (position > 1 && position <= adatperData0.size() + 1) {
				view = layoutInflater.inflate(R.layout.item_main_page_listview,
						null);
				int num = position - 1 - 1;
				if (num != 0) {
					View lineView = view.findViewById(R.id.id_view_line);
					lineView.setVisibility(View.VISIBLE);
				}
				TextView textViewTitle = (TextView) view
						.findViewById(R.id.id_news_title);
				textViewTitle.setText(adatperData0.get(num).getTitle());

				TextView textViewNick = (TextView) view
						.findViewById(R.id.id_news_auther);
				textViewNick.setText(adatperData0.get(num).getUser().getNick());

				TextView textViewNewsDesc = (TextView) view
						.findViewById(R.id.id_news_desc);
				textViewNewsDesc
						.setText(adatperData0.get(num).getDescription());
				textViewNewsDesc.setVisibility(View.VISIBLE);

				ImageView newsImage = (ImageView) view
						.findViewById(R.id.id_news_pic1);
				LinearLayout layout = (LinearLayout) view
						.findViewById(R.id.id_layout_newsimg);
				layout.setVisibility(View.VISIBLE);
				newsImage.setImageResource(R.drawable.default_icon);
				System.out.println("newsImage:" + newsImage);
				// newsImage.setVisibility(View.VISIBLE);
				newsImage.setTag(adatperData0.get(num).getTitleImg());
				System.out.println("adatperData0.get(num).getTitleImg():"
						+ adatperData0.get(num).getTitleImg());
				Drawable cachedImage = asyncImageLoader.loadDrawable(
						adatperData0.get(num).getTitleImg(), listView,
						new ImageCallback() {

							@Override
							public void imageLoaded(Drawable imageDrawable,
									String imageUrl, ListView mListView) {
								System.out.println("imageDrawable:"
										+ imageDrawable);
								ImageView imageViewByTag = null;
								imageViewByTag = (ImageView) mListView
										.findViewWithTag(imageUrl);
								System.out.println("imageViewByTag"
										+ imageViewByTag);
								if (imageViewByTag != null
										&& imageDrawable != null) {
									imageViewByTag
											.setImageDrawable(imageDrawable);
								} else {
									try {
										imageViewByTag
												.setImageResource(R.drawable.user_default_icon);
									} catch (Exception e) {
									}
								}

							}
						});
				if (cachedImage != null) {
					newsImage.setImageDrawable(cachedImage);
				}
			} else if (position == adatperData0.size() + 2) {
				view = layoutInflater.inflate(R.layout.item_main_listview_head,
						null);
				TextView textViewHead = (TextView) view
						.findViewById(R.id.id_text_head);
				textViewHead.setText("市场行情");

				TextView moreTextView = (TextView) view
						.findViewById(R.id.id_text_more);

				moreTextView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						MainActivity mainActivity = (MainActivity) context;
						NqzxMainActivity.currentPosition = 3;
						mainActivity.setSelect(1);
					}
				});
			} else if (position >= adatperData0.size() + 3
					&& position < adatperData1.size() + adatperData0.size() + 3) {
				int num = position - adatperData0.size() - 3;
				System.out.println(num + "..,,,,,,,,,,,,,,,,,,,,,");
				view = layoutInflater.inflate(R.layout.item_main_page_listview,
						null);

				TextView textViewTitle = (TextView) view
						.findViewById(R.id.id_news_title);
				textViewTitle.setText(adatperData1.get(num).getTitle());

				TextView textViewNick = (TextView) view
						.findViewById(R.id.id_news_auther);
				textViewNick.setText(adatperData1.get(num).getUser().getNick());

				TextView textViewwTime = (TextView) view
						.findViewById(R.id.id_news_time);
				// System.out.println("adatperData1.get(num).getTime():"+adatperData1.get(num).getTime());
				if (!"null".equals(adatperData1.get(num).getTime())) {
					textViewwTime.setText(adatperData1.get(num).getTime()
							.subSequence(5, 10));
				}

				textViewwTime.setVisibility(View.VISIBLE);
			} else if (position == adatperData0.size() + adatperData1.size()
					+ 3) {
				view = layoutInflater.inflate(R.layout.item_main_listview_head,
						null);
				TextView textViewHead = (TextView) view
						.findViewById(R.id.id_text_head);
				textViewHead.setText("种植技术");
				TextView moreTextView = (TextView) view
						.findViewById(R.id.id_text_more);
				moreTextView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						MainActivity mainActivity = (MainActivity) context;
						NqzxMainActivity.currentPosition = 1;
						mainActivity.setSelect(1);
					}
				});
			} else {
				view = layoutInflater.inflate(R.layout.item_main_page_listview,
						null);
				int num = position - adatperData0.size() - adatperData1.size()
						- 4;
				System.out.println(num + "...,,,,,,,,,,,,,,,,,,,,,");
				TextView textViewTitle = (TextView) view
						.findViewById(R.id.id_news_title);
				textViewTitle.setText(adatperData2.get(num).getTitle());

				TextView textViewNick = (TextView) view
						.findViewById(R.id.id_news_auther);
				textViewNick.setText(adatperData2.get(num).getUser().getNick());

				TextView textViewwTime = (TextView) view
						.findViewById(R.id.id_news_time);
				textViewwTime.setText(adatperData2.get(num).getTime()
						.subSequence(5, 10));
				textViewwTime.setVisibility(View.VISIBLE);

				if (num == (adatperData2.size() - 1)) {
					LinearLayout layout = (LinearLayout) view
							.findViewById(R.id.id_layout_item);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(20, 0, 20, 20);// 4个参数按顺序分别是左上右下
					layout.setLayoutParams(layoutParams);
				}
			}
		} else {
			view = layoutInflater.inflate(R.layout.item_main_page_listview,
					null);
		}
		return view;
	}
}
