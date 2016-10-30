package com.codepath.apps.restclienttemplate.model.Entity;

import io.realm.RealmObject;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class SizeEntity extends RealmObject {
    private SmallSizeEntity mediumSize;

    public SizeEntity() {
    }

    public SmallSizeEntity getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(SmallSizeEntity mediumSize) {
        this.mediumSize = mediumSize;
    }
}
