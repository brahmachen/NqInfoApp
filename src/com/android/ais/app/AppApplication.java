package com.android.ais.app;

import android.app.Application;

public class AppApplication extends Application {

	private static AppApplication mApp;

	public static AppApplication getInstance() {
		return mApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public AppApplication() {
		mApp = this;
	}

}
