package com.android.app.entity;

import java.io.Serializable;

public class NqltInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6395291554556777477L;

	/*
	 * id：论坛动态id type ：类型（病虫害防治、农情资讯、种植技术........） thumb ：标题图片 image
	 * description ：论坛动态大体描述 comments sorttime ：时间
	 */
	private String id;
	private int type;
	private String title;
	private String content;
	private String time;
	private User user = new User();
	private int comments;
	private int zancount;
	   private String zanFlag ; 


	public NqltInfo(String id, int type, String title, String content,
			String time, int comments, int zancount , String zanflag ) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.content = content;
		this.time = time;
		this.comments = comments;
		this.zancount = zancount;
		this.zanFlag = zanflag ;
	}



	public NqltInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User userinfo) {
		this.user = userinfo;
	}



	public int getComments() {
		return comments;
	}



	public void setComments(int comments) {
		this.comments = comments;
	}



	public int getZancount() {
		return zancount;
	}



	public void setZancount(int zancount) {
		this.zancount = zancount;
	}



	public  class User implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

	@Override
	public String toString() {
		return "NqltInfo [id=" + id + ", type=" + type + ", title=" + title
				+ ", content=" + content + ", time=" + time + ", user=" + user
				+ ", comments=" + comments + ", zancount=" + zancount + "]";
	}



	public String getZanFlag() {
		return zanFlag;
	}



	public void setZanFlag(String zanFlag) {
		this.zanFlag = zanFlag;
	}



	
	

}
