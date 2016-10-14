package com.example.ais;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.android.ais.app.R;
import com.example.weather.WeatherDate;
import com.example.weather.WeatherUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends Fragment implements OnClickListener {

	private LineChart lineChart = null;
	private LineData lineData = null;
	private WeatherDate weatherDate = null;
	private String cityCode = "101120801";

	private ImageView imgChangeCity = null;
	private SharedPreferences sp = null;
	private Editor editor = null;

	private TextView textViewCity = null;
	private TextView textViewPMInfo = null;
	private TextView textViewWeatherType = null;
	private TextView textViewTemp = null;
	private TextView textViewWeathInfo = null;
	private TextView textViewTodayTemp = null;
	private TextView textViewTodayWeather = null;
	private TextView textViewTomTemp = null;
	private TextView textViewTomWeather = null;

	private TextView week_1 = null;
	private TextView week_2 = null;
	private TextView week_3 = null;
	private TextView week_4 = null;
	private TextView week_5 = null;
	private TextView week_6 = null;

	private TextView date_1 = null;
	private TextView date_2 = null;
	private TextView date_3 = null;
	private TextView date_4 = null;
	private TextView date_5 = null;
	private TextView date_6 = null;
	private TextView weath_1 = null;
	private TextView weath_2 = null;
	private TextView weath_3 = null;
	private TextView weath_4 = null;
	private TextView weath_5 = null;
	private TextView weath_6 = null;

	private TextView temp_1 = null;
	private TextView temp_2 = null;
	private TextView temp_3 = null;
	private TextView temp_4 = null;
	private TextView temp_5 = null;
	private TextView temp_6 = null;
	
	private ImageView todyImg = null;
	private ImageView temoImg = null;

	private TextView textChangeCity = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View thisView = inflater.inflate(R.layout.layout_weatherpage,
				container, false);

		initView(thisView);
		initEvents();
		// initData();
		return thisView;
	}

	@Override
	public void onResume() {
		super.onResume();
		sp = getActivity().getSharedPreferences("cityinfo",
				getActivity().MODE_WORLD_READABLE);
		editor = sp.edit();
		String codeString = sp.getString("citycode", "");
		// System.out.println("读取出的是："+codeString);
		if ((!codeString.equals("")) && (!codeString.equals(cityCode))) {
			cityCode = codeString;
			// progressBar.setVisibility(View.VISIBLE);
			ThreadGetWeatherData connectThread = new ThreadGetWeatherData();
			connectThread.start();
		}
	}

	private void initEvents() {
		imgChangeCity.setOnClickListener(this);
		textChangeCity.setOnClickListener(this);
	}

	private void initView(View thisView) {

		// 读取用户设置的城市
		sp = getActivity().getSharedPreferences("cityinfo",
				getActivity().MODE_WORLD_READABLE);
		editor = sp.edit();
		String codeString = sp.getString("citycode", "");
		System.out.println("读取出的是：" + codeString);
		if (!codeString.equals("")) {
			cityCode = codeString;
		}

		lineChart = (LineChart) thisView.findViewById(R.id.id_linechart_1);
		// lineData = getLineData(null);
		// showChart(lineChart, null);
		textViewCity = (TextView) thisView.findViewById(R.id.id_text_city);
		textViewPMInfo = (TextView) thisView.findViewById(R.id.id_text_pm);
		textViewWeatherType = (TextView) thisView
				.findViewById(R.id.id_text_weathertype);
		textViewTemp = (TextView) thisView.findViewById(R.id.id_text_temp);
		textViewWeathInfo = (TextView) thisView
				.findViewById(R.id.id_text_otherinfo);
		textViewTodayTemp = (TextView) thisView
				.findViewById(R.id.id_text_todaytemp);
		textViewTodayWeather = (TextView) thisView
				.findViewById(R.id.id_text_todyweather);
		textViewTomTemp = (TextView) thisView
				.findViewById(R.id.id_text_temotemp);
		textViewTomWeather = (TextView) thisView
				.findViewById(R.id.id_text_temoweather);

		week_1 = (TextView) thisView.findViewById(R.id.id_week_11);
		week_2 = (TextView) thisView.findViewById(R.id.id_week_22);
		week_3 = (TextView) thisView.findViewById(R.id.id_week_33);
		week_4 = (TextView) thisView.findViewById(R.id.id_week_44);
		week_5 = (TextView) thisView.findViewById(R.id.id_week_55);
		week_6 = (TextView) thisView.findViewById(R.id.id_week_66);

		date_1 = (TextView) thisView.findViewById(R.id.id_date_11);
		date_2 = (TextView) thisView.findViewById(R.id.id_date_22);
		date_3 = (TextView) thisView.findViewById(R.id.id_date_33);
		date_4 = (TextView) thisView.findViewById(R.id.id_date_44);
		date_5 = (TextView) thisView.findViewById(R.id.id_date_55);
		date_6 = (TextView) thisView.findViewById(R.id.id_date_66);

		weath_1 = (TextView) thisView.findViewById(R.id.id_weath_11);
		weath_2 = (TextView) thisView.findViewById(R.id.id_weath_22);
		weath_3 = (TextView) thisView.findViewById(R.id.id_weath_33);
		weath_4 = (TextView) thisView.findViewById(R.id.id_weath_44);
		weath_5 = (TextView) thisView.findViewById(R.id.id_weath_55);
		weath_6 = (TextView) thisView.findViewById(R.id.id_weath_66);

		temp_1 = (TextView) thisView.findViewById(R.id.id_temp_11);
		temp_2 = (TextView) thisView.findViewById(R.id.id_temp_22);
		temp_3 = (TextView) thisView.findViewById(R.id.id_temp_33);
		temp_4 = (TextView) thisView.findViewById(R.id.id_temp_44);
		temp_5 = (TextView) thisView.findViewById(R.id.id_temp_55);
		temp_6 = (TextView) thisView.findViewById(R.id.id_temp_66);
		
		imgChangeCity = (ImageView) thisView
				.findViewById(R.id.id_img_changecity);
		textChangeCity = (TextView) thisView
				.findViewById(R.id.id_text_changcity);

		todyImg = (ImageView) thisView.findViewById(R.id.id_todyimg_weather);
		temoImg = (ImageView) thisView.findViewById(R.id.id_temoimg_weather);
		ThreadGetWeatherData threadGetWeatherData = new ThreadGetWeatherData();
		threadGetWeatherData.start();
	}

	private void initData() {

		if (weatherDate != null) {

			textViewCity.setText(weatherDate.getCity());
			// String PMInfo = ;
			textViewPMInfo.setText(weatherDate.getPm() + "  "
					+ weatherDate.getPm_level());
			textViewWeatherType.setText(weatherDate.getWeather1());
			textViewTemp.setText(weatherDate.getTemp() + "°");
			textViewWeathInfo.setText("风力/" + weatherDate.getFl1() + "  湿度/"
					+ weatherDate.getSd());
			textViewTodayTemp.setText("今天     " + weatherDate.getTemp1());
			textViewTodayWeather.setText(weatherDate.getWeather1());
			textViewTomTemp.setText("明天     " + weatherDate.getTemp2());
			textViewTomWeather.setText(weatherDate.getWeather2());

			Time time = new Time("GMT+8");
			time.setToNow();
			int w = time.weekDay;// 0:周日
			String[] week = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四",
					"星期五", "星期六" };
			// 得到未来六天的日期
			String[] dateStrings = new String[7];
			Date date = new Date();
			// System.out.println(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			for (int i = 0; i < 6; i++) {
				date = calendar.getTime();
				// System.out.println(date);
				dateStrings[i] = (date.getMonth() + 1) + "/";
				dateStrings[i] += date.getDate();
				// System.out.println(dateStrings[i]);
				calendar.add(Calendar.DATE, 1);
			}
			date_1.setText(dateStrings[0]);
			date_2.setText(dateStrings[1]);
			date_3.setText(dateStrings[2]);
			date_4.setText(dateStrings[3]);
			date_5.setText(dateStrings[4]);
			date_6.setText(dateStrings[5]);

			week_1.setText(week[(w) % 7]);
			week_2.setText(week[(w + 1) % 7]);
			week_3.setText(week[(w + 2) % 7]);
			week_4.setText(week[(w + 3) % 7]);
			week_5.setText(week[(w + 4) % 7]);
			week_6.setText(week[(w + 5) % 7]);

			weath_1.setText(weatherDate.getWeather1());
			weath_2.setText(weatherDate.getWeather2());
			weath_3.setText(weatherDate.getWeather3());
			weath_4.setText(weatherDate.getWeather4());
			weath_5.setText(weatherDate.getWeather5());
			weath_6.setText(weatherDate.getWeather6());
			
			temp_1.setText(weatherDate.getTemp1());
			temp_2.setText(weatherDate.getTemp2());
			temp_3.setText(weatherDate.getTemp3());
			temp_4.setText(weatherDate.getTemp4());
			temp_5.setText(weatherDate.getTemp5());
			temp_6.setText(weatherDate.getTemp6());

			if (weatherDate.getWeather1().equals("晴")) {
				todyImg.setImageResource(R.drawable.weather_forecast_08);
			} else if (weatherDate.getWeather1().equals("多云")) {
				todyImg.setImageResource(R.drawable.weather_forecast_01);
			} else if (weatherDate.getWeather1().equals("阴")) {
				todyImg.setImageResource(R.drawable.weather_forecast_03);
			} else if (weatherDate.getWeather1().equals("阵雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather1().equals("雷阵雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_12);
			} else if (weatherDate.getWeather1().equals("小雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_15);
			} else if (weatherDate.getWeather1().equals("小到中雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_15);
			} else if (weatherDate.getWeather1().equals("中雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_04);
			} else if (weatherDate.getWeather1().equals("中到大雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_04);
			} else if (weatherDate.getWeather1().equals("大雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_16);
			} else if (weatherDate.getWeather1().equals("大到暴雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_16);
			} else if (weatherDate.getWeather1().equals("暴雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather1().equals("大暴雨")) {
				todyImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather1().equals("雨夹雪")) {
				todyImg.setImageResource(R.drawable.weather_forecast_13);
			}
			if (weatherDate.getWeather2().equals("晴")) {
				temoImg.setImageResource(R.drawable.weather_forecast_08);
			} else if (weatherDate.getWeather2().equals("多云")) {
				temoImg.setImageResource(R.drawable.weather_forecast_01);
			} else if (weatherDate.getWeather2().equals("阴")) {
				temoImg.setImageResource(R.drawable.weather_forecast_03);
			} else if (weatherDate.getWeather2().equals("阵雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather2().equals("雷阵雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_12);
			} else if (weatherDate.getWeather2().equals("小雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_15);
			} else if (weatherDate.getWeather2().equals("小到中雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_15);
			} else if (weatherDate.getWeather2().equals("中雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_04);
			} else if (weatherDate.getWeather2().equals("中到大雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_04);
			} else if (weatherDate.getWeather2().equals("大雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_16);
			} else if (weatherDate.getWeather2().equals("大到暴雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_16);
			} else if (weatherDate.getWeather2().equals("暴雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather2().equals("大暴雨")) {
				temoImg.setImageResource(R.drawable.weather_forecast_05);
			} else if (weatherDate.getWeather2().equals("雨夹雪")) {
				temoImg.setImageResource(R.drawable.weather_forecast_13);
			}

			lineData = getLineData(weatherDate.getTempList());
			showChart();
		}
	}

	class ThreadGetWeatherData extends Thread {
		@Override
		public void run() {
			WeatherUtil weatherUtil = new WeatherUtil();
			// CurrentWeather currentWeather = null;
			WeatherDate weatherDate = null;
			Message message = new Message();
			if (weatherUtil.isNetworkAvailable(getActivity()) == false) {
				message.arg1 = 1;
			} else {
				weatherDate = weatherUtil.getWeatherDate(
						"http://weather.123.duba.net/static/weather_info/",
						cityCode);
			}
			message.obj = weatherDate;
			handler.sendMessage(message);
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			System.out.println("handler begin~");
			if (msg.arg1 == 1) {
				Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_LONG)
						.show();
			} else {
				weatherDate = (WeatherDate) msg.obj;
				System.out.println("in handler" + weatherDate);
				initData();
				int[] dd = weatherDate.getTempList();
				for (int i = 0; i < dd.length; i++) {
					// System.out.println(dd[i] + "   dddddddddddddddd");
				}
				// lineData = getLineData(weatherDate.getTempList());
				// showChart(lineChart, lineData);
			}
			System.out.println("handler end~");
		}
	};

	private void showChart() {
		lineChart.setDrawBorders(false); // 是否在折线图上添加边框

		// no description text
		lineChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		lineChart
				.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid background
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

		// enable touch gestures
		lineChart.setTouchEnabled(true); // 设置是否可以触摸

		// enable scaling and dragging
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(true);// 是否可以缩放

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//

		// lineChart.setBackgroundColor(color);// 设置背景

		// add data
		lineChart.setData(lineData); // 设置数据

		lineChart.setVisibleXRange(20);// 设置可视的最大数据量
		// lineChart.setScaleY((float) 0.5);

		// get the legend (only possible after setting data)
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(8f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色
		// mLegend.setTypeface(mTf);// 字体

		mLegend.setEnabled(false);
		lineChart.animateX(2500); // 立即执行的动画,x轴

		// lineChart.setDoubleTapToZoomEnabled(true);

		XAxis xAxis = lineChart.getXAxis();
		// xAxis.setEnabled(false);//设置x轴隐藏
		xAxis.setPosition(XAxisPosition.BOTTOM);// 设置x轴位置
		xAxis.setTextSize(13);
		xAxis.setTextColor(Color.rgb(255, 255, 255));
		// xAxis.setTextSize(15);
		// xAxis.setAvoidFirstLastClipping(false);
		// xAxis.setDrawLabels(true);
		// xAxis.setLabelsToSkip(10);//x軸的数的间隔数
		// xAxis.setSpaceBetweenLabels(10);

		YAxis yAxis = lineChart.getAxisLeft();
		// yAxis.setShowOnlyMinMax(true);
		// yAxis.setAxisMinValue(20);
		yAxis.setStartAtZero(false);
		yAxis.setEnabled(false);// 设置y轴隐藏
		yAxis.setSpaceBottom((float) 0.4);// 设置底部空间
		// yAxis.setLabelCount(3);//y轴上的标签的显示的个数

		YAxis yAxis2 = lineChart.getAxisRight();
		yAxis2.setEnabled(false);
	}

	private LineData getLineData(int[] temp) {

		System.out.println("---getingLineData");
		ArrayList<String> xValues = new ArrayList<String>();
		// for (int i = 0; i < count; i++) {
		// // x轴显示的数据，这里默认使用数字下标显示
		// xValues.add("" + i);
		// }
		Time time = new Time("GMT+8");
		time.setToNow();
		int w = time.weekDay;
		String[] week = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

		xValues.add("今天");
		xValues.add("明天");
		xValues.add("后天");
		xValues.add(week[(w + 2) % 7]);
		xValues.add(week[(w + 3) % 7]);
		xValues.add(week[(w + 4) % 7]);
		// xValues.add(week[(w+5)%7]);

		// y轴的数据
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		ArrayList<Entry> yValues2 = new ArrayList<Entry>();
		// for (int i = 0; i < count; i++) {
		// float value = (float) (Math.random() * range) + 3;
		// yValues.add(new Entry(value, i));
		// }
		//
		if (temp != null) {
			for (int i = 1; i <= 6; i++) {
				// System.out.println(temp[2*i]+"  "+temp[2*i - 1]);
				if (temp[2 * i - 1] != WeatherDate.VOID_VALUE)
					yValues.add(new Entry(temp[2 * i - 1], i - 1));
				yValues2.add(new Entry(temp[2 * i], i - 1));
			}
		}
		// create a dataset and give it a type
		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "最高温" /* 显示在比例图上 */);

		LineDataSet lineDataSet2 = new LineDataSet(yValues2, "最低温");
		// mLineDataSet.setFillAlpha(110);
		// mLineDataSet.setFillColor(Color.RED);

		// 用y轴的集合来设置参数
		lineDataSet.setLineWidth(1.75f); // 线宽
		lineDataSet.setCircleSize(5f);// 显示的圆形大小
		lineDataSet.setColor(Color.WHITE);// 显示颜色
		lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
		lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
		lineDataSet.setValueTextSize(10f);
		lineDataSet.setValueTextColor(Color.WHITE);

		lineDataSet2.setLineWidth(1.75f); // 线宽
		lineDataSet2.setCircleSize(5f);// 显示的圆形大小
		lineDataSet2.setColor(Color.WHITE);// 显示颜色
		lineDataSet2.setCircleColor(Color.WHITE);// 圆形的颜色
		lineDataSet2.setHighLightColor(Color.WHITE); // 高亮的线的颜色
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
		if (v.getId() == R.id.id_img_changecity) {
			getActivity().finish();
			
		}else if(v.getId()==R.id.id_text_changcity){
			Intent intent = new Intent();
			intent.setClass(getActivity(), ChangeCityActivity.class);
			startActivity(intent);
		}
	}
}
