/*
 * Assignment InClass07.
 * NewsDAO.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class NewsDAO {
	private SQLiteDatabase db;

	public NewsDAO(SQLiteDatabase db) {
		this.db = db;
	}

	public long saveNewNews(NewsStory news) {
		ContentValues values = new ContentValues();
		values.put(NewsTable.NEWS_LINK, news.getLink());
		values.put(NewsTable.NEWS_TITLE, news.getTitle());
		values.put(NewsTable.NEWS_DESCRIPTION, news.getDescription());
		values.put(NewsTable.NEWS_SMALL_IMAGE, news.getThumbnail());
		values.put(NewsTable.NEWS_LARGE_IMAGE, news.getUrl());
		
		SimpleDateFormat targetFormat = news.getTargetDateFormater();
		values.put(NewsTable.NEWS_PUB_DATE, targetFormat.format(news.getPubDate()));
		values.put(NewsTable.NEWS_COUNT, news.getViewCount());
		return db.insert(NewsTable.TABLE_NAME, null, values);
	}

	public boolean updateNews(NewsStory news) {
		ContentValues values = new ContentValues();
		values.put(NewsTable.NEWS_LINK, news.getLink());
		values.put(NewsTable.NEWS_TITLE, news.getTitle());
		values.put(NewsTable.NEWS_DESCRIPTION, news.getDescription());
		values.put(NewsTable.NEWS_SMALL_IMAGE, news.getThumbnail());
		values.put(NewsTable.NEWS_LARGE_IMAGE, news.getUrl());
		
		SimpleDateFormat targetFormat = news.getTargetDateFormater();
		values.put(NewsTable.NEWS_PUB_DATE, targetFormat.format(news.getPubDate()));
		values.put(NewsTable.NEWS_COUNT, news.getViewCount());
		String url = DatabaseUtils.sqlEscapeString(news.getLink());
		return db.update(NewsTable.TABLE_NAME, values, NewsTable.NEWS_LINK + "=" + url, null) > 0;
	}

	public NewsStory getNewsCount(String url) {
		NewsStory news = null;
		url = DatabaseUtils.sqlEscapeString(url);
		Cursor c = db.query(true, NewsTable.TABLE_NAME, new String[] {
				NewsTable.NEWS_LINK, NewsTable.NEWS_TITLE, NewsTable.NEWS_DESCRIPTION, NewsTable.NEWS_SMALL_IMAGE, NewsTable.NEWS_LARGE_IMAGE, NewsTable.NEWS_PUB_DATE, NewsTable.NEWS_COUNT }, NewsTable.NEWS_LINK + "=" + url, null,null, null, null, null);
		if (c != null && c.moveToFirst()) {
			news = this.buildNewsFromCursor(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return news;
	}

	public List<NewsStory> getAll() {
		List<NewsStory> list = new ArrayList<NewsStory>();
		Cursor c = db.query(NewsTable.TABLE_NAME, new String[] {
				NewsTable.NEWS_LINK, NewsTable.NEWS_TITLE, NewsTable.NEWS_DESCRIPTION, NewsTable.NEWS_SMALL_IMAGE, NewsTable.NEWS_LARGE_IMAGE, NewsTable.NEWS_PUB_DATE, NewsTable.NEWS_COUNT }, null, null, null, null, null);
		if (c != null && c.moveToFirst() ) {
			
			do {
				NewsStory news = this.buildNewsFromCursor(c);
				if (news != null) {
					list.add(news);
				}
			} while (c.moveToNext());
			if (!c.isClosed()) {
				c.close();
			}
		}
		return list;
	}

	private NewsStory buildNewsFromCursor(Cursor c) {
		NewsStory news = null;
		if (c != null) {
			news = new NewsStory();
			news.setLink(c.getString(0));
			news.setTitle(c.getString(1));
			news.setDescription(c.getString(2));
			news.setThumbnail(c.getString(3));
			news.setUrl(c.getString(4));
			String pubDate = c.getString(5);
			SimpleDateFormat targetFormat = news.getTargetDateFormater();
			try {
				news.setPubDate(targetFormat.parse(pubDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			news.setViewCount(c.getInt(6));
		}
		return news;
	}
}
