package com.android.ais.app;

import com.example.weather.GetPro;

public class Constants {

	static String ip = GetPro.getIP(AppApplication.getInstance()) ;
	
	
	public static String IP_ADDRESS = "http://"+ ip +":8080/NqInfoSer/main/ThreadLogin.action";
	public final static String ICON_DEFAULT_PATH = "http://"+ ip +":8080/NqInforSer/user_default_icon.png";
	public final static String UPLOAD_IMG  = "http://"+ ip +":8080/NqInfoSer/main/uploadFile";  
	public static final String MY_HTTP_HOME = "http://"+ ip +":8080/NqInfoSer/";
	
	
	public  final static  int TYPE_WITHOUT_IMG = 0;
	public  final static  int TYPE_WITH_IMG = 1;
	
	public  final static  int SOCIALIZATION_COMMENT = 0;
	public  final static  int SOCIALIZATION_REPLAY = 1;
	
	public static final String APP_FOLDER = "agriculture"; 
	
	// SD卡图片缓存的路径
	// "农情资讯"新闻列表的图片缓存路径
	public final static  String NEWS_LIST_IMG_CACHE_FOLDER = "/mnt/sdcard/ais/images/newsitem/";
	public final static  String PUBLISH_CAMERA_FOLDER = "/mnt/sdcard/ais/images/publish/";
	
	 
	public static int ITEM_IMG_WIDTH = 80;
	public static int ITEM_IMG_HEIGHT = 80;
	public static int GALLERY_WIDTH;
	public static int GALLERY_HEIGHT;
	
	

	
	
	
	
}
