package com.elvn.finger.db;

import com.elvn.finger.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DBTest extends Activity {

	private static SQLiteDatabase db;
	private Button bn = null;
	private ListView listView;
	private static String srcString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		srcString = this.getFilesDir().toString()
				+ "/my.db3";
		System.out.println("srcString=="+srcString);
		db = SQLiteDatabase.openOrCreateDatabase(srcString, null);
		listView = (ListView) findViewById(R.id.show);
		bn = (Button) findViewById(R.id.ok);
		bn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title = ((EditText) findViewById(R.id.title)).getText()
						.toString();
				String content = ((EditText) findViewById(R.id.content))
						.getText().toString();
				try {
					insertData(db, title, content);
					Cursor cursor = db.rawQuery("select * from news_inf", null);
					inflateList(cursor);
				} catch (SQLiteException se) {
					//执行 DDL创建数据表
					db.execSQL("create table news_inf(_id integer primary key autoincrement, news_title varchar(50), news_content varchar(255))");
					//执行insert语句插入数据
					insertData(db, title, content);
					//执行查询
					Cursor cursor = db.rawQuery("select * from news_inf", null);
					inflateList(cursor);
				}
			}
		});
	}

	private void insertData(SQLiteDatabase db, String title, String content) {
		// 执行插入语句
		db.execSQL("insert into news_inf values(null,?,?)", new String[] {
				title, content });
	}

	private void inflateList(Cursor cursor) {
		// 填充 SimpleCursorAdapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(DBTest.this,
				R.layout.line, cursor, new String[] { "news_title",
						"news_content" }, new int[] { R.id.my_title,
						R.id.my_content });
		listView.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 退出程序时关闭SQLiteDatabase
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
}
