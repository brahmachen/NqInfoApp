package com.android.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class Util {

	public static String TAG = "UTIL";

	public static Bitmap getbitmap(String imageUri) {
		// /Log.v(TAG, "getbitmap:" + imageUri);
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			// Log.v(TAG, "image download finished." + imageUri);
		} catch (IOException e) {
			e.printStackTrace();
			// Log.v(TAG, "getbitmap bmp fail---");
			return null;
		}
		return bitmap;
	}

	public static boolean phoneCheck(String str, Context mContext) {

		if (str.length() != 11) {
			Toast.makeText(mContext, "手机号位数不足!", Toast.LENGTH_SHORT).show();
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {
			Toast.makeText(mContext, "手机号格式不正确!", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	public static boolean IDCheck(String str, Context mContext, Toast mToast) {
		return false;

	}
}
