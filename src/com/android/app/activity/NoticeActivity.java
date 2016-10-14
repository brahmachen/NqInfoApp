package com.android.app.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.ais.app.R;

public class NoticeActivity extends Activity implements ActionBar.TabListener {
	ActionBar actionBar = null;

	
	private Fragment zhuTieFragment=null;
	private Fragment huiFuFragment=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);

		actionBar = this.getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.addTab(actionBar.newTab().setText("主贴")
				.setTabListener(this));

		actionBar.addTab(actionBar.newTab().setText("回复")
				.setTabListener(this));
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("我的消息");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentTransaction action = this.getFragmentManager()
				.beginTransaction();
		hideAll(action);
		if (tab.getPosition() == 0) {
			if(zhuTieFragment==null){
				zhuTieFragment = new ZhuTieFragment();
				action.replace(R.id.container, zhuTieFragment);
			}else{
				action.show(zhuTieFragment);
			}
		}else if(tab.getPosition() == 1){
			if(huiFuFragment==null){
				huiFuFragment = new HuiFuFragment();
				action.add(R.id.container, huiFuFragment);
			}else{
				action.show(huiFuFragment);
			}
		}
		action.commit();
	}

	private void hideAll(FragmentTransaction action){
		if(zhuTieFragment!=null){
			action.hide(zhuTieFragment);
		}
		if(huiFuFragment!=null){
			action.hide(huiFuFragment);
		}
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}
}
