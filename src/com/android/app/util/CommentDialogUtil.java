package com.android.app.util;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.ais.app.R;
import com.android.app.entity.AisLtComment;

public class CommentDialogUtil {
	
	private Context context;
	private Dialog mDialog;
	LayoutInflater inflater;

	private String userId;
	private String topicId;
	private String commentFlag;
	private String commentType;


	public CommentDialogUtil(Context mContext,String topicId,
			String userId, String commentType,  String commentFlag) {
		super();
		this.context = mContext;
		this.inflater = LayoutInflater.from(context);
		this.userId = userId;
		this.topicId = topicId;
		this.commentFlag = commentFlag;
		this.commentType = commentType;
	}

	
	


	public void displayDialog(Context context, View view, int gravity) {
		displayDialog(context, view, gravity, Color.TRANSPARENT);
	}

	public void displayDialog(Context context, View view, int gravity,
			int backGround) {
	/*	if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return;
		}
		dismissDialog();*/
		
		mDialog = new Dialog(context, R.style.ShareMenuDialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(true);
		Window window = mDialog.getWindow();
		window.setGravity(gravity);
		window.setBackgroundDrawable(new ColorDrawable(backGround));
		window.setWindowAnimations(R.style.ShareAnimation);
		WindowManager.LayoutParams mParams = mDialog.getWindow()
				.getAttributes();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		mParams.width = (int) (display.getWidth() * 1.0);
		mDialog.getWindow().setAttributes(mParams);
		mDialog.show();
	}

	public void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		mDialog = null;
	}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getTopicId() {
		return topicId;
	}


	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}


	public String getCommentFlag() {
		return commentFlag;
	}


	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}


	public String getCommentType() {
		return commentType;
	}


	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

}
