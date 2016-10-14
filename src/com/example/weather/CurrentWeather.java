package com.example.weather;

public class CurrentWeather {
	private String cityid; 
	private String  city;
	private String  date;
	private String  fl;//风力
	private String  fx;//风向
	private String  pm;//颗粒物
	private String  pm_level;//污染程度
	private String  sd;//湿度
	private String  temp;//温度
	private String  weather;//天气
	private String  js;//降水概率
	private String  update_time;//更新时间
	public CurrentWeather() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFl() {
		return fl;
	}
	public void setFl(String fl) {
		this.fl = fl;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public String getPm_level() {
		return pm_level;
	}
	public void setPm_level(String pm_level) {
		this.pm_level = pm_level;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	@Override
	public String toString() {
		return "CurrentWeather [cityid=" + cityid + ", city=" + city
				+ ", date=" + date + ", fl=" + fl + ", fx=" + fx + ", pm=" + pm
				+ ", pm_level=" + pm_level + ", sd=" + sd + ", temp=" + temp
				+ ", weather=" + weather + ", js=" + js + ", update_time="
				+ update_time + "]";
	}
	
	
}
