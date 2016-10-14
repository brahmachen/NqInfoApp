package com.android.app.entity;

public class CommonUser {

	private String name;
	private String pwd;
	private String question1;

	private String question2;
	private String question3;

	private String ans1;
	private String ans2;
	private String ans3;

	// private String phone = null;
	// private String idcard = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns3() {
		return ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	/*
	 * public String getPhone() { return phone; }
	 * 
	 * public void setPhone(String phone) { this.phone = phone; }
	 * 
	 * public String getIdcard() { return idcard; }
	 * 
	 * public void setIdcard(String idcard) { this.idcard = idcard; }
	 */
	public CommonUser(String name, String pwd, String question1,
			String question2, String question3, String ans1, String ans2,
			String ans3) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.question1 = question1;
		this.question2 = question2;
		this.question3 = question3;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[{\"name\":" + "\"" + name + "\"" + ",\"pwd\":" + "\"" + pwd
				+ "\"" + ",\"question1\":" + "\"" + question1 + "\""
				+ ",\"ans1\":" + "\"" + ans1 + "\"" + ",\"question2\":" + "\""
				+ question2 + "\"" + ",\"ans2\":" + "\"" + ans2 + "\""
				+ ",\"question3\":" + "\"" + question3 + "\"" + ",\"ans3\":"
				+ "\"" + ans3 + "\"" + "}]";
	}

}
