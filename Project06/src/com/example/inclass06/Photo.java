package com.example.inclass06;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable{
	private String title, url, owner;

	public Photo(){}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return this.title;
	}
	
	
	public static Photo createFromJSON(JSONObject jsonObj) throws JSONException{
		Photo photo = new Photo();
		photo.setTitle(jsonObj.getString("name"));
		
		JSONObject userObj = jsonObj.getJSONObject("user");
		photo.setOwner(userObj.getString("firstname") + " " + userObj.getString("lastname"));
		
		photo.setUrl(jsonObj.getString("image_url"));
		return photo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.owner);
		dest.writeString(this.title);
		dest.writeString(this.url);
	}
	
	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>(){

		@Override
		public Photo createFromParcel(Parcel source) {
			return new Photo(source);
		}

		@Override
		public Photo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Photo[size];
		}
		
	};
	
	private Photo(Parcel in){
		this.owner = in.readString();
		this.title = in.readString();
		this.url = in.readString();
	}
	
	
}
