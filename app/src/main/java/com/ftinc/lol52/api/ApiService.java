package com.ftinc.lol52.api;

import com.ftinc.lol52.api.model.LolCommit;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by r0adkll on 5/15/15.
 */
public interface ApiService {

    @GET("/commits")
    Observable<List<LolCommit>> getCommits();

    @GET("/commits")
    Observable<List<LolCommit>> getCommits(@Query("age") Long age);

    @GET("/commits/{start}/{end}")
    Observable<List<LolCommit>> getCommits(@Path("start") Long start,
                                           @Path("end") Long end);

}
