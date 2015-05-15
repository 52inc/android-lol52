package com.ftinc.lol52.api;

import android.content.Context;

import com.ftinc.lol52.util.LoganSquareConverter;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static retrofit.RestAdapter.LogLevel.*;

/**
 * Created by r0adkll on 5/15/15.
 */
@Module
public class ApiModule {

    @Provides @Singleton
    Endpoint provideEndPoint(){
        return Endpoints.newFixedEndpoint("http://r0adkll.com:8321/API/V1/");
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Provides @Singleton
    OkClient provideClient(OkHttpClient client){
        return new OkClient(client);
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Context ctx,
                                   OkClient client,
                                   Endpoint endpoint){
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setConverter(new LoganSquareConverter())
                .setErrorHandler(new AppErrorHandler(ctx))
                .setLogLevel(BASIC)
                .build();
    }

    @Provides @Singleton
    ApiService provideApiClient(RestAdapter adapter){
        return adapter.create(ApiService.class);
    }

}
