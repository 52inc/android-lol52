package com.ftinc.lol52.ui.screens.detail;

import dagger.Subcomponent;

/**
 * Created by r0adkll on 5/18/15.
 */
@Subcomponent(modules = CommitDetailModule.class)
public interface CommitDetailComponent {

    void inject(CommitDetailActivity activity);

}
