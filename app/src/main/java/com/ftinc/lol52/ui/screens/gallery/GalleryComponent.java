package com.ftinc.lol52.ui.screens.gallery;

import dagger.Subcomponent;

/**
 * Created by r0adkll on 5/15/15.
 */
@Subcomponent(modules = GalleryModule.class)
public interface GalleryComponent {
    void inject(GalleryActivity activity);
}
