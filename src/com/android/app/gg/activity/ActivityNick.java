package com.android.app.gg.activity;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.ais.app.R;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.AisLtComment;
import com.android.app.entity.Security;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityNick extends Activity {

	private EditText nick = null;

	private Button mButton = null;
	private ProgressDialog mDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_findpwd_nick);
		nick = (EditText) findViewById(R.id.user_name);

		mButton = (Button) findViewById(R.id.find_nick);

		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (TextUtils.isEmpty(nick.getText().toString().trim())) {
					showMsg("«Î ‰»ÎÍ«≥∆!");
					return;
				}

				mDialog = new ProgressDialog(ActivityNick.this);
				mDialog.setTitle("◊¢≤·");
				mDialog.setMessage("’˝‘⁄≤È—Ø£¨«Î…‘∫Û...");
				findNick();

			}

		});

	}

	private void findNick() {
		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("name", nick.getText().toString().trim());

		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().queryQuestion(_Params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						mDialog.show();

					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, content);
						mDialog.dismiss();
						Type tp = new TypeToken<AISDataBase<Security>>() {
						}.getType();
						AISDataBase<Security> ss = (AISDataBase<Security>) ParseUtils
								.Gson2Object(content, tp);
						if (!ss.isState()) {
							showMsg(ss.getMessage());
						} else {

							Intent mIntent = new Intent(ActivityNick.this,
									ActivityFindPwd.class);
							mIntent.putExtra("questions", ss.getData());

							startActivity(mIntent);
							finish();
							Log.i("info", ss.getData().get(0).getQuestion());
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						mDialog.dismiss();
						showMsg("Õ¯¬Á“Ï≥££¨«Î…‘∫Û÷ÿ ‘!");
					}
				});
	}

	public void back(View v) {
		this.finish();
	}

	public void showMsg(String msg) {

		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
