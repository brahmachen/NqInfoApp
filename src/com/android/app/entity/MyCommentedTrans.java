package com.android.app.entity;

import java.math.BigDecimal;

/**
 * AisLtComment entity. @author MyEclipse Persistence Tools
 */

public class MyCommentedTrans implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String content;
	private BigDecimal pingluncount;
	private BigDecimal zancount;
	private String time;
	private String type;
	private String commentFlag;
	private String zanFlag;
	// 内部类对象
	private CommentUser commentUser = new CommentUser();
	private CommentedUser commentedUser = new CommentedUser();
	private CommentNqxx commentNqxx = new CommentNqxx();


	public static class CommentUser {
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

		public CommentUser() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CommentUser(int id, String nick, String icon) {
			super();
			this.id = id;
			this.nick = nick;
			this.icon = icon;
		}
	}

	public static class CommentedUser {
		private int id;
		private String nick;
		private String icon;
		private String date;
		private String content;
		private String commentId;
		private String zanCount;
		private String pinglunCount;
		private String zanFlag;
		
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

		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public CommentedUser() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getCommentId() {
			return commentId;
		}

		public void setCommentId(String commentId) {
			this.commentId = commentId;
		}

		public String getZanCount() {
			return zanCount;
		}

		public void setZanCount(String zanCount) {
			this.zanCount = zanCount;
		}

		public String getPinglunCount() {
			return pinglunCount;
		}

		public void setPinglunCount(String pinglunCount) {
			this.pinglunCount = pinglunCount;
		}

		public String getZanFlag() {
			return zanFlag;
		}

		public void setZanFlag(String zanFlag) {
			this.zanFlag = zanFlag;
		}

		public CommentedUser(int id, String nick, String icon, String date,
				String content, String commentId, String zanCount,
				String pinglunCount ,String zanflag  ) {
			super();
			this.id = id;
			this.nick = nick;
			this.icon = icon;
			this.date = date;
			this.content = content;
			this.commentId = commentId;
			this.zanCount = zanCount;
			this.pinglunCount = pinglunCount;
		     this.zanFlag = zanflag ; 
		}

		

	
	}
	
	public class CommentNqxx{
		int id;
        String title ; 
        String userId ;
        String content ;
		public CommentNqxx(int id, String title, String userId,String content) {
			super();
			this.id = id;
			this.title = title;
			this.userId = userId;
			this.content = content ; 
			
			
			
			
		}
		public CommentNqxx() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		} 
        
        
        
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPingluncount() {
		return pingluncount;
	}

	public void setPingluncount(BigDecimal pingluncount) {
		this.pingluncount = pingluncount;
	}

	public BigDecimal getZancount() {
		return zancount;
	}

	public void setZancount(BigDecimal zancount) {
		this.zancount = zancount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getCommentFlag() {
		return commentFlag;
	}

	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}


	public CommentUser getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(CommentUser commentUser) {
		this.commentUser = commentUser;
	}

	public CommentedUser getCommentedUser() {
		return commentedUser;
	}

	public void setCommentedUser(CommentedUser commentedUser) {
		this.commentedUser = commentedUser;
	}
	
	

	public CommentNqxx getCommentNqxx() {
		return commentNqxx;
	}

	public void setCommentNqxx(CommentNqxx commentNqxx) {
		this.commentNqxx = commentNqxx;
	}

	public MyCommentedTrans() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyCommentedTrans(String id, String content,
			BigDecimal pingluncount, BigDecimal zancount, String time,
			String type, String commentFlag ,String zanflag ) {
		super();
		this.id = id;
		this.content = content;
		this.pingluncount = pingluncount;
		this.zancount = zancount;
		this.time = time;
		this.type = type;
		this.commentFlag = commentFlag;
        this.zanFlag = zanflag ;
	}

	public String getZanFlag() {
		return zanFlag;
	}

	public void setZanFlag(String zanFlag) {
		this.zanFlag = zanFlag;
	}

	

	
	
}