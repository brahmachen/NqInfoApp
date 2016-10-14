package com.android.app.entity;

public class ZhuTieTrans {
	private NqltInfo mNqltInfo;
	private AisLtCommentTrans aisLtCommentTrans;
	public ZhuTieTrans(NqltInfo mNqltInfo, AisLtCommentTrans aisLtCommentTrans) {
		super();
		this.mNqltInfo = mNqltInfo;
		this.aisLtCommentTrans = aisLtCommentTrans;
	}
	public NqltInfo getmNqltInfo() {
		return mNqltInfo;
	}
	public AisLtCommentTrans getAisLtCommentTrans() {
		return aisLtCommentTrans;
	}
}
