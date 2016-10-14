package com.android.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class NqzxInfo implements Serializable {
	/**
	 * 
	 */
	private int contentid;
	private User userinfo = new User();
	private String title;
	private String description;
	private String time;
	private String content;
	private BigDecimal modelid;
	private BigDecimal comments;
	private BigDecimal zancount;
	private String zanFlag;
	private String titleImg;

	public NqzxInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NqzxInfo(int contentid, String title, String description,
			String time, String content, BigDecimal modelid,
			BigDecimal comments, BigDecimal zancount, String zanflag ,String titleImg) {
		super();
		this.contentid = contentid;

		this.title = title;
		this.description = description;
		this.time = time;
		this.content = content;
		this.modelid = modelid;
		this.comments = comments;
		this.zancount = zancount;
		this.zanFlag = zanflag;
	    this.titleImg = titleImg ;
	}

	public class User {
		private int id;
		private String nick;
		private String icon;

		public int getId() {
			return id;
		}

		public void setId(int id) {
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

		public User() {
			super();
			// TODO Auto-generated constructor stub
		}

		public User(int id, String nick, String icon) {
			super();
			this.id = id;
			this.nick = nick;
			this.icon = icon;
		}

	}

	public int getContentid() {
		return contentid;
	}

	public void setContentid(int contentid) {
		this.contentid = contentid;
	}

	public User getUser() {
		return userinfo;
	}

	public void setUser(User userinfo) {
		this.userinfo = userinfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getModelid() {
		return modelid;
	}

	public void setModelid(BigDecimal modelid) {
		this.modelid = modelid;
	}

	public BigDecimal getComments() {
		return comments;
	}

	public void setComments(BigDecimal comments) {
		this.comments = comments;
	}

	public BigDecimal getZancount() {
		return zancount;
	}

	public void setZancount(BigDecimal zancount) {
		this.zancount = zancount;
	}

	@Override
	public String toString() {
		return "NqzxInfo [contentid=" + contentid + ", modelid=" + modelid
				+ ", title=" + title + ", description=" + description
				+ ", comments=" + comments + ", time=" + time + ", userinfo="
				+ userinfo + ", content=" + content + ", zancount=" + zancount
				+ "]";
	}

	public String getZanFlag() {
		return zanFlag;
	}

	public void setZanFlag(String zanFlag) {
		this.zanFlag = zanFlag;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

}
