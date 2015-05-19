package com.ftinc.lol52.ui.screens.detail;

import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by r0adkll on 5/18/15.
 */
@Module
public class CommitDetailModule {

    private CommitDetailView mView;

    public CommitDetailModule(CommitDetailView view) {
        this.mView = view;
    }

    @Provides
    CommitDetailView provideView(){
        return mView;
    }

    @Provides
    CommitDetailPresenter providePresenter(CommitDetailView view,
                                           OkHttpClient client){
        return new CommitDetailPresenterImpl(view, client);
    }

}
