package com.codepath.apps.restclienttemplate.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.RestClient;
import com.codepath.apps.restclienttemplate.adapter.TimelineAdapter;
import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.common.SearchRequest;
import com.codepath.apps.restclienttemplate.controller.fragment.ComposeTweetFragment;
import com.codepath.apps.restclienttemplate.data.ParseResponse;
import com.codepath.apps.restclienttemplate.data.TweetDataSource;
import com.codepath.apps.restclienttemplate.databinding.ActivityTimelineBinding;
import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends OAuthLoginActionBarActivity<RestClient> {
    private SearchRequest mSearchRequest;
    private TimelineAdapter mTimelineAdapter;
    private ActivityTimelineBinding binding;
    private LinearLayoutManager mLinearLayoutManager;
    private Gson mGson;
    private TweetDataSource mTweetDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        initCommon();
        initAdapter();

//

        configurationRefreshPage();
        if(null == savedInstanceState){
            getTweet();
        }else{
//            handleRotateOrientation(savedInstanceState);
            handleComplete();
        }
    }

    private void initCommon() {
        mSearchRequest = new SearchRequest();
        mGson = new Gson();
        binding.setWrapper(this);
        mTweetDataSource = new TweetDataSource();
        if(RestApplication.MODE == Constants.MODE_INTERNET){
            mTweetDataSource.clearAll();
        }

    }

    private interface HandleListener{
        void onResultResponse(List<Tweet> tweets);
    }

    private void handleComplete() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.pbLoadMore.setVisibility(View.GONE);
    }

    private void configurationRefreshPage() {
        binding.spRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweet();
                binding.spRefresh.setRefreshing(false);
            }
        });
    }

    private void initAdapter() {
        mTimelineAdapter = new TimelineAdapter(new TimelineAdapter.TimelineAdapterListener() {
            @Override
            public void onLoadMore() {
                getMoreTweet();
            }

            @Override
            public void onClickViewDetail(Tweet tweet, int pos) {
                Intent intent = new Intent(TimelineActivity.this, TweetDetailActivity.class);
                intent.putExtra(Constants.TWEET_ARGS_DETAIL, tweet);
                intent.putExtra(Constants.TWEET_ARGS_POS, pos);
                startActivityForResult(intent, Constants.DETAIL_REQUEST_CODE);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        binding.rvTweet.setAdapter(mTimelineAdapter);
        binding.rvTweet.setLayoutManager(mLinearLayoutManager);
    }

    private void getTweet(){
        mSearchRequest.resetPage();
        binding.pbLoading.setVisibility(View.VISIBLE);
        if(RestApplication.MODE == Constants.MODE_INTERNET){
            fetchTweet(mSearchRequest.getPage(),new HandleListener() {
                @Override
                public void onResultResponse(List<Tweet> tweets) {
                    Log.d("InternetCheck","mode 1 get");
                    mTimelineAdapter.setTweets(tweets);
                    mTweetDataSource.store(tweets);
                    binding.rvTweet.scrollToPosition(0);
                }
            });
        }else{
            Log.d("InternetCheck","mode 2 get");
            List<Tweet> tweets = mTweetDataSource.getAll();
            mTimelineAdapter.setTweets(tweets);
            handleComplete();
        }

    }

    private void getMoreTweet(){
        mSearchRequest.nextPage();
        binding.pbLoading.setVisibility(View.VISIBLE);
        if(RestApplication.MODE == Constants.MODE_INTERNET){
            fetchTweet(mSearchRequest.getPage(), new HandleListener() {
                @Override
                public void onResultResponse(List<Tweet> tweets) {
                    Log.d("InternetCheck","mode 1 more");
                    mTimelineAdapter.addTweets(tweets);
                    mTweetDataSource.store(tweets);
                }
            });
        }else{
            Log.d("InternetCheck","mode 2 more");
            Toast.makeText(this,"Internet have been not connected", Toast.LENGTH_LONG).show();
        }

    }

    private void fetchTweet(int page, HandleListener listener){
        RestApplication.getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(null != response){
                    Log.d("TimeLine", response.toString());
                    List<Tweet> tweets = ParseResponse.getTweetFromResp(response);

                    if(tweets.size() > 0 ){
                        listener.onResultResponse(tweets);
                        handleComplete();
                    }
                }


            }
        });
    }

    public void onClickFabButton(){
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment fragment = ComposeTweetFragment.newInstance();
        fragment.setListener(tweet -> {
            mTimelineAdapter.addPostTweet(tweet);
            binding.rvTweet.scrollToPosition(0);
        });
        fragment.show(fm, "Compose");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == Constants.DETAIL_REQUEST_CODE){
            Tweet tweet = data.getParcelableExtra(Constants.TWEET_ARGS_DETAIL);
            int pos = data.getIntExtra(Constants.TWEET_ARGS_POS, 0);

            mTimelineAdapter.updateFavoriteTweet(tweet, pos);
        }
    }

    @Override
    public void onLoginSuccess() {
        // Todo
    }

    @Override
    public void onLoginFailure(Exception e) {
        // Todo
    }
}
