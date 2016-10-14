package com.android.app.entity;

import java.io.Serializable;

public class User implements Serializable {

	private String nickName;
	private String id;
	private String icon;

	public User(String nickName, String id, String icon) {
		super();
		this.nickName = nickName;
		this.id = id;
		this.icon = icon;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
