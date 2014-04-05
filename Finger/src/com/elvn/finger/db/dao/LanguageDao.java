package com.elvn.finger.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.elvn.finger.entity.Language;

import android.content.Context;
import android.database.Cursor;

public class LanguageDao extends BaseDao {
	
	private static final String TABLENAME = "language";

	public LanguageDao(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public LanguageDao(Context context,boolean isRead){
		super(context, isRead);
	}
	/**
	 * 获得所有语言信息
	 * @return
	 */
	public List<Language> getLanguageAll(){
		List<Language> languagesList = new ArrayList<Language>();
		String sqlStr = "select * from "+TABLENAME;
		Cursor cursor = null;
		try{
			cursor = sqliteDB.rawQuery(sqlStr, null);
			while(cursor.moveToNext()){
				Language language = new Language();
				language.set_id(cursor.getInt(0));
				language.set_language(cursor.getString(1));
				languagesList.add(language);
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
		return languagesList;
	}
}
