/*
 * Assignment InClass05.
 * PhotoSearchResult.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */
package com.example.inclass05;

public class PhotoSearchResult {
	String id, owner, secret, server, farm, title, url;
	boolean isPublic, isFriend, isFamily;
	int height, width;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getFarm() {
		return farm;
	}
	public void setFarm(String farm) {
		this.farm = farm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public boolean isFriend() {
		return isFriend;
	}
	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	public boolean isFamily() {
		return isFamily;
	}
	public void setFamily(boolean isFamily) {
		this.isFamily = isFamily;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "PhotoSearchResult [id=" + id + ", owner=" + owner + ", secret="
				+ secret + ", server=" + server + ", farm=" + farm + ", title="
				+ title + ", url=" + url + ", isPublic=" + isPublic
				+ ", isFriend=" + isFriend + ", isFamily=" + isFamily
				+ ", height=" + height + ", width=" + width + "]";
	}
	
	
}
