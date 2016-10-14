package com.example.ais;

import com.android.ais.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainPageActivity extends FragmentActivity implements
		OnClickListener {

	private LinearLayout layoutButtom = null;
	private LinearLayout layoutWeathReport = null;
	private LinearLayout layoutWeathQuery = null;
	private LinearLayout layoutWeathYears = null;

	private ImageView imgWeathReport = null;
	private ImageView imgWeatherQuery = null;
	private ImageView imgWeathYears = null;
 
	private Fragment mTab01 = null;
	private Fragment mTab02 = null;
	private Fragment mTab03 = null;

	// private Fragment nqltMainActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weath_main_fragment);
		initView();
		initEventts();
		setSelect(0);
	}

	private void initEventts() {
		layoutWeathQuery.setOnClickListener(this);
		layoutWeathReport.setOnClickListener(this);
		layoutWeathYears.setOnClickListener(this);

	}

	private void initView() {
		layoutButtom = (LinearLayout) findViewById(R.id.id_layout_buttom);
		layoutButtom.getBackground().setAlpha(255);
		layoutWeathReport = (LinearLayout) findViewById(R.id.id_tab_weathreport);
		layoutWeathQuery = (LinearLayout) findViewById(R.id.id_tab_weathQuery);
		layoutWeathYears = (LinearLayout) findViewById(R.id.id_tab_weathyears);

		imgWeatherQuery = (ImageView) findViewById(R.id.id_img_weathquery);
		imgWeathReport = (ImageView) findViewById(R.id.id_img_weathreport);
		imgWeathYears = (ImageView) findViewById(R.id.id_img_weathyears);

	}

	private void setSelect(int i) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			if (mTab01 == null) {
				mTab01 = new WeatherFragment();
				transaction.add(R.id.id_frame_content, mTab01);
			} else {
				transaction.show(mTab01);
			}
			imgWeathReport.setImageResource(R.drawable.tab_tqyb_pressed);

			break;
		case 1:
			if (mTab02 == null) {
				mTab02 = new WeatherQueryFragment();
				transaction.add(R.id.id_frame_content, mTab02);
			} else {
				transaction.show(mTab02);
			}
			imgWeatherQuery.setImageResource(R.drawable.tab_tqcx_pressed);

			break;
		case 2:
			if (mTab03 == null) {
				mTab03 = new HistoryWeatherFragment();
				transaction.add(R.id.id_frame_content, mTab03);
			} else {
				transaction.show(mTab03);
			}
			imgWeathYears.setImageResource(R.drawable.tab_wnqx_pressed);

			break;

		default:
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mTab01 != null)
			transaction.hide(mTab01);
		if (mTab02 != null)
			transaction.hide(mTab02);
		if (mTab03 != null)
			transaction.hide(mTab03);
	}

	@Override
	public void onClick(View v) {
		resetImg();
		switch (v.getId()) {
		case R.id.id_tab_weathreport:
			setSelect(0);
			break;
		case R.id.id_tab_weathQuery:
			setSelect(1);
			break;
		case R.id.id_tab_weathyears:
			setSelect(2);
			break;
		}
	}

	private void resetImg() {
		imgWeatherQuery.setImageResource(R.drawable.tab_tqcx_normal);
		imgWeathReport.setImageResource(R.drawable.tab_tqyb_normal);
		imgWeathYears.setImageResource(R.drawable.tab_wnqx_normal);
	}
}
