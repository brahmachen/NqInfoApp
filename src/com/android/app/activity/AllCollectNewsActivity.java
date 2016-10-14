package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import cn.univs.app.widget.ColumnHorizontalScrollView;

import com.android.ais.app.AppApplication;
import com.android.ais.app.R;
import com.android.app.adapter.NqltMainAdapter;
import com.android.app.adapter.NqzxMainAdapter;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.CollectNews;
import com.android.app.entity.NqltInfo;
import com.android.app.entity.NqzxInfo;
import com.android.app.sqlite.util.DBServer;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.ResourceUtil;
import com.android.app.util.ScreenSizeUtil;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AllCollectNewsActivity extends Activity implements
		IXListViewListener {

	private DBServer db;
	private List<CollectNews> newsData = new ArrayList<CollectNews>();
	ArrayList<String> nqltIds = new ArrayList<String>();
	ArrayList<String> nqzxIds = new ArrayList<String>();

	private AllCollectNewsActivity mInstance;

	private XListView mListView1, mListView2;
	NqltMainAdapter adapter1;
	NqzxMainAdapter adapter2;
	private int page1 = 1, page2 = 1;
	private String pageSize = "8";

	private ColumnHorizontalScrollView mNaviga_scroll;
	private LinearLayout mNavigation;
	private int columnSelectIndex = 0;
	private int mScreenWidth = 0;

	private ViewPager viewPiger = null;
	List<View> views = new ArrayList<View>();

	private PagerAdapter pagerAdapter = null;
	private ProgressDialog dialog;
	
	private ImageView iv_back ; 
	private TextView  tv_bianji ;
	
	private String zan_userId ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.collect_news_display);
		initview();
		initEvents();
	}

	private void initview() {
        mInstance = this; 
		dialog = new ProgressDialog(mInstance);
		
		
		mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
		
		
		if(!"NULL".equals(PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL"))){
			zan_userId = PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL") ; 
		}else{
			zan_userId="";
		}
		
		
		db=new DBServer(this);
		/*newsData = db.findAllCollectNews(zan_userId);
		if (!newsData.isEmpty()) {
			for (int i = 0; i < newsData.size(); i++) {
				if ("1".equals(newsData.get(i).getNewsType().trim())) {
					nqltIds.add(newsData.get(i).getNqxxId());
				} else {
					nqzxIds.add(newsData.get(i).getNqxxId());
				}
			}
		}*/

		mNaviga_scroll = (ColumnHorizontalScrollView) findViewById(R.id.collect_news_naviga_scroll);
		mNavigation = (LinearLayout) findViewById(R.id.collect_news_naviga_view);
		columnSelectIndex = 0;
		setTabTitle();
		iv_back = (ImageView) findViewById(R.id.collect_iv_back) ;
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		viewPiger = (ViewPager) findViewById(R.id.collect_news_viewpager);
		// viewPiger.setOffscreenPageLimit(5);
		LayoutInflater layoutInflater = LayoutInflater
				.from(AllCollectNewsActivity.this);
		View tab01 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab02 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		views.add(tab01);
		views.add(tab02);
		
		
		pagerAdapter = new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = views.get(position);
				if (position == 0 && (!newsData.isEmpty()) ){
					initTab01(view);
				} else if (position == 1 && (!newsData.isEmpty())) {
					initTab02(view);
				}else{
					Toast.makeText(AppApplication.getInstance(), "您当前暂无收藏内容", Toast.LENGTH_SHORT).show() ; 
				}
				container.addView(view);
				return view;

			}

			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}
		};
		viewPiger.setAdapter(pagerAdapter);
	

	}

	private void initTab02(View view) {
		mListView1 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView1.setPullLoadEnable(true);
		adapter1 = new NqltMainAdapter(AllCollectNewsActivity.this, mListView1);
		mListView1.setAdapter(adapter1);
		mListView1.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter1.ClearData();
				page1 = 1;
				getData(nqltIds, String.valueOf(page1), pageSize, "nqlt");
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page1 = page1 + 1;
				getData(nqltIds, String.valueOf(page1), pageSize, "nqlt");
			}

		});
		getData(nqltIds, String.valueOf(page1), pageSize, "nqlt");
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(AllCollectNewsActivity.this,
						CollectLtNewsActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqltInfo entitys = (NqltInfo) mListView1
						.getItemAtPosition(arg2);
				intent.putExtra("nqltId", String.valueOf(entitys.getId()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getType());
				intent.putExtra("zanflag", entitys.getZanFlag());
				intent.putExtra("zancount", String.valueOf(entitys.getZancount()));
				startActivity(intent);

			}

		});
	}

	private void initTab01(View view) {
		mListView2 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView2.setPullLoadEnable(true);
		adapter2 = new NqzxMainAdapter(AllCollectNewsActivity.this);
		mListView2.setAdapter(adapter2);

		getData(nqzxIds, String.valueOf(page2), pageSize, "nqzx");
		mListView2.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter2.ClearData();
				page2 = 1;
				getData(nqzxIds, String.valueOf(page2), pageSize, "nqzx");
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page2 = page2 + 1;
				getData(nqzxIds, String.valueOf(page2), pageSize, "nqzx");
			}

		});
		mListView2.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent.setClass(AllCollectNewsActivity.this,
						CollectNewsActivity.class);

				NqzxInfo entitys = (NqzxInfo) mListView2
						.getItemAtPosition(arg2);
				intent.putExtra("nqzxId",
						String.valueOf(entitys.getContentid()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getModelid());
				intent.putExtra("zanflag", entitys.getZanFlag());
				intent.putExtra("zancount", String.valueOf(entitys.getZancount()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);
			}

		});

	}

	

	
	public void getData(ArrayList<String> ids, String page, String pageSize,
			final String type) {
		
		if(ids.isEmpty()&&"nqlt".equals(type)){
			Toast.makeText(AppApplication.getInstance(), "您当前没收藏任何农情论坛信息", Toast.LENGTH_SHORT).show() ; 
			onLoad(1);
			return ; 
		}else if(ids.isEmpty()&&"nqzx".equals(type)){
			Toast.makeText(AppApplication.getInstance(), "您当前没收藏任何农情资讯信息", Toast.LENGTH_SHORT).show() ; 
			onLoad(2);
			return ; 
		}
		RequestParams baseParams = MyHttpAPIControl.getBaseParams();

		baseParams.put("ids", ids);
		baseParams.add("page", page);
		baseParams.add("pageSize", pageSize);
		baseParams.add("type", type);
		MyHttpAPIControl.newInstance().getCollectNews(baseParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						// mFailure_view.setVisibility(View.VISIBLE);
						Toast.makeText(AllCollectNewsActivity.this,
								"获取收藏信息失败！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						super.onStart();
						dialog.show();
						// mFailure_view.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);

						if ("nqlt".equals(type)) {
							Type tp = new TypeToken<AISDataBase<NqltInfo>>() {
							}.getType();
							AISDataBase<NqltInfo> ss = (AISDataBase<NqltInfo>) ParseUtils
									.Gson2Object(content, tp);
							if (ss != null && ss.isState()) {
								ArrayList<NqltInfo> naviga = ss.getData();
								adapter1.addData(naviga);
								adapter1.notifyDataSetChanged();
								onLoad(1);
								dialog.dismiss();
								
							}
						} else {
							Type tp = new TypeToken<AISDataBase<NqzxInfo>>() {
							}.getType();
							AISDataBase<NqzxInfo> ss = (AISDataBase<NqzxInfo>) ParseUtils
									.Gson2Object(content, tp);
							if (ss != null && ss.isState()) {
								ArrayList<NqzxInfo> naviga = ss.getData();
								adapter2.addData(naviga);
								adapter2.notifyDataSetChanged();
								onLoad(2);
								dialog.dismiss();
								
							}
						}

					}

				});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		nqzxIds.clear();
		nqltIds.clear();
		newsData = db.findAllCollectNews(zan_userId);
		if (!newsData.isEmpty()) {
			for (int i = 0; i < newsData.size(); i++) {
				if ("1".equals(newsData.get(i).getNewsType().trim())) {
					nqltIds.add(newsData.get(i).getNqxxId());
				} else {
					nqzxIds.add(newsData.get(i).getNqxxId());
				}
			}
		}
	
	}
	
	private void onLoad(int key) {
		switch (key) {
		case 1:
			mListView1.stopRefresh();
			mListView1.stopLoadMore();
			mListView1.setRefreshTime(new Date().toLocaleString());
			break;
		case 2:
			mListView2.stopRefresh();
			mListView2.stopLoadMore();
			mListView2.setRefreshTime(new Date().toLocaleString());
			break;
		default:
			break;
			
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void setTabTitle() {
		int count = 2;
		String channels[] = { "农情资讯", "农情论坛" };
		int dp2Px = ScreenSizeUtil.Dp2Px(AppApplication.getInstance(), 20);
		int width = (ScreenSizeUtil.getScreenWidth(mInstance) - 12 * 10 - 4
				- dp2Px - 19) / 2;
		mNavigation.removeAllViews();
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 12;
			params.rightMargin = 12;
			TextView localTextView = new TextView(AppApplication.getInstance());
			localTextView
					.setBackgroundResource(R.drawable.selector_navigation_btn);
			localTextView.setGravity(Gravity.CENTER);

			localTextView.setPadding(0, 5, 0, 5);
			localTextView.setId(i);
			localTextView.setText(channels[i]);
			localTextView.setTextColor(ResourceUtil.getColor(R.color.black)) ;

			/*
			 * localTextView .setTextColor(ResourceUtil
			 * .getColorStateList(R.color.top_category_scroll_text_color_day));
			 */
			localTextView.setTextSize(16);
			if (columnSelectIndex == i) {
				localTextView.setSelected(true);
			}
			localTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*for (int i = 0; i < mNavigation.getChildCount(); i++) {
						View localView = mNavigation.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							// 在此处加载数据
						}
					}*/
					v.setSelected(true);
					viewPiger.setCurrentItem(v.getId());
				}
			});

			mNavigation.addView(localTextView, params);
			if (i != count - 1) {
				ImageView imageView = new ImageView(AppApplication.getInstance());
				/*
				 * imageView.setImageDrawable(ResourceUtil
				 * .getDrawable(R.drawable.nav_line));
				 */
				LinearLayout.LayoutParams split = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mNavigation.addView(imageView, split);
			}
		}
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion * 2;
		// for (int i = 0; i < mNavigation.getChildCount(); i++) {
		View checkView = mNavigation.getChildAt(tab_postion * 2);
		int k = checkView.getMeasuredWidth();
		int l = checkView.getLeft();
		int i2 = l + k / 2 - mScreenWidth / 2;
		// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
		mNaviga_scroll.smoothScrollTo(i2, 0);
		// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
		// mItemWidth , 0);
		// }
		// 判断是否选中
		for (int j = 0; j < mNavigation.getChildCount(); j++) {
			View checkView1 = mNavigation.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion * 2) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView1.setSelected(ischeck);
		}
	}

	private void initEvents() {
		viewPiger.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				
				int currItem = viewPiger.getCurrentItem();
				
				selectTab(currItem);
				switch (currItem) {
				case 1:
					//getData(String.valueOf(1), String.valueOf(page1), pageSize, 1);

					break;
				case 2:
					//getData(String.valueOf(3), String.valueOf(page3), pageSize, 3);
					break;
				case 3:
					//getData(String.valueOf(4), String.valueOf(page4), pageSize, 4);

					break;
				case 4:
					//getData(String.valueOf(5), String.valueOf(page5), pageSize, 5);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

}
