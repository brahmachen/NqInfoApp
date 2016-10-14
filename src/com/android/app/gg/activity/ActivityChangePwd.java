package com.android.app.gg.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityChangePwd extends Activity {

	private EditText pwdbefor, pwdNew, pwdConform;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_pwd_change);

		pwdbefor = (EditText) findViewById(R.id.pwd_bafore);

		pwdNew = (EditText) findViewById(R.id.pwd_new);

		pwdConform = (EditText) findViewById(R.id.pwd_confirm);

	}

	public void backButton(View v) {

		this.finish();
	}

	public void savebutton(View v) {

		String before = pwdbefor.getText().toString().trim();
		String newPwd = pwdNew.getText().toString().trim();

		String conform = pwdConform.getText().toString().trim();

		if (TextUtils.isEmpty(before) || TextUtils.isEmpty(newPwd)
				|| TextUtils.isEmpty(conform)) {
			showMsg("���벻��Ϊ��!");
			return;
		}
		if (before.equals(newPwd)) {
			showMsg("����һ��,�����޸�");
			return;
		}
		if (!newPwd.equals(conform)) {
			showMsg("���벻һ��!");

			return;
		}

		String UserId = PreferenceUtils.getPrefString(ActivityChangePwd.this,
				"UserId", "NONE").trim();

		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("id", UserId);
		_Params.add("pwdbefore", before);
		_Params.add("pwd", newPwd);

		MyHttpAPIControl.newInstance().changePwd(_Params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						showMsg("�����޸ģ����Ժ󡣡�");
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);

						showMsg("�����쳣�����Ժ�����!");
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
						try {
							JSONObject _Object = new JSONObject(content);
							String message = _Object.getString("message");
							showMsg(message);

							if (message.equals("�޸ĳɹ�!")) {
								finish();
							}
						} catch (Exception e) {
						}

					}

				});

	}

	public void showMsg(String msg) {

		Toast.makeText(ActivityChangePwd.this, msg, Toast.LENGTH_SHORT).show();
	}
}
