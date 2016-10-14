package com.android.app.gg.activity;

import com.android.ais.app.R;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMain extends Activity {

	private TextView mTextView = null;

	private ImageView _ImageView = null;

	private String UserName = null;

	private String UserId = null;

	private String UserIcon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		mTextView = (TextView) findViewById(R.id.textView1);
		_ImageView = (ImageView) findViewById(R.id.user_logo);

		try {

			UserName = PreferenceUtils.getPrefString(ActivityMain.this,
					"UserName", null);
			UserId = PreferenceUtils.getPrefString(ActivityMain.this, "UserId",
					null);
			UserIcon = PreferenceUtils.getPrefString(ActivityMain.this,
					"UserIcon", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		mTextView.setText("Í«≥∆£∫" + UserName + "id∫≈:" + UserId + "UserIcon"
				+ UserIcon);
		// ªÒ»°Õ¯¬ÁÕº∆¨

		// Bitmap _Bitmap = Util.getbitmap(UserIcon);

		// _ImageView.setImageBitmap(_Bitmap);
	}
	// String UserPwd = getIntent().getStringExtra("UserPwd");

}
