package com.android.app.gg.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

import com.android.ais.app.Constants;
import com.android.app.entity.User;

public class LoginThread extends Thread {

	private Handler _Handler = null;

	// private Context _Context = null;

	private String name;
	private String pwd;

	private User _User;
	private static final int REQUEST_TIMEOUT = 30 * 1000;// 设置请求超时10秒钟
	private static final int SO_TIMEOUT = 59 * 1000; // 设置等待数据超时时间10秒钟

	private String responseMsg = "";

	public LoginThread(Handler _Handler, String name, String pwd) {

		this._Handler = _Handler;
		// this._Context = _Context;
		this.name = name;
		this.pwd = pwd;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("username=" + name + ":password=" + pwd);
		// URL合法，但是这一步并不验证密码是否正确
		boolean loginValidate = loginServer(name, pwd);
		System.out.println("----------------------------bool is :"
				+ loginValidate + "----------response:" + responseMsg);
		Message msg = _Handler.obtainMessage();

		if (loginValidate) {
			if (!responseMsg.equals("fail")) {
				msg.what = 0;
				String[] param = responseMsg.split(",");
				_User = new User(name, param[0], param[1]);
				msg.obj = _User;
				_Handler.sendMessage(msg);
			} else {
				msg.what = 1;
				_Handler.sendMessage(msg);
			}

		} else {
			msg.what = 4;
			_Handler.sendMessage(msg);
		}

	}

	/**
	 * 用户登录验证
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public boolean loginServer(String username, String password) {
		boolean loginValidate = false;
		// 使用apache HTTP客户端实现
		String urlStr = Constants.IP_ADDRESS;
		HttpPost request = new HttpPost(urlStr);
		// 如果传递参数多的话，可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 添加用户名和密码
		params.add(new BasicNameValuePair("flag", "userLogin"));

		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				loginValidate = true;
				// 获得响应信息
				responseMsg = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginValidate;
	}

	// 初始化HttpClient，并设置超时
	public HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

}
