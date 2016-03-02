/*
 * Assignment InClass07.
 * NewsDetailsActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends Activity {

	ProgressDialog loadingDialog = null;
	NewsStory newsStory = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		
		newsStory = (NewsStory)getIntent().getExtras().get(NewsActivity.STORY_KEY);
		
		setActivtyView();
	}
	
	protected void setActivtyView(){
		((TextView)findViewById(R.id.tvStoryTitle)).setText(newsStory.getTitle());
		SimpleDateFormat dateFormater = newsStory.getTargetDateFormater();
		
		((TextView)findViewById(R.id.tvPubDate)).setText(dateFormater.format(newsStory.getPubDate()));
		
		((TextView)findViewById(R.id.tvDescriptionText)).setText(newsStory.getDescription());
		((TextView)findViewById(R.id.tvViews)).setText(newsStory.getViewCount() + " " + getString(R.string.details_views_caption));
		
		loadingDialog = new ProgressDialog(NewsDetailsActivity.this);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setMessage(getString(R.string.image_loading_caption));
		loadingDialog.show();
		
		ImageView iv = (ImageView)findViewById(R.id.ivLinkImage);
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(NewsDetailsActivity.this, NewsWebViewActivity.class);
				intent.putExtra(NewsActivity.STORY_KEY, newsStory);
				startActivity(intent);
				
			}
		});
		
		Picasso.with(NewsDetailsActivity.this).load(newsStory.getUrl()).into(iv, new Callback() {
			
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
	
	
}
