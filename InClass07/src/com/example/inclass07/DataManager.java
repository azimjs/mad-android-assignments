/*
 * Assignment InClass07.
 * DataManager.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	NewsDAO dao;
	
	public DataManager(Context mContext){
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext);
		db = dbOpenHelper.getWritableDatabase();
		dao = new NewsDAO(db);
	}
	
	
	public void close(){
		db.close();
	}
	
	public long saveNewNews(NewsStory news) {
		return dao.saveNewNews(news);
	}
	
	public boolean updateNews(NewsStory news) {
		return dao.updateNews(news);
	}
	
	public NewsStory getNewsStory(String url) {
		return dao.getNewsCount(url);
	}
	
	public List<NewsStory> getAllNews() {
		return dao.getAll();
	}
	
	public void trucate(){
		NewsTable.onTruncate(db);
	}
}
