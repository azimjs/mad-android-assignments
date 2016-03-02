/*
 * Assignment InClass05.
 * IFlickrSearchResultParser.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass05;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

public interface IFlickrSearchResultParser {
	ArrayList<PhotoSearchResult> parseSearchResults(InputStream in) throws IOException, XmlPullParserException;
}
