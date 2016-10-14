package com.android.app.gg.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import com.android.ais.app.R;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.MyPlatformActionListener;
import com.android.app.util.PreferenceUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityLogin extends Activity implements OnClickListener {
	public static ActivityLogin instance = null;
	private ImageView sina, qq;

	private Button mButton = null;

	private Button forgetButton = null;

	private Button mLogin = null;
	// private PopupWindow popup;

	private RelativeLayout loginLayout;

	private EditText name;
	private EditText pwd;

	private ProgressDialog mDialog;

	MyPlatformActionListener _Listener = new MyPlatformActionListener();

	// private Button findPwdByQuest, findPwdByPhone, findPwdById, popup_cance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ShareSDK.initSDK(this);
		setContentView(R.layout.layout_login);
		instance = this;

		sina = (ImageView) findViewById(R.id.sina);

		qq = (ImageView) findViewById(R.id.qq);

		name = (EditText) findViewById(R.id.login_user_edit);
		pwd = (EditText) findViewById(R.id.login_passwd_edit);
		mButton = (Button) findViewById(R.id.register_new);

		forgetButton = (Button) findViewById(R.id.forget_passwd);

		mLogin = (Button) findViewById(R.id.login_login_btn);

		mButton.setOnClickListener(this);
		sina.setOnClickListener(this);
		qq.setOnClickListener(this);

		forgetButton.setOnClickListener(this);
		mLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_login_btn:

			String userEditStr = name.getText().toString().trim();
			String passwdEditStr = pwd.getText().toString().trim();

			if (("".equals(userEditStr) || null == userEditStr)
					|| ("".equals(passwdEditStr) || null == passwdEditStr)) {// ֻҪ�û�����������һ��Ϊ��

				showError(ActivityLogin.this, "�û������˺������������������!");
				return;
			}

			mDialog = new ProgressDialog(ActivityLogin.this);
			mDialog.setTitle("��½");
			mDialog.setMessage("���ڵ�½�����������Ժ�...");

			checkUserLogin(userEditStr, passwdEditStr);

			// LoginThread _Thread = new LoginThread(handler, userEditStr,
			// passwdEditStr);

			// _Thread.start();

			break;

		case R.id.register_new:
			startActivity(new Intent(this, ActivityRegister.class));

			break;
		case R.id.sina:
			Platform sinawWeibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);

			sinawWeibo.setPlatformActionListener(_Listener);
			// sinawWeibo.SSOSetting(false);
			sinawWeibo.authorize();
			this.finish();
			break;

		case R.id.qq:

			Platform TencentQq = ShareSDK.getPlatform(QQ.NAME);

			TencentQq.setPlatformActionListener(_Listener);

			TencentQq.authorize();

			// _Dialog = ProgressDialog.show(this, null, "���ڻ�ȡ��Ϣ�����Ժ�...");

			Toast.makeText(ActivityLogin.this, "���ڻ�ȡȨ����Ϣ�����Ժ�...",
					Toast.LENGTH_SHORT).show();
			finish();
			break;

		case R.id.forget_passwd: // ��������������롱��ִ���һ��������

			Intent mIntent = new Intent(ActivityLogin.this, ActivityNick.class);
			startActivity(mIntent);

			break;

		}
	}

	private void checkUserLogin(final String nick, String pwd) {
		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("nick", nick);
		_Params.add("pwd", pwd);
		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().checkUser(_Params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						mDialog.show();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						mDialog.cancel();
						showMsg("�����쳣�����Ժ�����!");
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						mDialog.cancel();
						System.out.println("****************" + content);

						try {

							JSONObject _Object = new JSONObject(content);
							String state = _Object.getString("state");
							String message = _Object.getString("message");
							if (state.equals("true")) {

								String[] param = message.split(",");

								PreferenceUtils.setPrefString(
										ActivityLogin.this, "UserName", nick);
								PreferenceUtils.setPrefString(
										ActivityLogin.this, "UserId",
										param[0].trim());
								PreferenceUtils.setPrefString(
										ActivityLogin.this, "UserIcon",
										param[1]);
								PreferenceUtils.setPrefString(
										ActivityLogin.this, "UserType",
										param[2]);
								PreferenceUtils.setPrefString(
										ActivityLogin.this, "UserType", "1");

								finish();
							} else {
								showMsg(message);
							}

						} catch (Exception e) {

							System.out.println(e.toString());
						}

					}

				});
	}

	private void showError(Context mContext, String string) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(mContext)
				.setIcon(
						getResources().getDrawable(R.drawable.login_error_icon))
				.setTitle("��¼ʧ��").setMessage(string).create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.cancel();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void showMsg(String msg) {

		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
