package com.android.app.util;

public class FertilizerValue {
	
	public String fertilizervalue(int type, String value1) {
		double value=Double.parseDouble(value1);
		switch (type) {
		case 0:
			if(value>120){
				return "较丰富";
			}else if(value>90){
				return "丰富";
			}else if(value>60){
				return "中等";
			}else{
				return "缺乏";
			}
		case 1:
			if(value>40){
				return "较丰富";
			}else if(value>30){
				return "丰富";
			}else if(value>15){
				return "中等";
			}else{
				return "缺乏";
			}
		case 2:
			if(value>150){
				return "较丰富";
			}else if(value>120){
				return "丰富";
			}else if(value>75){
				return "中等";
			}else{
				return "缺乏";
			}
			
		case 3:
			if(value>15){
				return "较丰富";
			}else if(value>12){
				return "丰富";
			}else if(value>10){
				return "中等";
			}else{
				return "缺乏";
			}
		default:
			if(value>9){
				return "极强碱性";
			}else if(value>8.5){
				return "强碱性";
			}else if(value>7.5){
				return "中等碱性";
			}else if(value>6.5){
				return "中性";
			}else if(value>5.5){
				return "中弱酸性";
			}else if(value>4.5){
				return "强酸性";
			}else{
				return "极强酸性";
			}
			
		}
	}
}
