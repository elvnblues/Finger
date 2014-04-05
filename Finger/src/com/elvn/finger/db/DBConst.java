package com.elvn.finger.db;

public class DBConst {
	// ���ݿ���
	public static final String DATABASE_NAME = "finger";
	//�汾��
	public static final int DATABASE_VERSION = 2014031901;
	
	public static final String DROP_SQL_PRE = "DROP TABLE IF EXISTS ";
	
	public static String DB_PATH = "/data/data/com.elvn.finger/databases/";  
	public static final String DB_NAME = "finger";
	
	//topic
	public static final String TOPIC_TABLE = "topic";
	//�������SQL���
	public static final String CREATE_TOPIC_TABLE = "create table IF NOT EXISTS topic(_id integer primary key autoincrement,title varchar(100),content text)";
	
}
