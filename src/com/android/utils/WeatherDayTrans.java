package com.android.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WeatherDay entity. @author MyEclipse Persistence Tools
 */

public class WeatherDayTrans implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private String cityId;
	private String weatherDate;
	private String temp;
	private String weatherType;
	private String fx;
	private String fl;
	private String sd;

	// Constructors

	/** default constructor */
	public WeatherDayTrans() {
	}

	/** minimal constructor */
	public WeatherDayTrans(BigDecimal id) {
		this.id = id;
	}

	/** full constructor */
	public WeatherDayTrans(BigDecimal id, String cityId, String weatherDate,
			String temp, String weatherType, String fx, String fl, String sd) {
		this.id = id;
		this.cityId = cityId;
		this.weatherDate = weatherDate;
		this.temp = temp;
		this.weatherType = weatherType;
		this.fx = fx;
		this.fl = fl;
		this.sd = sd;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getWeatherDate() {
		return this.weatherDate;
	}

	public void setWeatherDate(String weatherDate) {
		this.weatherDate = weatherDate;
	}

	public String getTemp() {
		return this.temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWeatherType() {
		return this.weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	public String getFx() {
		return this.fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	public String getFl() {
		return this.fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public String getSd() {
		return this.sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	@Override
	public String toString() {
		return "WeatherDayTrans [id=" + id + ", cityId=" + cityId
				+ ", weatherDate=" + weatherDate + ", temp=" + temp
				+ ", weatherType=" + weatherType + ", fx=" + fx + ", fl=" + fl
				+ ", sd=" + sd + "]";
	}

}