package com.elvn.finger.db.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.elvn.finger.db.DBConst;

public class DBToolHelper extends SQLiteOpenHelper {

	private SQLiteDatabase myDataBase;  
	  
    private  Context myContext; 
	
	public DBToolHelper(Context context) {
		super(context, DBConst.DATABASE_NAME, null, DBConst.DATABASE_VERSION);
		myContext = context;
	}
	 /** 
     * Creates a empty database on the system and rewrites it with your own database. 
     * */  
    public void createDataBase()throws IOException {  
        boolean dbExist = checkDataBase();  
        if (dbExist) {  
            //do nothing - database already exist  
        } else {  
            //By calling this method and empty database will be created into the default system path  
            //of your application so we are gonna be able to overwrite that database with our database.  
            this.getReadableDatabase();  
            try {  
                copyDataBase();  
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }  
  
    /** 
     * Check if the database already exist to avoid re-copying the file each time you open the application. 
     * @return true if it exists, false if it doesn't 
     */  
    private boolean checkDataBase() {  
        SQLiteDatabase checkDB = null;  
        try {  
            String myPath = DBConst.DB_PATH + DBConst.DB_NAME;  
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);  
        } catch (SQLiteException e) {  
            //database does't exist yet.  
        }  
  
        if (checkDB != null) {  
            checkDB.close();  
        }  
        return checkDB != null ? true : false;  
    }  
  
    /** 
     * Copies your database from your local assets-folder to the just created empty database in the 
     * system folder, from where it can be accessed and handled. 
     * This is done by transfering bytestream. 
     * */  
    private void copyDataBase()throws IOException {  
        //Open your local db as the input stream  
        InputStream myInput = myContext.getAssets().open(DBConst.DB_NAME);  
        // Path to the just created empty db  
        String outFileName = DBConst.DB_PATH + DBConst.DB_NAME;  
        //Open the empty db as the output stream  
        OutputStream myOutput = new FileOutputStream(outFileName);  
        //transfer bytes from the inputfile to the outputfile  
        byte[]buffer = new byte[1024];  
        int length;  
        while ((length = myInput.read(buffer)) > 0) {  
            myOutput.write(buffer, 0, length);  
        }  
  
        //Close the streams  
        myOutput.flush();  
        myOutput.close();  
        myInput.close();  
    }  
  
    public void openDataBase()throws SQLException {  
        //Open the database  
        String myPath = DBConst.DB_PATH + DBConst.DB_NAME;  
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);  
    }  
  
    @Override  
    public synchronized void close() {  
        if (myDataBase != null)  
            myDataBase.close();  
        super.close();  
    }  
	@Override
	public void onCreate(SQLiteDatabase db) {
		// ���� ��
//		db.execSQL(DBConst.CREATE_TOPIC_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DBConst.DROP_SQL_PRE + DBConst.TOPIC_TABLE);
		
		onCreate(db);
	}
}