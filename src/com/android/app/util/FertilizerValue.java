package com.android.app.util;

public class FertilizerValue {
	
	public String fertilizervalue(int type, String value1) {
		double value=Double.parseDouble(value1);
		switch (type) {
		case 0:
			if(value>120){
				return "�Ϸḻ";
			}else if(value>90){
				return "�ḻ";
			}else if(value>60){
				return "�е�";
			}else{
				return "ȱ��";
			}
		case 1:
			if(value>40){
				return "�Ϸḻ";
			}else if(value>30){
				return "�ḻ";
			}else if(value>15){
				return "�е�";
			}else{
				return "ȱ��";
			}
		case 2:
			if(value>150){
				return "�Ϸḻ";
			}else if(value>120){
				return "�ḻ";
			}else if(value>75){
				return "�е�";
			}else{
				return "ȱ��";
			}
			
		case 3:
			if(value>15){
				return "�Ϸḻ";
			}else if(value>12){
				return "�ḻ";
			}else if(value>10){
				return "�е�";
			}else{
				return "ȱ��";
			}
		default:
			if(value>9){
				return "��ǿ����";
			}else if(value>8.5){
				return "ǿ����";
			}else if(value>7.5){
				return "�еȼ���";
			}else if(value>6.5){
				return "����";
			}else if(value>5.5){
				return "��������";
			}else if(value>4.5){
				return "ǿ����";
			}else{
				return "��ǿ����";
			}
			
		}
	}
}
