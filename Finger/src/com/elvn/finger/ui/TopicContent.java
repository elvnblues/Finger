package com.elvn.finger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.elvn.finger.R;

public class TopicContent extends Activity {
	//UI
	private TextView tv_content;
	private TextView tv_inf_title;
	private Button btn_return;
	private TextView tv_T_title;
	
	//DATA
	private String contentString;
	private String titleString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
		setContentView(R.layout.activity_content);
		init();
		setListener();
	}
	
	private void init(){
		Intent intent = this.getIntent();
		contentString = intent.getStringExtra("content");
		titleString = intent.getStringExtra("title");
		
		tv_content = (TextView)findViewById(R.id.tv_content);
		tv_content.setText(Html.fromHtml("<font color=\"#ff0000\">答：</font>"+contentString));
		tv_inf_title = (TextView)findViewById(R.id.tv_inf_title);
		tv_inf_title.setText(titleString);
		
		btn_return = (Button)findViewById(R.id.title_top_left);
		btn_return.setBackgroundResource(R.drawable.title_top_return);
		btn_return.setVisibility(View.VISIBLE);
		
		tv_T_title = (TextView)findViewById(R.id.title_top_text);
		tv_T_title.setText("内容");
	}
	private void setListener(){
		btn_return.setOnClickListener(btn_OnClickListener);
	}
	private OnClickListener btn_OnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.title_top_left:
				finish();
				break;
			}
		}
	};
}
