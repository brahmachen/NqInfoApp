package com.android.app.util;

import java.util.HashMap;

import android.os.Handler.Callback;
import android.os.Message;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.android.ais.app.AppApplication;
import com.mob.tools.utils.UIHandler;

public class MyPlatformShareListener implements PlatformActionListener,
		Callback {
	private static final int MSG_ACTION_CCALLBACK = 2;

	@Override
	public void onCancel(Platform platform, int action) {

		// 取消
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> arg2) {
		// 成功
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {

		// 失敗
		// 打印错误信息,print the error msg
		t.printStackTrace();
		// 错误监听,handle the error msg
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {

		switch (msg.arg1) {
		case 1:

			Platform plat = (Platform) msg.obj;

			if (plat.getDb().getPlatformNname().equals(SinaWeibo.NAME)) {

				plat.removeAccount(true);
			}
			// 成功
			showMsg("回调成功");

			break;
		case 2:
			// 失败
			showMsg("分享失败");

			break;
		case 3:
			// 取消
			showMsg("分享取消");

			break;

		}

		return false;

	}

	public void showMsg(String msg) {

		Toast.makeText(AppApplication.getInstance(), msg, Toast.LENGTH_SHORT)
				.show();
	}
}
