package com.android.app.gg.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.univs.app.widget.AbstractSpinerAdapter.IOnItemSelectListener;
import cn.univs.app.widget.SpinerPopWindow;

import com.android.ais.app.R;
import com.android.app.entity.Security;
import com.android.app.util.MyHttpAPIControl;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityFindPwd extends Activity implements OnClickListener,
		IOnItemSelectListener {

	private EditText answer;

	private EditText pwd;
	private EditText repwd;

	private Button mButton;
	private Button mButton2;
	private Button mButton3;
	private LinearLayout _Layout = null;
	private LinearLayout _Layout1 = null;
	private LinearLayout _Layout2 = null;

	private RelativeLayout queLayout = null;

	private ProgressDialog mDialog = null;

	private List<String> nameList = new ArrayList<String>();

	private SpinerPopWindow mSpinerPopWindow;

	private TextView mTView;

	private List<Security> mList = new ArrayList<Security>();

	private int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_findpwd_byquset);

		init();
	}

	// 初始化
	@SuppressWarnings("unchecked")
	private void init() {

		// Toast.makeText(ActivityFindPwd.this, user_name,
		// Toast.LENGTH_SHORT).show();

		answer = (EditText) findViewById(R.id.pwd_question);

		pwd = (EditText) findViewById(R.id.new_pwd);
		repwd = (EditText) findViewById(R.id.new_pwd_re);

		mButton = (Button) findViewById(R.id.find_pwd_back);
		mButton2 = (Button) findViewById(R.id.btn_back_find);
		mButton3 = (Button) findViewById(R.id.find_pwd_submit);
		mButton3.setVisibility(View.GONE);
		_Layout = (LinearLayout) findViewById(R.id.validate_success);
		_Layout1 = (LinearLayout) findViewById(R.id.validate_hiden1);

		_Layout.setVisibility(View.GONE);

		mTView = (TextView) findViewById(R.id.tv_value);
		queLayout = (RelativeLayout) findViewById(R.id.question_input);

		queLayout.setOnClickListener(this);

		mList = (List<Security>) getIntent().getSerializableExtra("questions");

		// String[] names = getResources().getStringArray(R.array.hero_name);
		for (int i = 0; i < mList.size(); i++) {
			nameList.add(mList.get(i).getQuestion());
		}
		mSpinerPopWindow = new SpinerPopWindow(this);

		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
		mButton.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		mButton3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_pwd_back:
			String ans = answer.getText().toString().trim();
			if (mTView.getText().toString().trim().length() == 0) {
				Toast.makeText(this, "您未选择问题!", Toast.LENGTH_SHORT).show();
				return;
			}
			if (validate(ans)) {

				// String question = mTView.getText().toString();

				if (mList.get(flag).getAnswer().equals(ans)) {
					_Layout.setVisibility(View.VISIBLE);
					mButton3.setVisibility(View.VISIBLE);
					_Layout1.setVisibility(View.GONE);

					mButton.setVisibility(View.GONE);
				} else {
					showMsg("回答错误");
				}

			}
			/**
			 * 请求服务器验证是否正确， 如果密保正确，重置密码，否则不执行
			 */

			// SecurityConfirm(user_name, ans, question);
			//
			//
			break;

		case R.id.btn_back_find:
			ActivityFindPwd.this.finish();
			break;
		case R.id.find_pwd_submit:

			String password = pwd.getText().toString().trim();
			String repassword = repwd.getText().toString().trim();
			if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {

				Toast.makeText(ActivityFindPwd.this, "密码不为空!",
						Toast.LENGTH_SHORT).show();
			} else {

				if (!password.equals(repassword)) {

					Toast.makeText(ActivityFindPwd.this, "密码不一致!",
							Toast.LENGTH_SHORT).show();
				} else {

					/**
					 * 实现重置密码代码
					 */
					mDialog = new ProgressDialog(ActivityFindPwd.this);
					mDialog.setTitle("修改密码");
					mDialog.setMessage("正在修改，请稍后...");
					findPwd(mList.get(flag).getNick_id().trim(), password);

				}

			}

			break;
		case R.id.question_input:
			showSpinWindow();
			break;
		}
	}

	// private void SecurityConfirm(String name, String ans, String question) {
	// // TODO Auto-generated method stub
	//
	// RequestParams _Params = MyHttpAPIControl.getBaseParams();
	//
	// _Params.add("name", name);
	// _Params.add("answer", ans);
	// _Params.add("question", question);
	//
	// Log.i("info", _Params.toString());
	// MyHttpAPIControl.newInstance().SecurityConform(_Params,
	// new AsyncHttpResponseHandler() {
	// @Override
	// public void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	//
	// mDialog.show();
	// }
	//
	// @Override
	// public void onFailure(Throwable error, String content) {
	// // TODO Auto-generated method stub
	// super.onFailure(error, content);
	//
	// mDialog.dismiss();
	// showMsg("网络异常，请稍后重试!");
	// }
	//
	// @Override
	// public void onSuccess(String content) {
	// // TODO Auto-generated method stub
	// super.onSuccess(content);
	//
	// mDialog.dismiss();
	// System.out.println("****************" + content);
	//
	// try {
	//
	// JSONObject _Object = new JSONObject(content);
	//
	// String state = _Object.getString("state");
	//
	// if (state.equals("true")) {
	//
	// _Layout.setVisibility(View.VISIBLE);
	// mButton3.setVisibility(View.VISIBLE);
	//
	// _Layout1.setVisibility(View.GONE);
	//
	// mButton.setVisibility(View.GONE);
	// } else {
	// showMsg(_Object.getString("message"));
	// }
	//
	// } catch (Exception e) {
	//
	// System.out.println(e.toString());
	// }
	//
	// }
	//
	// });
	//
	// }

	private void showSpinWindow() {
		Log.e("", "showSpinWindow");

		mSpinerPopWindow.setWidth(mTView.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView);
	}

	private boolean validate(String str) {

		if (TextUtils.isEmpty(str)) {
			Toast.makeText(ActivityFindPwd.this, "答案不为空!", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		return true;
	}

	@Override
	public void onItemClick(int pos) {
		setHero(pos);
	}

	private void setHero(int pos) {
		if (pos >= 0 && pos <= nameList.size()) {
			String value = nameList.get(pos);

			mTView.setText(value);

			flag = pos;
		}
	}

	private void findPwd(final String id, String pwd) {
		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("id", id);
		_Params.add("pwd", pwd);
		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().UpdatePwd(_Params,
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
						mDialog.dismiss();
						showMsg("网络异常，请稍后重试!");
					}

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						mDialog.dismiss();
						System.out.println("****************" + content);

						try {
							JSONObject _Object = new JSONObject(content);
							String state = _Object.getString("state");
							String message = _Object.getString("message");
							if (state.equals("true")) {

								showMsg(message);
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

	public void showMsg(String msg) {

		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
