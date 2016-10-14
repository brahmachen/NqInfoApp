package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.univs.app.widget.ColumnHorizontalScrollView;

import com.android.ais.app.AppApplication;
import com.android.ais.app.MainActivity;
import com.android.ais.app.R;
import com.android.app.adapter.NqzxMainAdapter;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.NqzxInfo;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.ScreenSizeUtil;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NqzxMainActivity extends Fragment implements IXListViewListener {
	private Context mInstance;

	private ColumnHorizontalScrollView mNaviga_scroll;
	private LinearLayout mNavigation;
	private int columnSelectIndex = 0;
	private int mScreenWidth = 0;
	private View mFailure_view;

	private ViewPager viewPiger = null;
	private PagerAdapter pagerAdapter = null;
	List<View> views = new ArrayList<View>();

	private ProgressDialog dialog;

	private XListView mListView1, mListView2, mListView3, mListView4,
			mListView5;
	NqzxMainAdapter adapter1, adapter2, adapter3, adapter4, adapter5;
	private int page1 = 1, page2 = 1, page3 = 1, page4 = 1, page5 = 1;
	private String pageSize = "8";
	String userId;

	public static int currentPosition = -1;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// mInstance = this;
	// mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
	// requestWindowFeature(Window.FEATURE_NO_TITLE);//关掉title
	// setContentView(R.layout.nqzx_layout_main);
	// initView();
	// initEvents();
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.nqzx_layout_main, container,
				false);
		mInstance = getActivity();
		mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
		initView(view);
		initEvents();
		return view;
	}

	private void initView(View view) {
		dialog = new ProgressDialog(mInstance);

		if (!"NULL".equals(PreferenceUtils.getPrefString(
				AppApplication.getInstance(), "UserId", "NULL"))) {
			userId = PreferenceUtils.getPrefString(
					AppApplication.getInstance(), "UserId", "NULL");
		} else if (!"NULL".equals(PreferenceUtils.getPrefString(
				AppApplication.getInstance(), "AnonymousUserId", "NULL"))) {
			userId = PreferenceUtils.getPrefString(
					AppApplication.getInstance(), "AnonymousUserId", "NULL");
		} else {
			userId = "-1";
		}

		TextView title = (TextView) view.findViewById(R.id.tv_head_title);
		title.setText("农情资讯");
		view.findViewById(R.id.iv_head_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MainActivity mainActivity = (MainActivity) getActivity();
						mainActivity.setSelect(4);
					}
				});
		view.findViewById(R.id.iv_head_right).setVisibility(View.GONE);
		mNaviga_scroll = (ColumnHorizontalScrollView) view
				.findViewById(R.id.naviga_scroll);
		mNavigation = (LinearLayout) view.findViewById(R.id.naviga_view);
		// mFailure_view = findViewById(R.id.failure_view);
		// 重新加载数据
		/*
		 * findViewById(R.id.reload).setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { getChanneldata(); } });
		 */
		columnSelectIndex = 0;
		setTabTitle();

		viewPiger = (ViewPager) view.findViewById(R.id.id_viewpager);
		viewPiger.setOffscreenPageLimit(5);

		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View tab01 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab02 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab03 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab04 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab05 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);

		views.add(tab01);
		views.add(tab02);
		views.add(tab03);
		views.add(tab04);
		views.add(tab05);

		pagerAdapter = new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = views.get(position);
				if (position == 0) {
					initTab01(view);
				} else if (position == 1) {
					initTab02(view);
				} else if (position == 2) {
					initTab03(view);
				} else if (position == 3) {
					initTab04(view);
				} else {
					initTab05(view);
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
		if (currentPosition == -1) {
			viewPiger.setCurrentItem(0);
		}
		viewPiger.setCurrentItem(currentPosition);
		selectTab(currentPosition);
		System.out.println("currentPosition:" + currentPosition);

	}

	private void setTabTitle() {
		int count = 5;
		String channels[] = { "病虫防治", "种植技术", "新闻资讯", "市场行情", "供求信息" };
		int dp2Px = ScreenSizeUtil.Dp2Px(getActivity(), 20);
		int width = (ScreenSizeUtil.getScreenWidth(mInstance) - 12 * 10 - 4
				- dp2Px - 19) / 4;
		mNavigation.removeAllViews();
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 12;
			params.rightMargin = 12;
			TextView localTextView = new TextView(getActivity());
			localTextView
					.setBackgroundResource(R.drawable.selector_navigation_btn);
			localTextView.setGravity(Gravity.CENTER);

			localTextView.setPadding(0, 5, 0, 5);
			localTextView.setId(i);
			localTextView.setText(channels[i]);

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
					/*
					 * for (int i = 0; i < mNavigation.getChildCount(); i++) {
					 * View localView = mNavigation.getChildAt(i); if (localView
					 * != v) localView.setSelected(false); else {
					 * localView.setSelected(true); // 在此处加载数据 } }
					 */
					v.setSelected(true);
					viewPiger.setCurrentItem(v.getId());
				}
			});

			mNavigation.addView(localTextView, params);
			if (i != count - 1) {
				ImageView imageView = new ImageView(getActivity());
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
					// getData(String.valueOf(1), String.valueOf(page1),
					// pageSize, 1);

					break;
				case 2:
					// getData(String.valueOf(3), String.valueOf(page3),
					// pageSize, 3);
					break;
				case 3:
					// getData(String.valueOf(4), String.valueOf(page4),
					// pageSize, 4);

					break;
				case 4:
					// getData(String.valueOf(5), String.valueOf(page5),
					// pageSize, 5);
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

	private void initTab01(View view) {
		mListView1 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView1.setPullLoadEnable(true);
		adapter1 = new NqzxMainAdapter(getActivity());
		mListView1.setAdapter(adapter1);
		mListView1.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter1.ClearData();
				page1 = 1;
				getData(String.valueOf(1), String.valueOf(page1), pageSize, 1,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page1 = page1 + 1;
				getData(String.valueOf(1), String.valueOf(page1), pageSize, 1,
						userId);
			}

		});
		getData(String.valueOf(1), String.valueOf(page1), pageSize, 1, userId);
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(getActivity(), NqZxContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqzxInfo entitys = (NqzxInfo) mListView1
						.getItemAtPosition(arg2);
				intent.putExtra("nqzxId",
						String.valueOf(entitys.getContentid()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getModelid());
				intent.putExtra("zanCount", entitys.getZancount().toString());
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);

			}

		});
	}

	private void initTab02(View view) {
		mListView2 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView2.setPullLoadEnable(true);
		adapter2 = new NqzxMainAdapter(getActivity());
		mListView2.setAdapter(adapter2);

		getData(String.valueOf(2), String.valueOf(page2), pageSize, 2, userId);
		mListView2.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter2.ClearData();
				page2 = 1;
				getData(String.valueOf(2), String.valueOf(page2), pageSize, 2,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page2 = page2 + 1;
				getData(String.valueOf(2), String.valueOf(page2), pageSize, 2,
						userId);
			}

		});
		mListView2.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(getActivity(), NqZxContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
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
				intent.putExtra("zanCount", entitys.getZancount().toString());
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);
			}

		});

	}

	private void initTab03(View view) {
		mListView3 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView3.setPullLoadEnable(true);
		adapter3 = new NqzxMainAdapter(getActivity());
		mListView3.setAdapter(adapter3);

		getData(String.valueOf(3), String.valueOf(page3), pageSize, 3, userId);
		mListView3.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter3.ClearData();
				page3 = 1;
				getData(String.valueOf(3), String.valueOf(page3), pageSize, 3,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page3 = page3 + 1;
				getData(String.valueOf(3), String.valueOf(page3), pageSize, 3,
						userId);
			}

		});
		mListView3.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(getActivity(), NqZxContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqzxInfo entitys = (NqzxInfo) mListView3
						.getItemAtPosition(arg2);
				intent.putExtra("nqzxId",
						String.valueOf(entitys.getContentid()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getModelid());
				intent.putExtra("zanCount", entitys.getZancount().toString());
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);

			}

		});

	}

	private void initTab04(View view) {
		mListView4 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView4.setPullLoadEnable(true);
		adapter4 = new NqzxMainAdapter(getActivity());
		mListView4.setAdapter(adapter4);

		getData(String.valueOf(4), String.valueOf(page4), pageSize, 4, userId);
		mListView4.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter4.ClearData();
				page4 = 1;
				getData(String.valueOf(4), String.valueOf(page4), pageSize, 4,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page4 = page4 + 1;
				getData(String.valueOf(4), String.valueOf(page4), pageSize, 4,
						userId);
			}

		});
		mListView4.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(getActivity(), NqZxContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象

				NqzxInfo entitys = (NqzxInfo) mListView4
						.getItemAtPosition(arg2);

				intent.putExtra("nqzxId",
						String.valueOf(entitys.getContentid()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getModelid());
				intent.putExtra("zanCount", entitys.getZancount().toString());
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);

			}

		});

	}

	private void initTab05(View view) {
		mListView5 = (XListView) view.findViewById(R.id.weatherListView);
		// progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		mListView5.setPullLoadEnable(true);
		adapter5 = new NqzxMainAdapter(getActivity());
		mListView5.setAdapter(adapter5);

		getData(String.valueOf(5), String.valueOf(page5), pageSize, 5, userId);
		mListView5.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				adapter5.ClearData();
				page5 = 1;
				getData(String.valueOf(5), String.valueOf(page5), pageSize, 5,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				page5 = page5 + 1;
				getData(String.valueOf(5), String.valueOf(page5), pageSize, 5,
						userId);
			}

		});
		mListView5.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(getActivity(), NqZxContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqzxInfo entitys = (NqzxInfo) mListView5
						.getItemAtPosition(arg2);
				intent.putExtra("nqzxId",
						String.valueOf(entitys.getContentid()));
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("time", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("topicType", entitys.getModelid());
				intent.putExtra("zanCount", entitys.getZancount().toString());
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				intent.putExtra("isHaveData", "1");
				startActivity(intent);
			}

		});

	}

	public void getData(String type, String page, String pageSize,
			final int key, String userId) {
		RequestParams baseParams = MyHttpAPIControl.getBaseParams();

		baseParams.add("type", type);
		baseParams.add("page", page);
		baseParams.add("pageSize", pageSize);
		baseParams.add("userId", userId);
		MyHttpAPIControl.newInstance().getNqzx(baseParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						// mFailure_view.setVisibility(View.VISIBLE);
						Toast.makeText(getActivity(), "获取数据失败！",
								Toast.LENGTH_SHORT).show();
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
						Type tp = new TypeToken<AISDataBase<NqzxInfo>>() {
						}.getType();
						AISDataBase<NqzxInfo> ss = (AISDataBase<NqzxInfo>) ParseUtils
								.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							ArrayList<NqzxInfo> naviga = ss.getData();
							dialog.dismiss();
							switch (key) {
							case 1:
								adapter1.addData(naviga);
								break;
							case 2:
								adapter2.addData(naviga);
								break;
							case 3:
								adapter3.addData(naviga);
								break;
							case 4:
								adapter4.addData(naviga);
								break;
							case 5:
								adapter5.addData(naviga);
								break;

							default:
								break;
							}
							onLoad(key);
							dialog.dismiss();
						}

					}

				});
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
		case 3:
			mListView3.stopRefresh();
			mListView3.stopLoadMore();
			mListView3.setRefreshTime(new Date().toLocaleString());
			break;
		case 4:
			mListView4.stopRefresh();
			mListView4.stopLoadMore();
			mListView4.setRefreshTime(new Date().toLocaleString());

			break;
		case 5:
			mListView5.stopRefresh();
			mListView5.stopLoadMore();
			mListView5.setRefreshTime(new Date().toLocaleString());
			break;

		default:
			break;
		}

	}

}
