package com.android.app.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * AisLtComment entity. @author MyEclipse Persistence Tools
 */

public class AisZxComment implements java.io.Serializable {

	// Fields
		private String id;
		private String content;
		private BigDecimal pingluncount;
		private BigDecimal zancount;
		private String time;
		private String type;
		private String commentFlag;
		private String zanFlag ; 
		// 内部类对象
		private CommentUser commentUser = new CommentUser();
		private CommentedUser commentedUser = new CommentedUser();
		private CommentNqlt commentNqlt = new CommentNqlt();

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
			private String zanCount;
			private String pinglunCount;
			private String zanFlag ; 
			
			private String commentId;

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
					String content, String zanCount, String pinglunCount,
					String commentId ,String zanflag) {
				super();
				this.id = id;
				this.nick = nick;
				this.icon = icon;
				this.date = date;
				this.content = content;
				this.zanCount = zanCount;
				this.pinglunCount = pinglunCount;
				this.commentId = commentId;
				this.zanFlag = zanflag ;
			}

		

		

		
		}
		
		public class CommentNqlt{
			int id;

			public CommentNqlt(int id) {
				super();
				this.id = id;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public CommentNqlt() {
				super();
				// TODO Auto-generated constructor stub
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
		
		

		public CommentNqlt getCommentNqlt() {
			return commentNqlt;
		}

		public void setCommentNqlt(CommentNqlt commentNqlt) {
			this.commentNqlt = commentNqlt;
		}

		public AisZxComment() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getZanFlag() {
			return zanFlag;
		}

		public void setZanFlag(String zanFlag) {
			this.zanFlag = zanFlag;
		}

		public AisZxComment(String id, String content,
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

		
}