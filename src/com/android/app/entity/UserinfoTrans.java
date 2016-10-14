package com.android.app.entity;

public class UserinfoTrans implements java.io.Serializable {

	// Fields

	private String id;
	private String nick;
	private String icon;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public UserinfoTrans(String id, String nick, String icon) {
		super();
		this.id = id;
		this.nick = nick;
		this.icon = icon;
	}

	public UserinfoTrans() {
		super();
		// TODO Auto-generated constructor stub
	}

}