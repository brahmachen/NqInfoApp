package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import me.maxwin.view.ListViewForWebView;
import me.maxwin.view.ListViewForWebView.ListViewForWebViewListener;
import org.apache.http.Header;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.android.ais.app.AppApplication;
import com.android.ais.app.R;
import com.android.app.adapter.NqZxCommentAdapter;
import com.android.app.adapter.NqZxCommentAdapter.onClickButton;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.AisZxComment;
import com.android.app.entity.CollectNews;
import com.android.app.gg.activity.SelectPicPopupWindow;
import com.android.app.sqlite.util.DBServer;
import com.android.app.util.DialogUtils;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.MyPlatformShareListener;
import com.android.app.util.NqZxCommentDialogUtil;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.SharedPMananger;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CollectNewsActivity extends FragmentActivity implements
		ListViewForWebViewListener, onClickButton, OnClickListener {

	private DBServer db;

	private CollectNewsActivity mInstance;
	MyPlatformShareListener myPlatformShareListener = new MyPlatformShareListener();

	private WebView web_customer;
	private ImageView iv_head_left, iv_head_right;
	private TextView tv_head_title, txt_title, txt_time;
	protected View mView;
	private TextViewOnclick textOnclick;
	private WebSettings settings;
	private LinearLayout ll_news;

	private ProgressDialog dialog;
	// �����Ի������,����ͨ��new������õģ���ͨ��builder.create();
	private AlertDialog dialog_user;
	private AlertDialog.Builder builder = null;
	// ѹ���ö������ڻ�ȡView����
	private LayoutInflater inflater;

	/**
	 * �������������С
	 */
	int fontSize = 1;
	private String mHtmlContent;
	private TextView txt_small;
	private TextView txt_middle;
	private TextView txt_large;
	private String fontsize2;
	private TextView currentTextView;

	private Handler handler;

	private ListViewForWebView mListView;

	int page = 1, pageSize = 5;

	ProgressBar progressBar1;
	NqZxCommentAdapter adapter;

	View CommentEditTextView;
	EditText commentEditText;
	Button submitComment;

	LayoutInflater mLayoutInflater;
	NqZxCommentDialogUtil mCommentDialogUtil;

	String userId, topicId, title, content, time, topicType ,zan_userId  ,zanflag  , zancount = "0";

	private LinearLayout layoutShare = null;
	private LinearLayout layoutComment = null;
	private LinearLayout layoutCollect = null;
	private TextView tv_shoucang;

	private Button btn_zan;
	private TextView tv_zancount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		textOnclick = new TextViewOnclick();
		requestWindowFeature(Window.FEATURE_NO_TITLE);// �ص�title
		setContentView(R.layout.nqzx_news_activity);

		

		if(!"NULL".equals(PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL"))){
			zan_userId = PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL") ; 
		}else{
			zan_userId="";
		}
		
		
		
		
		// ��ȡ����ı��
		topicId = getIntent().getStringExtra("nqzxId");

		userId = getIntent().getStringExtra("userId");

		title = getIntent().getStringExtra("title");
		time = getIntent().getStringExtra("time");
		content = getIntent().getStringExtra("content");
		topicType = getIntent().getStringExtra("topicType");
		zanflag = getIntent().getStringExtra("zanflag"); 
		zancount = getIntent().getStringExtra("zancount"); 
		
	
		db = new DBServer(this);

		tv_shoucang = (TextView) findViewById(R.id.nqxx_comment_shoucang_title);

		if (db.isNewsExists(topicId.trim()+"2"+zan_userId)) {
			tv_shoucang.setText("ȡ��\n�ղ�");
		}

		System.out.println("���۵�topicId��" + topicId + "  userId : " + userId
				+ "  page: " + page + "  pageSize: " + pageSize);

		handler = new Handler();
		initView();
		getdata();
		getAisComment(topicId, userId, String.valueOf(page),
				String.valueOf(pageSize));
	}

	private void initView() {
		dialog = new ProgressDialog(mInstance);
		// dialog.show();

		tv_head_title = (TextView) findViewById(R.id.tv_head_title);
		txt_title = (TextView) findViewById(R.id.nqzx_layout_news_txt_title);
		txt_time = (TextView) findViewById(R.id.nqzx_layout_news_txt_time);
		tv_head_title.setText("�ղ�����");
		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_right = (ImageView) findViewById(R.id.iv_head_right);

		layoutShare = (LinearLayout) findViewById(R.id.nqxx_comment_share);
		layoutComment = (LinearLayout) findViewById(R.id.nqxx_comment_pinglun);
		layoutCollect = (LinearLayout) findViewById(R.id.nqxx_comment_shoucang);
		initEvent();

		btn_zan = (Button) findViewById(R.id.nqzx_layout_news_zan);
		if ("0".equals(zanflag)) {
			btn_zan.setBackgroundResource(R.drawable.comment_social_cl_unlike);
		} else {
			btn_zan.setBackgroundResource(R.drawable.comment_social_cl_like);
		}
		
		
		btn_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if ("0".equals(zanflag)) {
					Zan(topicId , zan_userId);
				} else {
					CancleZan(topicId , zan_userId );
				}
			}
		});
		tv_zancount = (TextView) findViewById(R.id.nqzx_layout_news_zancount);
		tv_zancount.setText(zancount) ; 
		
		
		mLayoutInflater = getLayoutInflater();

		ll_news = (LinearLayout) findViewById(R.id.nqzx_layout_news_ll_news);

		iv_head_left.setOnClickListener(textOnclick);
		iv_head_right.setOnClickListener(textOnclick);

		mListView = (ListViewForWebView) findViewById(R.id.nqzx_layout_news_ListView);
		mListView.setPullLoadEnable(true);

		adapter = new NqZxCommentAdapter(CollectNewsActivity.this, mListView);
		adapter.setOnclickButton(this);
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		initWeb();

		inflater = LayoutInflater.from(this);
	}

	private void initWeb() {
		try {
			fontsize2 = SharedPMananger.getString("fontsize", "middle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fontsize2 = "middle";
			e.printStackTrace();
		}
		web_customer = (WebView) findViewById(R.id.nqzx_layout_news_web_customer);

		settings = web_customer.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// web_customer.setInitialScale(150);// ��ʼ��ʾ����150%
		//settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// ����ʹ�û���
		if (settings.getTextSize() == WebSettings.TextSize.SMALLEST) {
			fontSize = 4;
		} else if (settings.getTextSize() == WebSettings.TextSize.SMALLER) {
			fontSize = 5;
		} else if (settings.getTextSize() == WebSettings.TextSize.NORMAL) {
			fontSize = 6;
		} else if (settings.getTextSize() == WebSettings.TextSize.LARGER) {
			fontSize = 7;
		} else if (settings.getTextSize() == WebSettings.TextSize.LARGEST) {
			fontSize = 8;
		}
		if (fontsize2.equals("small")) {
			settings.setTextSize(WebSettings.TextSize.SMALLER);
		}
		if (fontsize2.equals("middle")) {
			settings.setTextSize(WebSettings.TextSize.NORMAL);
		}
		if (fontsize2.equals("large")) {
			settings.setTextSize(WebSettings.TextSize.LARGER);
		}

	}

	private void getdata() {
		txt_title.setText(title);
		txt_time.setText(time);
		web_customer.loadDataWithBaseURL(null, content, "text/html", "utf-8",
				null);
	}

	private void getAisComment(String topicId, String userId, String page,
			String pageSize) {
		RequestParams baseParams = MyHttpAPIControl.getBaseParams();

		baseParams.add("topicId", topicId);
		baseParams.add("userId", userId);
		baseParams.add("page", page);
		baseParams.add("pageSize", pageSize);

		MyHttpAPIControl.newInstance().getAisZxComment(baseParams,
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
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						dialog.dismiss();
						Type tp = new TypeToken<AISDataBase<AisZxComment>>() {
						}.getType();
						System.out.println(content);
						AISDataBase<AisZxComment> ss = (AISDataBase<AisZxComment>) ParseUtils
								.Gson2Object(content, tp);
						// System.out.println("**********************888888"+ss.getData().get(1).getId());
						if (ss != null && ss.isState()) {
							ArrayList<AisZxComment> naviga = ss.getData();
							// progressBar1.setVisibility(View.GONE);
							adapter.addData(naviga);
							onLoad();

						}

					}

				});
	}

	/** ����¼� **/
	private class TextViewOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.iv_head_left:
				finish();
				break;
			case R.id.iv_head_right:
				showDialog();
				break;
			case R.id.txt_small:// ���� С
				SharedPMananger.putString("fontsize", "small");
				settings.setTextSize(WebSettings.TextSize.SMALLER);
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				break;
			case R.id.txt_middle:// ���� ��
				SharedPMananger.putString("fontsize", "middle");
				settings.setTextSize(WebSettings.TextSize.NORMAL);

				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				break;
			case R.id.txt_large:// ���� ��
				SharedPMananger.putString("fontsize", "large");
				settings.setTextSize(WebSettings.TextSize.LARGER);
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				break;

			default:
				break;
			}
		}
	}

	/***
	 * �ı����塢ҹ��ģʽ
	 */
	private void showDialog() {
		// TODO Auto-generated method stub

		mView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
		txt_small = (TextView) mView.findViewById(R.id.txt_small);
		txt_small.setOnClickListener(textOnclick);
		txt_middle = (TextView) mView.findViewById(R.id.txt_middle);
		txt_middle.setOnClickListener(textOnclick);
		txt_large = (TextView) mView.findViewById(R.id.txt_large);
		txt_large.setOnClickListener(textOnclick);
		// NightMode = (SlipButton) mView.findViewById(R.id.slipBtn);
		// NightMode.SetOnChangedListener(this);

		if (fontsize2.equals("small")) {
			currentTextView = txt_small;
		}
		if (fontsize2.equals("middle")) {
			currentTextView = txt_middle;
		}
		if (fontsize2.equals("large")) {
			currentTextView = txt_large;
		}
		currentTextView.setBackgroundColor(getResources().getColor(
				R.color.mode_night_green));
		currentTextView.setTextColor(Color.WHITE);
		DialogUtils.getInstance().displayDialog(this, mView, Gravity.BOTTOM);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page = page + 1;
		getAisComment(topicId, userId, String.valueOf(page),
				String.valueOf(pageSize));
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(new Date().toLocaleString());
	}

	@Override
	public void showDialog(String topicId, String userId, String commentType,
			String commentID) {
		// TODO Auto-generated method stub
		// DialogUtils.getInstance();
		mCommentDialogUtil = new NqZxCommentDialogUtil(
				CollectNewsActivity.this, topicId, userId, commentType,
				commentID);
		CommentEditTextView = mLayoutInflater.inflate(
				R.layout.nqxx_comment_edit, null);
		commentEditText = (EditText) CommentEditTextView
				.findViewById(R.id.nqxx_comment_edit_et);
		submitComment = (Button) CommentEditTextView
				.findViewById(R.id.nqxx_submit_comment);
		submitComment.setOnClickListener(this);
		mCommentDialogUtil.displayDialog(CollectNewsActivity.this,
				CommentEditTextView, 2);

	}

	private void initEvent() {
		// TODO Auto-generated method stub
		layoutShare.setOnClickListener(this);
		layoutComment.setOnClickListener(this);
		layoutCollect.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.nqxx_submit_comment:

			String content = commentEditText.getText().toString();

			RequestParams baseParams = MyHttpAPIControl.getBaseParams();
			baseParams.add("content", content);
			baseParams.add("topicId", mCommentDialogUtil.getTopicId());
			baseParams.add("commentType", mCommentDialogUtil.getCommentType());
			baseParams.add("commentedUserId", mCommentDialogUtil.getUserId());
			baseParams.add("commentFlag", mCommentDialogUtil.getCommentFlag());

			if (!"NONE".equals(PreferenceUtils.getPrefString(
					CollectNewsActivity.this, "UserId", "NONE")))
				baseParams.add("commentUserId", PreferenceUtils.getPrefString(
						CollectNewsActivity.this, "UserId", "NONE"));
			else if (!"NONE".equals(PreferenceUtils.getPrefString(
					CollectNewsActivity.this, "AnonymousUserId", "NONE")))
				baseParams.add("commentUserId", PreferenceUtils.getPrefString(
						CollectNewsActivity.this, "AnonymousUserId", "NONE"));

			mCommentDialogUtil.dismissDialog();

			MyHttpAPIControl.newInstance().submitAisZxComment(baseParams,
					new AsyncHttpResponseHandler() {

						@Override
						@Deprecated
						public void onFailure(Throwable error) {
							// TODO Auto-generated method stub
							super.onFailure(error);
							Toast.makeText(CollectNewsActivity.this,
									"������ô�ˣ��ύ������Ϣ��ʧ���ˣ�", Toast.LENGTH_SHORT)
									.show();
						}

						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
							dialog.show();
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String content) {
							super.onSuccess(statusCode, headers, content);

							dialog.dismiss();
							Type tp = new TypeToken<AisZxComment>() {
							}.getType();
							AisZxComment entity = (AisZxComment) ParseUtils
									.Gson2Object(content, tp);
							adapter.addDataFromHead(entity);

							onRefresh();
							Toast.makeText(CollectNewsActivity.this,
									"�ɹ�����������Ϣ��", Toast.LENGTH_SHORT).show();

						}
					});
			break;
		case R.id.nqxx_comment_pinglun:
			showDialog(topicId, userId, "1", null);
			break;
		case R.id.nqxx_comment_share:
			startActivityForResult(new Intent(AppApplication.getInstance(),
					SelectPicPopupWindow.class), 1);
			break;
		case R.id.nqxx_comment_shoucang:
			if (!db.isNewsExists(topicId.trim()+"2"+zan_userId)) {
				CollectNews entity = new CollectNews();
				entity.setNewsId(topicId.trim()+"2"+zan_userId);
				entity.setNqxxId(topicId);
				entity.setNewsType("1");
				entity.setUserId(zan_userId);
				entity.setNewsDate(new Date().toString());
				db.addNews(entity);
				tv_shoucang.setText("ȡ��\n�ղ�");
				Toast.makeText(AppApplication.getInstance(), "�ղسɹ�",
						Toast.LENGTH_SHORT).show();
			} else {
				db.deleteCollectNewsById(topicId.trim()+"2"+zan_userId) ; 
				tv_shoucang.setText("�ղ�");
				Toast.makeText(AppApplication.getInstance(),
						"ȡ���ղسɹ�", Toast.LENGTH_SHORT).show();
			}
			break ; 
		default:
			break;
		}

	}

	@Override
	public void showUserDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			switch (resultCode) {
			case 1:

				ShareParams qq = new ShareParams();
				qq.setTitle(title);
				qq.setTitleUrl("http://www.baidu.com");// ���������ת����
				// qq.setText(content);
				// qq.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				Platform qqq = ShareSDK.getPlatform(
						AppApplication.getInstance(), QQ.NAME);
				qqq.setPlatformActionListener(myPlatformShareListener);
				qqq.share(qq);

				break;
			case 2:

				ShareParams qzone = new ShareParams();
				qzone.setTitle(title);
				qzone.setTitleUrl("http://www.baidu.com");// ���������ת����
				// qzone.setText(content);
				// qzone.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				Platform zonep = ShareSDK.getPlatform(
						AppApplication.getInstance(), QZone.NAME);
				zonep.setPlatformActionListener(myPlatformShareListener);
				zonep.share(qzone);

				break;

			case 3:
				ShareParams sp = new ShareParams();
				sp.setTitle(title);
				sp.setTitleUrl("http://www.baidu.com"); // ����ĳ�����
				sp.setText(title);
				// sp.setImagePath("/sdcard/logo_bluetooth.png");
				// String[] am = {
				// "/sdcard/logo_bluetooth.png","/sdcard/logo_bluetooth.png" };
				// sp.setImageArray(am);
				// sp.setImageUrl("http://q.qlogo.cn/qqapp/1104711955/9DF669462BB45A4494EF6220B59008F4/40");
				// sp.setSite("�����������վ����");
				// sp.setSiteUrl("����������վ�ĵ�ַ");
				Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
				weibo.setPlatformActionListener(myPlatformShareListener); // ���÷�����
				weibo.share(sp);
				break;
			case 4:

				ShareParams Moments = new ShareParams();
				// �κη������Ͷ���Ҫtitle��text, the two params of title and text are
				// required in every share type

				Moments.setTitle(title);
				// Moments.text = content ;

				// sp.setShareType(Platform.SHARE_TEXT);

				// sp.setShareType(Platform.SHARE_IMAGE);
				// Moments.setShareType(Platform.SHARE_WEBPAGE);
				Moments.setUrl("http://www.baidu.com");
				// Moments.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				// sp.setImagePath(sd���µ�ͼƬ);

				// sp.setShareType(Platform.SHARE_IMAGE);
				// sp.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png");
				Platform plat = ShareSDK.getPlatform(
						AppApplication.getInstance(), WechatMoments.NAME);
				plat.setPlatformActionListener(myPlatformShareListener);

				plat.share(Moments);

				break;
			case 5:

				ShareParams friend = new ShareParams();
				// �κη������Ͷ���Ҫtitle��text, the two params of title and text are
				// required in every share type

				friend.setTitle(title);
				// friend.text = content;

				// sp.setShareType(Platform.SHARE_TEXT);

				// sp.setShareType(Platform.SHARE_IMAGE);
				friend.setShareType(Platform.SHARE_WEBPAGE);
				friend.setUrl("http://www.baidu.com");
				// friend.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				// sp.setImagePath(sd���µ�ͼƬ);

				// sp.setShareType(Platform.SHARE_IMAGE);
				// sp.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png");
				Platform FrPlat = ShareSDK.getPlatform(
						AppApplication.getInstance(), Wechat.NAME);
				FrPlat.setPlatformActionListener(myPlatformShareListener);
				FrPlat.share(friend);
				break;
			default:
				break;
			}
		}

	}

	public void Zan(String id ,String zan_userId) {

		RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		baseParams.add("id", id);
		baseParams.add("userId", zan_userId);
		MyHttpAPIControl.newInstance().zanNqzx(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(AppApplication.getInstance(),
								"�����쳣,��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						zanflag = "1";
						tv_zancount.setText(String.valueOf(Integer
								.parseInt(tv_zancount.getText().toString()) + 1));
						btn_zan.setBackgroundResource(R.drawable.comment_social_cl_like);
						Toast.makeText(AppApplication.getInstance(), "�޳ɹ� ��",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

	public void CancleZan(String id ,String zan_userId) {

		RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		baseParams.add("id", id);
		baseParams.add("userId", zan_userId);
		MyHttpAPIControl.newInstance().cancleZanNqzx(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(AppApplication.getInstance(),
								"�����쳣,ȡ����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						zanflag = "0";
						tv_zancount.setText(String.valueOf(Integer
								.parseInt(tv_zancount.getText().toString()) - 1));
						btn_zan.setBackgroundResource(R.drawable.comment_social_cl_unlike);
						Toast.makeText(AppApplication.getInstance(), "ȡ���޳ɹ� ��",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

}
