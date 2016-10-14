package com.android.utils;

import java.math.BigDecimal;

/**
 * Tb02027Id entity. @author MyEclipse Persistence Tools
 */

public class Tb02027Id implements java.io.Serializable {

	// Fields

	private String dim00101;
	private String dim00102;
	private String dim016;
	private BigDecimal idx0327;
	private BigDecimal idx0328;

	// Constructors

	/** default constructor */
	public Tb02027Id() {
	}

	/** full constructor */
	public Tb02027Id(String dim00101, String dim00102, String dim016,
			BigDecimal idx0327, BigDecimal idx0328) {
		this.dim00101 = dim00101;
		this.dim00102 = dim00102;
		this.dim016 = dim016;
		this.idx0327 = idx0327;
		this.idx0328 = idx0328;
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

	public BigDecimal getIdx0327() {
		return this.idx0327;
	}

	public void setIdx0327(BigDecimal idx0327) {
		this.idx0327 = idx0327;
	}

	public BigDecimal getIdx0328() {
		return this.idx0328;
	}

	public void setIdx0328(BigDecimal idx0328) {
		this.idx0328 = idx0328;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tb02027Id))
			return false;
		Tb02027Id castOther = (Tb02027Id) other;

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
				&& ((this.getIdx0327() == castOther.getIdx0327()) || (this
						.getIdx0327() != null && castOther.getIdx0327() != null && this
						.getIdx0327().equals(castOther.getIdx0327())))
				&& ((this.getIdx0328() == castOther.getIdx0328()) || (this
						.getIdx0328() != null && castOther.getIdx0328() != null && this
						.getIdx0328().equals(castOther.getIdx0328())));
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
				+ (getIdx0327() == null ? 0 : this.getIdx0327().hashCode());
		result = 37 * result
				+ (getIdx0328() == null ? 0 : this.getIdx0328().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Tb02027Id [dim00101=" + dim00101 + ", dim00102=" + dim00102
				+ ", dim016=" + dim016 + ", idx0327=" + idx0327 + ", idx0328="
				+ idx0328 + "]";
	}

}