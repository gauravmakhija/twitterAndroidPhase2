package com.yahoo.makhija.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -423074706015661304L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String followersCount;
	private String followingCount;
	private String tagLine;

	//User.fromJson(...)
	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		//extract values from json to populate the member variables
		try{
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.followersCount = jsonObject.getString("followers_count");
			user.tagLine = jsonObject.getString("description");
		}catch(JSONException e){
			return null;
		}
		try{
			user.followingCount = jsonObject.getString("following_count");
		}catch(JSONException e){
			user.followingCount = "0";	
		}
		return user;
	}
	
	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getFollowersCount(){
		return followersCount;
	}
	
	public String getFollowingCount(){
		return followingCount;
	}
	
	public String getTagLine(){
		return tagLine;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}
