package com.example.weather;

public class City {
	private String cituName;
	private String cityCode;
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	public City(String cituName, String cityCode) {
		super();
		this.cituName = cituName;
		this.cityCode = cityCode;
	}
	public String getCituName() {
		return cituName;
	}
	public void setCituName(String cituName) {
		this.cituName = cituName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Override
	public String toString() {
		return "City [cituName=" + cituName + ", cityCode=" + cityCode + "]";
	}
	
	
}
