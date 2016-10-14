package com.android.app.gg.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.util.Base64Coder;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ActivityChangeIcon extends Activity {
	private RelativeLayout photo;
	private AlertDialog dialog = null;
	private static final int PHOTO_REQUEST = 1;
	private static final int CAMERA_REQUEST = 2;
	private static final int PHOTO_CLIP = 3;
	private ImageView icon;

	private Bitmap _Bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_icon_change);

		icon = (ImageView) findViewById(R.id.face);
		photo = (RelativeLayout) findViewById(R.id.changephoto);
		photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeIcon();
			}
		});

	}

	/***
	 * 选择上传头像方式
	 */
	private void changeIcon() {

		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.layout_icon_select, null);

		// 2. 新建对话框对象
		dialog = new AlertDialog.Builder(ActivityChangeIcon.this).create();
		dialog.setCancelable(true);
		dialog.show();
		dialog.getWindow().setContentView(layout);

		// 4. 确定按钮
		Button camera = (Button) layout.findViewById(R.id.camera);

		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getPicFromCamera();
				dialog.dismiss();
			}
		});
		// 5. 取消按钮
		Button grally = (Button) layout.findViewById(R.id.grally);
		grally.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getPicFromPhoto();
				dialog.dismiss();
			}
		});

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-gednerated method stub
				dialog.dismiss();
			}
		});

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

	public void backButton(View v) {
		finish();
	}

	public void saveButton(View v) {

		if (_Bitmap == null) {
			Toast.makeText(ActivityChangeIcon.this, "请选择头像", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_Bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);

		byte[] b = stream.toByteArray();

		// 将图片流以字符串形式存储下来
		String file = new String(Base64Coder.encodeLines(b));

		RequestParams _Params = MyHttpAPIControl.getBaseParams();

		String id = PreferenceUtils.getPrefString(ActivityChangeIcon.this,
				"UserId", "NONE").trim();

		_Params.add("id", id);
		_Params.add("icon", file);

		Log.i("info", _Params.toString());
		MyHttpAPIControl.newInstance().changeIcon(_Params,
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
										ActivityChangeIcon.this, "UserIcon",
										_Object.getString("message"));
								setResult(6);
								finish();
							} else {

								showMsg("更换失败，请稍后重试..");

							}

						} catch (Exception e) {

							System.out.println(e.toString());
						}

					}

				});

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

					icon.setImageBitmap(_Bitmap);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showMsg(String msg) {

		Toast.makeText(ActivityChangeIcon.this, msg, Toast.LENGTH_SHORT).show();
	}
}
