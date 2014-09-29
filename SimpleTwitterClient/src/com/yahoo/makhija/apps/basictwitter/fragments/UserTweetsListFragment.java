package com.yahoo.makhija.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.makhija.apps.basictwitter.models.Tweet;

public class UserTweetsListFragment extends TweetsListFragment {

	private long max_id = 999999999999999999L;
	private long following_user_max_id = 999999999999999999L;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		String followingUserName = getActivity().getIntent().getStringExtra("followingUserName");
		if(followingUserName!=null && !followingUserName.isEmpty())
			populateTimeline(true,followingUserName);
		else
			populateTimeline(true);
		return v;
	}
	
	@Override
	public void populateTimeline(final boolean clearResults){
		getClient().getUserTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				if(clearResults)
					clearTweets();
				ArrayList<Tweet> fromJSONArray = Tweet.fromJSONArray(json);
				addAll(fromJSONArray);
				for(Tweet tweet : fromJSONArray){
					if(tweet.getUid() < max_id){
						max_id = tweet.getUid();
					}
				}
				Log.d("AAAAAAAAAAA: "+max_id);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug",e.toString());
				Log.d("debug",s.toString());
			}
			
		},max_id,null);
	}	
	
	public void populateTimeline(final boolean clearResults, final String user_name){
		getClient().getUserTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json) {
				if(clearResults)
					clearTweets();
				ArrayList<Tweet> fromJSONArray = Tweet.fromJSONArray(json);
				addAll(fromJSONArray);
				for(Tweet tweet : fromJSONArray){
					if(tweet.getUid() < following_user_max_id){
						following_user_max_id = tweet.getUid();
					}
				}
				Log.d("AAAAAAAAAAA: "+following_user_max_id);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug",e.toString());
				Log.d("debug",s.toString());
			}
			
		},following_user_max_id, user_name);
	}	

}
