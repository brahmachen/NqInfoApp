package com.example.weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


import android.content.Context;

public class GetPro {
	//@Test
	public static List<City> getPro(Context context) {
			try {
				Properties properties = new Properties();
				// InputStream inputStream =
				// ClassLoader.getSystemResourceAsStream("citycode.properties");
				InputStream inputStream = context.getAssets().open(
						"citycode.properties");
				System.out.println(inputStream);
				properties.load(inputStream);
				Enumeration enumeration = properties.propertyNames();
				ArrayList<City> list = new ArrayList<City>();
				while (enumeration.hasMoreElements()) {
					String keyString = (String) enumeration.nextElement();
					String valueString = properties.getProperty(keyString);
					list.add(new City(valueString, keyString));
				}
				return list;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}
	
	
	
	
	public static String  getIP(Context context) {
		try {
			Properties properties = new Properties();
			// InputStream inputStream =
			// ClassLoader.getSystemResourceAsStream("citycode.properties");
			InputStream inputStream = context.getAssets().open(
					"ipConfig.properties");
			System.out.println(inputStream);
			properties.load(inputStream);
			
			System.out.println("***********************ip:" + properties.getProperty("ip", "error"));
			return properties.getProperty("ip", "error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return null;
}

}
