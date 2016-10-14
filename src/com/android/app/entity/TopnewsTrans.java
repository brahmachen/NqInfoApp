package com.android.app.entity;



/**
 * Topnews entity. @author MyEclipse Persistence Tools
 */

public class TopnewsTrans  implements java.io.Serializable {


    // Fields    

     private String id;
     private String nqzxinfoId;
     private String time;
     private String imgurl;


    // Constructors

    /** default constructor */
    public TopnewsTrans() {
    }

	/** minimal constructor */
    public TopnewsTrans(String id, String nqzxinfo) {
        this.id = id;
        this.nqzxinfoId = nqzxinfo;
    }
    
    /** full constructor */
    public TopnewsTrans(String id, String nqzxinfo, String time, String imgurl) {
        this.id = id;
        this.nqzxinfoId = nqzxinfo;
        this.time = time;
        this.imgurl = imgurl;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

 
    public String getNqzxinfoId() {
		return nqzxinfoId;
	}

	public void setNqzxinfoId(String nqzxinfoId) {
		this.nqzxinfoId = nqzxinfoId;
	}

	public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public String getImgurl() {
        return this.imgurl;
    }
    
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
   








}