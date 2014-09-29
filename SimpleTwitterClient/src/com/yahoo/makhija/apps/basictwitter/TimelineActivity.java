package com.yahoo.makhija.apps.basictwitter;

import java.io.Serializable;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.makhija.apps.basictwitter.fragments.MentionsTweetsListFragment;
import com.yahoo.makhija.apps.basictwitter.fragments.TimelineTweetsListFragment;
import com.yahoo.makhija.apps.basictwitter.listener.FragmentTabListener;
import com.yahoo.makhija.apps.basictwitter.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	public static final String TWEET = "tweet";
	public static final int COMPOSE_TWEET = 1;
	
	private Tweet tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		this.tweet = new Tweet();
		setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab timelineTab = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.home)
				.setTag("TimelineTweetsListFragment")
				.setTabListener(
					new FragmentTabListener<TimelineTweetsListFragment>(R.id.flContainer, this, "fragment_timeline",
							TimelineTweetsListFragment.class));

			actionBar.addTab(timelineTab);
			actionBar.selectTab(timelineTab);

			Tab mentionsTab = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.at_the_rate)
				.setTag("MentionsTweetsListFragment")
				.setTabListener(
				    new FragmentTabListener<MentionsTweetsListFragment>(R.id.flContainer, this, "fragment_mentions",
				    		MentionsTweetsListFragment.class));

			actionBar.addTab(mentionsTab);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }
	
	public void onComposeTweet(MenuItem mi){
    	Intent i = new Intent(this,ComposeTweetActivity.class);
    	i.putExtra(TWEET, tweet);
       	startActivityForResult(i, COMPOSE_TWEET);
    }
	
	public void onShowProfile(MenuItem mi){
		Intent i = new Intent(this,ProfileActivity.class);
		startActivity(i);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(requestCode == TimelineActivity.COMPOSE_TWEET){
			if(resultCode==TimelineActivity.RESULT_OK){
				Serializable serializableExtra = data.getSerializableExtra(TimelineActivity.TWEET);

				if(serializableExtra!=null && serializableExtra.toString()!=null && !serializableExtra.toString().isEmpty()){
					Tweet tweet = (Tweet)serializableExtra;
					//tweet.setUser(user);
					TimelineTweetsListFragment timelineTweetsListFragment = (TimelineTweetsListFragment) 
							getSupportFragmentManager().findFragmentById(R.id.flContainer);
					timelineTweetsListFragment.addTweet(tweet);
				}
			}
		}
	}
	
}
