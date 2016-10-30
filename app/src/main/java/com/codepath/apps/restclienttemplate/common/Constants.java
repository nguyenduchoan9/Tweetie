package com.codepath.apps.restclienttemplate.common;

/**
 * Created by Nguyen.D.Hoang on 10/27/2016.
 */

public class Constants {
    public static String TWEET_ARGS = "TWEET_ARGS";
    public static String TWEET_ARGS_REPLY = "TWEET_ARGS_REPLY";
    public static String TWEET_ARGS_DETAIL = "TWEET_ARGS_DETAIL";
    public static String TWEET_ARGS_POS = "TWEET_ARGS_POS";

    public static int DETAIL_REQUEST_CODE = 4;

    public static int STATUS_CODE_OK=200;


    public static String TYPE_PHOTO_STRING = "photo";
    public static String TYPE_VIDEO_STRING = "media";

    public static int TYPE_NULL = 0;
    public static int TYPE_PHOTO = 1;
    public static int TYPE_VIDEO = 2;

    public static int RETWEET_KEY=999;
    public static final int NOT_CLICK=0;
    public static final int CLICKED=1;

    public static String PING_GOOGLE = "/system/bin/ping -c 1 8.8.8.8";

    public static int MODE_INTERNET= 1;
    public static int MODE_NO_INTERNET= 2;
}
