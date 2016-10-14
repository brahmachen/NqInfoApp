package com.android.app.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

import com.android.ais.app.AppApplication;
import com.android.ais.app.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author mdj 请求接口控制类
 * 
 */
@SuppressWarnings("unused")
public class MyHttpAPIControl {
	private static MyHttpAPIControl mInstance = null;
	public static AsyncHttpClient client = new AsyncHttpClient();

	private static final AppApplication getApplication() {
		return AppApplication.getInstance();
	}

	private MyHttpAPIControl() {
	}

	public static MyHttpAPIControl newInstance() {
		if (mInstance == null) {
			mInstance = new MyHttpAPIControl();
		}
		setRequestParam();
		return mInstance;
	}

	public static void setRequestParam() {
		// 接下来是设置各种参数。。。
		final int DEFAULT_MAX_CONNECTIONS = 5;
		final int DEFAULT_SOCKET_TIMEOUT = 5 * 1000;
		final int DEFAULT_MAX_RETRIES = 3; // 最多重试3次
		final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 2000;
		// final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;

		int maxConnections = DEFAULT_MAX_CONNECTIONS;
		int connectTimeout = DEFAULT_SOCKET_TIMEOUT;
		int responseTimeout = DEFAULT_SOCKET_TIMEOUT; // 各种参数设置

		BasicHttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, connectTimeout);
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
				new ConnPerRouteBean(maxConnections));
		ConnManagerParams.setMaxTotalConnections(httpParams,
				DEFAULT_MAX_CONNECTIONS);

		HttpConnectionParams.setSoTimeout(httpParams, responseTimeout);
		HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

	}

	/**
	 * @return 请求头
	 */
	private static BasicHeader[] getBaseHeader() {
		BasicHeader[] header = new BasicHeader[1];
		return header;
	}

	/**
	 * 所有接口 请求通用参数
	 */
	public static RequestParams getBaseParams() {
		RequestParams params = new RequestParams();
		return params;
	}

	/**
	 * 基础 get
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void get(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		String urlWithQueryString = AsyncHttpClient.getUrlWithQueryString(url,
				params);
		client.get(getApplication(), url, null, params, handler);

	}

	/**
	 * 基础 post
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void post(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.post(getApplication(), url, null, params,
				"application/x-www-form-urlencoded", handler);
	}

	/**
	 * 基础 put
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void put(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.put(getApplication(), url, null, null,
				"application/x-www-form-urlencoded", handler);
	}

	/**
	 * 基础 delete
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void delete(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.delete(getApplication(), url, getBaseHeader(), params, handler);
	}

	public void getNqzx(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {

		get(Constants.MY_HTTP_HOME + "main/nqzxItem", baseParams, handler);
	}

	public void getNqlt(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {

		get(Constants.MY_HTTP_HOME + "main/nqltItem", baseParams, handler);
	}

	public void getAisLtComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {

		get(Constants.MY_HTTP_HOME + "main/AisLtComment", baseParams, handler);
	}

	public void getAisZxComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/AisZxComment", baseParams, handler);
	}

	public void upLoadUserInfo(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		post(Constants.MY_HTTP_HOME + "/main/userInfo", baseParams, handler);
	}

	public void submitAisZxComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/submitAisZxComment", baseParams,
				handler);
	}

	// 调教农情论坛评论
	public void submitAisLtComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		post(Constants.MY_HTTP_HOME + "main/submitAisLtComment", baseParams,
				handler);
	}

	// 发布农情论坛动态
	public void publishNqlt(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/publishNqlt", baseParams, handler);
	}

	// 获取养分信息
	public void getSoil(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/getsoil", baseParams, handler);
	}

	public void addAnonymousUser(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/addAnonymousUser", baseParams,
				handler);

	}

	// 用户等信息

	public void queryQuestion(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/queryQuesByNick", baseParams,
				handler);
	}

	public void changeNick(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/changeNick", baseParams, handler);

	}

	public void changePwd(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/changePwd", baseParams, handler);

	}

	public void changeIcon(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/changeIcon", baseParams, handler);

	}

	public void checkUser(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/loginCommon", baseParams, handler);

	}

	public void UserRegister(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/register", baseParams, handler);

	}

	public void SecurityConform(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/SecurityConform", baseParams,
				handler);

	}

	public void UpdatePwd(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/UpdatePwd", baseParams, handler);

	}

	public void ThreadLogin(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		post(Constants.MY_HTTP_HOME + "main/ThreadLogin", baseParams, handler);

	}

	// 赞农情论坛和农情资讯评论
	public void zanAisLtComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/ZanAisLtComment", baseParams,
				handler);

	}

	public void zanAisZxComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/ZanAisZxComment", baseParams,
				handler);

	}

	// 取消 赞农情论坛和农情资讯评论
	public void cancleZanAisLtComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/cancleZanAisltComment", baseParams,
				handler);
	}

	public void cancleZanAisZxComment(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/cancleZanAisZxComment", baseParams,
				handler);

	}

	// 获取首页农情资讯
	public void getTopNqzxItem(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/getTopNqzxItem", baseParams, handler);

	}

	// 赞农情资讯和农情论坛
	public void zanNqzx(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/zanNqzx", baseParams, handler);

	}

	public void zanNqlt(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/zanNqlt", baseParams, handler);

	}

	// 取消 赞农情资讯和农情论坛
	public void cancleZanNqlt(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/cancleZanNqlt", baseParams, handler);
	}

	public void cancleZanNqzx(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/cancleZanNqzx", baseParams, handler);

	}

	public void getCollectNews(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		get(Constants.MY_HTTP_HOME + "main/getCollectNews", baseParams, handler);
	}

	public void getMyNqltCommented(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getMyNqltCommented", baseParams,
				handler);
	}

	public void getTopNews(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getTopNews", baseParams, handler);
	}

	public void getTopNewsContent(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getTopNewsContent", baseParams,
				handler);
	}

	// System.out.println("***************************");
	public void getWeatherData(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/weatherItem", baseParams, handler);
	}

	public void getTb02012Data(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getCloudItem", baseParams, handler);
	}

	public void getTb02018Data(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getWaterItem", baseParams, handler);
	}

	public void getTb02027Data(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getHumisityItem", baseParams,
				handler);
	}

	public void getTb02037Data(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getTemperatureItem", baseParams,
				handler);
	}

	public void getMyCommented(RequestParams baseParams,
			AsyncHttpResponseHandler handler) {
		get(Constants.MY_HTTP_HOME + "main/getMyCommented", baseParams, handler);

	}

}
