package com.android.app.sqlite.util;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{

	public DBHelper(Context context) {
		super(context, "myCollect.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
				String classesSQL = "CREATE TABLE news(news_Id  varchar(20)  primary key  , news_nqxxId varchar(20) , userId varchar(20) , news_type varchar(20) , news_date varchar(20) )";
		

		db.execSQL(classesSQL);
		Log.d("my", "create table classes:"+classesSQL);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		
	}

}
