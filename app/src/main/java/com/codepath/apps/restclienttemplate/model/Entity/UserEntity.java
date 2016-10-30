package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class UserEntity extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private String profileImageUrl;
    private String createDate;
    private long favouriteCount;
    private long listed;
    private long followerCount;
    private long friendCount;
    private String screenName;



    public UserEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(long favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public long getListed() {
        return listed;
    }

    public void setListed(long listed) {
        this.listed = listed;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
