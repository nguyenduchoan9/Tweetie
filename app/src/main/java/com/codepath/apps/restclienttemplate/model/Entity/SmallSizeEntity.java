package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class SmallSizeEntity extends RealmObject {
    private long height;
    private long width;
    private String resize;

    public SmallSizeEntity(){}

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public String getResize() {
        return resize;
    }

    public void setResize(String resize) {
        this.resize = resize;
    }
}
