package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.model.Entity.SmallSizeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class SmallSize implements Parcelable{
    @SerializedName("h")
    private long height;
    @SerializedName("w")
    private long width;
    @SerializedName("resize")
    private String resize;

    public SmallSizeEntity toEntity(){
        SmallSizeEntity smallSizeEntity = new SmallSizeEntity();
        smallSizeEntity.setHeight(this.height);
        smallSizeEntity.setWidth(this.width);
        smallSizeEntity.setResize(this.resize);

        return smallSizeEntity;
    }

    public SmallSize(SmallSizeEntity entity){
        this.height=entity.getHeight();
        this.width=entity.getWidth();
        this.resize=entity.getResize();
    }

    public long getHeight() {
        return height;
    }

    public long getWidth() {
        return width;
    }

    public String getResize() {
        return resize;
    }

    protected SmallSize(Parcel in) {
        height = in.readLong();
        width = in.readLong();
        resize = in.readString();
    }

    public static final Creator<SmallSize> CREATOR = new Creator<SmallSize>() {
        @Override
        public SmallSize createFromParcel(Parcel in) {
            return new SmallSize(in);
        }

        @Override
        public SmallSize[] newArray(int size) {
            return new SmallSize[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(height);
        dest.writeLong(width);
        dest.writeString(resize);
    }
}
