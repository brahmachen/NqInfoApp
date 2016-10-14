package com.example.ais;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.ais.app.R;
import com.android.app.util.MyHttpAPIControl;
import com.android.utils.AISDataBase;
import com.android.utils.ParseUtils;
import com.android.utils.Tb02012Id;
import com.android.utils.Tb02018Id;
import com.android.utils.Tb02027Id;
import com.android.utils.Tb02037Id;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HistoryWeatherFragment extends Fragment {

	private static final String[] queryType = { "�����»���", "���½�ˮ��", "���¿���ƽ��ʪ��",
			"����ƽ������"};
	private static final String[] queryCity = { "�ĳ���" };
	private static final String[] queryZone = { "��������", "������", "ݷ��", "��ƽ��",
			"������", "����", "������", "������" };
	private static final String[] zoneCode = { "371502", "371521", "371522",
			"371523", "371524", "371525", "371526", "371581" };
	private static String[] yearStrings = null;
	private static String[] monthStrings = null;
	private Spinner spinnerQueryType = null;
	private Spinner spinnerQueryCity = null;
	private Spinner spinnerQueryZone = null;

	private Spinner spinnerStartYear = null;
	private Spinner spinnerStartMonth = null;
	private Spinner spinnerEndYear = null;
	private Spinner spinnerEndMonth = null;

	private ArrayAdapter<String> spinnerQueryTypeAdapter = null;
	private ArrayAdapter<String> spinnerQueryCityAdapter = null;
	private ArrayAdapter<String> spinnerQueryZoneAdapter = null;

	private ArrayAdapter<String> spinnerStartYearAdapter = null;
	private ArrayAdapter<String> spinnerStartMonthAdapter = null;
	private ArrayAdapter<String> spinnerEndYearAdapter = null;
	private ArrayAdapter<String> spinnerEndMonthAdapter = null;

	private String startYear = "1981";
	private String startMonth = "1";
	private String endYear = "1981";
	private String endMonth = "1";
	private String AreaCode = "371502";

	private Button buttonConfrim = null;
	private int queryTypeMark = 0;// ��־λ����ѯ���ͣ���position���Ӧ
	private int queryCityMark = 0;
	// ����ͼ
	private LineChart lineChart = null;
	private LineData lineData = null;
	// ��״ͼ
	private BarChart barChart = null;
	private BarData barData = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab03, container, false);
		initViews(view);
		sendRequest();
		return view;
	}

	private void initViews(View view) {
		initMonthString();
		spinnerQueryType = (Spinner) view
				.findViewById(R.id.id_sppiner_querytype);
		spinnerQueryTypeAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, queryType);
		spinnerQueryTypeAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerQueryType.setAdapter(spinnerQueryTypeAdapter);
		spinnerQueryType
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						TextView tv = (TextView)view;
						tv.setTextColor(Color.WHITE);
						queryTypeMark = position;
						System.out.println("position: " + position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

		spinnerQueryCity = (Spinner) view
				.findViewById(R.id.id_sppiner_querycity);
		spinnerQueryCityAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, queryCity);
		spinnerQueryCityAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerQueryCity.setAdapter(spinnerQueryCityAdapter);
		spinnerQueryCity
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						TextView tv = (TextView)view;
						tv.setTextColor(Color.WHITE);
						System.out.println("position: " + position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

		// ѡ�����
		spinnerQueryZone = (Spinner) view
				.findViewById(R.id.id_sppiner_queryzone);
		spinnerQueryZoneAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, queryZone);
		spinnerQueryZoneAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerQueryZone.setAdapter(spinnerQueryZoneAdapter);
		spinnerQueryZone
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						TextView tv = (TextView)view;
						tv.setTextColor(Color.WHITE);
						AreaCode = zoneCode[position];
						System.out.println("position: " + position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		// ѡ��ʼ���
		spinnerStartYear = (Spinner) view
				.findViewById(R.id.id_sppiner_startyear);
		spinnerStartYearAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, yearStrings);
		spinnerStartYearAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerStartYear.setAdapter(spinnerStartYearAdapter);
		spinnerStartYear
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						TextView tv = (TextView)view;
						tv.setTextColor(Color.WHITE);
						System.out.println("position: " + position);
						startYear = yearStrings[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		// ѡ��ʼ�·�
		spinnerStartMonth = (Spinner) view
				.findViewById(R.id.id_sppiner_startmonth);
		spinnerStartMonthAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, new String[]{"1"});
		spinnerStartMonthAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerStartMonth.setAdapter(spinnerStartMonthAdapter);
		spinnerStartMonth
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						TextView tv = (TextView)view;
						tv.setTextColor(Color.WHITE);
						System.out.println("position: " + position);
						startMonth = monthStrings[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		// ѡ��������
		spinnerEndYear = (Spinner) view.findViewById(R.id.id_sppiner_endyear);
		spinnerEndYearAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, yearStrings);
		spinnerEndYearAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerEndYear.setAdapter(spinnerEndYearAdapter);
		spinnerEndYear.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView)view;
				tv.setTextColor(Color.WHITE);
				System.out.println("position: " + position);
				endYear = yearStrings[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		// ѡ������·�
		spinnerEndMonth = (Spinner) view.findViewById(R.id.id_sppiner_endmonth);
		spinnerEndMonthAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, new String[]{"12"});
		spinnerEndMonthAdapter
				.setDropDownViewResource(R.layout.spinner_textview);
		spinnerEndMonth.setAdapter(spinnerEndMonthAdapter);
		spinnerEndMonth.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView)view;
				tv.setTextColor(Color.WHITE);
				endMonth = monthStrings[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		buttonConfrim = (Button) view
				.findViewById(R.id.id_button_confrim_query);
		buttonConfrim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendRequest();
			}
			// }
		});
		lineChart = (LineChart) view.findViewById(R.id.linechart_tab03);
		barChart = (BarChart) view.findViewById(R.id.barchart_tab03);
	}

	protected void sendRequest() {

		switch (queryTypeMark) {
		case 0:
			RequestParams requestParams = MyHttpAPIControl.getBaseParams();
			requestParams.add("startYear", startYear);
			requestParams.add("endYear", endYear);
			requestParams.add("cityId", AreaCode);
			MyHttpAPIControl.newInstance().getTb02037Data(requestParams,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFinish() {
							super.onFinish();
						}

						@Override
						public void onStart() {
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							Type type = new TypeToken<AISDataBase<Tb02037Id>>() {
							}.getType();
							AISDataBase<Tb02037Id> aw = (AISDataBase<Tb02037Id>) ParseUtils
									.Gson2Object(content, type);
							System.out.println(aw);
							if (aw != null && aw.isState()) {
								ArrayList<Tb02037Id> list = aw.getData();
								barChart.setVisibility(View.GONE);
								lineData = getLineData(list);
								lineChart.setVisibility(View.VISIBLE);
								showChart();
								for (Tb02037Id weatherDayTrans : list) {
									System.out.println(weatherDayTrans);
								}

							}
						}
					});
			break;
		case 1:
			RequestParams requestParams1 = MyHttpAPIControl.getBaseParams();
			requestParams1.add("startYear", startYear);
			requestParams1.add("endYear", endYear);
			requestParams1.add("cityId", AreaCode);
			MyHttpAPIControl.newInstance().getTb02018Data(requestParams1,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFinish() {
							super.onFinish();
						}

						@Override
						public void onStart() {
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							Type type = new TypeToken<AISDataBase<Tb02018Id>>() {
							}.getType();
							AISDataBase<Tb02018Id> aw = (AISDataBase<Tb02018Id>) ParseUtils
									.Gson2Object(content, type);
							System.out.println(aw);
							if (aw != null && aw.isState()) {
								ArrayList<Tb02018Id> list = aw.getData();
								lineChart.setVisibility(View.GONE);
								barChart.setVisibility(View.VISIBLE);
								barData = getBarData(list);
								showBarChart();
								for (Tb02018Id weatherDayTrans : list) {
									System.out.println(weatherDayTrans);
								}

							}
						}

					});

			break;
		case 2:
			RequestParams requestParams2 = MyHttpAPIControl.getBaseParams();
			requestParams2.add("startYear", startYear);
			requestParams2.add("endYear", endYear);
			requestParams2.add("cityId", AreaCode);
			System.out.println("------------------" + AreaCode);
			MyHttpAPIControl.newInstance().getTb02027Data(requestParams2,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFinish() {
							super.onFinish();
						}

						@Override
						public void onStart() {
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							Type type = new TypeToken<AISDataBase<Tb02027Id>>() {
							}.getType();
							AISDataBase<Tb02027Id> aw = (AISDataBase<Tb02027Id>) ParseUtils
									.Gson2Object(content, type);
							System.out.println(aw);
							if (aw != null && aw.isState()) {
								ArrayList<Tb02027Id> list = aw.getData();
								barChart.setVisibility(View.GONE);
								lineData = getLineDataSd(list);
								lineChart.setVisibility(View.VISIBLE);
								showChart();
							}
						}

					});
			break;
		case 3:
			RequestParams requestParams3 = MyHttpAPIControl.getBaseParams();
			requestParams3.add("startYear", startYear);
			requestParams3.add("endYear", endYear);
			requestParams3.add("cityId", AreaCode);
			System.out.println("------------------" + AreaCode);
			System.out.println(startYear);
			System.out.println(endYear);
			MyHttpAPIControl.newInstance().getTb02012Data(requestParams3,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFinish() {
							super.onFinish();
						}

						@Override
						public void onStart() {
							System.out.println("---------starting");
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							Type type = new TypeToken<AISDataBase<Tb02012Id>>() {
							}.getType();
							AISDataBase<Tb02012Id> aw = (AISDataBase<Tb02012Id>) ParseUtils
									.Gson2Object(content, type);
							if (aw != null && aw.isState()) {
								ArrayList<Tb02012Id> list = aw.getData();
								barChart.setVisibility(View.GONE);
								lineData = getLineDataFs(list);
								lineChart.setVisibility(View.VISIBLE);
								showChart();
								for (Tb02012Id weatherDayTrans : list) {
									System.out.println(weatherDayTrans);
								}

							}
						}

					});
			break;
		case 4:
			
			break;
		default:
			break;
		}
	}
	private LineData getLineDataFs(ArrayList<Tb02012Id> list) {
		ArrayList<String> xValues = new ArrayList<String>();

		int num = 0;
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		for (Tb02012Id tb02015Id : list) {
			if (tb02015Id.getIdx0322() == null){
				//num++;
				continue;
			}
			String xv = tb02015Id.getDim00102() + "��";
			if (num >= 1
					&& !(list.get(num - 1).getDim00101().equals(tb02015Id
							.getDim00101()))) {
				xv = tb02015Id.getDim00101() + "��";
			}
			xValues.add(num, xv);
			System.out.println("tb02027Id.getIdx0328():"+tb02015Id.getIdx0322());
			yValues.add(new Entry(tb02015Id.getIdx0322().floatValue(), num));
			num++;
		}
		// create a dataset and give it a type
		// y������ݼ���
		LineDataSet lineDataSet = new LineDataSet(yValues, "����ƽ������" /* ��ʾ�ڱ���ͼ�� */);

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


		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets

		// create a data object with the datasets
		LineData lineData = new LineData(xValues, lineDataSets);
		//System.out.println(lineData.getYValueSum());
		return lineData;
	}
	private LineData getLineDataSd(ArrayList<Tb02027Id> list) {
		ArrayList<String> xValues = new ArrayList<String>();

		int num = 0;
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		for (Tb02027Id tb02027Id : list) {
			if (tb02027Id.getIdx0328() == null){
				//num++;
				continue;
			}
			String xv = tb02027Id.getDim00102() + "��";
			if (num >= 1
					&& !(list.get(num - 1).getDim00101().equals(tb02027Id
							.getDim00101()))) {
				xv = tb02027Id.getDim00101() + "��";
			}
			xValues.add(num, xv);
			System.out.println("tb02027Id.getIdx0328():"+tb02027Id.getIdx0328());
			yValues.add(new Entry(tb02027Id.getIdx0328().floatValue(), num));
			num++;
		}
		// create a dataset and give it a type
		// y������ݼ���
		LineDataSet lineDataSet = new LineDataSet(yValues, "��ƽ�����ʪ��" /* ��ʾ�ڱ���ͼ�� */);

		// ��y��ļ��������ò���
		lineDataSet.setLineWidth(1.75f); // �߿�
		lineDataSet.setCircleSize(5f);// ��ʾ��Բ�δ�С
		lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ
		lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ
		lineDataSet.setHighLightColor(Color.WHITE); // �������ߵ���ɫ
		lineDataSet.setValueTextSize(10f);
		lineDataSet.setValueTextColor(Color.WHITE);


		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets

		// create a data object with the datasets
		LineData lineData = new LineData(xValues, lineDataSets);
		return lineData;
	}

	private void initMonthString() {
		yearStrings = new String[30];
		monthStrings = new String[12];
		for (int i = 1981; i <= 2010; i++) {
			yearStrings[i - 1981] = i + "";
		}
		for (int i = 1; i <= 12; i++) {
			monthStrings[i - 1] = i + "";
		}
	}

	private LineData getLineData(ArrayList<Tb02037Id> list) {

		ArrayList<String> xValues = new ArrayList<String>();

		int num = 0;
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		ArrayList<Entry> yValues2 = new ArrayList<Entry>();
		for (Tb02037Id tb02037Id : list) {
			if (tb02037Id.getIdx0348() == null
					|| tb02037Id.getIdx0349() == null) {
				num++;
				continue;
			}
			String xv = tb02037Id.getDim00102() + "��";
			if (num >= 1
					&& !(list.get(num - 1).getDim00101().equals(tb02037Id
							.getDim00101()))) {
				xv = tb02037Id.getDim00101() + "��";
			}
			xValues.add(num, xv);
			yValues.add(new Entry(tb02037Id.getIdx0348().floatValue(), num));
			yValues2.add(new Entry(tb02037Id.getIdx0349().floatValue(), num));
			num++;
		}
		// create a dataset and give it a type
		// y������ݼ���
		LineDataSet lineDataSet = new LineDataSet(yValues, "��ƽ�������" /* ��ʾ�ڱ���ͼ�� */);

		LineDataSet lineDataSet2 = new LineDataSet(yValues2, "��ƽ�������");
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
		lineChart.setDoubleTapToZoomEnabled(false);
		// enable scaling and dragging
		lineChart.setDragEnabled(true);// �Ƿ������ק
		lineChart.setScaleEnabled(true);// �Ƿ��������
		lineChart.setScaleYEnabled(false);//���ô�ֱ�����ϲ�������

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//

		// lineChart.setBackgroundColor(color);// ���ñ���

		// add data
		lineChart.setData(lineData); // ��������

		lineChart.fitScreen();//������¼��ؽ�������ʱ  x�᳤�Ȳ��Զ���Ӧ������
		//lineChart.setVisibleXRange(15);// ���ÿ��ӵ����������
		// lineChart.setScaleY((float) 0.5);

		// get the legend (only possible after setting data)
		Legend mLegend = lineChart.getLegend(); // ���ñ���ͼ��ʾ�������Ǹ�һ��y��value��

		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// ��ʽ
		mLegend.setFormSize(8f);// ����
		mLegend.setTextColor(Color.WHITE);// ��ɫ
		// mLegend.setTypeface(mTf);// ����

		mLegend.setEnabled(false);
		lineChart.animateX(2500); // ����ִ�еĶ���,x��

		// lineChart.setDoubleTapToZoomEnabled(true);

		XAxis xAxis = lineChart.getXAxis();
		// xAxis.setEnabled(false);//����x������
		xAxis.setPosition(XAxisPosition.BOTTOM);// ����x��λ��
		xAxis.setTextSize(15);
		xAxis.setTextColor(Color.WHITE);
		// xAxis.setAvoidFirstLastClipping(false);
		// xAxis.setDrawLabels(true);
		// xAxis.setLabelsToSkip(10);//x�S�����ļ����
		// xAxis.setSpaceBetweenLabels(10);

		YAxis yAxis = lineChart.getAxisLeft();
		// yAxis.setShowOnlyMinMax(true);
		// yAxis.setAxisMinValue(20);
		yAxis.setStartAtZero(false);
		yAxis.setEnabled(false);// ����y������
		// yAxis.setSpaceBottom((float) 0.4);//���õײ��ռ�
		// yAxis.setLabelCount(3);//y���ϵı�ǩ����ʾ�ĸ���

		YAxis yAxis2 = lineChart.getAxisRight();
		yAxis2.setEnabled(false);
	}

	private BarData getBarData(ArrayList<Tb02018Id> list) {
		ArrayList<String> xValues = new ArrayList<String>();
		// for (int i = 0; i < count; i++) {
		// xValues.add("��" + (i + 1) + "����");
		// }

		ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
		int num = 0;
		for (Tb02018Id tb02018Id : list) {
			if (tb02018Id.getIdx0316() == null) {
				num++;
				continue;
			}
			String xv = tb02018Id.getDim00102() + "��";
			if (num >= 1
					&& !(tb02018Id.getDim00101().equals(list.get(num - 1).getDim00101()))) {
				xv = tb02018Id.getDim00101() + "��";
			}
			xValues.add(num, xv);
			yValues.add(new BarEntry(tb02018Id.getIdx0316().floatValue(), num));
			num++;
		}
		// for (int i = 0; i < count; i++) {
		// float value = (float) (Math.random() * range/*100���ڵ������*/) + 3;
		// yValues.add(new BarEntry(value, i));
		// }

		// y������ݼ���
		BarDataSet barDataSet = new BarDataSet(yValues, "���½�ˮ��");

		barDataSet.setColor(Color.rgb(114, 188, 223));

		ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
		barDataSets.add(barDataSet); // add the datasets

		BarData barData = new BarData(xValues, barDataSets);
		return barData;
	}
	private void showBarChart() {  
        barChart.setDrawBorders(false);  ////�Ƿ�������ͼ����ӱ߿�   
            
        barChart.setDescription("");// ��������      
          
        // ���û�����ݵ�ʱ�򣬻���ʾ���������ListView��EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");      
                 
        barChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ      
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����      
        
        barChart.setTouchEnabled(true); // �����Ƿ���Դ���      
       
        barChart.setDragEnabled(true);// �Ƿ������ק      
        barChart.setScaleEnabled(true);// �Ƿ��������      
        barChart.setScaleYEnabled(false);//���ô�ֱ�����ϲ�������
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.setPinchZoom(false);//       
      
//      barChart.setBackgroundColor();// ���ñ���      
          
        barChart.setDrawBarShadow(true);  
         
        barChart.setData(barData); // ��������      
        barChart.fitScreen();//������¼��ؽ�������ʱ  x�᳤�Ȳ��Զ���Ӧ������
      //  barChart.setVisibleXRange(18);
        Legend mLegend = barChart.getLegend(); // ���ñ���ͼ��ʾ  
      
        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ      
        mLegend.setFormSize(6f);// ����      
        mLegend.setTextColor(Color.WHITE);// ��ɫ      
          
//      X���趨  
//      XAxis xAxis = barChart.getXAxis();  
//      xAxis.setPosition(XAxisPosition.BOTTOM);  
      
        barChart.animateX(2500); // ����ִ�еĶ���,x��    
    }  
}
