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
import com.android.app.entity.CommonUser;

public class RegisterThread extends Thread {

	private Handler _Handler = null;

	// private Context _Context = null;

	private CommonUser mUser = null;
	private static final int REQUEST_TIMEOUT = 30 * 1000;// ��������ʱ10����
	private static final int SO_TIMEOUT = 59 * 1000; // ���õȴ����ݳ�ʱʱ��10����

	private String img = null;
	private String responseMsg = "";

	public RegisterThread(Handler _Handler, CommonUser mUser, String img) {
		this._Handler = _Handler;
		// this._Context = _Context;
		this.mUser = mUser;
		this.img = img;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// System.out.println("username=" + name + ":password=" + pwd);

		// URL�Ϸ���������һ��������֤�����Ƿ���ȷ
		boolean loginValidate = loginServer(mUser, img);
		System.out.println("----------------------------bool is :"
				+ loginValidate + "----------response:" + responseMsg);

		Message msg = _Handler.obtainMessage();

		if (loginValidate) {
			if (responseMsg.equals("success")) {
				msg.what = 0;
				_Handler.sendMessage(msg);
			} else if (responseMsg.equals("repeat")) {
				msg.what = 1;

				// msg.obj = responseMsg;
				_Handler.sendMessage(msg);
			} else {
				msg.what = 2;

				// msg.obj = responseMsg;
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

	public boolean loginServer(CommonUser user, String imgStr) {
		boolean loginValidate = false;
		// ʹ��apache HTTP�ͻ���ʵ��
		String urlStr = Constants.IP_ADDRESS;
		HttpPost request = new HttpPost(urlStr);
		// ������ݲ�����Ļ������ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// ����û���������
		params.add(new BasicNameValuePair("flag", "CommonRegister"));

		params.add(new BasicNameValuePair("data", user.toString()));
		params.add(new BasicNameValuePair("img", imgStr));
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
