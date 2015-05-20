package com.ftinc.lol52;

import android.app.Service;
import android.content.Context;

import com.ftinc.kit.mvp.BaseApplication;
import com.ftinc.lol52.data.LolProvider;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import ollie.Ollie;
import ollie.Ollie.LogLevel;
import timber.log.Timber;

/**
 * Created by r0adkll on 5/14/15.
 */
public class App extends BaseApplication{

    private AppComponent mComponent;

    @Inject
    LolProvider mProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Dagger 2
        setupGraph();

        // Initialize Ollie
        mProvider.onCreate();

    }


    @DebugLog
    private void setupGraph(){
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mComponent.inject(this);
    }


    public AppComponent component(){
        return mComponent;
    }

    public static App get(Context ctx){
        return (App) ctx.getApplicationContext();
    }

    public static App get(Service ctx){
        return (App) ctx.getApplication();
    }

    /***********************************************************************************************
     *
     * Base Methods
     *
     */


    @Override
    public Timber.Tree[] getDebugTrees() {
        return new Timber.Tree[]{
            new Timber.DebugTree()
        };
    }

    @Override
    public Timber.Tree[] getReleaseTrees() {
        return new Timber.Tree[0];
    }

    @Override
    public Boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
