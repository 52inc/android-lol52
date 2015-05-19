package com.ftinc.lol52;

import com.ftinc.lol52.api.ApiModule;
import com.ftinc.lol52.ui.UiModule;
import com.ftinc.lol52.ui.screens.detail.CommitDetailComponent;
import com.ftinc.lol52.ui.screens.detail.CommitDetailModule;
import com.ftinc.lol52.ui.screens.gallery.GalleryComponent;
import com.ftinc.lol52.ui.screens.gallery.GalleryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by r0adkll on 5/15/15.
 */
@Singleton
@Component(
    modules = {
        AppModule.class,
        ApiModule.class,
        UiModule.class
    }
)
public interface AppComponent {
    void inject(App app);

    GalleryComponent plus(GalleryModule module);
    CommitDetailComponent plus(CommitDetailModule module);

}
