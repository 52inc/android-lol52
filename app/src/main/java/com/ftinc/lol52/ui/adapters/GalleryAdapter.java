package com.ftinc.lol52.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.widget.AspectRatioImageView;
import com.ftinc.lol52.R;
import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.util.AnimatedGifEncoder;
import com.ftinc.lol52.util.GifDecoder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import timber.log.Timber;

/**
 * Created by r0adkll on 5/15/15.
 */
public class GalleryAdapter extends BetterRecyclerAdapter<LolCommit, GalleryAdapter.GalleryViewHolder> {

    @Inject
    OkHttpClient client;

    /**
     * Constructor
     */
    @Inject
    public GalleryAdapter() {
        super();
    }


    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int i) {
        super.onBindViewHolder(holder, i);
        LolCommit commit = getItem(i);

        holder.title.setText(commit.commitHash);
        holder.subtitle.setText(commit.message);
        holder.author.setText(commit.authorName);


        // Load the image
        Timber.i("Loading gif @ %s", commit.imageUrl);

        Request request = new Request.Builder()
                .url(commit.imageUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    InputStream body = response.body().byteStream();
                    BufferedInputStream bis = new BufferedInputStream(body);
                    GifDrawable gif = new GifDrawable(bis);
                    holder.gifImage.setImageDrawable(gif);
                }
            }
        });

    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.gif_image)     GifImageView gifImage;
        @InjectView(R.id.title)         TextView title;
        @InjectView(R.id.subtitle)      TextView subtitle;
        @InjectView(R.id.author)        TextView author;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
