package com.yahoo.makhija.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.makhija.apps.basictwitter.models.User;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo(getIntent().getStringExtra("followingUserName"));
	}

	private void loadProfileInfo(String followingUserName) {
		TwitterApplication.getRestClient().fetchUserInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User u  = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		},followingUserName);
	}

	protected void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
	}
}
