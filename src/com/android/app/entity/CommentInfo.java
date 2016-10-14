package com.android.app.entity;

import java.util.Date;

public class CommentInfo {
    
	private String username;
	private String content;
	private Date date;
	private int type;      //    0:ÆÀÂÛ    1£º»Ø¸´
	private int commentCount;
    private int zanCount;
    private String img;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getZanCount() {
		return zanCount;
	}
	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public CommentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentInfo(String username, String content, Date date, int type,
			int commentCount, int zanCount, String img) {
		super();
		this.username = username;
		this.content = content;
		this.date = date;
		this.type = type;
		this.commentCount = commentCount;
		this.zanCount = zanCount;
		this.img = img;
	}

    
    
}
