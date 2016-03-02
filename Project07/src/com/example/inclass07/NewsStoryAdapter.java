/*
 * Assignment InClass07.
 * NewsStoryAdapter.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsStoryAdapter extends ArrayAdapter<NewsStory>{
	Context context;
	List<NewsStory> stories;
	
	public NewsStoryAdapter(Context context, int resource, List<NewsStory> objects) {
		super(context, resource, objects);
		this.context = context;
		this.stories = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.news_list_view, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView)convertView.findViewById(R.id.imageView1);
				holder.title = (TextView)convertView.findViewById(R.id.textView1);
				convertView.setTag(holder);
		}
		
		holder = (ViewHolder)convertView.getTag();
		ImageView image = holder.image;
		TextView title= holder.title;
		
		Picasso.with(context).load(stories.get(position).getUrl()).into(image);
		title.setText(stories.get(position).getTitle());
		
		return convertView;
		
	}
	
	
	static class ViewHolder{
		ImageView image;
		TextView title;
	}
}
