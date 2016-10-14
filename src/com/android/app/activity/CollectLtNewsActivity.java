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
import com.android.app.adapter.NqltCommentAdapter;
import com.android.app.adapter.NqltCommentAdapter.onClickButton;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.AisLtComment;
import com.android.app.entity.AisZxComment;
import com.android.app.entity.CollectNews;
import com.android.app.gg.activity.SelectPicPopupWindow;
import com.android.app.sqlite.util.DBServer;
import com.android.app.util.CommentDialogUtil;
import com.android.app.util.DialogUtils;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.MyPlatformShareListener;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.SharedPMananger;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CollectLtNewsActivity extends FragmentActivity implements
		ListViewForWebViewListener, onClickButton, OnClickListener {

	private DBServer db;

	MyPlatformShareListener myPlatformShareListener = new MyPlatformShareListener();
	private CollectLtNewsActivity mInstance;

	private WebView web_customer;
	private ImageView iv_head_left, iv_head_right;
	private TextView tv_head_title, txt_title, txt_time;
	protected View mView;
	private TextViewOnclick textOnclick;
	private WebSettings settings;
	private LinearLayout ll_news;

	private ProgressDialog dialog;
	// 创建对话框对象,不是通过new方法获得的，是通过builder.create();
	private AlertDialog dialog_user;
	private AlertDialog.Builder builder = null;
	// 压力泵对象，用于获取View对象
	private LayoutInflater inflater;

	/**
	 * 用来控制字体大小
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
	NqltCommentAdapter adapter;

	View CommentEditTextView;
	EditText commentEditText;
	Button submitComment;

	LayoutInflater mLayoutInflater;
	CommentDialogUtil mCommentDialogUtil;

	String userId, topicId, title, content, time, topicType  ,zan_userId , zanflag   , zancount = "0";

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
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.nqlt_news_activity);

		

		if(!"NULL".equals(PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL"))){
			zan_userId = PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL") ; 
		}else{
			zan_userId="";
		}
		
		
		
		// 获取主题的编号
		topicId = getIntent().getStringExtra("nqltId");

		userId = getIntent().getStringExtra("userId");

		title = getIntent().getStringExtra("title");
		time = getIntent().getStringExtra("time");
		content = getIntent().getStringExtra("content");
		topicType = getIntent().getStringExtra("topicType");
		zanflag = getIntent().getStringExtra("zanflag"); 
		zancount = getIntent().getStringExtra("zancount"); 
		
		db = new DBServer(this);
		tv_shoucang = (TextView) findViewById(R.id.nqxx_comment_shoucang_title);

		if (db.isNewsExists(topicId.trim()+"1"+zan_userId)) {
			tv_shoucang.setText("取消\n收藏");
		}

		System.out.println("评论的topicId：" + topicId + "  userId : " + userId
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
		txt_title = (TextView) findViewById(R.id.nqlt_layout_news_txt_title);
		txt_time = (TextView) findViewById(R.id.nqlt_layout_news_txt_time);
		tv_head_title.setText("收藏内容");
		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_right = (ImageView) findViewById(R.id.iv_head_right);

		btn_zan = (Button) findViewById(R.id.nqlt_layout_news_zan);
		tv_zancount = (TextView) findViewById(R.id.nqlt_layout_news_zancount);
		tv_zancount.setText(zancount) ;
		
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

		layoutShare = (LinearLayout) findViewById(R.id.nqxx_comment_share);
		layoutComment = (LinearLayout) findViewById(R.id.nqxx_comment_pinglun);
		layoutCollect = (LinearLayout) findViewById(R.id.nqxx_comment_shoucang);
		initEvent();

		mLayoutInflater = getLayoutInflater();

		ll_news = (LinearLayout) findViewById(R.id.nqlt_layout_news_ll_news);

		iv_head_left.setOnClickListener(textOnclick);
		iv_head_right.setOnClickListener(textOnclick);

		mListView = (ListViewForWebView) findViewById(R.id.nqlt_layout_news_ListView);
		mListView.setPullLoadEnable(true);

		adapter = new NqltCommentAdapter(CollectLtNewsActivity.this, mListView);
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
		web_customer = (WebView) findViewById(R.id.nqlt_layout_news_web_customer);

		settings = web_customer.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// web_customer.setInitialScale(150);// 初始显示比例150%
		//settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
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

		MyHttpAPIControl.newInstance().getAisLtComment(baseParams,
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
						Type tp = new TypeToken<AISDataBase<AisLtComment>>() {
						}.getType();
						System.out.println(content);
						AISDataBase<AisLtComment> ss = (AISDataBase<AisLtComment>) ParseUtils
								.Gson2Object(content, tp);
						// System.out.println("**********************888888"+ss.getData().get(1).getId());
						if (ss != null && ss.isState()) {
							ArrayList<AisLtComment> naviga = ss.getData();
							// progressBar1.setVisibility(View.GONE);
							adapter.addData(naviga);
							onLoad();

						}

					}

				});
	}

	/** 点击事件 **/
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
			case R.id.txt_small:// 字体 小
				SharedPMananger.putString("fontsize", "small");
				settings.setTextSize(WebSettings.TextSize.SMALLER);
				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				break;
			case R.id.txt_middle:// 字体 中
				SharedPMananger.putString("fontsize", "middle");
				settings.setTextSize(WebSettings.TextSize.NORMAL);

				currentTextView.setBackgroundColor(0);
				currentTextView.setTextColor(Color.BLACK);
				currentTextView = (TextView) v;
				currentTextView.setBackgroundColor(getResources().getColor(
						R.color.mode_night_green));
				currentTextView.setTextColor(Color.WHITE);
				break;
			case R.id.txt_large:// 字体 大
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
	 * 改变字体、夜间模式
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
		mCommentDialogUtil = new CommentDialogUtil(CollectLtNewsActivity.this,
				topicId, userId, commentType, commentID);
		CommentEditTextView = mLayoutInflater.inflate(
				R.layout.nqxx_comment_edit, null);
		commentEditText = (EditText) CommentEditTextView
				.findViewById(R.id.nqxx_comment_edit_et);
		submitComment = (Button) CommentEditTextView
				.findViewById(R.id.nqxx_submit_comment);
		submitComment.setOnClickListener(this);
		mCommentDialogUtil.displayDialog(CollectLtNewsActivity.this,
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
					CollectLtNewsActivity.this, "UserId", "NONE")))
				baseParams.add("commentUserId", PreferenceUtils.getPrefString(
						CollectLtNewsActivity.this, "UserId", "NONE"));
			else if (!"NONE".equals(PreferenceUtils.getPrefString(
					CollectLtNewsActivity.this, "AnonymousUserId", "NONE")))
				baseParams.add("commentUserId", PreferenceUtils.getPrefString(
						CollectLtNewsActivity.this, "AnonymousUserId", "NONE"));

			mCommentDialogUtil.dismissDialog();

			MyHttpAPIControl.newInstance().submitAisZxComment(baseParams,
					new AsyncHttpResponseHandler() {

						@Override
						@Deprecated
						public void onFailure(Throwable error) {
							// TODO Auto-generated method stub
							super.onFailure(error);
							Toast.makeText(CollectLtNewsActivity.this,
									"世界肿么了，提交评论消息都失败了！", Toast.LENGTH_SHORT)
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
							Type tp = new TypeToken<AisLtComment>() {
							}.getType();
							AisLtComment entity = (AisLtComment) ParseUtils
									.Gson2Object(content, tp);
							adapter.addDataFromHead(entity);

							onRefresh();
							Toast.makeText(CollectLtNewsActivity.this,
									"成功发表评论消息！", Toast.LENGTH_SHORT).show();

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
			if (!db.isNewsExists(topicId.trim()+"1"+zan_userId)) {
				CollectNews entity = new CollectNews();
				entity.setNewsId(topicId.trim()+"1"+zan_userId);
				entity.setNqxxId(topicId.trim());
				entity.setNewsType("1");
				entity.setUserId(zan_userId);
				entity.setNewsDate(new Date().toString());
				db.addNews(entity);
				tv_shoucang.setText("取消\n收藏");
				Toast.makeText(AppApplication.getInstance(), "收藏成功",
						Toast.LENGTH_SHORT).show();
			} else {
				db.deleteCollectNewsById(topicId.trim()+"1"+zan_userId) ; 
				tv_shoucang.setText("收藏");
				Toast.makeText(AppApplication.getInstance(),
						"取消收藏成功", Toast.LENGTH_SHORT).show();
			}
			break;
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
				qq.setTitleUrl("http://www.baidu.com");// 分享后点击跳转链接
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
				qzone.setTitleUrl("http://www.baidu.com");// 分享后点击跳转链接
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
				sp.setTitleUrl("http://www.baidu.com"); // 标题的超链接
				sp.setText(title);
				// sp.setImagePath("/sdcard/logo_bluetooth.png");
				// String[] am = {
				// "/sdcard/logo_bluetooth.png","/sdcard/logo_bluetooth.png" };
				// sp.setImageArray(am);
				// sp.setImageUrl("http://q.qlogo.cn/qqapp/1104711955/9DF669462BB45A4494EF6220B59008F4/40");
				// sp.setSite("发布分享的网站名称");
				// sp.setSiteUrl("发布分享网站的地址");
				Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
				weibo.setPlatformActionListener(myPlatformShareListener); // 设置分享事
				weibo.share(sp);
				break;
			case 4:

				ShareParams Moments = new ShareParams();
				// 任何分享类型都需要title和text, the two params of title and text are
				// required in every share type

				Moments.setTitle(title);
				// Moments.text = content ;

				// sp.setShareType(Platform.SHARE_TEXT);

				// sp.setShareType(Platform.SHARE_IMAGE);
				// Moments.setShareType(Platform.SHARE_WEBPAGE);
				Moments.setUrl("http://www.baidu.com");
				// Moments.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				// sp.setImagePath(sd卡下的图片);

				// sp.setShareType(Platform.SHARE_IMAGE);
				// sp.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/16/1373959974649.png");
				Platform plat = ShareSDK.getPlatform(
						AppApplication.getInstance(), WechatMoments.NAME);
				plat.setPlatformActionListener(myPlatformShareListener);

				plat.share(Moments);

				break;
			case 5:

				ShareParams friend = new ShareParams();
				// 任何分享类型都需要title和text, the two params of title and text are
				// required in every share type

				friend.setTitle(title);
				// friend.text = content;

				// sp.setShareType(Platform.SHARE_TEXT);

				// sp.setShareType(Platform.SHARE_IMAGE);
				friend.setShareType(Platform.SHARE_WEBPAGE);
				friend.setUrl("http://www.baidu.com");
				// friend.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
				// sp.setImagePath(sd卡下的图片);

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
		MyHttpAPIControl.newInstance().zanNqlt(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(AppApplication.getInstance(),
								"网络异常,赞失败！", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(AppApplication.getInstance(), "赞成功 ！",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

	public void CancleZan(String id ,String zan_uerId) {

		RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		baseParams.add("id", id);
		baseParams.add("userId", zan_userId);
		MyHttpAPIControl.newInstance().cancleZanNqlt(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(AppApplication.getInstance(),
								"网络异常,取消赞失败！", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(AppApplication.getInstance(), "取消赞成功 ！",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

}
