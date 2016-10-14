package com.android.app.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener.ListViewListener;
import android.Manifest.permission;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.adapter.ZhuTieAdapter;
import com.android.app.entity.AISDataBase;
import com.android.app.entity.AisLtCommentTrans;
import com.android.app.entity.MyNqltCommentedTrans;
import com.android.app.entity.MyNqltCommentedTrans.NqltInfoTrans;
import com.android.app.entity.NqltInfo;
import com.android.app.entity.ZhuTieTrans;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.android.app.util.PreferenceUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ZhuTieFragment extends Fragment implements OnItemClickListener{
	private XListView xListView = null;
	private ZhuTieAdapter zhuTieAdapter = null;
	private int page = 1;
	private ProgressBar progressBar= null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.notice_frame, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		xListView = (XListView) view.findViewById(R.id.id_xlistview);
		xListView.setPullLoadEnable(true);
		zhuTieAdapter = new ZhuTieAdapter(getActivity(), xListView);
		xListView.setAdapter(zhuTieAdapter);
		xListView.setOnItemClickListener(this);
		xListView.setXListViewListener(new ListViewListener(){

			@Override
			public void onRefresh() {
				super.onRefresh();
				zhuTieAdapter.ClearData();
				page = 1;
				getData(page, 5);
			}

			@Override
			public void onLoadMore() {
				super.onLoadMore();
				page+=1;
				getData(page, 5);
			}
			
		});
		progressBar=(ProgressBar) view.findViewById(R.id.id_progressbar_zhutie);
		progressBar.setVisibility(View.VISIBLE);
		getData(page, 5);
	}

	private void getData(int page, int pageSize) {
		String userId = PreferenceUtils.getPrefString(getActivity(), "UserId",
				"");
		System.out.println("userId:"+userId);
		if (userId.equals("")) {
			Toast.makeText(getActivity(), "登陆后才能查看通知", Toast.LENGTH_SHORT).show();
		} else {
			RequestParams baseParams = MyHttpAPIControl.getBaseParams();
			baseParams.add("page", String.valueOf(page));
			baseParams.add("pageSize", String.valueOf(pageSize));
			baseParams.add("userId", userId);
			MyHttpAPIControl.newInstance().getMyNqltCommented(baseParams,
					new AsyncHttpResponseHandler() {

						@Override
						public void onFinish() {
							super.onFinish();
							progressBar.setVisibility(View.GONE);
							xListView.stopLoadMore();
						}

						@Override
						public void onStart() {
							System.out.println("请求开始！！！------");
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							Type tp = new TypeToken<AISDataBase<MyNqltCommentedTrans>>() {
							}.getType();

							System.out.println("请求结果："+content);
							AISDataBase<MyNqltCommentedTrans> ss = (AISDataBase<MyNqltCommentedTrans>) ParseUtils
									.Gson2Object(content, tp);

							if (ss != null && ss.isState()) {
								ArrayList<MyNqltCommentedTrans> list = ss.getData();
								zhuTieAdapter.addData(list);
								xListView.stopRefresh();
								xListView.stopLoadMore();
								xListView.setRefreshTime(new Date().toLocaleString());
							}
						}

					});
		}
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		MyNqltCommentedTrans entitys = ((MyNqltCommentedTrans)zhuTieAdapter.getItem(position - 1));
		String userId = PreferenceUtils.getPrefString(getActivity(), "UserId", "");
		Intent intent = new Intent();
		intent.setClass(getActivity(), NqltContentActivity.class);
		
		intent.putExtra("nqltId", String.valueOf(entitys.getmNqltInfo().getContentid()));
		intent.putExtra("userId",userId);
		intent.putExtra("title", entitys.getmNqltInfo().getTitle());
		intent.putExtra("date", entitys.getmNqltInfo().getTime());
		intent.putExtra("content", entitys.getmNqltInfo().getContent());
		intent.putExtra("zanCount",
				String.valueOf(entitys.getmNqltInfo().getZancount()));
		intent.putExtra("zanflag", String.valueOf(entitys.getmNqltInfo().getZanFlag()));
		startActivity(intent);
	}
}
