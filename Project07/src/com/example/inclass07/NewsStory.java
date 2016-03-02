/*
 * Assignment InClass07.
 * NewsStory.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsStory implements Parcelable {
	String title, description, link, large_url, thumbnail;
	int viewCount;
	Date pubDate;
	SimpleDateFormat dtSourceFormat = null;
	SimpleDateFormat dtTargetFormat = null;
	
	
	public NewsStory(){}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getUrl() {
		return large_url;
	}
	public void setUrl(String url) {
		this.large_url = url;
	}
	public String getThumbnail(){
		return thumbnail;
	}
	public void setThumbnail(String url){
		this.thumbnail = url;
	}
	
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	public int getViewCount(){
		return viewCount;
	}
	
	public void setViewCount(int viewCount){
		this.viewCount = viewCount;
	}
	
	public SimpleDateFormat getSourceDateFormater(){
		if(dtSourceFormat == null){
			dtSourceFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		}
		return dtSourceFormat;
	}
	
	public SimpleDateFormat getTargetDateFormater(){
		if(dtTargetFormat == null){
			dtTargetFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		}
		return dtTargetFormat;
	}
	
	@Override
	public String toString() {
		return this.title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.description);
		dest.writeString(this.link);
		dest.writeString(this.thumbnail);
		dest.writeString(this.large_url);
		dest.writeSerializable(this.pubDate);
		dest.writeInt(this.viewCount);
	}


	public static final Parcelable.Creator<NewsStory> CREATOR = new Parcelable.Creator<NewsStory>(){

		@Override
		public NewsStory createFromParcel(Parcel source) {
			return new NewsStory(source);
		}

		@Override
		public NewsStory[] newArray(int size) {
			return new NewsStory[size];
		}
		
	};

	private NewsStory(Parcel in){
		this.title = in.readString();
		this.description= in.readString();
		this.link = in.readString();
		this.thumbnail = in.readString();
		this.large_url = in.readString();
		this.pubDate = (Date)in.readSerializable();
		this.viewCount = in.readInt();
	}
}
