package com.ftinc.lol52.ui.screens.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.util.ModelLoader;

/**
 * Created by r0adkll on 5/15/15.
 */
public interface GalleryPresenter {

    void loadCommits();

    void onItemClicked(Activity ctx, LolCommit lolCommit, View view);

    ModelLoader<LolCommit> provideLoader(Context ctx);

}
