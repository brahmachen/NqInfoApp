package com.android.app.activity;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.univs.app.widget.OnChangedListener;
import cn.univs.app.widget.SlipButton;

import com.android.ais.app.R;
import com.android.app.util.DialogUtils;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.SharedPMananger;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***
 * 新闻
 * 
 * @author issuser
 * 
 */
public class NewsActivity extends FragmentActivity implements OnChangedListener {
	private NewsActivity mInstance;

	private WebView web_customer;
	
	private ImageView iv_head_left, iv_head_right;
	private TextView tv_head_title, txt_title, txt_time;
	protected View mView;
	private TextViewOnclick textOnclick;
	private WebSettings settings;
	private LinearLayout  ll_news;
	
	private ProgressDialog dialog;
	
	/** 夜间模式开关 **/
	private SlipButton NightMode;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		textOnclick = new TextViewOnclick();
		requestWindowFeature(Window.FEATURE_NO_TITLE);//关掉title
		setContentView(R.layout.activity_news);
		handler=new Handler();
		initView();
		getdata();

	}

	private void initView() {
		dialog = new ProgressDialog(mInstance);
		dialog.show();
		/*Intent intent = getIntent();
		item = (ArticleItme) intent.getSerializableExtra("SpecialActivity");*/
		tv_head_title = (TextView) findViewById(R.id.tv_head_title);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_time = (TextView) findViewById(R.id.txt_time);
		tv_head_title.setText("农情资讯");
		iv_head_left = (ImageView) findViewById(R.id.iv_head_left);
		iv_head_right = (ImageView) findViewById(R.id.iv_head_right);
	
		ll_news = (LinearLayout) findViewById(R.id.ll_news);
		iv_head_left.setOnClickListener(textOnclick);
		iv_head_right.setOnClickListener(textOnclick);
		
		initWeb();
		}

	@SuppressLint("JavascriptInterface")
	private void initWeb() {
		try {
			fontsize2 = SharedPMananger.getString("fontsize", "middle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fontsize2 = "small";
			e.printStackTrace();
		}
		web_customer = (WebView) findViewById(R.id.web_customer);
		settings = web_customer.getSettings();
		settings.setLoadsImagesAutomatically(true);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// web_customer.setInitialScale(150);// 初始显示比例150%
		//settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
		if (settings.getTextSize() == WebSettings.TextSize.SMALLEST) {
			fontSize = 1;
		} else if (settings.getTextSize() == WebSettings.TextSize.SMALLER) {
			fontSize = 2;
		} else if (settings.getTextSize() == WebSettings.TextSize.NORMAL) {
			fontSize = 3;
		} else if (settings.getTextSize() == WebSettings.TextSize.LARGER) {
			fontSize = 4;
		} else if (settings.getTextSize() == WebSettings.TextSize.LARGEST) {
			fontSize = 5;
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
		web_customer.addJavascriptInterface(new JavascriptInterface(this),
				"imagelistner");
		web_customer.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				addImageClickListner();
				boolean boo;
				try {
					boo = SharedPMananger.getBoolean("NowChoose", false);
					if (boo) {
						web_customer
								.loadUrl("javascript:(function(){"
										+ "var objs = document.getElementsByTagName(\"p\"); "
										+ "for(var i=0;i<objs.length;i++){"
										+ "  objs[i].style.color=\"#fff\";"
										+ "}" + "})()");
						// web_customer
						// .loadUrl("javascript:(function(){"
						// +
						// "document.getElementsByTagName(\'body\')[0].style.webkitTextFillColor=\"#fff\";"
						// + "}" + "})()");

					} else {
						web_customer
								.loadUrl("javascript:(function(){"
										+ "var objs = document.getElementsByTagName(\"p\"); "
										+ "for(var i=0;i<objs.length;i++){"
										+ "  objs[i].style.color=\"#000\";"
										+ "}" + "})()");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		try {
			boolean putBoolean = SharedPMananger.getBoolean("NowChoose", false);
			if (putBoolean) {
				setOnNightMode();

			} else {
				setOffNightMode();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getdata() {
       RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		
		MyHttpAPIControl.newInstance().getNqzx(baseParams,new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						if(dialog.isShowing()){
							dialog.dismiss();
						}
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
						if(dialog.isShowing()){
							dialog.dismiss();
						}
						
					/*	Type tp = new TypeToken<ArticleItme>() {
						}.getType();
						ss = (ArticleItme) ParseUtils.Gson2Object(content, tp);
						if (ss != null && ss.isState()) {
							txt_title.setText(ss.getData().getTitle());
							txt_time.setText(ss.getData().getSource()
									+ "  "
									+ DateUtils.formatDateCN(new Date(ss
											.getData().getSorttime() * 1000)));
							mHtmlContent = ss.getData().getContent();
							web_customer.loadDataWithBaseURL(null,
									mHtmlContent, "text/html", "utf-8", null);
						}*/
					/*	
						txt_title.setText("1111");
						txt_time.setText("2222222");
						mHtmlContent = "<html><head><title>amCharts examples</title>   </head> <body>   success </body></html>";
						web_customer.loadDataWithBaseURL(null,
								mHtmlContent, "text/html", "utf-8", null);*/
					}
				});
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		txt_title.setText("1111");
		txt_time.setText("2222222");
		mHtmlContent = "<html> <head> </head><body>This is my JSP page. <img alt=\"shan\" src=\"http://192.168.0.121:8080/NqInfoSer/a.jpg\"> <br> </body></html>";
		System.out.println(mHtmlContent);
		/*web_customer.loadDataWithBaseURL(null,
				mHtmlContent, "text/html", "utf-8", null);*/
		
		web_customer.loadData(mHtmlContent, "text/html",  "utf-8");
		
		
		
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
	//	NightMode = (SlipButton) mView.findViewById(R.id.slipBtn);
		NightMode.SetOnChangedListener(this);

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
	public void OnChanged(boolean CheckState) {
		SharedPMananger.putBoolean("NowChoose", CheckState);
		if (CheckState) {
			setOnNightMode();
			web_customer.loadDataWithBaseURL(null, mHtmlContent, "text/html",
					"utf-8", null);
		} else {
			setOffNightMode();
			web_customer.loadDataWithBaseURL(null, mHtmlContent, "text/html",
					"utf-8", null);
		}
	}

	/**
	 * 关闭夜间模式
	 */
	private void setOffNightMode() {
		web_customer.setBackgroundColor(Color.WHITE);
		ll_news.setBackgroundColor(Color.WHITE);
		txt_title.setTextColor(Color.BLACK);
		txt_time.setTextColor(Color.BLACK);
	}

	/**
	 * 打开夜间模式
	 */
	private void setOnNightMode() {
		web_customer.setBackgroundColor(Color.GRAY);
		ll_news.setBackgroundColor(Color.GRAY);
		txt_title.setTextColor(Color.WHITE);
		txt_time.setTextColor(Color.WHITE);
		web_customer.reload();
	}

	
	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
		web_customer.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(this.src);  "
				+ "    }  " + "}" + "})()");
	}

	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}
	}

	/*
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}*/
	
}

