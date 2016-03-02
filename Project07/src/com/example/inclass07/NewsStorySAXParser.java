/*
 * Assignment InClass07.
 * NewsStorySAXParser.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass07;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.util.Xml;

public class NewsStorySAXParser extends DefaultHandler {
	ArrayList<NewsStory> stories = null;
	NewsStory story = null;
	int largestImgWidth = 0;
	String storyUrl = "";
	StringBuilder xmlInnerText = null;

	public ArrayList<NewsStory> parseStories(InputStream in)
			throws IOException, SAXException {

		Xml.parse(in, Xml.Encoding.UTF_8, this);
		return stories;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		stories = new ArrayList<NewsStory>();
		xmlInnerText = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("item")) {
			story = new NewsStory();
			Log.d("Exam01", "Entering item element");
		}else if(localName.equals("title")) {
			xmlInnerText.setLength(0);
		}else if(localName.equals("thumbnail")){
			int width = Integer.parseInt(attributes.getValue("width"));
			
			storyUrl = attributes.getValue("url");
			
			if(width > this.largestImgWidth){
				largestImgWidth = width;
				story.setUrl(storyUrl);
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);

		if (localName.equals("item")) {
			stories.add(story);
			story = null;
			largestImgWidth = 0;
		}
		if (story != null) {
			if (localName.equals("title")) {
				story.setTitle(xmlInnerText.toString().trim());
			}else if(localName.equals("description")) {
				story.setDescription(xmlInnerText.toString().trim());
			}else if(localName.equals("link")) {
				story.setLink(xmlInnerText.toString().trim());
			}else if(localName.equals("pubDate")) {
				SimpleDateFormat dtSourceFormat = story.getSourceDateFormater();
				SimpleDateFormat dtTargetFormat = story.getTargetDateFormater();
				
				try {
					story.setPubDate(dtSourceFormat.parse(xmlInnerText.toString().trim()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.d("Exam01", "Parsed date : " + dtTargetFormat.format(story.getPubDate()));
			
			}
			xmlInnerText.setLength(0);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		xmlInnerText.append(ch, start, length);
	}

}
