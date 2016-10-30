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

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.data.ParseResponse;
import com.codepath.apps.restclienttemplate.databinding.FragmentComposeTweetBinding;
import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetFragment extends DialogFragment {
    private FragmentComposeTweetBinding mFragmentComposeTweetBinding;

//    public void setFragmentComposeTweetBinding(FragmentComposeTweetBinding mFragmentComposeTweetBinding) {
//        this.mFragmentComposeTweetBinding = mFragmentComposeTweetBinding;
//    }

    private OnFragmentInteractionListener mListener;

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public ComposeTweetFragment() {
        // Required empty public constructor
    }

    public static ComposeTweetFragment newInstance() {
        ComposeTweetFragment fragment = new ComposeTweetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mTweet = getArguments().getParcelable(Constants.TWEET_ARGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentComposeTweetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_compose_tweet, container, false);
        mFragmentComposeTweetBinding.setUserModel(RestApplication.user);
        mFragmentComposeTweetBinding.setWrapper(this);
        onTextChange();
        mFragmentComposeTweetBinding.executePendingBindings();
        View view = mFragmentComposeTweetBinding.getRoot();
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTweetSuccess(Tweet tweet);
    }

    public void onTextChange() {

        mFragmentComposeTweetBinding.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numberCharacter = mFragmentComposeTweetBinding.etText.getText().length();
                int number = 140 - numberCharacter;
                if (number < 0) {
                    mFragmentComposeTweetBinding.tvNumberOfCharacter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                } else {
                    mFragmentComposeTweetBinding.tvNumberOfCharacter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                }
                mFragmentComposeTweetBinding.tvNumberOfCharacter.setText(String.valueOf(140 - numberCharacter));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupView() {
        getDialog().setTitle("Compose");
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
        window.setLayout((int) (size.x * 0.99), (int) (size.y * 0.95));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    public void onClickCloseButton() {
        closeDialog();
    }

    public void onClickTweetButton() {
        String body = mFragmentComposeTweetBinding.etText.getText().toString();
        RestApplication.getRestClient().postTweet(body, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (Constants.STATUS_CODE_OK == statusCode) {
                    Log.d("OnTweet", response.toString());
                    Tweet tweet = ParseResponse.getTweetFromResp(response);
                    mListener.onTweetSuccess(tweet);

                    closeDialog();
                }
            }
        });

    }

    private void closeDialog() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}
