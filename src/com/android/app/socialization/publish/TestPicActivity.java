package com.android.app.socialization.publish;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.android.ais.app.R;

public class TestPicActivity extends Activity {
	// ArrayList<Entity> dataList;//����װ������Դ���б�
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// �Զ����������
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	
	private TextView quxiao ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// �ص�title
		setContentView(R.layout.nqlt_pub_selectpic_1);

		
		quxiao = (TextView) findViewById(R.id.nqlt_pub_select_pic1_quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		}) ; 
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * ��ʼ��view��ͼ
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(TestPicActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * ����position���������Ի�ø�GridView����View��󶨵�ʵ���࣬Ȼ���������isSelected״̬��
				 * ���ж��Ƿ���ʾѡ��Ч���� ����ѡ��Ч���Ĺ��������������Ĵ����л���˵��
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * ֪ͨ���������󶨵����ݷ����˸ı䣬Ӧ��ˢ����ͼ
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(TestPicActivity.this,
						ImageGridActivity.class);
				intent.putExtra(TestPicActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivity(intent);
				finish();
			}

		});
	}
}
