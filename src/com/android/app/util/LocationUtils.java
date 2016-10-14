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
	//�ߵ�
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
	
	
	
	
	//��ʼ�ٶȶ�λ
	public void startBaidu() {
		if (baiduduManager == null) {
			//baduduManager = new LocationClient(context);
			//��λ������
			return ; 
		}
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		//��λ����ϵ����ѡȡ, gcj02��bd09ll��bd09
		option.setCoorType("bd09ll"); 
		//��λʱ����
		option.setScanSpan(5000);
		option.setAddrType("all");
		option.disableCache(true);
		option.setPriority(LocationClientOption.NetWorkFirst);
		baiduduManager.setLocOption(option);
		//ע�ᶨλ�ĳɹ��Ļص�
		baiduduManager.registerLocationListener(mBdLocationListener);
		baiduduManager.start();
	}
	//�����ٶȶ�λ
	public void stopBaidu() {
		baiduduManager.stop();
	}
	
   //��ʼGPS��λ
	public void startGps() {
		// ��ȡ��LocationManager����
		//gpsManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//provider��Ϊgps��λ��Ҳ��ΪΪ��վ��WIFI��λ
		String provider = gpsManager.getProvider(LocationManager.GPS_PROVIDER).getName();
		//3000msΪ��λ�ļ��ʱ�䣬10mΪ����仯��ֵ��gpsListenerΪ�ص��ӿ�
		gpsManager.requestLocationUpdates(provider, 3000, 10, gpsListener);
	}
	//����GPS��λ
	public void stopGps() {
		gpsManager.removeUpdates(gpsListener);
	}

	// GPS��λ������
	public LocationListener gpsListener = new LocationListener() {
		
		// λ�÷����ı�ʱ����
		public void onLocationChanged(Location location) {
			Log.e("Location", "onLocationChanged");
			Double geoLng = location.getLongitude() ; 
			Double geoLat = location.getLatitude();
			
			String x = String.valueOf(Math.round((geoLng - 115.17813) / 0.00054));
			String y = String.valueOf(Math.round((geoLat - 35.80422) / 0.00054));
		
			//locationStr ="x" + x  +  "y" + y ;
			locationStr ="x100y100";
			System.out.println("******************************************GPS location result �� "+ geoLng + " , "+ geoLat);
		
			String str[] = {locationStr , address , user_loc};
			String result = locationHelper.getLocation(str);	
			if("SUCCESS".equals(result))
				stopGps();
		    
		}

		// providerʧЧʱ����
		public void onProviderDisabled(String provider) {
			Log.e("Location", "onProviderDisabled");
		}

		// provider����ʱ����
		public void onProviderEnabled(String provider) {
			Log.e("Location", "onProviderEnabled");
		}

		// ״̬�ı�ʱ����
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
			System.out.println("******************************************baidu location result�� "+ geoLng + " , "+ geoLat);
			String str[] = {locationStr , address , user_loc};
			
			String result = locationHelper.getLocation(str);	
			if("SUCCESS".equals(result))
				stopBaidu();
		}

		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	
	//��ʼ�ߵ¶�λ
		public void startAmap() {
			aMapManager = LocationManagerProxy.getInstance(context);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2�汾��������������true��ʾ��϶�λ�а���gps��λ��false��ʾ�����綨λ��Ĭ����true Location
			 * API��λ����GPS�������϶�λ��ʽ
			 * ����һ�������Ƕ�λprovider���ڶ�������ʱ�������2000���룬������������������λ���ף����ĸ������Ƕ�λ������
			 */
			aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, mAMapLocationListener);
		}
	   // �����ߵ¶�λ
		public void stopAmap() {
			if (aMapManager != null) {
				aMapManager.removeUpdates(mAMapLocationListener);
				aMapManager.destory();
			}
			aMapManager = null;
		}
	//�ߵ¶�λ������
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
				System.out.println("******************************************gaode location result �� "+ geoLng + " , "+ geoLat);
			
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
