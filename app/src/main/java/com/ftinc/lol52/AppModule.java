package com.ftinc.lol52;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ollie.Ollie;

/**
 * Created by r0adkll on 5/15/15.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides @Singleton
    Context provideApplicationContext(){
        return mApp;
    }

    @Provides @Singleton
    Ollie.LogLevel provideOllieLogLevel(){
        return BuildConfig.DEBUG ? Ollie.LogLevel.BASIC : Ollie.LogLevel.NONE;
    }
}
