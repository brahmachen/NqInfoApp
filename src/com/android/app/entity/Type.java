package com.android.app.entity;

public class Type {
	
	private int index;

	public Type(int index) {
		this.index = index;
	}
 
	@Override
	public String toString() {
		String name = null;
		switch (index) {
		case 1:
			name = "病虫害防治";
			break;
		case 2:
			name = "种植技术";
			break;
		case 3:
			name = "新闻资讯";
			break;
		case 4:
			name = "市场行情";
			break;
		case 5:
			name = "供求信息";
			break;
		case 6:
			name = "你问我答";
			break;
	
		}
		return name;
	}
}