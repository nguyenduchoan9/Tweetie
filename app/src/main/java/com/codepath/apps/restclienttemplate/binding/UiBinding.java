package com.codepath.apps.restclienttemplate.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.codepath.apps.restclienttemplate.model.model.Media;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/27/2016.
 */

public class UiBinding {
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, List<Media> mediaList) {
        if (mediaList != null && mediaList.size() > 0) {
            Media media = mediaList.get(0);
            Picasso.with(imageView.getContext())
                    .load(media.getMediaUrl())
                    .into(imageView);
        }

    }
}
