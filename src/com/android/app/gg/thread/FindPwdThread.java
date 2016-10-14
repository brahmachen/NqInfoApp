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
	private static final int REQUEST_TIMEOUT = 30 * 1000;// ��������ʱ10����
	private static final int SO_TIMEOUT = 59 * 1000; // ���õȴ����ݳ�ʱʱ��10����

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

		// URL�Ϸ���������һ��������֤�����Ƿ���ȷ
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
	 * �û���¼��֤
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return
	 */

	public boolean loginServer(String quest, String ans) {
		boolean loginValidate = false;
		// ʹ��apache HTTP�ͻ���ʵ��
		String urlStr = Constants.IP_ADDRESS;
		HttpPost request = new HttpPost(urlStr);
		// ������ݲ�����Ļ������ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// ����û���������
		params.add(new BasicNameValuePair("flag", "findPwd"));

		params.add(new BasicNameValuePair("question", question));
		params.add(new BasicNameValuePair("ans", ans));
		//params.add(new BasicNameValuePair("phone", phone));
		//params.add(new BasicNameValuePair("idcard", idcard));
		params.add(new BasicNameValuePair("userName", userName));

		// params.add(new BasicNameValuePair("password", password));
		try {
			// �������������
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// ִ�����󷵻���Ӧ
			HttpResponse response = client.execute(request);

			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				loginValidate = true;
				// �����Ӧ��Ϣ
				responseMsg = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginValidate;
	}

	// ��ʼ��HttpClient�������ó�ʱ
	public HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

}
