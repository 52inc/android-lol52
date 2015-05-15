package com.ftinc.lol52.ui.screens.gallery;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.widget.EmptyView;
import com.ftinc.lol52.AppComponent;
import com.ftinc.lol52.R;
import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.ui.adapters.GalleryAdapter;
import com.ftinc.lol52.ui.model.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by r0adkll on 5/15/15.
 */
public class GalleryActivity extends BaseActivity implements GalleryView, LoaderManager.LoaderCallbacks<List<LolCommit>>, BetterRecyclerAdapter.OnItemClickListener<LolCommit> {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mRefresh;

    @InjectView(R.id.recycler)
    RecyclerView mRecycler;

    @InjectView(R.id.empty_view)
    EmptyView mEmptyView;

    @Inject
    GalleryPresenter mPresenter;

    @Inject
    GalleryAdapter mAdapter;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Setup with recyclerview
        mAdapter.setEmptyView(mEmptyView);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(this);

        // Setup Loader
        getSupportLoaderManager()
                .initLoader(0, null, this);

        mRefresh.setOnRefreshListener(() -> mPresenter.loadCommits());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadCommits();
    }

    /***********************************************************************************************
     *
     * Base Methods
     *
     */

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new GalleryModule(this))
                .inject(this);
    }

    @Override
    public Loader<List<LolCommit>> onCreateLoader(int id, Bundle args) {
        return mPresenter.provideLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<LolCommit>> loader, List<LolCommit> data) {
        if(!isFinishing()){
            mRefresh.setRefreshing(false);
            mAdapter.clear();
            mAdapter.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<LolCommit>> loader) {
        if(!isFinishing()){
            mRefresh.setRefreshing(false);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, LolCommit lolCommit, int i) {
        mPresenter.onItemClicked(lolCommit);
    }
}
