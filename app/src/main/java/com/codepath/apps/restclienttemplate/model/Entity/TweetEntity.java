package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class TweetEntity extends RealmObject {
    @PrimaryKey
    private long id;
    private String text;
    private UserEntity user;
    private String createDate;
    private long retweetCount;
    private boolean reweeted;
    private long favoriteCount;
    private boolean favorited;
    private String source;
    private String url;
    private EntityEntity entity;

    public TweetEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {

        this.user = user;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public boolean isReweeted() {
        return reweeted;
    }

    public void setReweeted(boolean reweeted) {
        this.reweeted = reweeted;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }


}
