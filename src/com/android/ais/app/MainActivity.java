package com.android.ais.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.activity.AllCollectNewsActivity;
import com.android.app.activity.MainPageFragment;
import com.android.app.activity.NoticeActivity;
import com.android.app.activity.NqltMainActivity;
import com.android.app.activity.NqzxMainActivity;
import com.android.app.activity.YfxxMainActivity;
import com.android.app.gg.activity.ActivityLogin;
import com.android.app.gg.activity.ActivitySetting;
import com.android.app.util.LocationUtils;
import com.android.app.util.LocationUtils.ILocation;
import com.android.app.util.NetWorkState;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.RoundImageView;
import com.android.app.util.Util;
import com.android.app.view.DragLayout;
import com.android.app.view.DragLayout.DragListener;
import com.baidu.location.LocationClient;
import com.example.ais.MainPageActivity;

public class MainActivity extends FragmentActivity implements ILocation,
		OnClickListener {

	private int currentFragment = 4;

	String locationParam = "", addressParam = "";
	LocationUtils locationUtils;
	private LocationManager gpsManager;
	private LocationClient baduduManager;

	private LinearLayout layoutNqlt = null;
	private LinearLayout layoutNqzx = null;
	private LinearLayout layoutCtpf = null;
	private LinearLayout layoutQxxx = null;

	private ImageButton imgNqlt = null;
	private ImageButton imgNqzx = null;
	private ImageButton imgCtpf = null;
	private ImageButton imgQxxx = null;

	private Fragment mainPageFragment = null;
	private Fragment nqltMainActivity = null;
	private Fragment nqzxMainActivity = null;

	/** 左边侧滑菜单 */
	private DragLayout mDragLayout;
	private ListView menuListView;// 菜单列表
	private ImageButton menuSettingBtn;// 菜单呼出按钮
	private RoundImageView iv_user;
	private TextView tv_user;
	private LinearLayout menu_header;

	private Handler pic_hdl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.activity_main);
		initview();
		initEvents();
		setSelect(4);

	}

	public void initview() {
		gpsManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		baduduManager = new LocationClient(this);
		locationUtils = new LocationUtils(MainActivity.this, gpsManager,
				baduduManager);
		locationUtils.startLocation();
		locationUtils.setLocationHelper(this);

		layoutNqlt = (LinearLayout) findViewById(R.id.id_tab_nqlt);
		layoutNqzx = (LinearLayout) findViewById(R.id.id_tab_nqzx);
		layoutCtpf = (LinearLayout) findViewById(R.id.id_tab_ctpf);
		layoutQxxx = (LinearLayout) findViewById(R.id.id_tab_qxxx);

		menu_header = (LinearLayout) findViewById(R.id.menu_header);

		imgNqlt = (ImageButton) findViewById(R.id.id_img_nqlt);
		imgNqzx = (ImageButton) findViewById(R.id.id_img_nqzx);
		imgCtpf = (ImageButton) findViewById(R.id.id_img_ctpf);
		imgQxxx = (ImageButton) findViewById(R.id.id_img_qxxx);

		/**
		 * 如果需要在别的Activity界面中也实现侧滑菜单效果，需要在布局中引入DragLayout（同本Activity方式），
		 * 然后在onCreate中声明使用; Activity界面部分，需要包裹在MyRelativeLayout中.
		 */
		mDragLayout = (DragLayout) findViewById(R.id.dl);
		mDragLayout.setDragListener(new DragListener() {// 动作监听
					@Override
					public void onOpen() {
					}

					@Override
					public void onClose() {
					}

					@Override
					public void onDrag(float percent) {

					}
				});

		// 生成测试菜单选项
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		String[] itemStr = { "我的消息", "我的收藏" };
		for (int i = 0; i < itemStr.length; i++) {
			Map<String, Object> item;
			item = new HashMap<String, Object>();
			item.put("item", itemStr[i]);
			data.add(item);
		}

		iv_user = (RoundImageView) findViewById(R.id.menu_user_ico);
		tv_user = (TextView) findViewById(R.id.menu_user_name);
		iv_user.setImageResource(R.drawable.default_icon);

		menuListView = (ListView) findViewById(R.id.menu_listview);
		menuListView.setAdapter(new SimpleAdapter(this, data,
				R.layout.menulist_item_text, new String[] { "item" },
				new int[] { R.id.menu_text }));
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					if ("0".equals(PreferenceUtils.getPrefString(
							MainActivity.this, "UserType", "2").trim())
							|| "1".equals(PreferenceUtils.getPrefString(
									MainActivity.this, "UserType", "2").trim())) {
						Intent mIntent = new Intent();
						mIntent.setClass(MainActivity.this,
								NoticeActivity.class);
						startActivity(mIntent);
					} else {
						Toast.makeText(MainActivity.this, "请先登录",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 1:
					if ("0".equals(PreferenceUtils.getPrefString(
							MainActivity.this, "UserType", "2").trim())
							|| "1".equals(PreferenceUtils.getPrefString(
									MainActivity.this, "UserType", "2").trim())) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,
								AllCollectNewsActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this, "请先登录",
								Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
				}
			}

		});

		// 添加监听，可点击呼出菜单
		menuSettingBtn = (ImageButton) findViewById(R.id.menu_imgbtn);
		menuSettingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDragLayout.open();
				if (!"2".equals(PreferenceUtils.getPrefString(
						MainActivity.this, "UserType", "2"))) {
					if (NetWorkState.isNetworkAvailable(AppApplication
							.getInstance())) {
						pic_hdl = new PicHandler();
						new LoadPicThread().start();
					}
				} else {
					tv_user.setText(PreferenceUtils.getPrefString(
							MainActivity.this, "UserName", "点击登陆").trim());
					iv_user.setImageResource(R.drawable.default_icon);
				}
			}
		});

	}

	private void initEvents() {
		layoutNqlt.setOnClickListener(this);
		layoutNqzx.setOnClickListener(this);
		layoutCtpf.setOnClickListener(this);
		layoutQxxx.setOnClickListener(this);
		menu_header.setOnClickListener(this);
		
		layoutCtpf.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					imgCtpf.setImageResource(R.drawable.tab_ctpf_pressed);
				}
				return false;
			}
		});
		layoutQxxx.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				imgQxxx.setImageResource(R.drawable.tab_qxxx_pressed);
				return false;
			}
		});
	}

	@Override
	public String getLocation(String[] location) {
		// TODO Auto-generated method stub
		this.locationParam = location[0];
		this.addressParam = location[1];

		if (!"nullnull".equals(location[2].trim())) {
			System.out.println("***************************user_loc： "
					+ location[2]);
			PreferenceUtils.setPrefString(MainActivity.this, "USER_LOC",
					location[2]);
		} else {
			PreferenceUtils
					.setPrefString(MainActivity.this, "USER_LOC", "NONE");
		}
		System.out.println("*******************参数：" + locationParam);
		if (!"".equals(locationParam) && !"".equals(addressParam)) {
			locationUtils.stopAmap();
			locationUtils.stopBaidu();
			locationUtils.stopGps();
		}

		return "SUCCESS";
	}

	@Override
	public void onClick(View view) {
		resetImg();
		switch (view.getId()) {
		case R.id.id_tab_nqlt:
			currentFragment = 1;
			setSelect(0);

			break;
		case R.id.id_tab_nqzx:
			NqzxMainActivity.currentPosition = 0;
			currentFragment = 2;
			setSelect(1);
			break;
		case R.id.id_tab_ctpf:
			setSelect(2);
			break;
		case R.id.id_tab_qxxx:
			setSelect(3);
			break;
		case R.id.menu_header:
			if (!"2".equals(PreferenceUtils.getPrefString(MainActivity.this,
					"UserType", "2").trim())) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActivitySetting.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActivityLogin.class);
				startActivity(intent);
			}
		default:
			break;
		}

	}

	public void setSelect(int i) {
		currentFragment = i;
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction, i);
		switch (i) {
		case 0:
			// || !(nqltMainActivity instanceof MainPageFragment)
			if (nqltMainActivity == null) {
				nqltMainActivity = new NqltMainActivity();
				transaction.add(R.id.id_frame_main, nqltMainActivity);

			} else {
				transaction.show(nqltMainActivity);
			}
			imgNqlt.setImageResource(R.drawable.tab_nqlt_pressed);
			break;
		case 1:
			// !(nqzxMainActivity instanceof MainPageFragment)
			if (nqzxMainActivity == null||NqzxMainActivity.currentPosition!=-1) {
				nqzxMainActivity = new NqzxMainActivity();
				transaction.add(R.id.id_frame_main, nqzxMainActivity);
			} else {
				transaction.show(nqzxMainActivity);
			}
			imgNqzx.setImageResource(R.drawable.tab_nqzx_pressed);
			break;
		case 2:
			Intent mIntent = new Intent();
			mIntent.setClass(MainActivity.this, YfxxMainActivity.class);
			mIntent.putExtra("location", locationParam);
			mIntent.putExtra("loc", addressParam);
			startActivity(mIntent);
			//imgCtpf.setImageResource(R.drawable.tab_ctpf_pressed);
			break;
		case 3:
			Intent intent = new Intent();
			intent.setClass(this, MainPageActivity.class);
			startActivity(intent);
			//imgQxxx.setImageResource(R.drawable.tab_qxxx_pressed);
			break;
		case 4:
			if (mainPageFragment == null) {
				mainPageFragment = new MainPageFragment();
				transaction.add(R.id.id_frame_main, mainPageFragment);
			} else {
				transaction.show(mainPageFragment);
			}
			resetImg();
			break;
		default:
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction, int i) {
		if (i == 2 || i == 3)
			return;
		if (nqltMainActivity != null)
			transaction.hide(nqltMainActivity);

		if (nqzxMainActivity != null)
			transaction.hide(nqzxMainActivity);

		if (mainPageFragment != null)
			transaction.hide(mainPageFragment);

	}

	private void resetImg() {
		imgNqlt.setImageResource(R.drawable.tab_nqlt_normal);
		imgNqzx.setImageResource(R.drawable.tab_nqzx_normal);
		imgCtpf.setImageResource(R.drawable.tab_ctpf_normal);
		imgQxxx.setImageResource(R.drawable.tab_qxxx_normal);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (currentFragment == 4) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("提示");
			builder.setMessage("确认退出?");
			builder.setPositiveButton("确  认",
					new AlertDialog.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			builder.setNegativeButton("取  消",
					new AlertDialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.show();
		} else {
			//currentFragment = 0;
			setSelect(4);
		}

		return super.onKeyUp(keyCode, event);
	}

	class LoadPicThread extends Thread {

		@Override
		public void run() {

			String iconUrl = PreferenceUtils.getPrefString(MainActivity.this,
					"UserIcon", Constants.ICON_DEFAULT_PATH).trim();
			Bitmap img = null;
			Message msg = pic_hdl.obtainMessage();
			if (!Constants.ICON_DEFAULT_PATH.trim().equals(iconUrl)) {
				img = Util.getbitmap(iconUrl);
				msg.what = 1;
				msg.obj = img;
			} else {
				msg.what = 0;
			}
			pic_hdl.sendMessage(msg);
		}

	}

	class PicHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1 &&   null!=msg.obj) {
				Bitmap myimg = (Bitmap) msg.obj;
				iv_user.setImageBitmap(myimg);
			} else {
				iv_user.setImageResource(R.drawable.default_icon);
			}
			

		}

	}

	// 解决偶尔出现的fragment重叠问题
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		// super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (NetWorkState.isNetworkAvailable(AppApplication.getInstance())) {

			if (!"2".equals(PreferenceUtils.getPrefString(MainActivity.this,
					"UserType", "2"))) {
				pic_hdl = new PicHandler();
				new LoadPicThread().start();
			} else {
				tv_user.setText(PreferenceUtils.getPrefString(
						MainActivity.this, "UserName", "点击登陆").trim());
				iv_user.setImageResource(R.drawable.default_icon);
			}

		} else {
			Toast.makeText(AppApplication.getInstance(), "您没有开启网络连接",
					Toast.LENGTH_SHORT).show();
		}
		
		tv_user.setText(PreferenceUtils.getPrefString(
				MainActivity.this, "UserName", "点击登陆").trim());
	
	}
	
	
}
