package com.example.ais;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.a;

import com.android.ais.app.R;
import com.example.weather.City;
import com.example.weather.GetPro;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

public class ChangeCityActivity extends Activity{

	private AutoCompleteTextView changeCity = null;
	private Button confirmButton = null;
	private SharedPreferences sp = null; 
	private List<City> cityList =null;
	private String cityCode = null;
	private ImageButton backImageButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_city);
		changeCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_changecity);
		confirmButton = (Button) findViewById(R.id.button_confirm);
		backImageButton = (ImageButton) findViewById(R.id.changecity_head_back);
		backImageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		sp = this.getSharedPreferences("cityinfo", MODE_WORLD_READABLE);
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cityName = changeCity.getText().toString();
				for(City city:cityList){
					if(city.getCituName().equals(cityName)){
						cityCode = city.getCityCode();
						break;
					}
				}
				
				Editor editor = sp.edit();
				editor.putString("citycode", cityCode);
				
				editor.commit();
				finish();
			}
		});
		
		List<String> list = new ArrayList<String>();
		cityList = GetPro.getPro(this); 
		for(City city:cityList){
			list.add(city.getCituName());
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
		//arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		changeCity.setAdapter(arrayAdapter);
	} 
	
	

}
