package com.ftinc.lol52.data;

import com.ftinc.lol52.BuildConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.Ollie;
import ollie.OllieProvider;

/**
 * Created by r0adkll on 5/19/15.
 */
@Singleton
public class LolProvider extends OllieProvider {

    private Ollie.LogLevel mLogLevel;

    public LolProvider(){
        mLogLevel = Ollie.LogLevel.BASIC;
    }

    @Inject
    public LolProvider(Ollie.LogLevel logLevel){
        mLogLevel = logLevel;
    }

    @Override
    protected String getDatabaseName() {
        return "lol52.db";
    }

    @Override
    protected int getDatabaseVersion() {
        return 1;
    }

    @Override
    protected String getAuthority() {
        return BuildConfig.APPLICATION_ID;
    }

    @Override
    protected Ollie.LogLevel getLogLevel() {
        return mLogLevel;
    }

}
