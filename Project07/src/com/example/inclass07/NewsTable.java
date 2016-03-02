/*
 * Assignment InClass07.
 * NewsTable.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsTable {

	static final String TABLE_NAME = "history";
	static final String NEWS_LINK = "link";
	static final String NEWS_TITLE = "title";
	static final String NEWS_DESCRIPTION = "description";
	static final String NEWS_SMALL_IMAGE = "url";
	static final String NEWS_LARGE_IMAGE = "thumbnail";
	static final String NEWS_PUB_DATE = "pubDate";
	static final String NEWS_COUNT = "count" ;
	

	static public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + NewsTable.TABLE_NAME + " (");
		sb.append(NewsTable.NEWS_LINK + " text not null primary key, ");
		sb.append(NewsTable.NEWS_TITLE + " text not null, ");
		sb.append(NewsTable.NEWS_DESCRIPTION + " text not null, ");
		sb.append(NewsTable.NEWS_SMALL_IMAGE + " text, ");
		sb.append(NewsTable.NEWS_LARGE_IMAGE + " text, ");
		sb.append(NewsTable.NEWS_PUB_DATE + " text, ");
		sb.append(NewsTable.NEWS_COUNT + " integer DEFAULT 0");
		sb.append(");");
		try {
			db.execSQL(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NewsTable.TABLE_NAME);
		NewsTable.onCreate(db);
	}
	
	static public void onTruncate(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS " + NewsTable.TABLE_NAME);
		NewsTable.onCreate(db);
	}
}
