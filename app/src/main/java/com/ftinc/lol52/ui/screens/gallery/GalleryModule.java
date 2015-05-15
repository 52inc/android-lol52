package com.ftinc.lol52.ui.screens.gallery;

import com.ftinc.lol52.api.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by r0adkll on 5/15/15.
 */
@Module
public class GalleryModule {

    private GalleryView mView;

    public GalleryModule(GalleryView view) {
        this.mView = view;
    }

    @Provides
    GalleryView provideView(){
        return mView;
    }

    @Provides
    GalleryPresenter providePresenter(GalleryView view,
                                      ApiService api){
        return new GalleryPresenterImpl(view, api);
    }
}
