package com.example.weather;

public class WeatherDate {
	private String city;
	private String cityid;
	private String fl1;// 风力
	private String fl2;
	private String fl3;
	private String fl4;
	private String fl5;
	private String fl6;
	private String fx1;
	private String fx2;
	private String temp;// 当前气温
	private String temp1;// 未来几天气温
	private String temp2;
	private String temp3;
	private String temp4;
	private String temp5;
	private String temp6;
	private String wd;// 风向
	private String sd;// 湿度
	private String weather1;// 未来几天天气天气
	private String weather2;
	private String weather3;
	private String weather4;
	private String weather5;
	private String weather6;
	private String pm;
	private String pm_level;
	private String update_time;// 更新时间
	public static final int VOID_VALUE = -50;

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

	public WeatherDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getFl1() {
		return fl1;
	}

	public void setFl1(String fl1) {
		this.fl1 = fl1;
	}

	public String getFl2() {
		return fl2;
	}

	public void setFl2(String fl2) {
		this.fl2 = fl2;
	}

	public String getFl3() {
		return fl3;
	}

	public void setFl3(String fl3) {
		this.fl3 = fl3;
	}

	public String getFl4() {
		return fl4;
	}

	public void setFl4(String fl4) {
		this.fl4 = fl4;
	}

	public String getFl5() {
		return fl5;
	}

	public void setFl5(String fl5) {
		this.fl5 = fl5;
	}

	public String getFl6() {
		return fl6;
	}

	public void setFl6(String fl6) {
		this.fl6 = fl6;
	}

	public String getFx1() {
		return fx1;
	}

	public void setFx1(String fx1) {
		this.fx1 = fx1;
	}

	public String getFx2() {
		return fx2;
	}

	public void setFx2(String fx2) {
		this.fx2 = fx2;
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

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getTemp3() {
		return temp3;
	}

	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}

	public String getTemp4() {
		return temp4;
	}

	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}

	public String getTemp5() {
		return temp5;
	}

	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}

	public String getTemp6() {
		return temp6;
	}

	public void setTemp6(String temp6) {
		this.temp6 = temp6;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getWeather1() {
		return weather1;
	}

	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}

	public String getWeather2() {
		return weather2;
	}

	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}

	public String getWeather3() {
		return weather3;
	}

	public void setWeather3(String weather3) {
		this.weather3 = weather3;
	}

	public String getWeather4() {
		return weather4;
	}

	public void setWeather4(String weather4) {
		this.weather4 = weather4;
	}

	public String getWeather5() {
		return weather5;
	}

	public void setWeather5(String weather5) {
		this.weather5 = weather5;
	}

	public String getWeather6() {
		return weather6;
	}

	public void setWeather6(String weather6) {
		this.weather6 = weather6;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public int[] getTempList() {
		int[] list = new int[13];
		String[] tempList = new String[] { temp1, temp2, temp3, temp4, temp5,
				temp6 };
		list[0] = Integer.parseInt(temp);
		for (int i = 0; i < 6; i++) {
			String t1 = "";
			String t2 = "";
			boolean m = false;
			int length = tempList[i].length();
			for (int j = 0; j < length; j++) {
				char a = tempList[i].charAt(j);
				if (a == '~')
					m = true;
				if (a >= '0' && a <= '9' || a == '-') {
					if (m == false) {
						t1 += a;
					} else {
						t2 += a;
					}
				}
			}
			// System.out.println("t1="+t1);
			// System.out.println("t2="+t2);
			if (t2 == "") {
				list[2 * i + 1] = Integer.parseInt(t1)+10;
				list[2 * i + 2] = Integer.parseInt(t1);
			} else {
				list[2 * i + 1] = Integer.parseInt(t1);
				list[2 * i + 2] = Integer.parseInt(t2);
			}

			// if(t1=="") list[2*i+1] = 0;
			// else list[2*i+1] = Integer.parseInt(t1);
			// if(t2=="") list[2*i+2] = 0;
			// else list[2*i+2] = Integer.parseInt(t2);
		}
		System.out.println("---list---");
		for (int i = 0; i < 13; i++) {
			//System.out.println(list[i]);
		}
		return list;
	}

	@Override
	public String toString() {
		return "WeatherDate [city=" + city + ", cityid=" + cityid + ", fl1="
				+ fl1 + ", fl2=" + fl2 + ", fl3=" + fl3 + ", fl4=" + fl4
				+ ", fl5=" + fl5 + ", fl6=" + fl6 + ", fx1=" + fx1 + ", fx2="
				+ fx2 + ", temp=" + temp + ", temp1=" + temp1 + ", temp2="
				+ temp2 + ", temp3=" + temp3 + ", temp4=" + temp4 + ", temp5="
				+ temp5 + ", temp6=" + temp6 + ", wd=" + wd + ", sd=" + sd
				+ ", weather1=" + weather1 + ", weather2=" + weather2
				+ ", weather3=" + weather3 + ", weather4=" + weather4
				+ ", weather5=" + weather5 + ", weather6=" + weather6 + ", pm="
				+ pm + ", pm_level=" + pm_level + ", update_time="
				+ update_time + "]";
	}
	
}
