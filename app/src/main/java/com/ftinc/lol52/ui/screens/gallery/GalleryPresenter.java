package com.ftinc.lol52.ui.screens.gallery;

import android.content.Context;

import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.util.ModelLoader;

/**
 * Created by r0adkll on 5/15/15.
 */
public interface GalleryPresenter {

    void loadCommits();

    void onItemClicked(LolCommit lolCommit);

    ModelLoader<LolCommit> provideLoader(Context ctx);

}
