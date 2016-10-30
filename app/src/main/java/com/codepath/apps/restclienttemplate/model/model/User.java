package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/25/2016.
 */

public class User implements Parcelable{

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_image_url")
    private String profileImageUrl;
    @SerializedName("created_at")
    private String createDate;
    @SerializedName("favourites_count")
    private long favouriteCount;
    @SerializedName("listed_count")
    private long listed;
    @SerializedName("followers_count")
    private long followerCount;
    @SerializedName("friends_count")
    private long friendCount;
    @SerializedName("screen_name")
    private String screenName;

    public String getScreenName() {
        return screenName;
    }

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        profileImageUrl = in.readString();
        createDate = in.readString();
        favouriteCount = in.readLong();
        listed = in.readLong();
        followerCount = in.readLong();
        friendCount = in.readLong();
        location = in.readString();
        screenName=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getFavouriteCount() {
        return favouriteCount;
    }

    public long getListed() {
        return listed;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLocation() {
        return location;
    }

    @SerializedName("location")
    private String location;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(profileImageUrl);
        dest.writeString(createDate);
        dest.writeLong(favouriteCount);
        dest.writeLong(listed);
        dest.writeLong(followerCount);
        dest.writeLong(friendCount);
        dest.writeString(location);
        dest.writeString(screenName);
    }
}
