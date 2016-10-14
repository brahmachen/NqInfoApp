package com.android.app.sqlite.util;
import java.util.ArrayList;
import java.util.List;

import com.android.app.entity.CollectNews;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBServer 
{
	private DBHelper dbhelper;
	public DBServer(Context context)
	{
		 this.dbhelper = new DBHelper(context);
	}
	/**
	 * ����ղ�
	 * @param entity
	 */
	public void addNews(CollectNews entity)
	{
		  
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		  Object[] arrayOfObject = new Object[5];
		  arrayOfObject[0] = entity.getNewsId() ; 
		  arrayOfObject[1] = entity.getNqxxId() ; 
		  arrayOfObject[2] = entity.getNewsType() ; 
		  arrayOfObject[3] = entity.getNewsDate() ;
		  arrayOfObject[4] = entity.getUserId() ; 
		  localSQLiteDatabase.execSQL("insert into news(news_Id , news_nqxxId , news_type , news_date ,userId ) values(?,?,?,? ,?)", arrayOfObject);
		  localSQLiteDatabase.close();
		  System.out.println("******************-------------------->>>>insert news success");
	}

	
	/**
	 * ɾ��һ���ղ�
	 * @param student_id
	 */
	public void deleteCollectNewsById(String news_id)
	{
	    SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	    Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] = news_id;
	    localSQLiteDatabase.execSQL("delete from news where news_Id=?", arrayOfObject);
	    localSQLiteDatabase.close();
	}
	

	/**
	 * ɾ�������ղ�
	 * @param student_id
	 */
	public void deleteAllCollectNews(String userId)
	{
	    SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	    Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] = userId;
	    localSQLiteDatabase.execSQL("delete from news where userId=?" , arrayOfObject);
	    localSQLiteDatabase.close();
	}
	
	
	
	/**
	 * ʹ�����źŲ���������Ϣ
	 * @param classId
	 * @return
	 */
	public List<CollectNews> findCollectNewsByNewsId(String newId)
	{
		  List<CollectNews> localArrayList=new ArrayList<CollectNews>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select news_Id , news_nqxxId, news_type , news_date  from news  " +
		  		"where news_Id=?  order by news_date desc", new String[]{newId});
		 
		  while (localCursor.moveToNext())
		  {
			  CollectNews temp=new CollectNews();
			  temp.setNewsId(localCursor.getString(localCursor.getColumnIndex("news_Id")));
			  temp.setNqxxId(localCursor.getString(localCursor.getColumnIndex("news_nqxxId")));
			  temp.setNewsType(localCursor.getString(localCursor.getColumnIndex("news_type")));
			  temp.setNewsDate(localCursor.getString(localCursor.getColumnIndex("news_date")));
			  temp.setUserId(localCursor.getString(localCursor.getColumnIndex("userId")));
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }
	
	
	/**
	 * ������������
	 * @param className
	 * @return
	 */
	public List<CollectNews> findAllCollectNews(String userId)
	{
		  List<CollectNews> localArrayList=new ArrayList<CollectNews>();
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();  
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select * from news " +
		  		"where userId=?  order by news_date desc ", new String[]{userId});
		  while (localCursor.moveToNext())
		  {
			  CollectNews temp=new CollectNews();
			  temp.setNewsId(localCursor.getString(localCursor.getColumnIndex("news_Id")));
			  temp.setNqxxId(localCursor.getString(localCursor.getColumnIndex("news_nqxxId")));
			  temp.setNewsType(localCursor.getString(localCursor.getColumnIndex("news_type")));
			  temp.setNewsDate(localCursor.getString(localCursor.getColumnIndex("news_date")));
			  temp.setUserId(localCursor.getString(localCursor.getColumnIndex("userId")));
		      localArrayList.add(temp);
		  }
		  localSQLiteDatabase.close();
		  return localArrayList;
	 }

	
	/**
	 * �����Ƿ��и�����
	 * @param studentId
	 * @return
	 */
	public boolean isNewsExists(String newsId)
	{
		  SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();    
		  Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from news  " +
		  		"where news_Id=?", new String[]{newsId});
		  localCursor.moveToFirst();
		  if(localCursor.getLong(0)>0)
			  return true;
		  else
			  return false;
	 }
	
	
	
	/*
	*//**
	 * ɾ��һ���ղ�
	 * ͬʱ��ɾ��News�иð༶������
	 * @param class_id
	 *//*
	public void deleteClass(String class_id)
	{
	    SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	    //�����˼���ɾ���ͼ�������
	    //��ִ���м�����ϵ������ʱ����������á�PRAGMA foreign_keys=ON��
	    //��������ϵĬ��ʧЧ
	    localSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
	    Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] =class_id;
	    localSQLiteDatabase.execSQL("delete from classes where class_id=?", arrayOfObject);
	    localSQLiteDatabase.close();
	}*/
	

	/*
	*//**
	 * �޸��ղ���Ϣ
	* @param entity
	*//*
   public void updateStudentInfo(Student entity)
   {
	   SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
	   Object[] arrayOfObject = new Object[4];
	   
	   arrayOfObject[0] = entity.getStudentName();
	   arrayOfObject[1] = entity.getScore();
	   arrayOfObject[2] = entity.getClassId();
	   arrayOfObject[3] = entity.getStudentId();
   
	   localSQLiteDatabase.execSQL("update students set student_name=?,score=?,class_id=?  where student_id=?", arrayOfObject);
	   localSQLiteDatabase.close();
   }*/
}
