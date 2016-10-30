package com.codepath.apps.restclienttemplate.data;

import com.codepath.apps.restclienttemplate.model.model.Tweet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class ParseResponse {

    public static Tweet getTweetFromResp(JSONObject response){
        Gson gson = new Gson();
        Tweet tweet = gson.fromJson(response.toString(), new TypeToken<Tweet>(){}.getType());

        return tweet;
    }

    public static List<Tweet> getTweetFromResp(JSONArray response){
        Gson gson = new Gson();
        List<Tweet> tweets = gson.fromJson(response.toString(),
                new TypeToken<List<Tweet>>() {
                }.getType());

        return tweets;
    }

    
}
