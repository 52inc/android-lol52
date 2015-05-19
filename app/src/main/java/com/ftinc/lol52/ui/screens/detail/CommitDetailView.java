package com.ftinc.lol52.ui.screens.detail;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by r0adkll on 5/18/15.
 */
public interface CommitDetailView {

    void setGifImage(GifDrawable drawable);

    void setAuthor(String name, String email);

    void setTimestamp(String time);

    void setMessage(String message);

    void setCommitHash(String hash);

    void setRepository(String repo);

}
