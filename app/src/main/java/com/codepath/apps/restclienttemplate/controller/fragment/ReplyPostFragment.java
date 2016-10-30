package com.codepath.apps.restclienttemplate.controller.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.data.ParseResponse;
import com.codepath.apps.restclienttemplate.databinding.FragmentReplyPostBinding;
import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ReplyPostFragment extends DialogFragment {
    private FragmentReplyPostBinding mFragmentReplyPostBinding;
    private Tweet mTweet;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener{
        void onReplyTweet(Tweet tweet);
    }

    public ReplyPostFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReplyPostFragment newInstance(Tweet tweet) {
        ReplyPostFragment fragment = new ReplyPostFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.TWEET_ARGS_REPLY, tweet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTweet = getArguments().getParcelable(Constants.TWEET_ARGS_REPLY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentReplyPostBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_reply_post, container, false);
        mFragmentReplyPostBinding.setUserModel(RestApplication.user);
        mFragmentReplyPostBinding.setWrapper(this);
        mFragmentReplyPostBinding.setToUserModel(mTweet.getUser());
        onTextChange();
        mFragmentReplyPostBinding.executePendingBindings();
        return mFragmentReplyPostBinding.getRoot();
    }
    public void onTextChange(){

        mFragmentReplyPostBinding.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numberCharacter = mFragmentReplyPostBinding.etText.getText().length();
                int number = 140-numberCharacter;
                if(number < 0 ){
                    mFragmentReplyPostBinding.tvNumberOfCharacter.setTextColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                }else{
                    mFragmentReplyPostBinding.tvNumberOfCharacter.setTextColor(ContextCompat.getColor(getContext(),R.color.colorBlack));
                }
                mFragmentReplyPostBinding.tvNumberOfCharacter.setText(String.valueOf(140-numberCharacter));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Reply");
        mFragmentReplyPostBinding.etText.setSelection(mFragmentReplyPostBinding.etText.getText().length());
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.99), (int)(size.y * 0.95));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    public void onClickCloseButton(){
        closeDialog();
    }

    public void onClickTweetButton(){
        String body = mFragmentReplyPostBinding.etText.getText().toString();

        RestApplication.getRestClient().postTweet(body, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(Constants.STATUS_CODE_OK == statusCode){
                    Log.d("OnTweet", response.toString());
                    Tweet tweet = ParseResponse.getTweetFromResp(response);
                    mListener.onReplyTweet(tweet);
                    Toast.makeText(mFragmentReplyPostBinding.getRoot().getContext(),
                            "Reply has been sent.", Toast.LENGTH_SHORT).show();
                    closeDialog();
                }
            }
        });

    }

    private void closeDialog(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

}
