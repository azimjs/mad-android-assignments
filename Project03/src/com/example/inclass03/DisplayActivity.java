/*
 * Assignment In Class 3.
 * DisplayActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	final int REQUEST_CODE = 1001;
	Student student = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		student = (Student)getIntent().getExtras().get(MainActivity.STUDENT_KEY);
		setDisplayData(student);
		
		
		((ImageButton)findViewById(R.id.ibName)).setOnClickListener(new EditClickHandler());
		((ImageButton)findViewById(R.id.ibEmail)).setOnClickListener(new EditClickHandler());
		((ImageButton)findViewById(R.id.ibLanguage)).setOnClickListener(new EditClickHandler());
		((ImageButton)findViewById(R.id.ibStatus)).setOnClickListener(new EditClickHandler());
		((ImageButton)findViewById(R.id.ibMood)).setOnClickListener(new EditClickHandler());
	}
	
	
	private void setDisplayData(Student student){
		((TextView)findViewById(R.id.tvNameDisplay)).setText(student.getName());
		((TextView)findViewById(R.id.tvEmailDisplay)).setText(student.getEmail());
		((TextView)findViewById(R.id.tvFavoriteDisplay)).setText(student.getLanguage());
		
		if(student.isStatus()){
			((TextView)findViewById(R.id.tvStatusDisplay)).setText("Searchable");
		}else{
			((TextView)findViewById(R.id.tvStatusDisplay)).setText("Unsearchable");
		}
		
		((TextView)findViewById(R.id.tvMoodDisplay)).setText(student.getMood() + "% Positive");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Student student = (Student)data.getExtras().get(MainActivity.STUDENT_KEY);
		setDisplayData(student);
		this.student = student;
	}
	
	class EditClickHandler implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String editType = "NAME";
			switch(v.getId()){
			case R.id.ibName:
				editType = "NAME";
				break;
			case R.id.ibEmail:
				editType = "EMAIL";
				break;
			case R.id.ibLanguage:
				editType = "LANGUAGE";
				break;
			case R.id.ibStatus:
				editType = "STATUS";
				break;
			case R.id.ibMood:
				editType = "MOOD";
				break;
			}
			Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.putExtra(MainActivity.STUDENT_KEY, student);
			intent.putExtra(MainActivity.EDIT_TYPE_KEY, editType);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}
}
