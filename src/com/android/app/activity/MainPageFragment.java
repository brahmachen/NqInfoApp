package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.ais.app.AppApplication;
import com.android.ais.app.R;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.NqzxInfo;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainPageFragment extends Fragment implements OnItemClickListener {

	private Activity mInstance;

	private ListView mainPageListView = null;
	private List<NqzxInfo> adatperData = null;
	
	private String zan_userId ;

	List<NqzxInfo> adatperData0 = new ArrayList<NqzxInfo>();
	List<NqzxInfo> adatperData1 = new ArrayList<NqzxInfo>();
	List<NqzxInfo> adatperData2 = new ArrayList<NqzxInfo>();
	
	private ProgressBar progressBar = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_mainpage_listview,
				container, false);
		mInstance = getActivity();

		if(!"NULL".equals(PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL"))){
			zan_userId = PreferenceUtils.getPrefString(AppApplication.getInstance(), "UserId", "NULL") ; 
		}else if(!"NULL".equals(PreferenceUtils.getPrefString(AppApplication.getInstance(), "AnonymousUserId", "NULL"))){
			zan_userId = PreferenceUtils.getPrefString(AppApplication.getInstance(), "AnonymousUserId", "NULL") ; 
		}else{
			zan_userId="-1";
		}
		
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		RequestParams requestParams = MyHttpAPIControl.getBaseParams();
		requestParams.add("userId", zan_userId);
		MyHttpAPIControl.newInstance().getTopNqzxItem(requestParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						super.onFinish();
					}

					@Override
					public void onStart() {
						System.out.println("---requestStart---");
						super.onStart();
						progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Type type = new TypeToken<AISDataBase<NqzxInfo>>() {
						}.getType();
						AISDataBase<NqzxInfo> an = (AISDataBase<NqzxInfo>) ParseUtils
								.Gson2Object(content, type);
						if (an != null && an.isState()) {
							adatperData = an.getData();
							for (NqzxInfo nqzxInfo : adatperData) {
								if (nqzxInfo.getModelid().intValue() == 3) {
									adatperData0.add(nqzxInfo);
								} else if (nqzxInfo.getModelid().intValue() == 4) {
									adatperData1.add(nqzxInfo);
								} else if (nqzxInfo.getModelid().intValue() == 2) {
									adatperData2.add(nqzxInfo);
								}
							}

							MainPageAdapter mainPageAdapter = new MainPageAdapter(
									getActivity(), adatperData0, adatperData1,
									adatperData2,mainPageListView);
							mainPageListView.setAdapter(mainPageAdapter);
							progressBar.setVisibility(View.GONE);
						}
					}

				});
	}

	private void initView(View view) {
		mainPageListView = (ListView) view
				.findViewById(R.id.id_listview_mainpage);
		MainPageAdapter mainPageAdapter = new MainPageAdapter(getActivity());
		mainPageListView.setAdapter(mainPageAdapter);
		mainPageListView.setOnItemClickListener(this);
		mainPageListView.setDividerHeight(0);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar_maipage_loading);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (position == 0) {
		} else if (position == 1) {
		} else if (position > 1 && position <= adatperData0.size() + 1) {
			NqzxInfo entitys = adatperData0.get(position - 2);
			Intent intent = new Intent();
			intent.setClass(getActivity(), NqZxContentActivity.class);
			intent.putExtra("nqzxId", String.valueOf(entitys.getContentid()));
			intent.putExtra("userId", String.valueOf(entitys.getUser().getId()));
			intent.putExtra("title", entitys.getTitle());
			intent.putExtra("time", entitys.getTime());
			intent.putExtra("content", entitys.getContent());
			intent.putExtra("zanCount", String.valueOf(entitys.getZancount()));
			intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
			intent.putExtra("isHaveData", "1");
			startActivity(intent);
		} else if (position == adatperData0.size() + 2) {
		} else if (position >= adatperData0.size() + 3
				&& position < adatperData1.size() + adatperData0.size() + 3) {
			NqzxInfo entitys = adatperData1.get(position - adatperData0.size()
					- 3);
			Intent intent = new Intent();
			intent.setClass(getActivity(), NqZxContentActivity.class);
			intent.putExtra("nqzxId", String.valueOf(entitys.getContentid()));
			intent.putExtra("userId", String.valueOf(entitys.getUser().getId()));
			intent.putExtra("title", entitys.getTitle());
			intent.putExtra("time", entitys.getTime());
			intent.putExtra("content", entitys.getContent());
			intent.putExtra("zanCount", String.valueOf(entitys.getZancount()));
			intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
			intent.putExtra("isHaveData", "1");
			startActivity(intent);
		} else if (position == adatperData0.size() + adatperData1.size() + 3) {
		} else {
			NqzxInfo entitys = adatperData2.get(position - adatperData0.size()
					- adatperData1.size() - 4);
			Intent intent = new Intent();
			intent.setClass(getActivity(), NqZxContentActivity.class);
			intent.putExtra("nqzxId", String.valueOf(entitys.getContentid()));
			intent.putExtra("userId", String.valueOf(entitys.getUser().getId()));
			intent.putExtra("title", entitys.getTitle());
			intent.putExtra("time", entitys.getTime());
			intent.putExtra("content", entitys.getContent());
			intent.putExtra("zanCount", String.valueOf(entitys.getZancount()));
			intent.putExtra("zanflag", String.valueOf(entitys.getZanFlag()));
			intent.putExtra("isHaveData", "1");
			startActivity(intent);
		}
	}
}
