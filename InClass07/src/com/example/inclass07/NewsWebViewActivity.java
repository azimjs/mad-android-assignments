/*
 * Assignment InClass07.
 * NewsWebViewActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebViewActivity extends Activity {
	NewsStory newsStory = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_web_view);
		
		newsStory = (NewsStory)getIntent().getExtras().get(NewsActivity.STORY_KEY);
		
		WebView webView = (WebView)findViewById(R.id.webView1);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(newsStory.getLink());
		
	}
}
