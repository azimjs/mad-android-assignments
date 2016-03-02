/*
 * Assignment InClass06.
 * DetailsActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass06;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends Activity {
	ProgressDialog loadingDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		Photo photo = (Photo)getIntent().getExtras().get(GalleryActivity.PHOTO_KEY);
		
		TextView tvTitle = (TextView)findViewById(R.id.tvPhotoTitle);
		tvTitle.setText(photo.getTitle());
		TextView tvOwner = (TextView)findViewById(R.id.tvOwner);
		tvOwner.setText(getString(R.string.photo_owner_caption) + " " + photo.getOwner());
	
		showPhoto(photo);
		
	}
	
	
	protected void showPhoto(Photo photo){
		
		final ImageView ivPhoto = (ImageView)findViewById(R.id.ivPhotoArea);
		
		loadingDialog = new ProgressDialog(DetailsActivity.this);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setMessage(getString(R.string.photo_loading_caption));
		loadingDialog.show();
		
		
		Picasso.with(this).load(photo.getUrl()).into(ivPhoto, new Callback() {
			
			@Override
			public void onSuccess() {
				loadingDialog.dismiss();
			}
			
			@Override
			public void onError() {
				Picasso.with(DetailsActivity.this).load(R.drawable.photo_not_found).into(ivPhoto);
				
			}
		});
	}
}
