package com.android.app.util;

import java.util.HashMap;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import com.android.ais.app.AppApplication;
import com.android.ais.app.Constants;
import com.android.app.entity.User;
import com.android.app.gg.thread.CheckUser;

public class MyPlatformActionListener implements PlatformActionListener,
		Callback {
	private static final int MSG_ACTION_CCALLBACK = 2;

	private User _mUser = null;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				// 第三方登录成功
				// 将用户名传递到后台
				// Toast.makeText(Activityface.instance, "登录成功！",
				// Toast.LENGTH_SHORT).show();
				Toast.makeText(AppApplication.getInstance(), "登录成功！",
						Toast.LENGTH_SHORT).show();
				PreferenceUtils.setPrefString(AppApplication.getInstance(),
						"UserName", _mUser.getNickName());

				PreferenceUtils.setPrefString(AppApplication.getInstance(),
						"UserId", (String) msg.obj);
				PreferenceUtils.setPrefString(AppApplication.getInstance(),
						"UserIcon", _mUser.getIcon());
				PreferenceUtils.setPrefString(AppApplication.getInstance(),"UserType", "0");
				break;
			case 3:
				Toast.makeText(AppApplication.getInstance(), "注册失败，请稍后重试",
						Toast.LENGTH_SHORT).show();
				break;

			case 4:
				Toast.makeText(AppApplication.getInstance(), "网络异常，请稍后重试",
						Toast.LENGTH_SHORT).show();
				break;

			}
		};

	};

	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		String _name = platform.getDb().getUserName();
		String _id = platform.getDb().getUserId();
		String _icon = platform.getDb().getUserIcon() == null ? Constants.ICON_DEFAULT_PATH
				: platform.getDb().getUserIcon();
		_mUser = new User(_name, _id, _icon);
		CheckUser _User = new CheckUser(mHandler, _mUser);
		_User.start();
		// UIHandler.sendMessage(msg, this);
		Log.i("info", "登录成功!");
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub

		switch (msg.arg1) {
		case 3:

			/*
			 * // 成功 Platform platform = (Platform) msg.obj;
			 * 
			 * String _name = platform.getDb().getUserName(); String _id =
			 * platform.getDb().getUserId(); String icon =
			 * platform.getDb().getUserIcon();
			 * 
			 * _mUser = null;
			 * 
			 * _mUser = new User(_name, _id, icon);
			 * 
			 * Log.i("info", "-----------------成功!");
			 * 
			 * // 联网校验数据库中是否含有该用户 // CheckUser _User = new CheckUser(handler,
			 * _mUser);
			 * 
			 * // _User.start();
			 */
			/*
			 * 根据name和id判断数据库中是否已经含有该name和id，若无则将该name，id，icon存入数据库中；
			 * 否则，直接根据name或id跳转；
			 */
			break;
		}
		return false;
	}

}
