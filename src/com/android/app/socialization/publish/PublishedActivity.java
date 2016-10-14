package com.android.app.socialization.publish;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.ais.app.Constants;
import com.android.ais.app.R;
import com.android.app.gg.activity.ActivityLogin;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PublishedActivity extends Activity {

	private Activity mInstance;

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private EditText mEditText;
	private Thread uploadPicThread;

	private ImageView iv_back;

	List<String> picList = new ArrayList<String>();
	ArrayList<String> pics = new ArrayList<String>();
	String publish_content = "";
	private ProgressDialog dialog;

	// 创建对话框对象,不是通过new方法获得的，是通过builder.create();
	private AlertDialog dialog_user;
	private AlertDialog.Builder builder = null;
	// 压力泵对象，用于获取View对象
	private LayoutInflater inflater;

	private static final String[] spinnerList = { "病虫害防治", "种植技术", "供求信息",
			"你问我答" };
	private Spinner spinnerQueryType = null;
	private ArrayAdapter<String> spinnerQueryTypeAdapter = null;

	int queryTypeMark = 1;

	private String select_flag = "0";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.nqlt_pub_main);
		mInstance = this;

		init();
	}

	public void init() {
		dialog = new ProgressDialog(PublishedActivity.this);
		select_flag = getIntent().getStringExtra("select_flag");
		
		inflater = LayoutInflater.from(mInstance);
		// 创建一个对话框
		createDialog();

		mEditText = (EditText) findViewById(R.id.nqlt_pub_et);
		mEditText.setHint("请输入您要分享的内容");

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		// adapter.update();

		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

		iv_back = (ImageView) findViewById(R.id.pub_nqlt_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		spinnerQueryType = (Spinner) findViewById(R.id.pub_sppiner_querytype);
		spinnerQueryTypeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerList);
		spinnerQueryTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerQueryType.setAdapter(spinnerQueryTypeAdapter);
		spinnerQueryType
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						switch (position) {
						case 0:
							queryTypeMark = 1;
							break;
						case 1:
							queryTypeMark = 2;
							break;
						case 2:
							queryTypeMark = 5;
							break;
						case 3:
							queryTypeMark = 6;
							break;

						default:
							break;
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

		findViewById(R.id.activity_selectimg_send).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {

						if (!"NONE".equals(PreferenceUtils.getPrefString(
								mInstance, "UserId", "NONE"))) {

							for (int i = 0; i < Bimp.drr.size(); i++) {
								String Str = Bimp.drr.get(i).substring(
										Bimp.drr.get(i).lastIndexOf("/") + 1,
										Bimp.drr.get(i).lastIndexOf("."));
								// picList.add(Environment.getExternalStorageState()+FileUtils.SDPATH+Str+".JPEG");
								if (Environment
										.getExternalStorageState()
										.equals(android.os.Environment.MEDIA_MOUNTED)) {
									pics.add(Str + ".JPEG");
									picList.add(FileUtils.SDPATH + Str
											+ ".JPEG");
								}
							}

							publish_content = mEditText.getText().toString();
							if ("".equals(publish_content)) {
								publish_content = PreferenceUtils
										.getPrefString(PublishedActivity.this,
												"PUB_CONTENT", "");
							}
							if ("".equals(publish_content)) {
								Toast.makeText(PublishedActivity.this,
										"请输入动态内容！", Toast.LENGTH_SHORT).show();
								return;
							}
							String type = String.valueOf(queryTypeMark);
							String userId = "";
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									PublishedActivity.this, "UserId", "NONE")))
								userId = PreferenceUtils.getPrefString(
										PublishedActivity.this, "UserId",
										"NONE");

							PublishPage(type, "", publish_content, userId, pics);

							uploadPicThread = new Thread(mRunnable);
							uploadPicThread.start();
						} else {
							dialog_user.show();
						}
					}
				});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.nqlt_pub_item_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				/*
				 * if (position == 9) { holder.image.setVisibility(View.GONE); }
				 */
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			// 获取一次EditText的内容
			publish_content = mEditText.getText().toString();

			if (null == publish_content) {
				PreferenceUtils.setPrefString(PublishedActivity.this,
						"PUB_CONTENT", "");
			} else {
				PreferenceUtils.setPrefString(PublishedActivity.this,
						"PUB_CONTENT", publish_content);
			}
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(PublishedActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);

			View view = View.inflate(mContext,
					R.layout.nqlt_pub_selectpic_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setFinishOnTouchOutside(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					Intent intent = new Intent(PublishedActivity.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void photo() {

		String sdStatus = Environment.getExternalStorageState();

		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent openCameraIntent = new Intent(
				"android.media.action.IMAGE_CAPTURE");
		File file = new File(Constants.NEWS_LIST_IMG_CACHE_FOLDER, String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		path = file.getPath();

		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		 System.out.println("file  path    --------------------->>>"+ path+'\n'+ imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			// System.out.println("***********************************"+Bimp.drr.size()+'\n'+resultCode+'\n'+data);
			if (Bimp.drr.size() < 9 && resultCode == RESULT_OK) {
				Bimp.drr.add(path);
			}
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				FileUtils.deleteDir();
				break;
			}
			;

		};
	};

	Runnable mRunnable = new Runnable() {

		public void run() {

			for (int i = 0; i < picList.size(); i++) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					File icon = new File(picList.get(i));
					System.out
							.println("********************************file is exixts ?"
									+ icon.exists());
					UploadUtil.uploadFile(icon, Constants.UPLOAD_IMG);
				}
			}

			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(1);
		}
	};

	public void PublishPage(String type, String title, String content,
			String userId, ArrayList<String> pics) {
		RequestParams baseParams = MyHttpAPIControl.getBaseParams();

		baseParams.add("type", type);
		baseParams.add("title", title);
		baseParams.add("content", content);

		baseParams.put("pics", pics);

		if (!"NONE".equals(PreferenceUtils.getPrefString(
				PublishedActivity.this, "UserId", "NONE")))
			baseParams.add("userId", PreferenceUtils.getPrefString(
					PublishedActivity.this, "UserId", "NONE"));

		MyHttpAPIControl.newInstance().publishNqlt(baseParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						// mFailure_view.setVisibility(View.VISIBLE);
					}

					@Override
					public void onStart() {
						super.onStart();
						dialog.show();
						// mFailure_view.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						dialog.dismiss();
						PreferenceUtils.setPrefString(PublishedActivity.this,
								"PUB_CONTENT", "");
						Toast.makeText(PublishedActivity.this, "发布农情论坛消息成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}

				});
	}

	public View getLayout() {
		// 通过压力泵获取View
		LinearLayout ll = (LinearLayout) inflater.inflate(
				R.layout.user_login_check_dialog, null);
		return ll;
	}

	public void createDialog() {
		builder = new AlertDialog.Builder(mInstance);
		// 设置对话框的布局
		builder.setView(getLayout());
		builder.setTitle("提示框");

		builder.setIcon(R.drawable.ic_action_search);
		// 确定按钮的监听器
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						// 转到手机设置界面，用户设置GPS
						Intent intent = new Intent();
						intent.setClass(mInstance, ActivityLogin.class);
						startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
					}
				});
		// 取消按钮的监听器
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog_user.dismiss();
						Toast.makeText(mInstance, "匿名用户不能发表动态！",
								Toast.LENGTH_SHORT).show();
					}

				});
		//
		dialog_user = builder.create();
	}

	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mEditText.setText(PreferenceUtils.getPrefString(PublishedActivity.this,
				"PUB_CONTENT", "").toString());
		adapter.update();
		adapter.notifyDataSetChanged();
		
	}

}
