package com.ftinc.lol52.ui.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by r0adkll on 5/18/15.
 */
public interface CommitDetailPresenter {

    void parseExtras(Context ctx, Intent intent, Bundle icicle);

    void saveInstanceState(Bundle outState);

}
