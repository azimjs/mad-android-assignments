/*
 * Assignment InClass05.
 * FlickrPullParser.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass05;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class FlickrPullParser implements IFlickrSearchResultParser  {

	@Override
	public ArrayList<PhotoSearchResult> parseSearchResults(InputStream in) throws IOException, XmlPullParserException{
		
		ArrayList<PhotoSearchResult> results = new ArrayList<PhotoSearchResult>();
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		
		parser.setInput(in, "UTF-8");
		
		PhotoSearchResult result = null;
		
		
		int event = parser.getEventType();
		
		while(event != XmlPullParser.END_DOCUMENT){
			switch(event){
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("photo")){
						result = new PhotoSearchResult();
						result.setId(parser.getAttributeValue(null, "id"));
						result.setOwner(parser.getAttributeValue(null,"owner"));
						result.setSecret(parser.getAttributeValue(null,"secret"));
						result.setServer(parser.getAttributeValue(null,"server"));
						result.setFarm(parser.getAttributeValue(null,"farm"));
						result.setTitle(parser.getAttributeValue(null,"title"));
						result.setUrl(parser.getAttributeValue(null,"url_m"));
						result.setHeight(Integer.parseInt(parser.getAttributeValue(null,"height_m")));
						result.setWidth(Integer.parseInt(parser.getAttributeValue(null,"width_m")));
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("photo")){
						results.add(result);
						result = null;
					}
					break;
			}
			event = parser.next();
		}
		return results;
	}

}
