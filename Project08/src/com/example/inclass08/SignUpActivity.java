/*
 * Assignment InClass08.
 * SignUpActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		((Button) findViewById(R.id.btn_signup_signup))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!isValid()) {
							Toast.makeText(
									SignUpActivity.this,
									getString(R.string.signup_err_missing_inputs),
									Toast.LENGTH_LONG).show();
							return;
						}
						if (!passwordsMatch()) {
							Toast.makeText(
									SignUpActivity.this,
									getString(R.string.signup_err_pass_mismatch),
									Toast.LENGTH_LONG).show();
							return;
						}

						registerNewUser();

					}
				});

		((Button) findViewById(R.id.btn_signup_cancel))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(SignUpActivity.this,
								LoginActivity.class);
						startActivity(intent);
						finish();
					}
				});

	}

	protected void registerNewUser() {
		EditText etName = (EditText) findViewById(R.id.et_signup_name);
		EditText etEmail = (EditText) findViewById(R.id.et_signup_email);
		EditText etPassword = (EditText) findViewById(R.id.et_signup_password);

		ParseUser user = new ParseUser();
		user.setUsername(etEmail.getText().toString());
		user.setPassword(etPassword.getText().toString());
		user.setEmail(etEmail.getText().toString());
		user.add("Name", etName.getText().toString());
		user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {
				if (null == e) {
					Toast.makeText(SignUpActivity.this,
							getString(R.string.signup_success_text),
							Toast.LENGTH_LONG).show();

					(new Handler()).postDelayed(new Runnable() {
						@Override
						public void run() {
							startTodoActivity();
						}
					}, 3000);
				} else {
					if (e.getCode() == e.EMAIL_TAKEN) {
						Toast.makeText(SignUpActivity.this, getString(R.string.signup_err_email_taken),
								Toast.LENGTH_LONG).show();
					} else {

						Toast.makeText(SignUpActivity.this, e.getMessage(),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	protected void startTodoActivity() {
		Intent intent = new Intent(SignUpActivity.this, ToDoActivity.class);
		startActivity(intent);
		finish();
	}

	protected boolean isValid() {
		EditText etName = (EditText) findViewById(R.id.et_signup_name);
		EditText etEmail = (EditText) findViewById(R.id.et_signup_email);
		EditText etPassword = (EditText) findViewById(R.id.et_signup_password);
		EditText etConfPassword = (EditText) findViewById(R.id.et_signup_conf_password);

		if ("".equalsIgnoreCase(etName.getText().toString().trim())
				|| "".equalsIgnoreCase(etEmail.getText().toString().trim())
				|| "".equalsIgnoreCase(etPassword.getText().toString().trim())
				|| "".equalsIgnoreCase(etConfPassword.getText().toString()
						.trim())) {
			return false;
		} else {
			if (!etPassword.getText().toString()
					.equalsIgnoreCase(etConfPassword.getText().toString())) {
				return false;
			}
			return true;
		}
	}

	protected boolean passwordsMatch() {
		EditText etPassword = (EditText) findViewById(R.id.et_signup_password);
		EditText etConfPassword = (EditText) findViewById(R.id.et_signup_conf_password);
		if (!etPassword.getText().toString()
				.equalsIgnoreCase(etConfPassword.getText().toString())) {
			return false;
		}
		return true;
	}
}
