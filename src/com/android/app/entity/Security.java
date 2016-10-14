package com.android.app.entity;

import java.io.Serializable;

public class Security implements Serializable {

	private String id;
	private String nick_id;

	private String question;

	private String answer;

	public Security() {
		// TODO Auto-generated constructor stub
	}

	public Security(String nickId, String question, String answer) {
		super();
		nick_id = nickId;
		this.question = question;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick_id() {
		return nick_id;
	}

	public void setNick_id(String nickId) {
		nick_id = nickId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
