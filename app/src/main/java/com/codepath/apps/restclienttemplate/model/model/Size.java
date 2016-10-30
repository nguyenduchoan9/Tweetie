package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.model.Entity.SizeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class Size implements Parcelable{
    @SerializedName("thumb")
    private SmallSize mediumSize;

    public SizeEntity toEntity(){
        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setMediumSize(this.mediumSize.toEntity());

        return sizeEntity;
    }

    public Size(SizeEntity entity){
        this.mediumSize = new SmallSize(entity.getMediumSize());
    }


    protected Size(Parcel in) {
        mediumSize = in.readParcelable(SmallSize.class.getClassLoader());
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    public SmallSize getMediumSize() {
        return mediumSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mediumSize, flags);
    }
}
