/*
 * Assignment In Class 4.
 * SlideShowActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class SlideShowActivity extends Activity {
	ProgressDialog progressUrl;
	ProgressDialog imageUrl;
	ArrayList<String> al;
	int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_show);
		new GetImageUrl().execute(getString(R.string.image_uri));
	}

	public void startSlideShow() {
		new SlideShowTask().execute(al.get(0));
	}

	class GetImageUrl extends AsyncTask<String, Integer, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			al = new ArrayList<String>();
			
			try {
				Log.d("InClass04", "Fetching images from " + params[0]);
				URL url = new URL(params[0]);
				String line = "";
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				while ((line = in.readLine()) != null) {
					al.add(line);
				}
				
				in.close();

			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressUrl.dismiss();
			Log.d("InClass04","Completed fetching of "+ al.size() + " image Uri's");
			startSlideShow();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressUrl = new ProgressDialog(SlideShowActivity.this);
			progressUrl.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressUrl.setCancelable(false);
			progressUrl.setMessage(getString(R.string.dlg_loading_image_links));
			progressUrl.show();
		}

	}

	class SlideShowTask extends AsyncTask<String, Bitmap, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			imageUrl = new ProgressDialog(SlideShowActivity.this);
			imageUrl.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			imageUrl.setCancelable(false);
			imageUrl.setMessage(getString(R.string.dlg_loading_images));
			imageUrl.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			currentIndex++;
			if (currentIndex < al.size()) {
				new SlideShowTask().execute(al.get(currentIndex));
			} else {
				finish();
			}
		}

		@Override
		protected void onProgressUpdate(Bitmap... values) {
			super.onProgressUpdate(values);
			imageUrl.hide();
			((ImageView) findViewById(R.id.ivSlideShow)).setImageBitmap(values[0]);
		}

		@Override
		protected Void doInBackground(String... params) {
	
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");

				Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
				Log.d("InClass04", image.toString());
				con.disconnect();
				Thread.sleep(750);
				publishProgress(image);
				Thread.sleep(5000);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

	}

}
