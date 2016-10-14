package com.android.app.util;

public class ClickStateChangeUtils {
    
	private static boolean comment_zan ; 
	private static boolean reply_zan ; 
	private static boolean reply_comment_zan ;
	
	private static boolean zx_comment_zan ; 
	private static boolean zx_reply_zan ; 
	private static boolean zx_reply_comment_zan ;
	
	
	
	
	public static boolean isComment_zan() {
		return comment_zan;
	}
	public static void setComment_zan() {
		comment_zan = !comment_zan;
	}
	public static boolean isReply_zan() {
		return reply_zan;
	}
	public static void setReply_zan() {
		reply_zan = !reply_zan;
	}
	public static boolean isReply_comment_zan() {
		return reply_comment_zan;
	}
	public static void setReply_comment_zan() {
		reply_comment_zan = !reply_comment_zan;
	}
	public static boolean isZx_comment_zan() {
		return zx_comment_zan;
	}
	public static void setZx_comment_zan() {
		zx_comment_zan = !zx_comment_zan;
	}
	public static boolean isZx_reply_zan() {
		return zx_reply_zan;
	}
	public static void setZx_reply_zan() {
		zx_reply_zan = !zx_reply_zan;
	}
	public static boolean isZx_reply_comment_zan() {
		return zx_reply_comment_zan;
	}
	public static void setZx_reply_comment_zan() {
		zx_reply_comment_zan = !zx_reply_comment_zan;
	}
   
	
	public static void setAllFalse() {
		comment_zan = false; 
		reply_zan = false ;
		reply_comment_zan = false ; 
		zx_comment_zan = false ; 
		zx_reply_zan = false ; 
		zx_reply_comment_zan = false ; 
	}
   


}
