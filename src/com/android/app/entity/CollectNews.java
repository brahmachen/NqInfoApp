package com.android.app.entity;

public class CollectNews {

	private String newsId;
	private String nqxxId;
	private String newsType;
	private String newsDate;
	private String userId ;

	public CollectNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CollectNews(String newsId, String nqxxId, String newsType,
			String newsDate, String userId) {
		super();
		this.newsId = newsId;
		this.nqxxId = nqxxId;
		this.newsType = newsType;
		this.newsDate = newsDate;
		this.userId = userId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNqxxId() {
		return nqxxId;
	}

	public void setNqxxId(String nqxxId) {
		this.nqxxId = nqxxId;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
