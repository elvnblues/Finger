package com.elvn.finger.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.elvn.finger.R;
import com.elvn.finger.adapter.LanguageAdapter;
import com.elvn.finger.constrant.Const;
import com.elvn.finger.constrant.ConstClass;
import com.elvn.finger.db.dao.LanguageDao;
import com.elvn.finger.entity.Language;

public class SettingAct extends NotifitionActivity {
	//UI
	private TextView title_top_text;
	private Button title_top_return;
	
	private Spinner spinner_language;
	
	
	//DATA
	private LanguageAdapter languageAdapter;
	private LanguageDao languageDao;
	private List<Language> languageList;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int languageID = ConstClass.getSharedPreferencesVal(Const.SETTINGLANGUAGE, SettingAct.this, Const.SETTINGLANGNOW);
		spinner_language.setSelection(languageID);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_setting);
		init();
		setListener();
	}
	
	private void init(){
		title_top_text = (TextView)findViewById(R.id.title_top_text);
		title_top_return = (Button)findViewById(R.id.title_top_left);
		
		title_top_text.setText(R.string.setting);
		title_top_return.setBackgroundResource(R.drawable.title_top_return);
		title_top_return.setVisibility(View.VISIBLE);
		
		spinner_language = (Spinner)findViewById(R.id.setting_pinner_language);
		
		languageDao = new LanguageDao(SettingAct.this);
		languageList = languageDao.getLanguageAll();
		languageAdapter = new LanguageAdapter(SettingAct.this, languageList);
		spinner_language.setAdapter(languageAdapter);
		
		
	}
	private void setListener(){
		title_top_return.setOnClickListener(btn_OnClickListener);
		spinner_language.setOnItemSelectedListener(spinner_onItemClickListener);
	}
	
	private OnClickListener btn_OnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_top_left://返回
				finish();
				break;

			default:
				break;
			}
		}
	};
	private OnItemSelectedListener  spinner_onItemClickListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO 选择语言触发
			//保存选中的语言ID
			ConstClass.setSharedPreferencesVal(SettingAct.this, Const.SETTINGLANGUAGE, Const.SETTINGLANGNOW, position);
			System.out.println("positionposition=="+position);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
}
