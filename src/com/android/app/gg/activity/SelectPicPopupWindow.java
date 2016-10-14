package com.android.app.gg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.adapter.MyGridViewAdapter;

public class SelectPicPopupWindow extends Activity {

	private LinearLayout layout;
	private String[] name = { "QQ", "QQ空间", "新浪微博", "朋友圈", "微信好友" };
	private int[] img = { R.drawable.logo_qq, R.drawable.logo_qzone,
			R.drawable.logo_sinaweibo, R.drawable.logo_wechatmoments,
			R.drawable.logo_wechat };
	private GridView mGridView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);

		layout = (LinearLayout) findViewById(R.id.pop_layout);

		// 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		// 添加按钮监听

		mGridView = (GridView) findViewById(R.id.gridView);

		MyGridViewAdapter mAdapter = new MyGridViewAdapter(
				getApplicationContext(), img, name);

		mGridView.setAdapter(mAdapter);
		mGridView.setNumColumns(img.length);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				setResult(arg2+1);

				Log.i("info", arg2 + "sssss");
				finish();

			}
		});
	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("info", "sssdsss");
		
		
		finish();
		return true;
	}

}
