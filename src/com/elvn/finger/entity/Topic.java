package com.elvn.finger.entity;

import android.R.integer;

public class Topic {
	private int _id;
	private String title;
	private String content;
	private int _languageID;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int get_languageID() {
		return _languageID;
	}
	public void set_languageID(int _languageID) {
		this._languageID = _languageID;
	}
	
	
}
