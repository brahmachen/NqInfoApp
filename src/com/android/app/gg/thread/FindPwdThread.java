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

public class FindPwdThread extends Thread {

	private Handler _Handler = null;

	// private Context _Context = null;

	private String question;
	private String ans;

	//private String phone;
	//private String idcard;

	private String userName;
	private static final int REQUEST_TIMEOUT = 30 * 1000;// 设置请求超时10秒钟
	private static final int SO_TIMEOUT = 59 * 1000; // 设置等待数据超时时间10秒钟

	private String responseMsg = "";

	public FindPwdThread(Handler mHandler, String userName, String question,
			String ans) {
		super();
		this.question = question;
		this.ans = ans;
		//this.phone = phone;
		//this.idcard = idcard;
		this._Handler = mHandler;
		this.userName = userName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// System.out.println("username=" + name + ":password=" + pwd);

		// URL合法，但是这一步并不验证密码是否正确
		boolean loginValidate = loginServer(question, ans);
		System.out.println("----------------------------bool is :"
				+ loginValidate + "----------response:" + responseMsg);

		Message msg = _Handler.obtainMessage();

		if (loginValidate) {
			if (responseMsg.equals("success")) {
				msg.what = 0;
				_Handler.sendMessage(msg);
			} else {
				msg.what = 1;

				msg.obj = responseMsg;
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

	public boolean loginServer(String quest, String ans) {
		boolean loginValidate = false;
		// 使用apache HTTP客户端实现
		String urlStr = Constants.IP_ADDRESS;
		HttpPost request = new HttpPost(urlStr);
		// 如果传递参数多的话，可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 添加用户名和密码
		params.add(new BasicNameValuePair("flag", "findPwd"));

		params.add(new BasicNameValuePair("question", question));
		params.add(new BasicNameValuePair("ans", ans));
		//params.add(new BasicNameValuePair("phone", phone));
		//params.add(new BasicNameValuePair("idcard", idcard));
		params.add(new BasicNameValuePair("userName", userName));

		// params.add(new BasicNameValuePair("password", password));
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
