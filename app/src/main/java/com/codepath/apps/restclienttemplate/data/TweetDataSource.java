package com.codepath.apps.restclienttemplate.data;

import com.codepath.apps.restclienttemplate.model.Entity.TweetEntity;
import com.codepath.apps.restclienttemplate.model.model.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nguyen.D.Hoang on 10/30/2016.
 */

public class TweetDataSource {

    public void store(final List<Tweet> tweets) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Tweet item : tweets) {
                    TweetEntity entity = item.toEntity();
                    realm.copyToRealmOrUpdate(entity);
//                    TweetEntity entity = realm.createObject(TweetEntity.class);
//                    entity = item.toEntity();
//
//                    UserEntity userEntity = item.getUser().toEntity();
//                    entity.setUser(userEntity);

//                    EntityEntity entityEntity = item.getEntity().toEntity();
//                    SmallSizeEntity smallSizeEntity = item.getEntity().getMedia().get(0).getSize().getMediumSize().toEntity();
//                    entity

                }
            }
        });
        realm.close();
    }

    public void clearAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(TweetEntity.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
        realm.close();
    }

    public List<Tweet>  getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<Tweet> tweets = new ArrayList<>();
        RealmResults<TweetEntity> results =
                realm.where(TweetEntity.class).findAll();
        for (TweetEntity entity: results) {
            tweets.add(new Tweet(entity));
        }
        realm.close();

        return tweets;
    }
}
