/*
 * Assignment InClass06.
 * GalleryActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GalleryActivity extends Activity {
	public static final String PHOTO_KEY = "PHOTO_KEY";
	ProgressDialog loadingDialog = null;
	ArrayList<Photo> searchResults = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		String searchTerm = getIntent().getExtras().getString(MainActivity.SEARCH_KEY);
		
		String searchUri = getString(R.string.api_uri_template);
		
		try {
			searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchUri = searchUri.replace(getString(R.string.api_term_tag), searchTerm);
		
		
		loadingDialog = new ProgressDialog(GalleryActivity.this);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setMessage(getString(R.string.results_loading_caption));
		loadingDialog.show();
		
		new PhotoSearchTask(searchUri).execute();
	}
	
	
	protected void onSearchComplete(ArrayList<Photo> results){
		loadingDialog.dismiss();
		searchResults = results;
		
		//Wire up listview adapter
		ListView listView = (ListView) findViewById(R.id.lvPhotoResults);
		ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, android.R.id.text1, searchResults);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Log.d("InClass06", "Loading details for " + ((TextView)view).getText().toString());
				Intent intent = new Intent(GalleryActivity.this, DetailsActivity.class);
				intent.putExtra(PHOTO_KEY, searchResults.get(position));
				startActivity(intent);
			}
		});
		
	}
	
	
	private class PhotoSearchTask extends AsyncTask<Void, Void, ArrayList<Photo>> {

		String searchUri = "";
		
		public PhotoSearchTask(String searchUri){
			this.searchUri = searchUri;
		}
		
		@Override
		protected ArrayList<Photo> doInBackground(Void... params) {
			ArrayList<Photo> results = new ArrayList<Photo>();

			try {
				Log.d("InClass06", "500px API called: " + this.searchUri);
				
				URL url = new URL(this.searchUri);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String lineBuffer = "";
				StringBuilder sb = new StringBuilder();
				
				try{
					while ((lineBuffer = in.readLine()) != null) {
						sb.append(lineBuffer);
					}
					
					in.close();
					String jsonBuffer = sb.toString();
					JSONObject jsonResponse = new JSONObject(jsonBuffer);
					JSONArray jsonPhotoArray = jsonResponse.getJSONArray("photos");
					for(int i=0; i< jsonPhotoArray.length(); i++){
						Photo photo =Photo.createFromJSON(jsonPhotoArray.getJSONObject(i));
						Log.d("InClass06", photo.toString());
						results.add(photo);
					}
					
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
		protected void onPostExecute(ArrayList<Photo> result) {
			onSearchComplete(result);
			super.onPostExecute(result);
		}
	}
}
