package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.common.Constants;
import com.codepath.apps.restclienttemplate.utils.DateTimeUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/25/2016.
 */

public class Tweet implements Parcelable{

    @SerializedName("id") private long id;
    @SerializedName("text") private String text;
    @SerializedName("user") private User user;
    @SerializedName("created_at") private String createDate;
    @SerializedName("retweet_count") private long retweetCount;
    @SerializedName("retweeted") private boolean reweeted;
    @SerializedName("favorite_count") private long favoriteCount;
    @SerializedName("favorited") private boolean favorited;
    @SerializedName("source") private String source;
    @SerializedName("url") private String url;
    @SerializedName("entities") private Entity entity;

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setReweeted(boolean reweeted) {
        this.reweeted = reweeted;
    }

    protected Tweet(Parcel in) {
        id = in.readLong();
        text = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        createDate = in.readString();
        retweetCount = in.readLong();
        reweeted = in.readByte() != 0;
        favoriteCount = in.readLong();
        favorited = in.readByte() != 0;
        source = in.readString();
        url = in.readString();
        entity = in.readParcelable(Entity.class.getClassLoader());
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public boolean isReweeted() {
        return reweeted;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public Entity getEntity() {
        return entity;
    }



    public String getCreateDate() {
        return DateTimeUtil.getRelativeTimeAgo(createDate);
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }



    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(text);
        dest.writeParcelable(user, flags);
        dest.writeString(createDate);
        dest.writeLong(retweetCount);
        dest.writeByte((byte) (reweeted ? 1 : 0));
        dest.writeLong(favoriteCount);
        dest.writeByte((byte) (favorited ? 1 : 0));
        dest.writeString(source);
        dest.writeString(url);
        dest.writeParcelable(entity, flags);
    }

    public int type (){
        List<Media> medias =  this.entity.getMedia();
        if(medias != null && medias.size() > 0){
            String type = medias.get(0).getType();

            if(type.equals(Constants.TYPE_PHOTO_STRING)){
                return Constants.TYPE_PHOTO;
            }else if(type.equals(Constants.TYPE_VIDEO_STRING)){
                return Constants.TYPE_VIDEO;
            }
        }
        return Constants.TYPE_NULL;
    }
}
