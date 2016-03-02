/*
 * Assignment InClass05.
 * FlickrSAXParser.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass05;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class FlickrSAXParser extends DefaultHandler implements
		IFlickrSearchResultParser {
	ArrayList<PhotoSearchResult> results;
	PhotoSearchResult result;

	@Override
	public ArrayList<PhotoSearchResult> parseSearchResults(InputStream in)
			throws IOException, XmlPullParserException {

		try {
			Xml.parse(in, Xml.Encoding.UTF_8, this);
		} catch (SAXException ex) {
			ex.printStackTrace();
		}
		return results;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		results = new ArrayList<PhotoSearchResult>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("photo")) {
			result = new PhotoSearchResult();

			result.setId(attributes.getValue("id"));
			result.setOwner(attributes.getValue("owner"));
			result.setSecret(attributes.getValue("secret"));
			result.setServer(attributes.getValue("server"));
			result.setFarm(attributes.getValue("farm"));
			result.setTitle(attributes.getValue("title"));
			result.setFarm(attributes.getValue("isfamily"));
			result.setUrl(attributes.getValue("url_m"));
			result.setHeight(Integer.parseInt(attributes.getValue("height_m")));
			result.setWidth(Integer.parseInt(attributes.getValue("width_m")));

			results.add(result);
		}
	}

}
