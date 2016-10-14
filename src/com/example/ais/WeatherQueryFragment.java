package com.example.ais;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.ais.app.R;
import com.android.app.util.MyHttpAPIControl;
import com.android.utils.AISDataBase;
import com.android.utils.ParseUtils;
import com.android.utils.WeatherDayTrans;
import com.example.ais.WeatherFragment.ThreadGetWeatherData;
import com.example.weather.City;
import com.example.weather.GetPro;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WeatherQueryFragment extends Fragment implements OnClickListener {

	private Button buttonShowBeginDatePaker = null;
	private Button buttonShowEndDatePaker = null;
	private String startDate = "20150719";
	private String endDate = "20150730";
	private TableLayout tableLayout = null;
	private TableRow tableRowHead = null;

	private LineChart lineChart = null;
	private LineData lineData = null;

	private SharedPreferences sp = null;
	private Editor editor = null;
	private String cityCode = "101120801";

	private TextView cityTextView = null;
	private List<City> cityList = null;
	
	private ImageView imgChangeCity = null;
	private TextView textChangeCity = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View thisView = inflater.inflate(R.layout.tab02, container, false);
		initView(thisView);
		initEvents();
		return thisView;
	}

	@Override
	public void onResume() {
		super.onResume();
		sp = getActivity().getSharedPreferences("cityinfo",
				getActivity().MODE_WORLD_READABLE);
		editor = sp.edit();
		String codeString = sp.getString("citycode", "");
		// System.out.println("��ȡ�����ǣ�"+codeString);
		if ((!codeString.equals("")) && (!codeString.equals(cityCode))) {
			cityCode = codeString;
			getTheData();
		}
	}

	private void initEvents() {
		buttonShowBeginDatePaker.setOnClickListener(this);
		buttonShowEndDatePaker.setOnClickListener(this);
		
		imgChangeCity.setOnClickListener(this);
		textChangeCity.setOnClickListener(this);
		          
//		Calendar calendar = Calendar.getInstance();
//		int yearString = calendar.get(Calendar.YEAR);
//		int monthString = calendar.get(Calendar.MONTH);
//		int dayOfString = calendar.get(Calendar.DAY_OF_MONTH);
//		String a1;
//		monthString += 1;
//		if (monthString <= 9) {
//			a1 = "0" + monthString;
//		} else {
//			a1 = monthString + "";
//		}
//		String a2;
//		if (dayOfString <= 9) {
//			a2 = "0" + dayOfString;
//		} else {
//			a2 = dayOfString + "";
//		}
		//endDate = yearString + a1 + a2;
		getTheData();
	}

	private void initView(View view) {

		// ��ȡ�û����õĳ���
		sp = getActivity().getSharedPreferences("cityinfo",
				getActivity().MODE_WORLD_READABLE);
		editor = sp.edit();
		String codeString = sp.getString("citycode", "");
		System.out.println("��ȡ�����ǣ�" + codeString);
		if (!codeString.equals("")) {
			cityCode = codeString;
		}

		buttonShowBeginDatePaker = (Button) view
				.findViewById(R.id.button_showbegindatepaker);
		buttonShowEndDatePaker = (Button) view
				.findViewById(R.id.button_showenddatepaker);
		lineChart = (LineChart) view.findViewById(R.id.linechart_weather_query);
		tableLayout = (TableLayout) view.findViewById(R.id.id_layout_table1);
		tableRowHead = (TableRow) tableLayout
				.findViewById(R.id.id_tablerow_head);

		imgChangeCity = (ImageView) view
				.findViewById(R.id.id_img_changecity);
		textChangeCity = (TextView) view
				.findViewById(R.id.id_text_changcity);
		cityTextView = (TextView) view.findViewById(R.id.id_text_city);
		
		cityList = GetPro.getPro(getActivity());
		for (City city : cityList) {
			if (cityCode.equals(city.getCityCode())) {
				cityTextView.setText(city.getCituName());
				break;
			}
		}
	}

	protected Dialog onCreateDialog(Button button) {
		Dialog dialog = null;
		if (button.getId() == R.id.button_showbegindatepaker) {
			Calendar calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(getActivity(),
					new MyOnDateSetListener1(), calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		} else if (button.getId() == R.id.button_showenddatepaker) {
			Calendar calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(getActivity(),
					new MyOnDateSetListener2(), calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		}
		return dialog;
	}

	class MyOnDateSetListener1 implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			monthOfYear += 1;
			String myDate = year + "/" + monthOfYear + "/" + dayOfMonth;
			buttonShowBeginDatePaker.setText(myDate);
			System.out.println("----" + year + " " + monthOfYear + " "
					+ dayOfMonth);
			String monthString;
			String dayString;
			if (monthOfYear < 10) {
				monthString = "0" + monthOfYear;
			} else {
				monthString = "" + monthOfYear;
			}
			if (dayOfMonth < 10) {
				dayString = "0" + dayOfMonth;
			} else {
				dayString = "" + dayOfMonth;
			}
			startDate = "" + year + monthString + dayString;
		}
	}

	class MyOnDateSetListener2 implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			monthOfYear += 1;
			String myDate = year + "/" + monthOfYear + "/" + dayOfMonth;
			buttonShowEndDatePaker.setText(myDate);

			String monthString;
			String dayString;
			if (monthOfYear < 10) {
				monthString = "0" + monthOfYear;
			} else {
				monthString = "" + monthOfYear;
			}
			if (dayOfMonth < 10) {
				dayString = "0" + dayOfMonth;
			} else {
				dayString = "" + dayOfMonth;
			}
			endDate = "" + year + monthString + dayString;
			System.out.println("----" + year + " " + monthOfYear + " "
					+ dayOfMonth);

			getTheData();
			System.out.println("startDate" + startDate);
			System.out.println("endDate" + endDate);

		}

	}

	private void getTheData() {
		RequestParams requestParams = MyHttpAPIControl.getBaseParams();
		requestParams.add("startDate", startDate);
		requestParams.add("endDate", endDate);
		requestParams.add("cityId", cityCode);

		System.out.println("startDate:" + startDate);
		System.out.println("endDate:" + endDate);
		System.out.println("cityId:" + cityCode);
		MyHttpAPIControl.newInstance().getWeatherData(requestParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						super.onFinish();
					}

					@Override
					public void onStart() {
						System.out.println("----------------------start");
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Type type = new TypeToken<AISDataBase<WeatherDayTrans>>() {
						}.getType();
						AISDataBase<WeatherDayTrans> aw = (AISDataBase<WeatherDayTrans>) ParseUtils
								.Gson2Object(content, type);
						// System.out.println(aw);
						if (aw != null && aw.isState()) {
							ArrayList<WeatherDayTrans> weatherDatas = aw
									.getData();
							for (WeatherDayTrans weatherDayTrans : weatherDatas) {
								System.out.println(weatherDayTrans);
							}
							lineData = getLineData(weatherDatas);
							showChart();
							showTable(weatherDatas);
							cityList = GetPro.getPro(getActivity());
							for (City city : cityList) {
								if (cityCode.equals(city.getCityCode())) {
									cityTextView.setText(city.getCituName());
									break;
								}
							}
						}
					}
				});
	}

	private void showTable(ArrayList<WeatherDayTrans> weatherDatas) {
		tableLayout.removeAllViews();

		tableLayout.addView(tableRowHead);
		for (WeatherDayTrans weatherDayTrans : weatherDatas) {
			TableRow tableRow1 = new TableRow(getActivity());
			TextView tableDateTextView = new TextView(getActivity());
			TextView tableTempTextView = new TextView(getActivity());
			TextView tableFxTextView = new TextView(getActivity());
			TextView tableFlTextView = new TextView(getActivity());
			TextView tableWeatherTextView = new TextView(getActivity());

			tableDateTextView.setText(weatherDayTrans.getWeatherDate());
			tableTempTextView.setText(weatherDayTrans.getTemp());
			tableFxTextView.setText(weatherDayTrans.getFx());
			tableFlTextView.setText(weatherDayTrans.getFl());
			tableWeatherTextView.setText(weatherDayTrans.getWeatherType());

			tableDateTextView.setTextColor(Color.WHITE);
			tableTempTextView.setTextColor(Color.WHITE);
			tableFxTextView.setTextColor(Color.WHITE);
			tableFxTextView.setTextColor(Color.WHITE);
			tableFlTextView.setTextColor(Color.WHITE);
			tableWeatherTextView.setTextColor(Color.WHITE);

			tableRow1.addView(tableDateTextView);
			tableRow1.addView(tableTempTextView);
			tableRow1.addView(tableFxTextView);
			tableRow1.addView(tableFlTextView);
			tableRow1.addView(tableWeatherTextView);
			tableRow1.setPadding(10, 5, 10, 5);
			tableLayout.addView(tableRow1);
		}
	}

	private void showChart() {
		lineChart.setDrawBorders(false); // �Ƿ�������ͼ����ӱ߿�
		// no description text
		lineChart.setDescription("");// ��������
		// ���û�����ݵ�ʱ�򣬻���ʾ���������listview��emtpyview
		lineChart
				.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid background
		lineChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����

		// enable touch gestures
		lineChart.setTouchEnabled(true); // �����Ƿ���Դ���

		// enable scaling and dragging
		lineChart.setDragEnabled(true);// �Ƿ������ק
		lineChart.setScaleEnabled(true);// �Ƿ��������
		lineChart.fitScreen();//������¼��ؽ�������ʱ  x�᳤�Ȳ��Զ���Ӧ������

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//

		// lineChart.setBackgroundColor(color);// ���ñ���

		// add data
		lineChart.setData(lineData); // ��������
		lineChart.setVisibleXRange(7);// ���ÿ��ӵ����������

		lineChart.animateX(2500); // ����ִ�еĶ���,x��
		// get the legend (only possible after setting data)
		Legend mLegend = lineChart.getLegend(); // ���ñ���ͼ��ʾ�������Ǹ�һ��y��value��

		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// ��ʽ
		mLegend.setFormSize(6f);// ����
		mLegend.setTextColor(Color.WHITE);// ��ɫ
		// mLegend.setTypeface(mTf);// ����

		XAxis xAxis = lineChart.getXAxis();
		// xAxis.setEnabled(false);// ����x������
		xAxis.setPosition(XAxisPosition.BOTTOM);// ����x��λ��
		xAxis.setTextSize(10);
		xAxis.setTextColor(Color.rgb(255, 255, 255));

		YAxis yAxis = lineChart.getAxisLeft();
		// yAxis.setShowOnlyMinMax(true);
		// yAxis.setAxisMinValue(20);
		yAxis.setStartAtZero(false);
		yAxis.setEnabled(false);// ����y������
		// yAxis.setSpaceBottom((float) 0.4);//���õײ��ռ�
		// yAxis.setLabelCount(3);//y���ϵı�ǩ����ʾ�ĸ���

		YAxis yAxis2 = lineChart.getAxisRight();
		yAxis2.setStartAtZero(false);
		yAxis2.setEnabled(false);
	}

	private LineData getLineData(ArrayList<WeatherDayTrans> list) {

		ArrayList<String> xValues = new ArrayList<String>();

		int num = 0;
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		ArrayList<Entry> yValues2 = new ArrayList<Entry>();
		for (WeatherDayTrans weatherDayTrans : list) {
			xValues.add(num, weatherDayTrans.getWeatherDate());
			String temp = weatherDayTrans.getTemp();
			String highTemp = "";
			String lowTemp = "";
			boolean m = false;
			for (int i = 0; i < temp.length(); i++) {
				char a = temp.charAt(i);
				if (a == '~')
					m = true;
				if (a >= '0' && a <= '9' || a == '-') {
					if (m == false) {
						highTemp += a;
					} else {
						lowTemp += a;
					}
				}
			}
			System.out.println(highTemp + "   " + lowTemp);
			if ("".equals(lowTemp)) {
				yValues.add(new Entry(Float.parseFloat(highTemp), num));
				yValues2.add(new Entry(Float.parseFloat(highTemp), num));
			} else {
				yValues.add(new Entry(Float.parseFloat(highTemp), num));
				yValues2.add(new Entry(Float.parseFloat(lowTemp), num));
			}
			num++;
		}
		// create a dataset and give it a type
		// y������ݼ���
		LineDataSet lineDataSet = new LineDataSet(yValues, "�����" /* ��ʾ�ڱ���ͼ�� */);

		LineDataSet lineDataSet2 = new LineDataSet(yValues2, "�����");
		// mLineDataSet.setFillAlpha(110);
		// mLineDataSet.setFillColor(Color.RED);

		// ��y��ļ��������ò���
		lineDataSet.setLineWidth(1.75f); // �߿�
		lineDataSet.setCircleSize(5f);// ��ʾ��Բ�δ�С
		lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ
		lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ
		lineDataSet.setHighLightColor(Color.WHITE); // �������ߵ���ɫ
		lineDataSet.setValueTextSize(10f);
		lineDataSet.setValueTextColor(Color.WHITE);

		lineDataSet2.setLineWidth(1.75f); // �߿�
		lineDataSet2.setCircleSize(5f);// ��ʾ��Բ�δ�С
		lineDataSet2.setColor(Color.WHITE);// ��ʾ��ɫ
		lineDataSet2.setCircleColor(Color.WHITE);// Բ�ε���ɫ
		lineDataSet2.setHighLightColor(Color.WHITE); // �������ߵ���ɫ
		lineDataSet2.setValueTextSize(10f);
		lineDataSet2.setValueTextColor(Color.WHITE);

		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets
		lineDataSets.add(lineDataSet2);

		// create a data object with the datasets
		LineData lineData = new LineData(xValues, lineDataSets);
		System.out.println(lineData.getYValueSum());
		return lineData;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button_showbegindatepaker) {
			onCreateDialog(buttonShowBeginDatePaker).show();
		} else if (v.getId() == R.id.button_showenddatepaker) {
			onCreateDialog(buttonShowEndDatePaker).show();
		} else if (v.getId() == R.id.id_img_changecity) {
			getActivity().finish();

		} else if (v.getId() == R.id.id_text_changcity) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), ChangeCityActivity.class);
			startActivity(intent);
		}
	}

}
