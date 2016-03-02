/*
 * Assignment In Class 3.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String STUDENT_KEY = "STUDENT_KEY";
	public static final String EDIT_TYPE_KEY = "EDIT_TYPE_KEY";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		final Student student = new Student();

		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!checkFields()){
					return;
				}
				setStudentData(student);
				// Do error checking on screen

				// Create intent and pass in student data
				Intent intent = new Intent(MainActivity.this,
						DisplayActivity.class);
				intent.putExtra(STUDENT_KEY, student);
				// start intent
				startActivity(intent);
			}
		});
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
}
