package com.android.app.activity;

import java.lang.reflect.Type;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ais.app.R;
import com.android.app.entity.Soil;
import com.android.app.util.FertilizerCalculate;
import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.ParseUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CtpfCardActivity extends Activity {

	// 参数
	private String paramter;
	int crop;

	private double N, P, K;
	FertilizerCalculate calculate = new FertilizerCalculate();

	String[] result = new String[4];
	String[] cropName = { "小麦", "玉米" };
	String xiaomaiStr;
	int flag; // 标志

	private ProgressDialog dialog_jiazai;

	// 创建对话框对象,不是通过new方法获得的，是通过builder.create();
	private AlertDialog dialog;
	private AlertDialog.Builder builder = null;
	// 压力泵对象，用于获取View对象
	private LayoutInflater inflater;
	// 声明LocationManager对象的引用

	TextView chunyangfen_chundan, chunyangfen_ko, chunyangfen_p2o5,
			jifei_fuhefei, zhuifei_zhuishi_niaosu, beizhu,
			ctpf_tv_title;
	ImageView ctpf_iv_back;
	private EditText targetEditText = null;

	String[] xiaomai, targetArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 关掉title
		setContentView(R.layout.ctpf_main_card_activity);
		// 初始化压力泵对象
		inflater = LayoutInflater.from(this);

		chunyangfen_chundan = (TextView) findViewById(R.id.ctpf_tv_soil_chundan_content);
		chunyangfen_ko = (TextView) findViewById(R.id.ctpf_tv_soil_ko_content);
		chunyangfen_p2o5 = (TextView) findViewById(R.id.ctpf_tv_soil_p2o5_content);
		jifei_fuhefei = (TextView) findViewById(R.id.ctpf_tv_soil_fuhefei_content);
		zhuifei_zhuishi_niaosu = (TextView) findViewById(R.id.ctpf_tv_soil_zhuishi_niaosu_content);
		beizhu = (TextView) findViewById(R.id.ctpf_card_jianyi);
		ctpf_tv_title = (TextView) findViewById(R.id.ctpf_tv_title);
		ctpf_iv_back = (ImageView) findViewById(R.id.ctpf_iv_back);
		dialog_jiazai = new ProgressDialog(CtpfCardActivity.this);
		targetEditText = (EditText) findViewById(R.id.ctpf_et_target);
		paramter = getIntent().getStringExtra("idxy");
		crop = getIntent().getIntExtra("crop", -1);
		ctpf_tv_title.setText("作物种类：" + cropName[crop]);
		Resources res = getResources();
		targetArray = res.getStringArray(R.array.ctpf_crop_target_xydm);
		// 创建一个对话框
		createDialog();
		xiaomai = res.getStringArray(R.array.ctpf_xiaomai);
		getData(paramter);
		
		ctpf_iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public void calculate(View view) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(CtpfCardActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		if (getText() == -1) {
			dialog.show();
		} else {
			getData(paramter);
		}
	}

	public double getText() {
		// TODO Auto-generated method stub
		double userTarget;
		try {
			userTarget = Double.parseDouble((String.valueOf(targetEditText
					.getText().toString().trim()))); // 获得输入的值
		} catch (Exception e) {
			userTarget = 0;
		}
		if (userTarget < Integer.parseInt(targetArray[crop * 2].trim())
				|| userTarget > Integer.parseInt(targetArray[crop * 2 + 1]
						.trim()) || userTarget == 0) {
			return -1;
		} else {
			return userTarget;
		}
	}

	public void UpdateTextView(Soil mSoil) {
		if (getText() != -1 && crop != -1) {

			N = calculate.calculateFertillizer(getText(),
					Double.parseDouble(mSoil.getContentM()), 0, crop);
			P = calculate.calculateFertillizer(getText(),
					Double.parseDouble(mSoil.getContentP()), 1, crop);
			K = calculate.calculateFertillizer(getText(),
					Double.parseDouble(mSoil.getContentK()), 2, crop);
			// 当前应该施多少肥料 = 计算出来了量 - 土壤中已有的量
			Double shi_N = N;
			Double shi_P = P;
			Double shi_K = K;
			
			/*Double shi_N = N-Double.parseDouble(mSoil.getContentN()); 
			Double shi_P =P-Double.parseDouble(mSoil.getContentP()); 
			Double shi_K =K-Double.parseDouble(mSoil.getContentK());*/
			
			// 纯养分推荐
			chunyangfen_chundan.setText(String.format("%.2f", shi_N > 0 ? shi_N
					: 0));
			chunyangfen_ko
					.setText(String.format("%.2f", shi_K > 0 ? shi_K : 0));
			chunyangfen_p2o5.setText(String.format("%.2f", shi_P > 0 ? shi_P
					: 0));

			if (crop == 0) { // 小麦
				Double niaosuDouble = (shi_N - (shi_P / 0.2) * 0.18) / 0.46; // 追施尿素的量
				jifei_fuhefei.setText(String.format("%.2f",
						shi_P / 0.2 > 0 ? shi_P / 0.2 : 0));
				zhuifei_zhuishi_niaosu.setText(String.format("%.2f",
						niaosuDouble > 0 ? niaosuDouble : 1.5));
				xiaomaiStr = xiaomai[0] + "\n" + xiaomai[1];
			} else if (crop == 1) { // 玉米
				Double jifei = ( (shi_N ) / 0.46 ) * 0.6   + shi_P + shi_K ; 
				Double niaosuDouble = ( (shi_N ) / 0.46 ) * 0.6 ;
				jifei_fuhefei.setText(String.format("%.2f",
						jifei > 0 ? jifei : 0 ));
				zhuifei_zhuishi_niaosu.setText(String.format("%.2f",
						niaosuDouble > 0 ? niaosuDouble : 1.5));
				xiaomaiStr = "";
			}
		}

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
						Toast.makeText(CtpfCardActivity.this, "获取数据失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						super.onStart();
						dialog_jiazai.show();
						// mFailure_view.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);

						dialog_jiazai.dismiss();
						Type tp = new TypeToken<Soil>() {
						}.getType();

						Soil mSoil = (Soil) ParseUtils.Gson2Object(content, tp);

						if (mSoil != null) {
							UpdateTextView(mSoil);
						}

					}

				});
	}

	// 对话框
	public View getLayout() {
		// 通过压力泵获取View
		LinearLayout ll = (LinearLayout) inflater.inflate(
				R.layout.ctpfcard_net_dialog, null);
		return ll;
	}

	public void createDialog() {
		builder = new AlertDialog.Builder(this);
		// 设置对话框的布局
		builder.setView(getLayout());
		builder.setTitle("提示框");

		builder.setIcon(R.drawable.ic_action_search);
		builder.setMessage("目标产量请设置在:\n" + targetArray[crop * 2] + "(公斤/亩)到"
				+ targetArray[crop * 2 + 1] + "(公斤/亩)范围内!");

		// 确定按钮的监听器
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						targetEditText.setText("");
						dialog.dismiss();
					}
				});
		// 取消按钮的监听器
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(CtpfCardActivity.this
										.getCurrentFocus().getWindowToken(),
										InputMethodManager.RESULT_SHOWN);
					}
				});
		//
		dialog = builder.create();
	}

}
