package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class Media implements Parcelable{
    @SerializedName("type")
    private String type;

    @SerializedName("sizes")
    private Size size;
    @SerializedName("url")
    private String url;
    @SerializedName("media_url")
    private String mediaUrl;
    @SerializedName("display_url")
    private String displayUrl;
    @SerializedName("id")
    private long id;
    @SerializedName("expanded_url")
    private String expandedUrl;

    protected Media(Parcel in) {
        type = in.readString();
        size = in.readParcelable(Size.class.getClassLoader());
        url = in.readString();
        mediaUrl = in.readString();
        displayUrl = in.readString();
        id = in.readLong();
        expandedUrl = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public long getId() {
        return id;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeParcelable(size, flags);
        dest.writeString(url);
        dest.writeString(mediaUrl);
        dest.writeString(displayUrl);
        dest.writeLong(id);
        dest.writeString(expandedUrl);
    }
}
