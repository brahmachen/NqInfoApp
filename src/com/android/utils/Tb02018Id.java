package com.android.utils;

import java.math.BigDecimal;

/**
 * Tb02018Id entity. @author MyEclipse Persistence Tools
 */

public class Tb02018Id implements java.io.Serializable {

	// Fields

	private String dim00101;
	private String dim00102;
	private String dim00104;
	private String dim016;
	private BigDecimal idx0316;

	// Constructors

	/** default constructor */
	public Tb02018Id() {
	}

	/** full constructor */
	public Tb02018Id(String dim00101, String dim00102, String dim00104,
			String dim016, BigDecimal idx0316) {
		this.dim00101 = dim00101;
		this.dim00102 = dim00102;
		this.dim00104 = dim00104;
		this.dim016 = dim016;
		this.idx0316 = idx0316;
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

	public String getDim00104() {
		return this.dim00104;
	}

	public void setDim00104(String dim00104) {
		this.dim00104 = dim00104;
	}

	public String getDim016() {
		return this.dim016;
	}

	public void setDim016(String dim016) {
		this.dim016 = dim016;
	}

	public BigDecimal getIdx0316() {
		return this.idx0316;
	}

	public void setIdx0316(BigDecimal idx0316) {
		this.idx0316 = idx0316;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tb02018Id))
			return false;
		Tb02018Id castOther = (Tb02018Id) other;

		return ((this.getDim00101() == castOther.getDim00101()) || (this
				.getDim00101() != null && castOther.getDim00101() != null && this
				.getDim00101().equals(castOther.getDim00101())))
				&& ((this.getDim00102() == castOther.getDim00102()) || (this
						.getDim00102() != null
						&& castOther.getDim00102() != null && this
						.getDim00102().equals(castOther.getDim00102())))
				&& ((this.getDim00104() == castOther.getDim00104()) || (this
						.getDim00104() != null
						&& castOther.getDim00104() != null && this
						.getDim00104().equals(castOther.getDim00104())))
				&& ((this.getDim016() == castOther.getDim016()) || (this
						.getDim016() != null && castOther.getDim016() != null && this
						.getDim016().equals(castOther.getDim016())))
				&& ((this.getIdx0316() == castOther.getIdx0316()) || (this
						.getIdx0316() != null && castOther.getIdx0316() != null && this
						.getIdx0316().equals(castOther.getIdx0316())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDim00101() == null ? 0 : this.getDim00101().hashCode());
		result = 37 * result
				+ (getDim00102() == null ? 0 : this.getDim00102().hashCode());
		result = 37 * result
				+ (getDim00104() == null ? 0 : this.getDim00104().hashCode());
		result = 37 * result
				+ (getDim016() == null ? 0 : this.getDim016().hashCode());
		result = 37 * result
				+ (getIdx0316() == null ? 0 : this.getIdx0316().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Tb02018Id [dim00101=" + dim00101 + ", dim00102=" + dim00102
				+ ", dim00104=" + dim00104 + ", dim016=" + dim016
				+ ", idx0316=" + idx0316 + "]";
	}

}