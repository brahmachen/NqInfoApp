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
	private String[] name = { "QQ", "QQ�ռ�", "����΢��", "����Ȧ", "΢�ź���" };
	private int[] img = { R.drawable.logo_qq, R.drawable.logo_qzone,
			R.drawable.logo_sinaweibo, R.drawable.logo_wechatmoments,
			R.drawable.logo_wechat };
	private GridView mGridView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);

		layout = (LinearLayout) findViewById(R.id.pop_layout);

		// ���ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
		layout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�",
						Toast.LENGTH_SHORT).show();
			}
		});
		// ��Ӱ�ť����

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

	// ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("info", "sssdsss");
		
		
		finish();
		return true;
	}

}
