package com.android.app.gg.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityChangeNick extends Activity {

	private EditText NewName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_nick_change);
		NewName = (EditText) findViewById(R.id.user_nick_change);
	}

	// 返回出发事件
	public void backButton(View v) {
		this.finish();
	}

	// 保存出发事件
	public void saveButton(View v) {

		String id = PreferenceUtils.getPrefString(this, "UserId", "NONE")
				.trim();
		final String newName = NewName.getText().toString().trim();

		if (TextUtils.isEmpty(newName)) {
			showMsg("新昵称不能为空!");
			return;
		}

		String name = PreferenceUtils.getPrefString(this, "UserName", "NONE");

		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("id", id);

		_Params.add("nick", name);

		_Params.add("newNick", newName);

		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().changeNick(_Params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();

						showMsg("正在保存，请稍后。。。");
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);

						showMsg("网络异常，请稍后重试!");
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);

						System.out.println("****************" + content);

						try {

							JSONObject _Object = new JSONObject(content);

							String state = _Object.getString("state");

							if (state.equals("true")) {

								PreferenceUtils.setPrefString(
										ActivityChangeNick.this, "UserName",
										newName);

								Intent mIntent = new Intent();
								mIntent.putExtra("UserName", newName);
								setResult(5, mIntent);
								finish();

							} else {

								showMsg("保存失败，请稍后重试..");

							}

						} catch (Exception e) {

							System.out.println(e.toString());
						}

					}

				});

	}

	public void showMsg(String msg) {

		Toast.makeText(ActivityChangeNick.this, msg, Toast.LENGTH_SHORT).show();
	}
}
