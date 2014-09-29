package com.yahoo.makhija.apps.basictwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.yahoo.makhija.apps.basictwitter.models.Tweet;
import com.yahoo.makhija.apps.basictwitter.models.User;

public class ComposeTweetActivity extends Activity {
	
	private static final int COLOR_LIGHT_GRAY = -3355444;
	private TwitterClient client;
	private Tweet tweet;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		client = TwitterApplication.getRestClient();
		user = new User();
		setup();
	}
	
	private void setup() {
		client.fetchUserInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug",json.toString());
				try {
					user.setName(json.getString("name"));
					user.setScreenName(json.getString("screen_name"));
					user.setProfileImageUrl(json.getString("profile_image_url_https"));
					user.setUid(json.getLong("id_str"));
					tweet = (Tweet)getIntent().getSerializableExtra("tweet");
					tweet.setUser(user);
					SmartImageView ivProfilePic = (SmartImageView) findViewById(R.id.ivProfilePic);
					ivProfilePic.setImageUrl(user.getProfileImageUrl());
					TextView tvUserId = (TextView)findViewById(R.id.tvUserId);
					tvUserId.setText("@"+user.getScreenName());
					tvUserId.setTextColor(COLOR_LIGHT_GRAY);
					TextView tvUsersName = (TextView)findViewById(R.id.tvUsersName);
					tvUsersName.setText(user.getName());
				} catch (JSONException e) {
					Log.d("debug", "failed to set user info after receiving" + e);
				}
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", "failed to get user info");
			}
		},null);
	}
	
	public void onTweet(View v){
		TextView tvTweet = (TextView)findViewById(R.id.etTweet);
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, JSONObject jsonObj) {				
				try {
					tweet.setBody(jsonObj.get("text").toString());
					tweet.setCreatedAt(jsonObj.getString("created_at"));
					tweet.setUid(jsonObj.getLong("id_str"));
				} catch (JSONException e) {
					Log.d("debug", "tweet being sent back to timeline might be inappropriate" + e);
				}
				Intent i = new Intent();
				i.putExtra("tweet", tweet);
				setResult(RESULT_OK, i);
				finish();
			}
			
			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				Log.d("debug", "tweeting failed");
			}
			
		},tvTweet.getText().toString());
	}
	
	public void onCancel(View v){
		Intent i = new Intent();
		i.putExtra("tweet", "");
		setResult(RESULT_OK, i);
		finish();
	}

}
