package com.elvn.finger.adapter;

import java.util.List;

import com.elvn.finger.R;
import com.elvn.finger.entity.Language;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LanguageAdapter extends BaseAdapter {
	private Context context;
	private List<Language> languageList;

	public LanguageAdapter(Context context, List<Language> languageList) {
		this.context = context;
		this.languageList = languageList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return languageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return languageList.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater = LayoutInflater.from(context);
		convertView = _LayoutInflater.inflate(R.layout.spinner_language_item, null);
		if (convertView != null) {

			TextView lanTextView = (TextView) convertView
					.findViewById(R.id.tv_spinner_language);
			Language language = languageList.get(position);
			lanTextView.setText(language.get_language());
			lanTextView.setTag(language.get_id());
		}
		return convertView;
	}

}
