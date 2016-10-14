package com.android.utils;

import java.math.BigDecimal;

/**
 * Tb02012Id entity. @author MyEclipse Persistence Tools
 */

public class Tb02012Id implements java.io.Serializable {

	// Fields

	private String dim00101;
	private String dim00102;
	private String dim016;
	private BigDecimal idx0322;
	private BigDecimal idx0321;
	private BigDecimal idx0359;

	// Constructors

	/** default constructor */
	public Tb02012Id() {
	}

	/** full constructor */
	public Tb02012Id(String dim00101, String dim00102, String dim016,
			BigDecimal idx0311, BigDecimal idx0322, BigDecimal idx0321,
			BigDecimal idx0359) {
		this.dim00101 = dim00101;
		this.dim00102 = dim00102;
		this.dim016 = dim016;
		this.idx0322 = idx0322;
		this.idx0321 = idx0321;
		this.idx0359 = idx0359;
	}

	// Property accessors

	public String getDim00101() {
		return this.dim00101;
	}

	public void setDim00101(String dim00101) {
		this.dim00101 = dim00101;
	}

	public String getDim00102() {
		return this.dim00102;
	}

	public void setDim00102(String dim00102) {
		this.dim00102 = dim00102;
	}

	public String getDim016() {
		return this.dim016;
	}

	public void setDim016(String dim016) {
		this.dim016 = dim016;
	}

	public BigDecimal getIdx0322() {
		return this.idx0322;
	}

	public void setIdx0322(BigDecimal idx0322) {
		this.idx0322 = idx0322;
	}

	public BigDecimal getIdx0321() {
		return this.idx0321;
	}

	public void setIdx0321(BigDecimal idx0321) {
		this.idx0321 = idx0321;
	}

	public BigDecimal getIdx0359() {
		return this.idx0359;
	}

	public void setIdx0359(BigDecimal idx0359) {
		this.idx0359 = idx0359;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tb02012Id))
			return false;
		Tb02012Id castOther = (Tb02012Id) other;

		return ((this.getDim00101() == castOther.getDim00101()) || (this
				.getDim00101() != null && castOther.getDim00101() != null && this
				.getDim00101().equals(castOther.getDim00101())))
				&& ((this.getDim00102() == castOther.getDim00102()) || (this
						.getDim00102() != null
						&& castOther.getDim00102() != null && this
						.getDim00102().equals(castOther.getDim00102())))
				&& ((this.getDim016() == castOther.getDim016()) || (this
						.getDim016() != null && castOther.getDim016() != null && this
						.getDim016().equals(castOther.getDim016())))
				&& ((this.getIdx0322() == castOther.getIdx0322()) || (this
						.getIdx0322() != null && castOther.getIdx0322() != null && this
						.getIdx0322().equals(castOther.getIdx0322())))
				&& ((this.getIdx0321() == castOther.getIdx0321()) || (this
						.getIdx0321() != null && castOther.getIdx0321() != null && this
						.getIdx0321().equals(castOther.getIdx0321())))
				&& ((this.getIdx0359() == castOther.getIdx0359()) || (this
						.getIdx0359() != null && castOther.getIdx0359() != null && this
						.getIdx0359().equals(castOther.getIdx0359())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDim00101() == null ? 0 : this.getDim00101().hashCode());
		result = 37 * result
				+ (getDim00102() == null ? 0 : this.getDim00102().hashCode());
		result = 37 * result
				+ (getDim016() == null ? 0 : this.getDim016().hashCode());
		result = 37 * result
				+ (getIdx0322() == null ? 0 : this.getIdx0322().hashCode());
		result = 37 * result
				+ (getIdx0321() == null ? 0 : this.getIdx0321().hashCode());
		result = 37 * result
				+ (getIdx0359() == null ? 0 : this.getIdx0359().hashCode());
		return result;
	}

}