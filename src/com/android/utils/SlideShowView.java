package com.android.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.ais.app.R;
import com.android.app.activity.NqZxContentActivity;
import com.android.app.entity.TopnewsTrans;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.Util;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
 * 
 * @author caizhiming
 *
 */

public class SlideShowView extends FrameLayout implements OnClickListener {

	// 轮播图图片数量
	private final static int IMAGE_COUNT = 5;
	// 自动轮播的时间间隔
	private final static int TIME_INTERVAL = 50;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = false;

	private Context context = null;
	// 自定义轮播图的资源ID
	private int[] imagesResIds;
	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;

	private Handler picHandler;
	private MyPagerAdapter myPagerAdapter = null;
	private ArrayList<TopnewsTrans> topnewsTransList = null;
	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public SlideShowView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public SlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		initData();
		initUI(context);
		if (isAutoPlay) {
			startPlay();
		}

	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		imagesResIds = new int[] { R.drawable.pic1, R.drawable.pic2,
				R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, };
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		for(int i = 0;i<IMAGE_COUNT;i++){
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(R.drawable.default_icon);
			imageViewsList.add(imageView);
		}

	}

	/**
	 * 初始化Views等UI
	 */
	private void initUI(final Context context) {
		LayoutInflater.from(context).inflate(R.layout.img_play, this, true);
		for (int imageID : imagesResIds) {
			ImageView view = new ImageView(context);
			view.setImageResource(imageID);
			view.setScaleType(ScaleType.FIT_XY);
			// imageViewsList.add(view);
			view.setOnClickListener(this);
		}

		dotViewsList.add(findViewById(R.id.v_dot1));
		dotViewsList.add(findViewById(R.id.v_dot2));
		dotViewsList.add(findViewById(R.id.v_dot3));
		dotViewsList.add(findViewById(R.id.v_dot4));
		dotViewsList.add(findViewById(R.id.v_dot5));

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);
		// myPagerAdapter = new MyPagerAdapter();
		// viewPager.setAdapter(myPagerAdapter);
		// viewPager.setOnPageChangeListener(new MyPageChangeListener());

		RequestParams requestParams = MyHttpAPIControl.getBaseParams();
		String userId = PreferenceUtils.getPrefString(context, "UserId", "");
		requestParams.add("userId", userId);
		com.android.app.util.MyHttpAPIControl.newInstance().getTopNews(
				requestParams, new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						super.onFinish();
					}

					@Override
					public void onStart() {
						System.out.println("---请求开始！---");
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						System.out.println(content);
						Type type = new TypeToken<AISDataBase<TopnewsTrans>>() {
						}.getType();
						AISDataBase<TopnewsTrans> aw = (AISDataBase<TopnewsTrans>) ParseUtils
								.Gson2Object(content, type);
						if (aw != null && aw.isState()) {
							topnewsTransList = aw
									.getData();
							picHandler = new PicHandler();
							for (int i = 0; i < topnewsTransList.size(); i++) {

								LoadPicThread loadPicThread = new LoadPicThread(
										topnewsTransList.get(i).getImgurl(),i+1);
								loadPicThread.start();
							}

						}
					}

				});
	}

	class LoadPicThread extends Thread {
		String picUrl = null;
		int index;
		@Override
		public void run() {
			System.out.println("picUrl:" + picUrl);
			Bitmap imgBitmap = Util.getbitmap(picUrl);
			Message msg = picHandler.obtainMessage();
			msg.what = index;
			msg.obj = imgBitmap;
			picHandler.sendMessage(msg);
		}

		LoadPicThread(String url,int index) {
			this.picUrl = url;
			this.index = index;
		}

	}

	class PicHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			ImageView imageView = new ImageView(context);
			if (msg.what != 0) {
				Bitmap bitmap = (Bitmap) msg.obj;
				imageView.setImageBitmap(bitmap);
			} else {
				imageView.setImageResource(R.drawable.default_icon);
			}
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setOnClickListener(SlideShowView.this);
			imageViewsList.set(IMAGE_COUNT-msg.what, imageView);
			if (imageViewsList.size() == IMAGE_COUNT) {
				System.out.println("imageViewsList.size()"
						+ imageViewsList.size());
				// myPagerAdapter.notifyDataSetChanged();
				myPagerAdapter = new MyPagerAdapter();
				viewPager.setAdapter(myPagerAdapter);
				viewPager.setOnPageChangeListener(new MyPageChangeListener());
			}
		}

	}

	/**
	 * 填充ViewPager的页面适配器
	 * 
	 * @author caizhiming
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author caizhiming
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub

			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.dot_focused);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_normal);
				}
			}
		}

	}

	/**
	 * 执行轮播图切换任务
	 *
	 * @author caizhiming
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 * @author caizhiming
	 */
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (viewPager.getCurrentItem()) {
		case 0:
			getInto(0);
			break;
		case 1:
			getInto(1);
			break;
		case 2:
			getInto(2);
			break;
		case 3:
			getInto(3);
			break;
		case 4:
			getInto(4);
			break;
		default:
			break;
		}
	}

	private void getInto(int position) {
		RequestParams requestParams = MyHttpAPIControl.getBaseParams();
		String userId = PreferenceUtils.getPrefString(context, "UserId", "");
		Intent intent = new Intent();
		intent.setClass(context, NqZxContentActivity.class);
		intent.putExtra("nqzxId", topnewsTransList.get(position).getNqzxinfoId());
		intent.putExtra("userId", userId);
		intent.putExtra("isHaveData","0");
		context.startActivity(intent);
	}

}