package com.ftinc.lol52.ui.screens.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.widget.AspectRatioImageView;
import com.ftinc.lol52.R;
import com.ftinc.lol52.api.ApiService;
import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.ui.screens.detail.CommitDetailActivity;
import com.ftinc.lol52.util.ModelLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ollie.query.Select;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by r0adkll on 5/15/15.
 */
public class GalleryPresenterImpl implements GalleryPresenter {

    private GalleryView mView;
    private ApiService mApi;

    public GalleryPresenterImpl(GalleryView view,
                                ApiService api) {
        this.mView = view;
        this.mApi = api;
    }


    @Override
    public void loadCommits() {

        mApi.getCommits()
                .flatMap(lolCommits -> Observable.from(lolCommits))
                .map(lolCommit -> {

                    LolCommit existing = Select.from(LolCommit.class)
                            .where("hash=?", lolCommit.commitHash)
                            .fetchSingle();

                    if(existing != null){
                        lolCommit.id = existing.id;
                    }

                    lolCommit.save();
                    return lolCommit;
                })
                .collect(() -> new ArrayList<LolCommit>(), (lolCommits, lolCommit) -> lolCommits.add(lolCommit))
                .compose(RxUtils.applyIOSchedulers())
                .subscribe(lolCommits -> mView.hideLoading(), throwable -> mView.hideLoading());


    }

    @Override
    public void onItemClicked(Activity ctx, LolCommit lolCommit, View view) {
        Intent detailIntent = CommitDetailActivity.createIntent(ctx, lolCommit);
        AspectRatioImageView gifImage = ButterKnife.findById(view, R.id.gif_image);
        UIUtils.startActivityWithTransition(ctx, detailIntent, Pair.create(gifImage, "gif_image"));
    }

    @Override
    public ModelLoader<LolCommit> provideLoader(Context ctx) {
        return new ModelLoader<>(ctx, LolCommit.class);
    }
}
