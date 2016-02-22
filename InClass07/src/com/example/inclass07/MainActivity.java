/*
 * Assignment InClass07.
 * MainActivity.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	public static final String NEWS_CATEGORY_KEY = "NEWS_CAT_KEY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String[] newsCategories = new String[] { "Top Stories", "World", "UK",
				"Business", "Politics", "Health", "Education & Family",
				"Science & Environment", "Technology", "Entrainment & Arts", "My History" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				newsCategories);
		ListView lvCategories = (ListView) findViewById(R.id.lvCategories);
		lvCategories.setAdapter(adapter);

		lvCategories
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						String news_uri = "";
						
						switch (position) {
						case 0:
							news_uri = getString(R.string.news_cat_top_stories_uri);
							break;
						case 1:
							news_uri = getString(R.string.news_cat_world_uri);
							break;
						case 2:
							news_uri = getString(R.string.news_cat_uk_uri);
							break;
						case 3:
							news_uri = getString(R.string.news_cat_business_uri);
							break;
						case 4:
							news_uri = getString(R.string.news_cat_politics_uri);
							break;
						case 5:
							news_uri = getString(R.string.news_cat_health_uri);
							break;
						case 6:
							news_uri = getString(R.string.news_cat_education_uri);
							break;
						case 7:
							news_uri = getString(R.string.news_cat_science_uri);
							break;
						case 8:
							news_uri = getString(R.string.news_cat_technology_uri);
							break;
						case 9:
							news_uri = getString(R.string.news_cat_entertainment_uri);
							break;
						case 10:
							news_uri = null;
							break;
						}
						
						Intent intent = new Intent(MainActivity.this, NewsActivity.class);
						intent.putExtra(NEWS_CATEGORY_KEY, news_uri);
						startActivity(intent);
					}

				});
	}
}
