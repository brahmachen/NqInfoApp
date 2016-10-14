package com.android.app.gg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ais.app.Constants;
import com.android.ais.app.R;
import com.android.app.util.AsyncImageLoader;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.Util;

public class ActivitySetting extends Activity implements OnClickListener {

	private RelativeLayout exit, change_pwd, nick, photo;

	public static ActivitySetting instance;

	private TextView nickname = null;

	private ImageView icon;
	private Handler pic_hdl;
	AsyncImageLoader asyncImageLoader = new AsyncImageLoader(52, 52);

	private AlertDialog dialog = null;

	private String type = "2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_setting);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		instance = this;

		type = PreferenceUtils.getPrefString(ActivitySetting.this, "UserType",
				"2").trim();

		exit = (RelativeLayout) findViewById(R.id.exit);
		change_pwd = (RelativeLayout) findViewById(R.id.change_pwd);

		nickname = (TextView) findViewById(R.id.usernick);

		nickname.setText(PreferenceUtils.getPrefString(ActivitySetting.this,
				"UserName", "点击登陆"));
		icon = (ImageView) findViewById(R.id.face);
		nick = (RelativeLayout) findViewById(R.id.nickchange);
		photo = (RelativeLayout) findViewById(R.id.changephoto);

		change_pwd.setOnClickListener(this);
		nick.setOnClickListener(this);
		photo.setOnClickListener(this);

		exit.setOnClickListener(this);
		pic_hdl = new PicHandler();

		new LoadPicThread().start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit:

			if (type.equals("2")) {
				showMsg("您还未登录");
			} else {
				showDialog();
			}
			break;

		case R.id.change_pwd:

			if (type.equals("2")) {

				showMsg("您尚未登录!");
			} else if (type.equals("0")) {

				showMsg("第三方用户无需修改密码!");
			} else {
				startActivity(new Intent(ActivitySetting.this,
						ActivityChangePwd.class));
			}
			break;

		case R.id.nickchange:

			if (type.equals("2")) {
				showMsg("您尚未登录!");
			} else if (type.equals("0")) {

				showMsg("第三方用户无需修改昵称!");
			} else {
				Intent mIntent = new Intent(ActivitySetting.this,
						ActivityChangeNick.class);
				startActivityForResult(mIntent, 4);
			}
			break;
		case R.id.changephoto:
			if (type.equals("2")) {
				showMsg("您尚未登录!");
			} else if (type.equals("0")) {

				showMsg("第三方用户无需修改头像!");
			} else {
				Intent _Intent = new Intent(ActivitySetting.this,
						ActivityChangeIcon.class);
				startActivityForResult(_Intent, 0);
			}
			break;
		}
	}

	private void showDialog() {

		AlertDialog.Builder mBuilder = new Builder(ActivitySetting.this);

		mBuilder.setMessage("确认退出吗？");
		mBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();

				PreferenceUtils.setPrefString(ActivitySetting.this, "UserId",
						"NONE");
				PreferenceUtils.setPrefString(ActivitySetting.this, "UserName",
						"点击登陆");
				PreferenceUtils.setPrefString(ActivitySetting.this, "UserIcon",
						"NONE");

				PreferenceUtils.setPrefString(ActivitySetting.this, "UserType",
						"2");

				finish();
			}
		});

		mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});

		dialog = mBuilder.create();
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 4 && resultCode == 5) {

			String NewName = data.getStringExtra("UserName");
			PreferenceUtils.setPrefString(ActivitySetting.this, "UserName",
					NewName);

			nickname.setText(NewName);
			return;
		}
		if (requestCode == 0 && resultCode == 6) {

			new LoadPicThread().start();
		}

	}

	// 返回出发事件
	public void setback(View v) {
		this.finish();
	}

	class LoadPicThread extends Thread {

		@Override
		public void run() {

			String iconUrl = PreferenceUtils.getPrefString(
					ActivitySetting.this, "UserIcon",
					Constants.ICON_DEFAULT_PATH).trim();
			Bitmap img = Util.getbitmap(iconUrl);

			Log.i("info", iconUrl);

			Message msg = pic_hdl.obtainMessage();

			msg.what = 0;

			msg.obj = img;

			pic_hdl.sendMessage(msg);

		}
	}

	class PicHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			// TODO Auto-generated method stub

			// String s = (String)msg.obj;

			// ptv.setText(s);

			Bitmap myimg = (Bitmap) msg.obj;
			icon.setImageBitmap(myimg);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showMsg(String msg) {

		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
