package com.ftinc.lol52.ui.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.ftinc.lol52.api.model.LolCommit;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ollie.Model;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import timber.log.Timber;

/**
 * Created by r0adkll on 5/18/15.
 */
public class CommitDetailPresenterImpl implements CommitDetailPresenter {

    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM, d yyyy h:mm a");

    private CommitDetailView mView;
    private LolCommit mCommit;
    private OkHttpClient mClient;

    /**
     * Constructor
     */
    public CommitDetailPresenterImpl(CommitDetailView view,
                                     OkHttpClient client) {
        this.mView = view;
        this.mClient = client;
    }


    @Override
    public void parseExtras(Context ctx, Intent intent, Bundle icicle) {

        long commitId = -1;
        if(intent != null){
            commitId = intent.getLongExtra(CommitDetailActivity.EXTRA_COMMIT, -1);
        }else if(icicle != null){
            commitId = icicle.getLong(CommitDetailActivity.EXTRA_COMMIT, -1);
        }

        if(commitId != -1){
            mCommit = Model.find(LolCommit.class, commitId);
        }else{
            // TODO: Exit activity
            return;
        }

        // Inflate UI with commit data
        mView.setAuthor(mCommit.authorName, mCommit.authorEmail);
        mView.setCommitHash(mCommit.commitHash);
        mView.setMessage(mCommit.message);
        mView.setRepository(mCommit.getRepo());
        mView.setTimestamp(mDateFormat.format(mCommit.getTime()));

        loadGifImage(ctx);
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        outState.putLong(CommitDetailActivity.EXTRA_COMMIT, mCommit.id);
    }

    private void loadGifImage(Context ctx){

        // 1) Calculate image file location
        File outputDir = new File(ctx.getFilesDir(), "gifs");
        outputDir.mkdir();
        File output = new File(outputDir, mCommit.commitHash.concat(".gif"));

        if(output.exists()){

            try {
                GifDrawable drawable = new GifDrawableBuilder()
                        .from(output)
                        .build();

                mView.setGifImage(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {

            // Load the image
            Timber.i("Loading gif @ %s", mCommit.imageUrl);

            Request request = new Request.Builder()
                    .url(mCommit.imageUrl)
                    .get()
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {

                        InputStream body = response.body().byteStream();

                        byte[] imageData = IOUtils.toByteArray(body);
                        FileUtils.writeByteArrayToFile(output, imageData);

                        new Handler(Looper.getMainLooper()).post(() -> {
                            try {
                                GifDrawable drawable = new GifDrawableBuilder()
                                        .from(output)
                                        .build();

                                mView.setGifImage(drawable);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            });
        }

    }

}
