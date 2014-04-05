package com.elvn.finger.db.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.elvn.finger.db.DBConst;

public class TopicDao extends BaseDao {

	public TopicDao(Context context) {
		super(context);
	}

	public TopicDao(Context context, boolean isRead) {
		super(context, isRead);
	}

	// column key
	private static final int _ID = 0;

	private static final String TITLE = "title";

	private static final String CONTENT = "content";

	private static final String TOPIC_TABLE = DBConst.TOPIC_TABLE;

	/**
	 * Add Topic
	 * 
	 * @param title
	 * @param content
	 * @return
	 */
	public long add(String title, String content) {
		ContentValues values = new ContentValues();
		values.put(TITLE, title);
		values.put(CONTENT, content);
		return sqliteDB.insert(DBConst.TOPIC_TABLE, null, values);
	}

	/**
	 * 通过Title 模糊查询所有Title
	 * @param v_title
	 * @return
	 */
	public List<Map<String, String>> searchTitle(String v_title,int languageID) {
		List<Map<String, String>> titleList = new ArrayList<Map<String, String>>();
		String sqlString = "select  * from " + TOPIC_TABLE
					+ " where title like ? and _languageID = "+languageID+" order by _id";
		
		String[] selectionArgs = new String[] { "%" + v_title + "%" };
		Cursor cursor = null;
		try{
			cursor = sqliteDB.rawQuery(sqlString, selectionArgs);
			
			while(cursor.moveToNext()){
				Map<String, String> titleMap = new HashMap<String, String>();
				titleMap.put(TITLE, cursor.getString(1));
				titleMap.put(CONTENT, cursor.getString(2));
				titleList.add(titleMap);
			}
				
				
		}catch(Exception ex){
			ex.printStackTrace();
			if(cursor!=null){
				try{
					cursor.close();
				}catch(Exception e){
					
				}
			}
		}
		return titleList;
	}


//	public String getContentByTitle() {
//
//	}
}
