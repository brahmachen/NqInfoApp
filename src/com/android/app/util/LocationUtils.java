package com.android.app.util;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


public class LocationUtils extends Activity{
	
	private Context context ; 

	//gps
	private LocationManager gpsManager;
	//baidu
	private LocationClient baiduduManager ;
	//高德
	private LocationManagerProxy aMapManager;
	
    String locationStr = "" ;
    String address = ""  ; 
    
    String user_loc ; 
    ILocation locationHelper ;
    
   
    
	public LocationUtils(Context context , LocationManager gpsManager , LocationClient baiduManager){
		this.context = context;
		this.gpsManager = gpsManager;
		this.baiduduManager = baiduManager ;
	}
	
	
	public void setLocationHelper(ILocation location){
		locationHelper = location ;
	}
	
	
	
	public void startLocation(){
		startAmap();
		startBaidu();
		startGps();
	}
	
	
	
	
	//开始百度定位
	public void startBaidu() {
		if (baiduduManager == null) {
			//baduduManager = new LocationClient(context);
			//定位的配置
			return ; 
		}
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		//定位坐标系类型选取, gcj02、bd09ll、bd09
		option.setCoorType("bd09ll"); 
		//定位时间间隔
		option.setScanSpan(5000);
		option.setAddrType("all");
		option.disableCache(true);
		option.setPriority(LocationClientOption.NetWorkFirst);
		baiduduManager.setLocOption(option);
		//注册定位的成功的回调
		baiduduManager.registerLocationListener(mBdLocationListener);
		baiduduManager.start();
	}
	//结束百度定位
	public void stopBaidu() {
		baiduduManager.stop();
	}
	
   //开始GPS定位
	public void startGps() {
		// 获取到LocationManager对象
		//gpsManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//provider可为gps定位，也可为为基站和WIFI定位
		String provider = gpsManager.getProvider(LocationManager.GPS_PROVIDER).getName();
		//3000ms为定位的间隔时间，10m为距离变化阀值，gpsListener为回调接口
		gpsManager.requestLocationUpdates(provider, 3000, 10, gpsListener);
	}
	//结束GPS定位
	public void stopGps() {
		gpsManager.removeUpdates(gpsListener);
	}

	// GPS定位监听器
	public LocationListener gpsListener = new LocationListener() {
		
		// 位置发生改变时调用
		public void onLocationChanged(Location location) {
			Log.e("Location", "onLocationChanged");
			Double geoLng = location.getLongitude() ; 
			Double geoLat = location.getLatitude();
			
			String x = String.valueOf(Math.round((geoLng - 115.17813) / 0.00054));
			String y = String.valueOf(Math.round((geoLat - 35.80422) / 0.00054));
		
			//locationStr ="x" + x  +  "y" + y ;
			locationStr ="x100y100";
			System.out.println("******************************************GPS location result ： "+ geoLng + " , "+ geoLat);
		
			String str[] = {locationStr , address , user_loc};
			String result = locationHelper.getLocation(str);	
			if("SUCCESS".equals(result))
				stopGps();
		    
		}

		// provider失效时调用
		public void onProviderDisabled(String provider) {
			Log.e("Location", "onProviderDisabled");
		}

		// provider启用时调用
		public void onProviderEnabled(String provider) {
			Log.e("Location", "onProviderEnabled");
		}

		// 状态改变时调用
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.e("Location", "onStatusChanged");
		}
	};
	
	
	
	
	public BDLocationListener mBdLocationListener = new BDLocationListener() {
		
		public void onReceiveLocation(BDLocation location) {
			
			Double geoLng = location.getLongitude() ; 
			Double geoLat = location.getLatitude();
			
			String x = String.valueOf(Math.round((geoLng - 115.17813) / 0.00054));
			String y = String.valueOf(Math.round((geoLat - 35.80422) / 0.00054));
		
			//locationStr ="x" + x  +  "y" + y ;
			locationStr ="x100y100";
		    
			address = location.getAddrStr();
			user_loc =location.getProvince() +  location.getCity()  ; 
			System.out.println("******************************************baidu location result： "+ geoLng + " , "+ geoLat);
			String str[] = {locationStr , address , user_loc};
			
			String result = locationHelper.getLocation(str);	
			if("SUCCESS".equals(result))
				stopBaidu();
		}

		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	
	//开始高德定位
		public void startAmap() {
			aMapManager = LocationManagerProxy.getInstance(context);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, mAMapLocationListener);
		}
	   // 结束高德定位
		public void stopAmap() {
			if (aMapManager != null) {
				aMapManager.removeUpdates(mAMapLocationListener);
				aMapManager.destory();
			}
			aMapManager = null;
		}
	//高德定位监听器
	public AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		public void onProviderEnabled(String provider) {
			
		}
		
		public void onProviderDisabled(String provider) {
			
		}
		
		public void onLocationChanged(Location location) {
			
		}
		
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				Double geoLat = location.getLatitude();
				Double geoLng = location.getLongitude();
				String desc = "";
				Bundle locBundle = location.getExtras();
				if (locBundle != null) {
					desc = locBundle.getString("desc");
				}
				String x = String.valueOf(Math.round((geoLng - 115.17813) / 0.00054));
				String y = String.valueOf(Math.round((geoLat - 35.80422) / 0.00054));

				//locationStr ="x" + x  +  "y" + y ;
				locationStr ="x100y100";
				
				
				
				address =  desc ; 	
				System.out.println("******************************************gaode location result ： "+ geoLng + " , "+ geoLat);
			
				user_loc =location.getProvince() +  location.getCity()   ; 
				
				String str[] = {locationStr , address , user_loc};
				String result = locationHelper.getLocation(str);	
				if("SUCCESS".equals(result))
					stopAmap();
				
				
			}
		}
	};

	
	
	
	
	public interface ILocation{
		public String getLocation(String[] location);
	}
}
