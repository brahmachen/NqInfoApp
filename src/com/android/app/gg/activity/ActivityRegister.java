package com.android.app.gg.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.univs.app.widget.AbstractSpinerAdapter.IOnItemSelectListener;
import cn.univs.app.widget.SpinerPopWindow;

import com.android.ais.app.R;
import com.android.app.entity.CommonUser;
import com.android.app.util.Base64Coder;
import com.android.app.util.MyHttpAPIControl;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityRegister extends Activity implements OnClickListener,
		IOnItemSelectListener {

	private Button back = null;

	private Button register = null;// 注册按钮

	private EditText user_name; // 用户昵称
	private EditText user_pwd;
	private EditText user_repwd;

	// 问题答案
	private EditText question1;
	private EditText question2;
	private EditText question3;

	private PopupWindow popup;

	// 问题
	private CommonUser mCommonUser = null;

	private ProgressDialog mDialog = null;

	private TextView mTextView1, mTextView2, mTextView3;

	private RelativeLayout mButton1, mButton2, mButton3;
	private SpinerPopWindow mSpinerPopWindow1, mSpinerPopWindow2,
			mSpinerPopWindow3;
	private List<String> nameList1 = new ArrayList<String>();
	private List<String> nameList2 = new ArrayList<String>();
	private List<String> nameList3 = new ArrayList<String>();

	private int flag = 1;

	private String img;
	private RelativeLayout _Layout;

	private ImageView _ImageView = null;

	private Bitmap _Bitmap = null;
	private static final int PHOTO_REQUEST = 1;
	private static final int CAMERA_REQUEST = 2;
	private static final int PHOTO_CLIP = 3;

	private String file = null;
	private Dialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_registe);
		inint();
	}

	/**
	 * 初始化
	 */
	private void inint() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.btn_back_re);
		register = (Button) findViewById(R.id.user_register);

		user_name = (EditText) findViewById(R.id.user_name);
		user_pwd = (EditText) findViewById(R.id.user_pwd);
		user_repwd = (EditText) findViewById(R.id.user_repwd);

		question1 = (EditText) findViewById(R.id.question1);
		question2 = (EditText) findViewById(R.id.question2);
		question3 = (EditText) findViewById(R.id.question3);

		mTextView1 = (TextView) findViewById(R.id.tv_value1);
		mTextView2 = (TextView) findViewById(R.id.tv_value2);
		mTextView3 = (TextView) findViewById(R.id.tv_value3);

		mButton1 = (RelativeLayout) findViewById(R.id.quest1);
		mButton2 = (RelativeLayout) findViewById(R.id.quest2);
		mButton3 = (RelativeLayout) findViewById(R.id.quest3);

		back.setOnClickListener(this);
		register.setOnClickListener(this);

		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		mButton3.setOnClickListener(this);

		_Layout = (RelativeLayout) findViewById(R.id.user_face);

		_ImageView = (ImageView) findViewById(R.id.face);
		_Layout.setOnClickListener(this);
		String[] names = getResources().getStringArray(R.array.hero_name);
		for (int i = 0; i < names.length; i++) {
			nameList1.add(names[i]);
		}

		String[] names2 = getResources().getStringArray(R.array.hero_1);
		for (int i = 0; i < names2.length; i++) {
			nameList2.add(names2[i]);
		}
		String[] names3 = getResources().getStringArray(R.array.hero_2);
		for (int i = 0; i < names3.length; i++) {
			nameList3.add(names3[i]);
		}

		mSpinerPopWindow1 = new SpinerPopWindow(this);
		mSpinerPopWindow1.refreshData(nameList1, 0);

		mSpinerPopWindow1.setItemListener(this);
		mSpinerPopWindow2 = new SpinerPopWindow(this);
		mSpinerPopWindow2.refreshData(nameList2, 0);
		mSpinerPopWindow2.setItemListener(this);
		mSpinerPopWindow3 = new SpinerPopWindow(this);
		mSpinerPopWindow3.refreshData(nameList3, 0);
		mSpinerPopWindow3.setItemListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_re:
			ActivityRegister.this.finish();
			break;

		case R.id.user_face:
			showDialog();
			break;
		case R.id.user_register:
			if (validateText()) {
				// 执行注册操作；

				mDialog = new ProgressDialog(ActivityRegister.this);
				mDialog.setTitle("注册");
				mDialog.setMessage("正在注册，请稍后...");

				Register();

			}
			break;
		case R.id.camera:

			if (popup.isShowing()) {
				getPicFromCamera();
			}
			if (popup != null && popup.isShowing()) {
				popup.dismiss();
			}
			break;

		case R.id.photo:
			getPicFromPhoto();
			if (popup != null && popup.isShowing()) {
				popup.dismiss();
			}
			break;
		case R.id.quest1:

			showSpinWindow(mSpinerPopWindow1, mTextView1);
			flag = 1;
			break;

		case R.id.quest2:
			showSpinWindow(mSpinerPopWindow2, mTextView2);
			flag = 2;
			break;
		case R.id.quest3:
			showSpinWindow(mSpinerPopWindow3, mTextView3);
			flag = 3;
			break;
		}
	}

	private void Register() {
		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		_Params.add("data", mCommonUser.toString());
		_Params.add("img", img);
		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().UserRegister(_Params,
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

							String message = _Object.getString("message");
							showMsg(message);

							if (message.equals("注册成功!")) {
								finish();
							}
						} catch (Exception e) {

							System.out.println(e.toString());
						}

					}

				});

	}

	private void showSpinWindow(SpinerPopWindow mSpinerPopWindow,
			TextView mTView) {
		Log.e("", "showSpinWindow");

		mSpinerPopWindow.setWidth(mTView.getWidth());

		mSpinerPopWindow.showAsDropDown(mTView);
	}

	private void getPicFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "test.jpg")));
		startActivityForResult(intent, CAMERA_REQUEST);
	}

	private void getPicFromPhoto() {

		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case CAMERA_REQUEST:
			switch (resultCode) {
			case -1:// -1表示拍照成功
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/test.jpg");
				if (file.exists()) {
					photoClip(Uri.fromFile(file));
				}
				break;
			default:
				break;
			}
			break;
		case PHOTO_REQUEST:
			if (data != null) {
				photoClip(data.getData());
			}
			break;
		case PHOTO_CLIP:

			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					_Bitmap = extras.getParcelable("data");
					_ImageView.setImageBitmap(_Bitmap);
				}
			}
			break;
		default:
			break;
		}
	}

	private void photoClip(Uri uri) {

		// 调用系统中自带的图片剪裁
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CLIP);
	}

	// 校验参数是否为空
	private boolean validateText() {
		// TODO Auto-generated method stub
		String name = user_name.getText().toString().trim();
		String pwd = user_pwd.getText().toString().trim();
		String repwd = user_repwd.getText().toString().trim();

		String ans1 = question1.getText().toString().trim();
		String ans2 = question2.getText().toString().trim();
		String ans3 = question3.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			user_name.setError("昵称不能为空!");
			return false;
		}
		if (TextUtils.isEmpty(pwd)) {
			user_pwd.setError("密码不能为空!");
			return false;
		}
		if (!pwd.equals(repwd)) {
			user_repwd.setError("密码不一致");
			return false;
		}

		if (TextUtils.isEmpty(ans1) || TextUtils.isEmpty(ans2)
				|| TextUtils.isEmpty(ans3)) {
			showError(this, "密保必须全部回答!");
			return false;
		}

		String quest1 = mTextView1.getText().toString().trim();
		String quest2 = mTextView2.getText().toString().trim();
		String quest3 = mTextView3.getText().toString().trim();

		if (quest1 == null || quest2 == null || quest3 == null) {

			Toast.makeText(this, "请选择问题!", Toast.LENGTH_SHORT).show();
		}

		if (_Bitmap == null) {

			_Bitmap = getRes("default_icon");
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_Bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);

		byte[] b = stream.toByteArray();

		// 将图片流以字符串形式存储下来
		img = new String(Base64Coder.encodeLines(b));

		mCommonUser = new CommonUser(name, pwd, quest1, quest2, quest3, ans1,
				ans2, ans3);

		return true;
	}

	private void showError(Context mContext, String string) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(mContext)
				.setIcon(
						getResources().getDrawable(R.drawable.login_error_icon))
				.setTitle("登录失败").setMessage(string).create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}

		if (dialog != null && dialog.isShowing()) {

			dialog.dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(int pos) {
		// TODO Auto-generated method stub

		switch (flag) {
		case 1:

			setHero(pos, nameList1, mTextView1);
			break;
		case 2:
			setHero(pos, nameList2, mTextView2);
			break;
		case 3:
			setHero(pos, nameList3, mTextView3);
			break;

		default:
			break;
		}
	}

	private void setHero(int pos, List<String> nameList, TextView mTView) {
		if (pos >= 0 && pos <= nameList.size()) {
			String value = nameList.get(pos);

			mTView.setText(value);

			if (((!TextUtils.isEmpty(mTextView1.getText().toString()) || (!TextUtils
					.isEmpty(mTextView2.getText().toString()))) && mTextView1
					.getText().toString()
					.equals(mTextView2.getText().toString()))) {

				showMsg("请不要选择相同的问题!");

				mTView.setText("");
				return;
			}

			if (((!TextUtils.isEmpty(mTextView2.getText().toString()) || (!TextUtils
					.isEmpty(mTextView3.getText().toString()))) && mTextView2
					.getText().toString()
					.equals(mTextView3.getText().toString()))) {
				mTView.setText("");
				showMsg("请不要选择相同的问题!");
				return;
			}
			if (((!TextUtils.isEmpty(mTextView1.getText().toString()) || (!TextUtils
					.isEmpty(mTextView3.getText().toString()))) && mTextView1
					.getText().toString()
					.equals(mTextView3.getText().toString()))) {
				mTView.setText("");
				showMsg("请不要选择相同的问题!");
				return;
			}
		}
	}

	public void showMsg(String msg) {

		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void showDialog() {

		AlertDialog.Builder mBuilder = new Builder(ActivityRegister.this);

		mBuilder.setPositiveButton("本地照片",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
						getPicFromPhoto();
					}
				});

		mBuilder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				getPicFromCamera();
			}
		});

		dialog = mBuilder.create();
		dialog.show();

	}

	public Bitmap getRes(String name) {
		ApplicationInfo appInfo = getApplicationInfo();
		int resID = getResources().getIdentifier(name, "drawable",
				appInfo.packageName);
		return BitmapFactory.decodeResource(getResources(), resID);
	}
}
