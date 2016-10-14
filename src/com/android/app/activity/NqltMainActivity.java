package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.univs.app.widget.ColumnHorizontalScrollView;
import cn.univs.app.widget.PopupMenu;
import cn.univs.app.widget.PopupMenu.MENUITEM;
import cn.univs.app.widget.PopupMenu.OnItemClickListenerForPopMenu;

import com.android.ais.app.AppApplication;
import com.android.ais.app.MainActivity;
import com.android.ais.app.R;
import com.android.app.adapter.NqltMainAdapter;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.NqltInfo;
import com.android.app.socialization.publish.PublishedActivity;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.ScreenSizeUtil;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NqltMainActivity extends Fragment implements IXListViewListener {
	private Activity mInstance;
	private ColumnHorizontalScrollView mNaviga_scroll;
	private LinearLayout mNavigation;
	private int columnSelectIndex = 0;
	private int mScreenWidth = 0;
	private View mFailure_view;

	private ViewPager viewPiger = null;
	private PagerAdapter pagerAdapter = null;
	List<View> views = new ArrayList<View>();

	private ProgressDialog dialog;
	// 创建对话框对象,不是通过new方法获得的，是通过builder.create();
	private AlertDialog dialog_user;
	private AlertDialog.Builder builder = null;
	// 压力泵对象，用于获取View对象
	private LayoutInflater inflater;
	private XListView mListView1, mListView2, mListView3, mListView4;

	NqltMainAdapter adapter1, adapter2, adapter3, adapter4;
	private int page1 = 1, page2 = 1, page3 = 1, page4 = 1;
	private int dataEnd1 = 0, dataEnd2 = 0, dataEnd3 = 0, dataEnd4 = 0;

	private String pageSize = "8";

	// 右上角弹出菜单按钮
	private ImageView iv_menu, iv_back;
	private PopupMenu popupMenu;

	String userId;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.nqlt_layout_main, container,
				false);
		mInstance = getActivity();
		initView(view);
		initEvents();
		return view;
	};

	private void initView(View view) {

		dialog = new ProgressDialog(mInstance);
		mScreenWidth = ScreenSizeUtil.getScreenWidth(mInstance);
		if (!"NULL".equals(PreferenceUtils.getPrefString(
				AppApplication.getInstance(), "UserId", "NULL"))) {
			userId = PreferenceUtils.getPrefString(
					AppApplication.getInstance(), "UserId", "NULL");
		} else {
			userId = "-1";
		}
		TextView title = (TextView) view.findViewById(R.id.tv_title);
		title.setText("农情论坛");

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
		viewPiger.setOffscreenPageLimit(4);

		LayoutInflater layoutInflater = LayoutInflater.from(mInstance);
		View tab01 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab02 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab03 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);
		View tab04 = layoutInflater.inflate(R.layout.nqinfo_main_tab, null);

		views.add(tab01);
		views.add(tab02);
		views.add(tab03);
		views.add(tab04);

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
				} else {
					initTab04(view);
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

		popupMenu = new PopupMenu(mInstance);
		iv_back = (ImageView) view.findViewById(R.id.iv_back);
		iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
		iv_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupMenu.showLocation(R.id.iv_menu);
				popupMenu
						.setOnItemClickListener(new OnItemClickListenerForPopMenu() {

							public void onClick(MENUITEM item, String str) {
								// TODO Auto-generated method stub
								if ("1".equals(str)) {
									if ("0".equals(PreferenceUtils
											.getPrefString(
													AppApplication
															.getInstance(),
													"UserType", "2").trim())
											|| "1".equals(PreferenceUtils
													.getPrefString(
															AppApplication
																	.getInstance(),
															"UserType", "2")
													.trim())) {
										Intent mIntent = new Intent();
										mIntent.putExtra("select_flag", "1") ; 
										mIntent.setClass(mInstance,
												PublishedActivity.class);
										startActivityForResult(mIntent, 1);
									} else {
										Toast.makeText(
												AppApplication.getInstance(),
												"发表动态需要登陆用户",
												Toast.LENGTH_SHORT).show();
									}

								}
							}
						});

			}
		});

		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.setSelect(4);
			}
		});
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	public void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion * 2;
		// for (int i = 0; i < mNavigation.getChildCount(); i++) {
		View checkView = mNavigation.getChildAt(tab_postion * 2);
		int k = checkView.getMeasuredWidth();
		int l = checkView.getLeft();
		int i2 = l + k / 2 - mScreenWidth / 2;
		mNaviga_scroll.smoothScrollTo(i2, 0);

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

					break;

				case 2:

					break;

				case 3:

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
		mListView1.setPullLoadEnable(true);
		adapter1 = new NqltMainAdapter(mInstance, mListView1);
		mListView1.setAdapter(adapter1);

		getData(String.valueOf(1), String.valueOf(page1), pageSize, 1, userId);
		mListView1.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				dataEnd1 = 0 ; 
				adapter1.ClearData();
				page1 = 1;
				getData(String.valueOf(1), String.valueOf(page1), pageSize, 1,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				if (dataEnd1 != 1) {
					page1 = page1 + 1;
					getData(String.valueOf(1), String.valueOf(page1), pageSize,
							1, userId);
				} else {
					onLoad(1);
					Toast.makeText(AppApplication.getInstance(),
							"数据加载完毕，没有更多内容", Toast.LENGTH_SHORT).show();
				}
			}

		});
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(mInstance, NqltContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqltInfo entitys = (NqltInfo) mListView1
						.getItemAtPosition(arg2);
				intent.putExtra("nqltId", entitys.getId());
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("date", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("zanCount",
						String.valueOf(entitys.getZancount()));
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				// intent.putExtra("username", entitys.getUser().getNick());

				startActivity(intent);

			}

		});

	}

	private void initTab02(View view) {
		mListView2 = (XListView) view.findViewById(R.id.weatherListView);

		mListView2.setPullLoadEnable(true);
		adapter2 = new NqltMainAdapter(mInstance, mListView2);
		mListView2.setAdapter(adapter2);

		getData(String.valueOf(2), String.valueOf(page2), pageSize, 2, userId);
		mListView2.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				dataEnd2 = 0 ; 
				adapter2.ClearData();
				page2 = 1;
				getData(String.valueOf(2), String.valueOf(page2), pageSize, 2,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				if (dataEnd2 != 1) {
					page2 = page2 + 1;
					getData(String.valueOf(2), String.valueOf(page2), pageSize,
							2, userId);
				} else {
					onLoad(2);
					Toast.makeText(AppApplication.getInstance(),
							"数据加载完毕，没有更多内容", Toast.LENGTH_SHORT).show();
				}
			}

		});

		mListView2.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(mInstance, NqltContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqltInfo entitys = (NqltInfo) mListView2
						.getItemAtPosition(arg2);
				intent.putExtra("nqltId", entitys.getId());
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("date", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("zanCount",
						String.valueOf(entitys.getZancount()));
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				startActivity(intent);

			}

		});

	}

	private void initTab03(View view) {
		mListView3 = (XListView) view.findViewById(R.id.weatherListView);
		mListView3.setPullLoadEnable(true);
		adapter3 = new NqltMainAdapter(mInstance, mListView3);
		mListView3.setAdapter(adapter3);

		getData(String.valueOf(5), String.valueOf(page3), pageSize, 3, userId);
		mListView3.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				dataEnd3 = 0 ; 
				adapter3.ClearData();
				page3 = 1;
				getData(String.valueOf(5), String.valueOf(page3), pageSize, 3,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				if (dataEnd3 != 1) {
					page3 = page3 + 1;
					getData(String.valueOf(5), String.valueOf(page3), pageSize,
							3, userId);

				} else {
					onLoad(3);
					Toast.makeText(AppApplication.getInstance(),
							"数据加载完毕，没有更多内容", Toast.LENGTH_SHORT).show();
				}
			}

		});
		mListView3.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(mInstance, NqltContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqltInfo entitys = (NqltInfo) mListView3
						.getItemAtPosition(arg2);
				intent.putExtra("nqltId", entitys.getId());
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("date", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("zanCount",
						String.valueOf(entitys.getZancount()));
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
				startActivity(intent);

			}

		});

	}

	private void initTab04(View view) {
		mListView4 = (XListView) view.findViewById(R.id.weatherListView);
		mListView4.setPullLoadEnable(true);
		adapter4 = new NqltMainAdapter(mInstance, mListView4);
		mListView4.setAdapter(adapter4);

		getData(String.valueOf(6), String.valueOf(page4), pageSize, 4, userId);
		mListView4.setXListViewListener(new ListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				super.onRefresh();
				dataEnd4 = 0 ; 
				adapter4.ClearData();
				page4 = 1;
				getData(String.valueOf(6), String.valueOf(page4), pageSize, 4,
						userId);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				super.onLoadMore();
				if (dataEnd4 != 1) {
					page4 = page4 + 1;
					getData(String.valueOf(6), String.valueOf(page4), pageSize,
							4, userId);
				} else {
					onLoad(4);
					Toast.makeText(AppApplication.getInstance(),
							"数据加载完毕，没有更多内容", Toast.LENGTH_SHORT).show();
				}
			}

		});
		mListView4.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// 为Intent指明目标
				intent.setClass(mInstance, NqltContentActivity.class);
				// 写入要传递的数据
				// 获得选中item项中的NqltInfo对象
				NqltInfo entitys = (NqltInfo) mListView4
						.getItemAtPosition(arg2);
				intent.putExtra("nqltId", entitys.getId());
				intent.putExtra("userId",
						String.valueOf(entitys.getUser().getId()));
				intent.putExtra("title", entitys.getTitle());
				intent.putExtra("date", entitys.getTime());
				intent.putExtra("content", entitys.getContent());
				intent.putExtra("zanCount",
						String.valueOf(entitys.getZancount()));
				intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
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
		MyHttpAPIControl.newInstance().getNqlt(baseParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						// mFailure_view.setVisibility(View.VISIBLE);
						Toast.makeText(mInstance, "获取数据失败！", Toast.LENGTH_SHORT)
								.show();
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
						Type tp = new TypeToken<AISDataBase<NqltInfo>>() {
						}.getType();

						AISDataBase<NqltInfo> ss = (AISDataBase<NqltInfo>) ParseUtils
								.Gson2Object(content, tp);

						if (ss != null && ss.isState()) {

							ArrayList<NqltInfo> naviga = ss.getData();
							if (naviga.size() == 0) {
								switch (key) {
								case 1:
									dataEnd1 = 1;
									break;
								case 2:
									dataEnd2 = 1;
									break;
								case 3:
									dataEnd3 = 1;
									break;
								case 4:
									dataEnd4 = 1;
									break;
								default:
									break;
								}
							} else {
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
								default:
									break;
								}
							}
						} else {
							switch (key) {
							case 1:
								dataEnd1 = 1;
								break;
							case 2:
								dataEnd2 = 1;
								break;
							case 3:
								dataEnd3 = 1;
								break;
							case 4:
								dataEnd4 = 1;
								break;
							default:
								break;
							}
						}
					
						onLoad(key);
						dialog.dismiss();
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

		default:
			break;
		}

	}

	private void setTabTitle() {

		String channels[] = { "病虫防治", "种植技术", "供求信息", "你问我答" };
		int count = channels.length;
		int dp2Px = ScreenSizeUtil.Dp2Px(mInstance, 20);
		int width = (ScreenSizeUtil.getScreenWidth(mInstance) - 12 * 10 - 4
				- dp2Px - 19) / 4;
		mNavigation.removeAllViews();
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					width, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 12;
			params.rightMargin = 12;
			TextView localTextView = new TextView(mInstance);
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
					v.setSelected(true);
					viewPiger.setCurrentItem(v.getId());
				}
			});

			mNavigation.addView(localTextView, params);
			if (i != count - 1) {
				ImageView imageView = new ImageView(mInstance);
				LinearLayout.LayoutParams split = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mNavigation.addView(imageView, split);
			}
		}
	}

}
