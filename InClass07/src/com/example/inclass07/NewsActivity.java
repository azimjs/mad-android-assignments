/*
 * Assignment InClass07.
 * NewsActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NewsActivity extends Activity {

	private static DataManager dm;
	public static final String STORY_KEY = "STORY_KEY";
	ArrayList<NewsStory> searchResults = null;
	NewsStoryAdapter newsAdapter = null;
	ProgressDialog loadingDialog = null;
	String storyUri = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		dm = new DataManager(this);
		
		storyUri = getIntent().getExtras().getString(MainActivity.NEWS_CATEGORY_KEY);
		Log.d("InClass07", "OnCreate storyUri set to " + storyUri);
		loadAllNewsStories();
	}
	
	protected void loadAllNewsStories(){
		loadingDialog = new ProgressDialog(NewsActivity.this);
		loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingDialog.setCancelable(false);
		loadingDialog.setMessage(getString(R.string.results_loading_caption));
		loadingDialog.show();
		if(null == storyUri){
			Log.d("InClass07", "loadAllNewsStories storyUri set to " + storyUri);
			searchResults = new ArrayList<NewsStory>( dm.getAllNews());
			Collections.sort(this.searchResults, new Comparator<NewsStory>(){

				@Override
				public int compare(NewsStory lhs, NewsStory rhs) {
					if(lhs.getViewCount()==(rhs.getViewCount())){
						return 0;
					}
					return Integer.valueOf(rhs.getViewCount()).compareTo(Integer.valueOf(lhs.getViewCount()));
				}
			});
			onFetchComplete(searchResults);
		}else{
			Log.d("InClass07", "loadAllNewsStories:reload storyUri set to " + storyUri);
			new FetchNewsStoriesTask(storyUri).execute();
		}
	}
	
	
	protected void updateSortViewBy(){
		Log.d("InClass07", "Sorting by views");
		Collections.sort(this.searchResults, new Comparator<NewsStory>(){

			@Override
			public int compare(NewsStory lhs, NewsStory rhs) {
				if(lhs.getViewCount()==(rhs.getViewCount())){
					return 0;
				}
				return Integer.valueOf(rhs.getViewCount()).compareTo(Integer.valueOf(lhs.getViewCount()));
			}
		});
		newsAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_sort_title_a2z) {
			
			Collections.sort(this.searchResults, new Comparator<NewsStory>(){

				@Override
				public int compare(NewsStory lhs, NewsStory rhs) {
					if(lhs.getTitle().equalsIgnoreCase(rhs.getTitle())){
						return 0;
					}
					
					return lhs.getTitle().compareTo(rhs.getTitle());
				}
				
			});
		
			newsAdapter.notifyDataSetChanged();
			return true;
		} else if (id == R.id.action_sort_title_z2a) {
			
			Collections.sort(this.searchResults, new Comparator<NewsStory>(){

				@Override
				public int compare(NewsStory lhs, NewsStory rhs) {
					if(lhs.getTitle().equalsIgnoreCase(rhs.getTitle())){
						return 0;
					}
					
					return rhs.getTitle().compareTo(lhs.getTitle());
				}
				
			});

			newsAdapter.notifyDataSetChanged();
			return true;
		} else if (id == R.id.action_sort_pubdate_ascending) {
			
			Collections.sort(this.searchResults, new Comparator<NewsStory>(){

				@Override
				public int compare(NewsStory lhs, NewsStory rhs) {
					if(lhs.getPubDate().equals(rhs.getPubDate())){
						return 0;
					}
					
					return lhs.getPubDate().compareTo(rhs.getPubDate());
				}
				
			});
			newsAdapter.notifyDataSetChanged();
			return true;
		} else if (id == R.id.action_sort_pubdate_descending) {
			
			Collections.sort(this.searchResults, new Comparator<NewsStory>(){

				@Override
				public int compare(NewsStory lhs, NewsStory rhs) {
					if(lhs.getPubDate().equals(rhs.getPubDate())){
						return 0;
					}
					
					return rhs.getPubDate().compareTo(lhs.getPubDate());
				}
				
			});
			
			newsAdapter.notifyDataSetChanged();
			return true;
		} else if (id == R.id.action_show_new_stories){
			filterViewedStories();
			newsAdapter.notifyDataSetChanged();
			return true;
		} else if (id == R.id.action_clear_my_history){
			dm.trucate();
			
			//newsAdapter.notifyDataSetChanged();
		
			loadAllNewsStories();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	protected NewsStory updateViewHistory(NewsStory story){
		NewsStory newsView = dm.getNewsStory(story.getLink());
		if(newsView == null){
			newsView = story;
			newsView.setViewCount(1);
			dm.saveNewNews(newsView);
		}else{
			int viewCount = newsView.getViewCount();
			newsView.setViewCount(++viewCount);
			Log.d("inClass07", "Updated view count to " + viewCount);
			dm.updateNews(newsView);
		}
		return newsView;
		
	}
	
	protected void filterViewedStories(){
		List<NewsStory> history = dm.getAllNews();
		
		for(NewsStory view : history){
			for(int i = 0; i < searchResults.size(); i++){
				if(view.getLink().equalsIgnoreCase(searchResults.get(i).getLink())){
					searchResults.remove(i);
					break;
				}
			}
		}
	}
	
	protected void onFetchComplete(ArrayList<NewsStory> stories){
		this.searchResults = stories;
		
		loadingDialog.dismiss();
		ListView lvStories = (ListView)findViewById(R.id.listView1);
		
		
		newsAdapter = new NewsStoryAdapter(this, R.layout.news_list_view, this.searchResults);
		
		lvStories.setAdapter(newsAdapter);
		
		lvStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewsStory story = searchResults.get(position);
				story = updateViewHistory(story);
				searchResults.get(position).setViewCount(story.getViewCount());
				
				Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
				intent.putExtra(STORY_KEY, story);
				startActivity(intent);
				if(null == storyUri || "".equals(storyUri)){
					updateSortViewBy();
					newsAdapter.notifyDataSetChanged();
				}
			}
		});
		
	}
	

	private class FetchNewsStoriesTask extends
			AsyncTask<Void, Void, ArrayList<NewsStory>> {

		String newsUri = "";

		public FetchNewsStoriesTask(String newsUri) {
			this.newsUri = newsUri;
		}

		@Override
		protected ArrayList<NewsStory> doInBackground(Void... params) {
			ArrayList<NewsStory> stories = null;
			try {
				URL url = new URL(newsUri);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();

				NewsStorySAXParser parser = new NewsStorySAXParser();
				stories = parser.parseStories(con.getInputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return stories;
		}

		@Override
		protected void onPostExecute(ArrayList<NewsStory> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				onFetchComplete(result);
			}
		}
		
		

	}
}
