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
			name = "���溦����";
			break;
		case 2:
			name = "��ֲ����";
			break;
		case 3:
			name = "������Ѷ";
			break;
		case 4:
			name = "�г�����";
			break;
		case 5:
			name = "������Ϣ";
			break;
		case 6:
			name = "�����Ҵ�";
			break;
	
		}
		return name;
	}
}