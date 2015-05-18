package com.ftinc.lol52.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.widget.AspectRatioImageView;
import com.ftinc.lol52.R;
import com.ftinc.lol52.api.model.LolCommit;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
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
        Context ctx = holder.itemView.getContext();
        LolCommit commit = getItem(i);

        holder.title.setText(commit.getRepo());
        holder.subtitle.setText(commit.authorName);

        File outputDir = new File(ctx.getFilesDir(), "gifs");
        outputDir.mkdir();
        File output = new File(outputDir, commit.commitHash.concat(".gif"));

        if(output.exists()){

            try {
                GifDrawable drawable = new GifDrawableBuilder()
                        .from(output)
                        .build();

                holder.gifImage.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {

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
                    if (response.isSuccessful()) {

                        InputStream body = response.body().byteStream();

                        byte[] imageData = IOUtils.toByteArray(body);
                        FileUtils.writeByteArrayToFile(output, imageData);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GifDrawable drawable = new GifDrawableBuilder()
                                            .from(body)
                                            .build();
                                    holder.gifImage.setImageDrawable(drawable);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                holder.gifImage.setImageURI(Uri.fromFile(output));
                            }
                        });
                    }
                }
            });
        }

    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.gif_image)
        AspectRatioImageView gifImage;
        @InjectView(R.id.title)         TextView title;
        @InjectView(R.id.subtitle)      TextView subtitle;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
