package com.android.utils;

import java.math.BigDecimal;

/**
 * Tb02015Id entity. @author MyEclipse Persistence Tools
 */

public class Tb02015Id implements java.io.Serializable {

	// Fields

	private String dim00101;
	private String dim00102;
	private String dim00105;
	private String dim016;
	private BigDecimal idx0322;

	// Constructors

	/** default constructor */
	public Tb02015Id() {
	}

	/** full constructor */
	public Tb02015Id(String dim00101, String dim00102, String dim00105,
			String dim016, BigDecimal idx0322) {
		this.dim00101 = dim00101;
		this.dim00102 = dim00102;
		this.dim00105 = dim00105;
		this.dim016 = dim016;
		this.idx0322 = idx0322;
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

	public String getDim00105() {
		return this.dim00105;
	}

	public void setDim00105(String dim00105) {
		this.dim00105 = dim00105;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Tb02015Id))
			return false;
		Tb02015Id castOther = (Tb02015Id) other;

		return ((this.getDim00101() == castOther.getDim00101()) || (this
				.getDim00101() != null && castOther.getDim00101() != null && this
				.getDim00101().equals(castOther.getDim00101())))
				&& ((this.getDim00102() == castOther.getDim00102()) || (this
						.getDim00102() != null
						&& castOther.getDim00102() != null && this
						.getDim00102().equals(castOther.getDim00102())))
				&& ((this.getDim00105() == castOther.getDim00105()) || (this
						.getDim00105() != null
						&& castOther.getDim00105() != null && this
						.getDim00105().equals(castOther.getDim00105())))
				&& ((this.getDim016() == castOther.getDim016()) || (this
						.getDim016() != null && castOther.getDim016() != null && this
						.getDim016().equals(castOther.getDim016())))
				&& ((this.getIdx0322() == castOther.getIdx0322()) || (this
						.getIdx0322() != null && castOther.getIdx0322() != null && this
						.getIdx0322().equals(castOther.getIdx0322())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDim00101() == null ? 0 : this.getDim00101().hashCode());
		result = 37 * result
				+ (getDim00102() == null ? 0 : this.getDim00102().hashCode());
		result = 37 * result
				+ (getDim00105() == null ? 0 : this.getDim00105().hashCode());
		result = 37 * result
				+ (getDim016() == null ? 0 : this.getDim016().hashCode());
		result = 37 * result
				+ (getIdx0322() == null ? 0 : this.getIdx0322().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Tb02015Id [dim00101=" + dim00101 + ", dim00102=" + dim00102
				+ ", dim00105=" + dim00105 + ", dim016=" + dim016
				+ ", idx0322=" + idx0322 + "]";
	}

}