package com.codepath.apps.restclienttemplate.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.RestClient;
import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.controller.fragment.ReplyPostFragment;
import com.codepath.apps.restclienttemplate.data.ParseResponse;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailBinding;
import com.codepath.apps.restclienttemplate.model.model.Media;
import com.codepath.apps.restclienttemplate.model.model.SmallSize;
import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.codepath.apps.restclienttemplate.utils.ImageUtil;
import com.codepath.apps.restclienttemplate.utils.NumberUtil;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TweetDetailActivity extends OAuthLoginActionBarActivity<RestClient> {

    private ActivityTweetDetailBinding mActivityTweetDetailBinding;
    private Tweet mTweet;
    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTweetDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_tweet_detail);
        getArgument();
        bindingUI();


    }

    private void getArgument() {

        mTweet = getIntent().getParcelableExtra(Constants.TWEET_ARGS_DETAIL);
        mPos = getIntent().getIntExtra(Constants.TWEET_ARGS_POS, 0);
    }

    private void bindingUI() {

        mActivityTweetDetailBinding.setTweetModel(mTweet);
        mActivityTweetDetailBinding.setWrapper(this);
        configurationIvCover();
        configurationRetweetButton();
        configurationFavoriteButton();
        configurationReplyButton();
        mActivityTweetDetailBinding.executePendingBindings();
    }


    private void configurationIvCover() {
        List<Media> mediaList = mTweet.getEntity().getMedia() != null ? mTweet.getEntity().getMedia() : new ArrayList<Media>();
        if (mediaList.size() > 0) {
            SmallSize mediumSize = mediaList.get(0).getSize().getMediumSize();
            ViewGroup.LayoutParams params = mActivityTweetDetailBinding.ivMedia.getLayoutParams();
            params.height = (int) ImageUtil.convertDpToPixel(mediumSize.getHeight(), this);
            mActivityTweetDetailBinding.ivMedia.setLayoutParams(params);
        }
    }

    private void configurationRetweetButton() {
        if (mTweet.isReweeted()) {
            mActivityTweetDetailBinding.ivRetweet.setImageResource(R.drawable.ic_retweet);
            mActivityTweetDetailBinding.ivRetweet.setTag(R.id.retweet, Constants.CLICKED);
        } else {
            mActivityTweetDetailBinding.ivRetweet.setImageResource(R.drawable.ic_not_retweet);
            mActivityTweetDetailBinding.ivRetweet.setTag(R.id.retweet, Constants.NOT_CLICK);
        }
        mActivityTweetDetailBinding.tvRetweet.setText(NumberUtil.format(mTweet.getRetweetCount()));
        mActivityTweetDetailBinding.ivRetweet.setOnClickListener(v -> {
            int click = (int) mActivityTweetDetailBinding.ivRetweet
                    .getTag(R.id.retweet);
            long countRetweet = Long.valueOf(mActivityTweetDetailBinding.tvRetweet.getText().toString().trim());
            if (click == Constants.CLICKED) {
                RestApplication.getRestClient().unRetweetTweet(mTweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (200 == statusCode) {
                            Toast.makeText(v.getContext(), "Post has been unretweeted.", Toast.LENGTH_SHORT).show();

                            mTweet.setReweeted(false);
                            mTweet.setRetweetCount(countRetweet - 1);
                            mActivityTweetDetailBinding.ivRetweet.setImageResource(R.drawable.ic_not_retweet);
                            mActivityTweetDetailBinding.ivRetweet.setTag(R.id.retweet, Constants.NOT_CLICK);
                            mActivityTweetDetailBinding.tvRetweet.setText(NumberUtil.format(countRetweet - 1));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Favorite", throwable.getMessage());
                        Toast.makeText(v.getContext(), "Post has been existed.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                RestApplication.getRestClient().retweetTweet(mTweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Toast.makeText(v.getContext(), "Post has been retweeted.", Toast.LENGTH_SHORT).show();

                        mTweet.setReweeted(true);
                        mTweet.setRetweetCount(countRetweet + 1);
                        mActivityTweetDetailBinding.ivRetweet.setImageResource(R.drawable.ic_retweet);
                        mActivityTweetDetailBinding.ivRetweet.setTag(R.id.retweet, Constants.CLICKED);
                        mActivityTweetDetailBinding.tvRetweet.setText(NumberUtil.format(countRetweet + 1));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Favorite", throwable.getMessage());
                        Toast.makeText(v.getContext(), "Post has been existed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void configurationFavoriteButton() {
        if (mTweet.isFavorited()) {
            mActivityTweetDetailBinding.ivFavorite.setImageResource(R.drawable.ic_favorite);
            mActivityTweetDetailBinding.ivFavorite.setTag(R.id.favorite, Constants.CLICKED);
        } else {
            mActivityTweetDetailBinding.ivFavorite.setImageResource(R.drawable.ic_not_favorite);
            mActivityTweetDetailBinding.ivFavorite.setTag(R.id.favorite, Constants.NOT_CLICK);
        }
        mActivityTweetDetailBinding.tvFavorite.setText(NumberUtil.format(mTweet.getFavoriteCount()));
        mActivityTweetDetailBinding.ivFavorite.setOnClickListener(v -> {
            int click = (int) mActivityTweetDetailBinding.ivFavorite
                    .getTag(R.id.favorite);
            if (click == Constants.CLICKED) {
                RestApplication.getRestClient().unFavoriteTweet(mTweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (200 == statusCode) {
                            Toast.makeText(v.getContext(), "Unfavorited", Toast.LENGTH_SHORT).show();
                            mTweet = ParseResponse.getTweetFromResp(response);

                            mActivityTweetDetailBinding.ivFavorite.setImageResource(R.drawable.ic_not_favorite);
                            mActivityTweetDetailBinding.ivFavorite.setTag(R.id.favorite, Constants.NOT_CLICK);
                            mActivityTweetDetailBinding.tvFavorite.setText(NumberUtil.format(mTweet.getFavoriteCount()));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Favorite", throwable.getMessage());
                        Toast.makeText(v.getContext(), "Post has been existed.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                RestApplication.getRestClient().favoriteTweet(mTweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Toast.makeText(v.getContext(), "Favorited", Toast.LENGTH_SHORT).show();
                        mTweet = ParseResponse.getTweetFromResp(response);

                        mActivityTweetDetailBinding.ivFavorite.setImageResource(R.drawable.ic_favorite);
                        mActivityTweetDetailBinding.ivFavorite.setTag(R.id.favorite, Constants.CLICKED);
                        mActivityTweetDetailBinding.tvFavorite.setText(NumberUtil.format(mTweet.getFavoriteCount()));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Favorite", throwable.getMessage());
                        Toast.makeText(v.getContext(), "Post has been existed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void configurationReplyButton() {
        mActivityTweetDetailBinding.ivReply.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            ReplyPostFragment replyPostFragment = ReplyPostFragment.newInstance(mTweet);
            replyPostFragment.setListener(new ReplyPostFragment.OnFragmentInteractionListener() {
                @Override
                public void onReplyTweet(Tweet tweetReply) {
                    Log.d("TweetEntity", tweetReply.toString());
                    Toast.makeText(TweetDetailActivity.this, "Reply has been sent.", Toast.LENGTH_SHORT).show();
                }
            });
            replyPostFragment.show(fm, "Reply");
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Constants.TWEET_ARGS_DETAIL, mTweet);
        intent.putExtra(Constants.TWEET_ARGS_POS, mPos);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure(Exception e) {

    }
}
