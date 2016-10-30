package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class UrlEntity extends RealmObject {
    private String expandedUrl;
    @PrimaryKey
    private String url;
    private String displayUrl;

    public UrlEntity(){}

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }
}
