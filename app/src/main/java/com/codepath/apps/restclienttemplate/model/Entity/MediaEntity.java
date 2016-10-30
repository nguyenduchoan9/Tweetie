package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class MediaEntity extends RealmObject {
    private String type;
    private SizeEntity size;
    private String url;
    private String mediaUrl;
    private String displayUrl;
    private long id;
    private String expandedUrl;



    public MediaEntity(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SizeEntity getSize() {
        return size;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }
}
