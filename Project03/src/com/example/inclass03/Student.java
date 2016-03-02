/*
 * Assignment In Class 3.
 * Students.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

	private String name;
	private String email;
	private String language;
	private boolean status;
	private int mood;
	
	public Student(){}
	
	public Student(String name, String email, String language, boolean status,
			int mood) {
		super();
		this.name = name;
		this.email = email;
		this.language = language;
		this.status = status;
		this.mood = mood;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getMood() {
		return mood;
	}
	public void setMood(int mood) {
		this.mood = mood;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(email);
		dest.writeString(language);
		if(status){
			dest.writeInt(0);
		}else{
			dest.writeInt(1);
		}
		dest.writeInt(mood);
	}
	
	
	public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>(){

		@Override
		public Student createFromParcel(Parcel source) {
			return new Student(source);
		}

		@Override
		public Student[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Student[size];
		}
		
	};
	
	private Student(Parcel in){
		this.name = in.readString();
		this.email = in.readString();
		this.language = in.readString();
		int statusInt = in.readInt();
		if(statusInt == 0){
			this.status = true;
		}else{
			this.status = false;
		}
		this.mood = in.readInt();
		
	}
	
	
}
