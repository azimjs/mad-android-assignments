/*
 * Assignment InClass08.
 * LoginActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		ParseUser current = ParseUser.getCurrentUser();
		
		if(current != null){
			startToDoActivity();
		}
		
		
		((Button)findViewById(R.id.btn_log_login)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isValid()){
					Toast.makeText(LoginActivity.this, getString(R.string.logon_err_missing_inputs), Toast.LENGTH_LONG).show();
				}else{
					EditText etEmail = (EditText)findViewById(R.id.et_log_email);
					EditText etPassword = (EditText)findViewById(R.id.et_log_password);
					
					ParseUser.logInInBackground(etEmail.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
						  public void done(ParseUser user, ParseException e) {
						    if (user != null) {
						    	Log.d("InClass08", "User successfully logged in");
						    	startToDoActivity();
						    } else {
						    	Toast.makeText(LoginActivity.this, getString(R.string.logon_err_failed_signin), Toast.LENGTH_LONG).show();
						    }
						  }
						});
				}
			}
		});
		
		((Button)findViewById(R.id.btn_log_create)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSignUpActivity();
			}
		});
		
		
		
	}
	
	protected boolean isValid(){
		EditText etEmail = (EditText)findViewById(R.id.et_log_email);
		EditText etPassword = (EditText)findViewById(R.id.et_log_password);
		
		if("".equalsIgnoreCase(etEmail.getText().toString().trim()) || 
				"".equalsIgnoreCase(etPassword.getText().toString().trim())){
			return false;
		}else{
			return true;
		}
	}
	
	protected void startToDoActivity(){
		Intent intent = new Intent(LoginActivity.this, ToDoActivity.class);
		startActivity(intent);
		finish();
	}
	
	protected void startSignUpActivity(){
		Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
		startActivity(intent);
		finish();
	}
}
