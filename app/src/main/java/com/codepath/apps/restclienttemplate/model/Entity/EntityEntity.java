package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class EntityEntity extends RealmObject {
    private RealmList<UrlEntity> url;
    private RealmList<MediaEntity> media;

    public EntityEntity(){}

    public RealmList<UrlEntity> getUrl() {
        return url;
    }

    public void setUrl(RealmList<UrlEntity> url) {
        this.url = url;
    }

    public RealmList<MediaEntity> getSize() {
        return media;
    }

    public void setSize(RealmList<MediaEntity> size) {
        this.media = size;
    }
}

