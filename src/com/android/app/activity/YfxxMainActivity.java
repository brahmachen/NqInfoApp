package com.android.app.activity;

import java.lang.reflect.Type;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.entity.Soil;
import com.android.app.util.FertilizerValue;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class YfxxMainActivity extends Activity {

	TextView tv_location, tv_N, tv_P, tv_K, tv_M, tv_PH, tv_N_pj, tv_P_pj,
			tv_K_pj, tv_M_pj, tv_PH_pj;

	String idxy;

	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.yfxx_main);
		initview();

	}

	public void initview() {
		dialog = new ProgressDialog(YfxxMainActivity.this);
		tv_location = (TextView) findViewById(R.id.yfxx_tv_location_content);
		tv_N = (TextView) findViewById(R.id.yfxx_tv_soil_N_content);
		tv_P = (TextView) findViewById(R.id.yfxx_tv_soil_P_content);
		tv_K = (TextView) findViewById(R.id.yfxx_tv_soil_K_content);
		tv_M = (TextView) findViewById(R.id.yfxx_tv_soil_M_content);
		tv_PH = (TextView) findViewById(R.id.yfxx_tv_soil_ph_content);
		tv_N_pj = (TextView) findViewById(R.id.yfxx_tv_soil_N_pj);
		tv_P_pj = (TextView) findViewById(R.id.yfxx_tv_soil_P_pj);
		tv_K_pj = (TextView) findViewById(R.id.yfxx_tv_soil_K_pj);
		tv_M_pj = (TextView) findViewById(R.id.yfxx_tv_soil_M_pj);
		tv_PH_pj = (TextView) findViewById(R.id.yfxx_tv_soil_ph_pj);

		// 请求调用webService中函数的参数
		idxy = getIntent().getStringExtra("location");

		String loc = getIntent().getStringExtra("loc");

		String position;
		if (loc == null) {
			position = "地理位置暂时无法解析";
		} else {
			position = loc;
		}

		tv_location.setText(position);
		getData(idxy);

		findViewById(R.id.yfxx_iv_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	public void UpdateTextView(Soil mSoil) {
		FertilizerValue fer = new FertilizerValue();
		tv_N.setText(String.format("%.1f",
				Double.parseDouble(mSoil.getContentN()))
				+ "mg/kg");
		tv_P.setText(String.format("%.1f",
				Double.parseDouble(mSoil.getContentP()))
				+ "mg/kg");
		tv_K.setText(String.format("%.1f",
				Double.parseDouble(mSoil.getContentK()))
				+ "mg/kg");
		tv_M.setText(String.format("%.1f",
				Double.parseDouble(mSoil.getContentM()))
				+ "g/kg");

		tv_PH.setText(Math.round((Double.parseDouble(mSoil.getPh()) * 100) / 100)
				+ "");

		tv_N_pj.setText(fer.fertilizervalue(0, mSoil.getContentN()));
		tv_P_pj.setText(fer.fertilizervalue(1, mSoil.getContentP()));
		tv_K_pj.setText(fer.fertilizervalue(2, mSoil.getContentK()));
		tv_M_pj.setText(fer.fertilizervalue(3, mSoil.getContentM()));
		tv_PH_pj.setText(fer.fertilizervalue(4, mSoil.getPh()));

	}

	public void b1(View view) { // 小麦

		Intent intent = new Intent();
		intent.setClass(YfxxMainActivity.this, CtpfCardActivity.class);
		intent.putExtra("crop", 0);
		intent.putExtra("idxy", idxy);
		startActivity(intent);
	}

	public void b2(View view) { // 玉米
		Intent intent = new Intent();
		intent.setClass(YfxxMainActivity.this, CtpfCardActivity.class);
		intent.putExtra("idxy", idxy);
		intent.putExtra("crop", 1);
		startActivity(intent);

	}

	public void getData(String idxy) {
		RequestParams baseParams = MyHttpAPIControl.getBaseParams();

		baseParams.add("idxy", idxy);

		MyHttpAPIControl.newInstance().getSoil(baseParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						// mFailure_view.setVisibility(View.VISIBLE);
						Toast.makeText(YfxxMainActivity.this, "获取数据失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						super.onStart();
						dialog.show();
						// mFailure_view.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);

						dialog.dismiss();
						Type tp = new TypeToken<Soil>() {
						}.getType();

						Soil mSoil = (Soil) ParseUtils.Gson2Object(content, tp);

						if (mSoil != null) {
							UpdateTextView(mSoil);
						}

					}

				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}

}