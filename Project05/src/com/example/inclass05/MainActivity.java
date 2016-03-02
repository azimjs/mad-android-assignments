/*
 * Assignment InClass05.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass05;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

	private int currentPhotoIndex = -1;
	private ArrayList<PhotoSearchResult> searchResults = null;
	private ProgressDialog loadingDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// switch on=SAX, off=pull

		((Button) findViewById(R.id.btnGo))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(!isConnectedOnline()){
							Toast.makeText(MainActivity.this, getString(R.string.err_missing_internet_connection), Toast.LENGTH_LONG).show();
							return;
						}
						
						EditText search = (EditText)findViewById(R.id.etSearch);
						String searchInput = search.getText().toString();
						
						if("".equals(searchInput)){
							search.setError(getString(R.string.err_missing_search));
							return;
						}
						String flickerSearchUri = getString(R.string.flickr_api_search_uri_template);
						//Create the selected parser
						Switch swParser = (Switch)findViewById(R.id.swParser);
						
						IFlickrSearchResultParser parser = null;
						if(swParser.isChecked()){
							parser = new FlickrSAXParser();
						}else{
							parser = new FlickrPullParser();
						}
						flickerSearchUri = flickerSearchUri.replace(getString(R.string.flickr_search_tag), searchInput);
						new FlickrSearchTask(flickerSearchUri, parser).execute();
					}
				});

		ImageButton ibPrevious = (ImageButton) findViewById(R.id.ibPrevious);
		ibPrevious.setEnabled(false);
		ibPrevious.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(currentPhotoIndex > 0){
							currentPhotoIndex--;
						}
						
						setButtonStates();
						showCurrentPhoto();
					}
				});

		ImageButton ibNext =(ImageButton) findViewById(R.id.ibNext);
		ibNext.setEnabled(false);
		ibNext.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(currentPhotoIndex == searchResults.size()-1){
							currentPhotoIndex = 0;
						}else{
							currentPhotoIndex++;
						}
						setButtonStates();
						showCurrentPhoto();
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
	
	protected void showCurrentPhoto(){
		ImageView iv = (ImageView)findViewById(R.id.ivPhoto);
		
		loadingDialog = new ProgressDialog(MainActivity.this);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setMessage(getString(R.string.photo_loading_caption));
		loadingDialog.show();
		
		Picasso.with(MainActivity.this).load(searchResults.get(currentPhotoIndex).getUrl()).into(iv, new Callback() {
			
			@Override
			public void onSuccess() {
				loadingDialog.dismiss();
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	protected void onSearchComplete(ArrayList<PhotoSearchResult> results){
		if(results != null && results.size() > 0){
			searchResults = results;
			currentPhotoIndex = 0;
			
			showCurrentPhoto();
			setButtonStates();
		}
	}
	
	protected void setButtonStates(){
		if(currentPhotoIndex < searchResults.size()-1){
			((ImageButton) findViewById(R.id.ibNext)).setEnabled(true);
		}
		if(currentPhotoIndex > 0){
			((ImageButton) findViewById(R.id.ibPrevious)).setEnabled(true);
		}
	}

	private class FlickrSearchTask extends
			AsyncTask<Void, Void, ArrayList<PhotoSearchResult>> {

		String searchUri = "";
		IFlickrSearchResultParser parser = null;
		
		public FlickrSearchTask(String searchUri, IFlickrSearchResultParser parser){
			this.searchUri = searchUri;
			this.parser = parser;
		}
		
		@Override
		protected ArrayList<PhotoSearchResult> doInBackground(Void... params) {
			ArrayList<PhotoSearchResult> results = null;

			try {
				Log.d("InClass05", "FlickrUri Called: " + this.searchUri);
				
				URL url = new URL(this.searchUri);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				try{
					results = parser.parseSearchResults(con.getInputStream());
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				
				return results;
				
			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return results;
		}

		@Override
		protected void onPostExecute(ArrayList<PhotoSearchResult> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				Log.d("InClass05", result.get(0).toString());
				onSearchComplete(result);
			}
		}
		
		

	}
}
