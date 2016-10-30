package com.codepath.apps.restclienttemplate.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.model.Entity.EntityEntity;
import com.codepath.apps.restclienttemplate.model.Entity.MediaEntity;
import com.codepath.apps.restclienttemplate.model.Entity.UrlEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class Entity implements Parcelable{
    @SerializedName("urls")
    private List<Url> url;

    @SerializedName("media")
    private List<Media> media;
    public Entity(){}

    public Entity(EntityEntity entityEntity){
        this.url = new ArrayList<>();
        this.media = new ArrayList<>();

        RealmList<UrlEntity> urlList = entityEntity.getUrl();
        if(urlList != null){
            if(urlList.size() >0){
                for (int i=0; i< urlList.size(); i++){
                    UrlEntity urlEntity = urlList.get(i);
                    Url url = new Url(urlEntity);
                    this.url.add(url);
                }

            }
        }


        RealmList<MediaEntity> mediaEntityList = entityEntity.getSize();
        if(mediaEntityList != null){
            if(mediaEntityList.size() >0){
                for (int i=0; i< mediaEntityList.size(); i++){
                    MediaEntity mediaEntity = mediaEntityList.get(i);
                    Media media = new Media(mediaEntity);
                    this.media.add(media);
                }

            }
        }

    }
    public EntityEntity toEntity(){
        EntityEntity entityEntity = new EntityEntity();
        RealmList<UrlEntity> urlEntityList = new RealmList<>();
        RealmList<MediaEntity> mediaEntityList = new RealmList<>();

        if(this.url != null){
            List<Url> urlList = this.url;
            if(urlList.size() >0){
                for (int i=0; i< urlList.size(); i++){
                    Url url = urlList.get(i);
                    UrlEntity urlEntity = url.toEntity();
                    urlEntityList.add(urlEntity);
                }

            }
        }


        if(this.media != null){
            List<Media> mediaList = this.media;
            if(mediaList.size() >0){
                for (int i=0; i< mediaList.size(); i++){
                    Media media = mediaList.get(i);
                    MediaEntity mediaEntity = media.toEntity();
                    mediaEntityList.add(mediaEntity);
                }

            }
        }


        entityEntity.setUrl(urlEntityList);
        entityEntity.setSize(mediaEntityList);

        return entityEntity;
    }



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
