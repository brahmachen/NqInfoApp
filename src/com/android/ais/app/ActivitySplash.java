package com.android.ais.app;

import java.util.ArrayList;

import com.android.app.util.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Window;

public class ActivitySplash extends Activity {

	private static final int GO_GUID = 100;
	private static final int GO_LOGIN = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.layout_welcome);
		// µÚÒ»´ÎµÇÂ¼
		if (isFirst()) {
			mHandler.sendEmptyMessageDelayed(GO_GUID, 1000);

		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
		}
	}

	public boolean isFirst() {
		return PreferenceUtils.getPrefBoolean(ActivitySplash.this, "isFirst",
				true);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_GUID:
				startActivity(new Intent(ActivitySplash.this,
						WelcomActivity.class));
				PreferenceUtils.setPrefBoolean(ActivitySplash.this, "isFirst",
						false);
				finish();
				break;
			case GO_LOGIN:
				startActivity(new Intent(ActivitySplash.this,
						MainActivity.class));
				finish();
				break;
			}
		}
	};

}
