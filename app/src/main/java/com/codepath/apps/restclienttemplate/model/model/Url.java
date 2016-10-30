package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.model.Entity.UrlEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class Url implements Parcelable{
    @SerializedName("expanded_url") private String expandedUrl;
    @SerializedName("url") private String url;
    @SerializedName("display_url") private String displayUrl;

    public UrlEntity toEntity(){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setDisplayUrl(this.displayUrl);
        urlEntity.setUrl(this.url);
        urlEntity.setExpandedUrl(this.expandedUrl);

        return urlEntity;
    }

    public Url(UrlEntity entity){
        this.expandedUrl=entity.getExpandedUrl();
        this.url=entity.getUrl();
        this.displayUrl=entity.getDisplayUrl();
    }

    protected Url(Parcel in) {
        expandedUrl = in.readString();
        url = in.readString();
        displayUrl = in.readString();
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expandedUrl);
        dest.writeString(url);
        dest.writeString(displayUrl);
    }
}
