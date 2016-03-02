/*
 * Assignment In Class 3.
 * EditActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		final Student student = (Student)getIntent().getExtras().get(MainActivity.STUDENT_KEY);
		setDisplayData(student);
		
		String editType = (String)getIntent().getExtras().get(MainActivity.EDIT_TYPE_KEY);
		setVisableViews(editType);
		
		
		Button btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!checkFields()){
					return;
				}
				setStudentData(student);
				Intent intent = new Intent();
				intent.putExtra(MainActivity.STUDENT_KEY, student);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		
	}
	
	private void setVisableViews(String editType){
		//TextView nameCap = (TextView)findViewById(R.id.tvNameCaption);
		EditText name = (EditText) findViewById(R.id.etName);
		
		//TextView emailCap = (TextView)findViewById(R.id.tvEmailCaption);
		EditText email = (EditText) findViewById(R.id.etEmail);
		
		TextView langCap = (TextView)findViewById(R.id.tvFavoriteLanguageCaption);
		RadioGroup rgLanguage = (RadioGroup) findViewById(R.id.rgFavoriteLanguage);
		
		Switch swSearchable = (Switch)findViewById(R.id.swSearchable);
		
		TextView moodCap = (TextView)findViewById(R.id.tvMoodCaption);
		SeekBar sbMood = (SeekBar)findViewById(R.id.sbMood);
		
		
		if(editType.equalsIgnoreCase("NAME")){
			//nameCap.setVisibility(View.VISIBLE);
			name.setVisibility(View.VISIBLE);
		}
		
		if(editType.equalsIgnoreCase("EMAIL")){
			//emailCap.setVisibility(View.VISIBLE);
			email.setVisibility(View.VISIBLE);
					
		}
		if(editType.equalsIgnoreCase("LANGUAGE")){
			langCap.setVisibility(View.VISIBLE);
			rgLanguage.setVisibility(View.VISIBLE);
			
		}
		if(editType.equalsIgnoreCase("STATUS")){
			swSearchable.setVisibility(View.VISIBLE);
		}
		if(editType.equalsIgnoreCase("MOOD")){
			moodCap.setVisibility(View.VISIBLE);
			sbMood.setVisibility(View.VISIBLE);
			
		}
		
	}
	
	
	protected void setDisplayData(Student student){
		EditText name = (EditText) findViewById(R.id.etName);
		EditText email = (EditText) findViewById(R.id.etEmail);
		Switch swSearchable = (Switch)findViewById(R.id.swSearchable);
		SeekBar sbMood = (SeekBar)findViewById(R.id.sbMood);
		
		name.setText(student.getName());
		email.setText(student.getEmail());
		if(student.getLanguage().equalsIgnoreCase("Java")){
			((RadioButton)findViewById(R.id.rbJava)).setChecked(true);
		}
		if(student.getLanguage().equalsIgnoreCase("C")){
			((RadioButton)findViewById(R.id.rbC)).setChecked(true);
		}
		if(student.getLanguage().equalsIgnoreCase("C#")){
			((RadioButton)findViewById(R.id.rbCsharp)).setChecked(true);
		}
		
		swSearchable.setChecked(student.isStatus());
		sbMood.setProgress(student.getMood());
	}
	
	protected boolean checkFields(){
		EditText name = (EditText) findViewById(R.id.etName);
		EditText email = (EditText) findViewById(R.id.etEmail);
		
		if("".equalsIgnoreCase(name.getText().toString())){
			Toast.makeText(this, getString(R.string.err_missing_name), Toast.LENGTH_LONG).show();;
			return false;
		}
		
		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
			Toast.makeText(this, getString(R.string.err_missing_email), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	protected void setStudentData(Student student) {
		EditText name = (EditText) findViewById(R.id.etName);
		EditText email = (EditText) findViewById(R.id.etEmail);
		RadioGroup rgLanguage = (RadioGroup) findViewById(R.id.rgFavoriteLanguage);
		Switch swSearchable = (Switch)findViewById(R.id.swSearchable);
		SeekBar sbMood = (SeekBar)findViewById(R.id.sbMood);
		student.setName(name.getText().toString());
		student.setEmail(email.getText().toString());
		
		
		switch (rgLanguage.getCheckedRadioButtonId()) {
		case R.id.rbJava:
			student.setLanguage("Java");
			break;
		case R.id.rbC:
			student.setLanguage("C");
			break;
		case R.id.rbCsharp:
			student.setLanguage("C#");
			break;
		default:
			break;
		}
		
		student.setStatus(swSearchable.isChecked());
		student.setMood(sbMood.getProgress());
	}
}
