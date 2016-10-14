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
				// ��������¼�ɹ�
				// ���û������ݵ���̨
				// Toast.makeText(Activityface.instance, "��¼�ɹ���",
				// Toast.LENGTH_SHORT).show();
				Toast.makeText(AppApplication.getInstance(), "��¼�ɹ���",
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
				Toast.makeText(AppApplication.getInstance(), "ע��ʧ�ܣ����Ժ�����",
						Toast.LENGTH_SHORT).show();
				break;

			case 4:
				Toast.makeText(AppApplication.getInstance(), "�����쳣�����Ժ�����",
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
		Log.i("info", "��¼�ɹ�!");
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
			 * // �ɹ� Platform platform = (Platform) msg.obj;
			 * 
			 * String _name = platform.getDb().getUserName(); String _id =
			 * platform.getDb().getUserId(); String icon =
			 * platform.getDb().getUserIcon();
			 * 
			 * _mUser = null;
			 * 
			 * _mUser = new User(_name, _id, icon);
			 * 
			 * Log.i("info", "-----------------�ɹ�!");
			 * 
			 * // ����У�����ݿ����Ƿ��и��û� // CheckUser _User = new CheckUser(handler,
			 * _mUser);
			 * 
			 * // _User.start();
			 */
			/*
			 * ����name��id�ж����ݿ����Ƿ��Ѿ����и�name��id�������򽫸�name��id��icon�������ݿ��У�
			 * ����ֱ�Ӹ���name��id��ת��
			 */
			break;
		}
		return false;
	}

}
