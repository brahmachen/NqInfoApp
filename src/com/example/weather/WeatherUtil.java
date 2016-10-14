package com.example.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WeatherUtil {
	/*
	 * 返回json数据
	 */
	public String getCurrectWeather(String cityId) {
		HttpGet httpGet = new HttpGet(
		// "http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100");乱码
		// "http://wthrcdn.etouch.cn/weather_mini?citykey=101010100");乱码
		// //"http://m.weather.com.cn/data/101280601.html");失效
				// "http://m.weather.com.cn/atad/101010100.html");不是最新的
				"http://weather.123.duba.net/static/weather_info/101120804.html");
		HttpClient httpClient = new DefaultHttpClient();
		InputStream inputStream = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, "utf-8"));
			String result = "";
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result = result + line;
			}
			result = result.substring(17, result.length() - 1);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public CurrentWeather getCurrentWeather(String url, String cityId) {
		JSONGeter jsonGeter = new JSONGeter();
		String result = "[";
		result += jsonGeter.getJSON(url) + "]";
		CurrentWeather currentWeather = null;
		try {
			JSONArray jsonArray = new JSONArray(result);
			JSONObject date = (JSONObject) jsonArray.get(0);
			JSONObject weatherinfo = date.getJSONObject("weatherinfo");
			currentWeather = new CurrentWeather();
			currentWeather.setCityid(weatherinfo.getString("cityid"));
			currentWeather.setCity(weatherinfo.getString("city"));
			currentWeather.setDate(weatherinfo.getString("date"));
			currentWeather.setFl(weatherinfo.getString("fl1"));
			currentWeather.setFx(weatherinfo.getString("fx1"));
			currentWeather.setPm(weatherinfo.getString("pm"));
			currentWeather.setPm_level(weatherinfo.getString("pm-level"));
			currentWeather.setSd(weatherinfo.getString("sd"));
			currentWeather.setTemp(weatherinfo.getString("temp"));
			currentWeather.setWeather(weatherinfo.getString("weather1"));
			currentWeather.setUpdate_time(date.getString("update_time"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println(currentWeather);
		return currentWeather;
	}

	public WeatherDate getWeatherDate(String url, String cityId) {
		JSONGeter jsonGeter = new JSONGeter();
		String result = "[";
		result += jsonGeter.getJSON(url+cityId+".html") + "]";
		WeatherDate weatherDate = null;
		try {
			JSONArray jsonArray = new JSONArray(result);
			JSONObject date = (JSONObject) jsonArray.get(0);
			JSONObject weatherinfo = date.getJSONObject("weatherinfo");
			weatherDate = new WeatherDate();
			weatherDate.setCity(weatherinfo.getString("city"));
			weatherDate.setCityid(weatherinfo.getString("cityid"));
			weatherDate.setFl1(weatherinfo.getString("fl1"));
			weatherDate.setFl2(weatherinfo.getString("fl2"));
			weatherDate.setFl3(weatherinfo.getString("fl3"));
			weatherDate.setFl4(weatherinfo.getString("fl4"));
			weatherDate.setFl5(weatherinfo.getString("fl5"));
			weatherDate.setFl6(weatherinfo.getString("fl6"));
			weatherDate.setFx1(weatherinfo.getString("fx1"));
			weatherDate.setFx2(weatherinfo.getString("fx2"));
			weatherDate.setTemp(weatherinfo.getString("temp"));
			weatherDate.setTemp1(weatherinfo.getString("temp1"));
			weatherDate.setTemp2(weatherinfo.getString("temp2"));
			weatherDate.setTemp3(weatherinfo.getString("temp3"));
			weatherDate.setTemp4(weatherinfo.getString("temp4"));
			weatherDate.setTemp5(weatherinfo.getString("temp5"));
			weatherDate.setTemp6(weatherinfo.getString("temp6"));
			weatherDate.setWd(weatherinfo.getString("wd"));
			weatherDate.setWeather1(weatherinfo.getString("weather1"));
			weatherDate.setWeather2(weatherinfo.getString("weather2"));
			weatherDate.setWeather3(weatherinfo.getString("weather3"));
			weatherDate.setWeather4(weatherinfo.getString("weather4"));
			weatherDate.setWeather5(weatherinfo.getString("weather5"));
			weatherDate.setWeather6(weatherinfo.getString("weather6"));
			weatherDate.setUpdate_time(date.getString("update_time"));
			weatherDate.setPm(weatherinfo.getString("pm"));
			weatherDate.setSd(weatherinfo.getString("sd"));
			weatherDate.setPm_level(weatherinfo.getString("pm-level"));
			return weatherDate;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 网络是否可用
	 *
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	// 以下是测试字符编码的
	public void testCharset(String datastr) {
		try {
			String temp = new String(datastr.getBytes(), "GBK");
			Log.v("TestCharset", "****** getBytes() -> GBK ******/n" + temp);
			temp = new String(datastr.getBytes("GBK"), "UTF-8");
			Log.v("TestCharset", "****** GBK -> UTF-8 *******/n" + temp);
			temp = new String(datastr.getBytes("GBK"), "ISO-8859-1");
			Log.v("TestCharset", "****** GBK -> ISO-8859-1 *******/n" + temp);
			temp = new String(datastr.getBytes("ISO-8859-1"), "UTF-8");
			Log.v("TestCharset", "****** ISO-8859-1 -> UTF-8 *******/n" + temp);
			temp = new String(datastr.getBytes("ISO-8859-1"), "GBK");
			Log.v("TestCharset", "****** ISO-8859-1 -> GBK *******/n" + temp);
			temp = new String(datastr.getBytes("UTF-8"), "GBK");
			Log.v("TestCharset", "****** UTF-8 -> GBK *******/n" + temp);
			temp = new String(datastr.getBytes("UTF-8"), "ISO-8859-1");
			Log.v("TestCharset", "****** UTF-8 -> ISO-8859-1 *******/n" + temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
