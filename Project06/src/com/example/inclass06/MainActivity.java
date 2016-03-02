/*
 * Assignment InClass06.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass06;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String SEARCH_KEY = "SEARCH_KEY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		((Button)findViewById(R.id.btnSubmit)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isConnectedOnline()){
					Toast.makeText(MainActivity.this, getString(R.string.err_missing_internet_connection), Toast.LENGTH_LONG).show();
					return;
				}
				
				EditText etSearch = (EditText)findViewById(R.id.etSearch);
				
				String searchTerm = etSearch.getText().toString();
				Log.d("InClass06", "Search Term -> " + searchTerm);
				if("".equalsIgnoreCase(searchTerm.trim())){
					Toast.makeText(MainActivity.this, getString(R.string.err_missing_term), Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
				intent.putExtra(SEARCH_KEY, searchTerm.trim());
				startActivity(intent);
			}
		});
	}
	
	private boolean isConnectedOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	
	
}
