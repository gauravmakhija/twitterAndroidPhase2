package com.yahoo.makhija.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yahoo.makhija.apps.basictwitter.EndlessScrollListener;
import com.yahoo.makhija.apps.basictwitter.R;
import com.yahoo.makhija.apps.basictwitter.TwitterApplication;
import com.yahoo.makhija.apps.basictwitter.TwitterClient;
import com.yahoo.makhija.apps.basictwitter.models.Tweet;
import com.yahoo.makhija.apps.basictwitter.models.TweetArrayAdapter;

public abstract class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter adapterTweets;
	private ListView lvTweets;
	private TwitterClient client;	
	
	public TwitterClient getClient(){
		return client;
	}
	public ListView getLvTweets() {
		return lvTweets;
	}
	protected TweetArrayAdapter getAdapter(){
		return adapterTweets;
	}
	protected void clearTweets(){
		tweets.clear();
	}
	protected ArrayList<Tweet> getTweets(){
		return tweets;
	}
	
	public abstract void populateTimeline(boolean clearResults);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		client = TwitterApplication.getRestClient();
		tweets = new ArrayList<Tweet>();
		adapterTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		this.lvTweets = (ListView)v.findViewById(R.id.lvTweets);
		getLvTweets().setAdapter(adapterTweets);
		setupOnScrollListener();
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}
		
	private void setupOnScrollListener() {
		getLvTweets().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	populateTimeline(false);
            }
		});
	}
	
	protected void addAll(ArrayList<Tweet> tweets){
		this.tweets.addAll(tweets);
		adapterTweets.addAll(tweets);
	}
	
	protected void add(int position, Tweet tweet){
		this.tweets.add(position, tweet);
		this.adapterTweets.notifyDataSetChanged();
	}

}
