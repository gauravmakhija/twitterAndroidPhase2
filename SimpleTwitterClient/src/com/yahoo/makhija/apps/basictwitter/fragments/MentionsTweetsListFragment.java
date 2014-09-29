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

public class MentionsTweetsListFragment extends TweetsListFragment {
	
	private long max_id = 999999999999999999L;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		populateTimeline(true);
		return v;
	}
	
	@Override
	public void populateTimeline(final boolean clearResults){
		getClient().getMentionsTimeline(new JsonHttpResponseHandler(){
			
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
				Log.d("BBBBBBBBBB: "+max_id);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug",e.toString());
				Log.d("debug",s.toString());
			}
			
		},max_id);
	}
	
}
