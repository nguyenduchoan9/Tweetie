package com.codepath.apps.restclienttemplate.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.controller.activity.TimelineActivity;
import com.codepath.apps.restclienttemplate.controller.fragment.ReplyPostFragment;
import com.codepath.apps.restclienttemplate.data.ParseResponse;
import com.codepath.apps.restclienttemplate.databinding.TweetItemBinding;
import com.codepath.apps.restclienttemplate.model.model.Media;
import com.codepath.apps.restclienttemplate.model.model.SmallSize;
import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.codepath.apps.restclienttemplate.utils.ImageUtil;
import com.codepath.apps.restclienttemplate.utils.NumberUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nguyen.D.Hoang on 10/27/2016.
 */

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tweet> mTweets;
    private TimelineAdapterListener mTimelineAdapterListener;

    public TimelineAdapter(TimelineAdapterListener timelineAdapterListener) {
        this.mTweets = new ArrayList<>();
        this.mTimelineAdapterListener = timelineAdapterListener;
    }

    public void setTweets(List<Tweet> articles) {
        mTweets.clear();
        mTweets.addAll(articles);
        notifyDataSetChanged();
    }

    public void addTweets(List<Tweet> articles) {
        int startPos = mTweets.size();
        mTweets.addAll(articles);
        notifyItemRangeInserted(startPos, articles.size());

    }

    public void addPostTweet(Tweet tweet) {
        mTweets.add(0, tweet);
        notifyItemInserted(0);
    }
    public void removePostTweet(int pos){
        mTweets.remove(pos);
        notifyItemRemoved(pos);
    }

    public void updateFavoriteTweet(Tweet tweet, int pos) {
        mTweets.set(pos, tweet);
        notifyItemChanged(pos);
    }


    public interface TimelineAdapterListener {
        void onLoadMore();
        void onClickViewDetail(Tweet tweet, int pos);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tweet_item, parent, false);
                return new ViewHolder(itemView);
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tweet_item, parent, false);
                return new ViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        if (holder instanceof ViewHolder) {
            bindViewHolder(tweet, (ViewHolder) holder, position);
        }
        if (position == mTweets.size() - 1 && mTimelineAdapterListener != null) {
            mTimelineAdapterListener.onLoadMore();
        }
    }

    private void bindViewHolder(Tweet tweet, ViewHolder holder, int pos) {
        holder.getBidning().setTweetModel(tweet);
        List<Media> mediaList = tweet.getEntity().getMedia() != null ? tweet.getEntity().getMedia() : new ArrayList<Media>();
        if (mediaList.size() > 0) {
            SmallSize mediumSize = mediaList.get(0).getSize().getMediumSize();
            ViewGroup.LayoutParams params = holder.getBidning().ivMedia.getLayoutParams();
            params.height = (int) ImageUtil.convertDpToPixel(mediumSize.getHeight(), holder.itemView.getContext());
            holder.getBidning().ivMedia.setLayoutParams(params);
        }

        configurationRetweetButton(holder, tweet, pos);
        configurationFavoriteButton(holder, tweet, pos);
        configurationReplyButton(holder, tweet);
        configurationViewDetail(holder, tweet, pos);
        holder.getBidning().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TweetItemBinding binding;

        public TweetItemBinding getBidning() {
            return binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public void configurationRetweetButton(ViewHolder holder, Tweet tweet, int pos) {
        if (tweet.isReweeted()) {
            holder.getBidning().ivRetweet.setImageResource(R.drawable.ic_retweet);
            holder.getBidning().ivRetweet.setTag(R.id.retweet, Constants.CLICKED);
        } else {
            holder.getBidning().ivRetweet.setImageResource(R.drawable.ic_not_retweet);
            holder.getBidning().ivRetweet.setTag(R.id.retweet, Constants.NOT_CLICK);
        }
        holder.getBidning().tvRetweet.setText(NumberUtil.format(tweet.getRetweetCount()));
        holder.getBidning().ivRetweet.setOnClickListener(v -> {
            int click = (int) holder.getBidning().ivRetweet
                    .getTag(R.id.retweet);
            if (click == Constants.CLICKED) {
                RestApplication.getRestClient().unRetweetTweet(tweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if(200 == statusCode){
                            Tweet tweetRes = ParseResponse.getTweetFromResp(response);

                            tweet.setReweeted(false);
                            tweet.setRetweetCount(tweet.getRetweetCount()-1);
                            updateFavoriteTweet(tweet, pos);

//                            int posUnRetweet = getPosOfTweet(tweetRes);
//                            if(posUnRetweet != -1)removePostTweet(posUnRetweet);
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
                RestApplication.getRestClient().retweetTweet(tweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Tweet tweetRes = ParseResponse.getTweetFromResp(response);

                        tweet.setReweeted(true);
                        tweet.setRetweetCount(tweet.getRetweetCount()+ 1);
                        updateFavoriteTweet(tweet, pos);

                        addPostTweet(tweetRes);
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

    private int getPosOfTweet(Tweet tweet){
        int pos =-1;
        for (int i =0; i < mTweets.size(); i++){
            Tweet tweetFind = mTweets.get(i);
            if(tweet.getId() == tweetFind.getId()){
                pos = i;
                break;
            }
        }
        return pos;
    }

    public void configurationFavoriteButton(ViewHolder holder, Tweet tweet, int pos) {
        if (tweet.isFavorited()) {
            holder.getBidning().ivFavorite.setImageResource(R.drawable.ic_favorite);
            holder.getBidning().ivFavorite.setTag(R.id.favorite, Constants.CLICKED);
        } else {
            holder.getBidning().ivFavorite.setImageResource(R.drawable.ic_not_favorite);
            holder.getBidning().ivFavorite.setTag(R.id.favorite, Constants.NOT_CLICK);
        }
        holder.getBidning().tvFavorite.setText(NumberUtil.format(tweet.getFavoriteCount()));
        holder.getBidning().ivFavorite.setOnClickListener(v -> {
            int click = (int) holder.getBidning().ivFavorite
                    .getTag(R.id.favorite);
            int favoriteCount = Integer.valueOf(holder.getBidning().tvFavorite.getText().toString().trim());
            if (click == Constants.CLICKED) {
                RestApplication.getRestClient().unFavoriteTweet(tweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if(200 == statusCode){
                            Tweet tweetRes = ParseResponse.getTweetFromResp(response);

                            updateFavoriteTweet(tweetRes, pos);
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
                RestApplication.getRestClient().favoriteTweet(tweet.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Tweet tweetRes = ParseResponse.getTweetFromResp(response);

                        updateFavoriteTweet(tweetRes, pos);
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

    public void configurationReplyButton(ViewHolder holder, Tweet tweet){
        holder.getBidning().ivReply.setOnClickListener(v -> {
            FragmentManager fm  = ((TimelineActivity)holder.itemView.getContext()).getSupportFragmentManager();
            ReplyPostFragment replyPostFragment = ReplyPostFragment.newInstance(tweet);
            replyPostFragment.setListener(new ReplyPostFragment.OnFragmentInteractionListener() {
                @Override
                public void onReplyTweet(Tweet tweetReply) {
                    addPostTweet(tweetReply);
                }
            });
            replyPostFragment.show(fm, "Reply");
        });
    }

    private void configurationViewDetail(ViewHolder holder, Tweet tweet, int pos){
        holder.getBidning().tvText.setOnClickListener(v -> {
            mTimelineAdapterListener.onClickViewDetail(tweet,pos);
        });
    }


}
