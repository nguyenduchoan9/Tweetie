package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class Entity implements Parcelable{
    @SerializedName("urls")
    private List<Url> url;

    @SerializedName("media")
    private List<Media> media;

    public List<Url> getUrl() {
        return url;
    }

    public List<Media> getMedia() {
        return media;
    }

    protected Entity(Parcel in) {
        url = in.createTypedArrayList(Url.CREATOR);
        media = in.createTypedArrayList(Media.CREATOR);
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(url);
        dest.writeTypedList(media);
    }
}
