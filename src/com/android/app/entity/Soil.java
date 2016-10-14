package com.android.app.entity;

/**
 * Soil entity. @author MyEclipse Persistence Tools
 */

public class Soil  implements java.io.Serializable {

	// Fields

	private String idxy;
	private String contentP;
	private String contentN;
	private String contentK;
	private String contentM;
	private String ph;

	// Constructors

	/** default constructor */
	public Soil() {
	}

	/** minimal constructor */
	public Soil(String idxy) {
		this.idxy = idxy;
	}

	/** full constructor */
	public Soil(String idxy, String contentP, String contentN, String contentK,
			String contentM, String ph) {
		this.idxy = idxy;
		this.contentP = contentP;
		this.contentN = contentN;
		this.contentK = contentK;
		this.contentM = contentM;
		this.ph = ph;
	}

	// Property accessors

	public String getIdxy() {
		return this.idxy;
	}

	public void setIdxy(String idxy) {
		this.idxy = idxy;
	}

	public String getContentP() {
		return this.contentP;
	}

	public void setContentP(String contentP) {
		this.contentP = contentP;
	}

	public String getContentN() {
		return this.contentN;
	}

	public void setContentN(String contentN) {
		this.contentN = contentN;
	}

	public String getContentK() {
		return this.contentK;
	}

	public void setContentK(String contentK) {
		this.contentK = contentK;
	}

	public String getContentM() {
		return this.contentM;
	}

	public void setContentM(String contentM) {
		this.contentM = contentM;
	}

	public String getPh() {
		return this.ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

}