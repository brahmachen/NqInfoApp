package com.android.app.entity;

import java.math.BigDecimal;
/**
 * AisLtComment entity. @author MyEclipse Persistence Tools
 */

public class MyNqltCommentedTrans implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NqltInfoTrans mNqltInfo = new NqltInfoTrans();
	private AisLtCommentTrans mMyNqltAisCommented = new AisLtCommentTrans();

	public MyNqltCommentedTrans() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyNqltCommentedTrans(NqltInfoTrans mNqltInfo,
			AisLtCommentTrans mMyNqltAisCommented) {
		super();
		this.mNqltInfo = mNqltInfo;
		this.mMyNqltAisCommented = mMyNqltAisCommented;
	}


	public NqltInfoTrans getmNqltInfo() {
		return mNqltInfo;
	}



	public void setmNqltInfo(NqltInfoTrans mNqltInfo) {
		this.mNqltInfo = mNqltInfo;
	}



	public AisLtCommentTrans getmMyNqltAisCommented() {
		return mMyNqltAisCommented;
	}



	public void setmMyNqltAisCommented(AisLtCommentTrans mMyNqltAisCommented) {
		this.mMyNqltAisCommented = mMyNqltAisCommented;
	}





	public class NqltInfoTrans  {

		// Fields
		private int contentid;
		private String title;
		private String description;
		private String time;
		private String content;
		private BigDecimal modelid;
		private BigDecimal comments;
		private BigDecimal zancount;
		private String zanFlag ; 
		private String titleImg ; 
		
		public NqltInfoTrans() {
			super();
			// TODO Auto-generated constructor stub
		}
		public NqltInfoTrans(int contentid, String title,
				String description, String time, String content,
				BigDecimal modelid, BigDecimal comments, BigDecimal zancount , String zanflag  , String titleImg) {
			super();
			this.contentid = contentid;

			this.title = title;
			this.description = description;
			this.time = time;
			this.content = content;
			this.modelid = modelid;
			this.comments = comments;
			this.zancount = zancount;
			this.zanFlag = zanflag ;
			this.titleImg = titleImg ;
		}

		public int getContentid() {
			return contentid;
		}

		public void setContentid(int contentid) {
			this.contentid = contentid;
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
	

	public class AisLtCommentTrans implements java.io.Serializable {

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

		public  class CommentUser {
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

		public AisLtCommentTrans() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AisLtCommentTrans(String id, String content,
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
}