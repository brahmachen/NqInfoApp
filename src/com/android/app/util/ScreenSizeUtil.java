package com.android.app.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenSizeUtil {

	private static DisplayMetrics metrics;

	/**
	 * ���ݾ��Գߴ�õ���Գߴ磬�ڲ�ͬ�ķֱ����豸����һ�µ���ʾЧ��, dip->pix
	 * 
	 * @param context
	 * @param givenAbsSize
	 *            ���Գߴ�
	 * @return
	 */
	public static int getSizeByGivenAbsSize(Context context, int givenAbsSize) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				givenAbsSize, context.getResources().getDisplayMetrics());
	}

	private static DisplayMetrics getDisplayMetrics(Context context) {
		if (metrics != null) {
			return metrics;
		}
		metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		return metrics;
	}

	public static int getScreenWidth(Context context) {
		return getDisplayMetrics(context).widthPixels; // ��Ļ��ȣ����أ�
	}

	public static int getScreenHeight(Context context) {
		return getDisplayMetrics(context).heightPixels;// ��Ļ�߶ȣ����أ�
	}

	public static float getScreenDensity(Context context) {
		return getDisplayMetrics(context).density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
	}

	public static int getScreenDensityDpi(Context context) {
		return getDisplayMetrics(context).densityDpi;// ��Ļ�ܶ�DPI��120 / 160 / 240��
	}

	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * ��pxֵת��Ϊspֵ����֤���ִ�С����
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(float pxValue, Context context) {
		return (int) (pxValue
				/ context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
	}

	/**
	 * ��spֵת��Ϊpxֵ����֤���ִ�С����
	 * 
	 * @param spValue
	 * @return
	 */
	public static int sp2px(float spValue, Context context) {
		return (int) (spValue
				* context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
	}
}
