package com.ftinc.lol52.ui.screens.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftinc.kit.util.BuildUtils;
import com.ftinc.kit.util.Utils;
import com.ftinc.kit.widget.AspectRatioImageView;
import com.ftinc.kit.widget.ScrimInsetsFrameLayout;
import com.ftinc.lol52.AppComponent;
import com.ftinc.lol52.R;
import com.ftinc.lol52.api.model.LolCommit;
import com.ftinc.lol52.ui.model.BaseActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by r0adkll on 5/18/15.
 */
public class CommitDetailActivity extends BaseActivity implements CommitDetailView, View.OnClickListener {

    /***********************************************************************************************
     *
     * Static Methods
     *
     */

    public static Intent createIntent(Context ctx, LolCommit commit){
        Intent intent = new Intent(ctx, CommitDetailActivity.class);
        intent.putExtra(EXTRA_COMMIT, commit.id);
        return intent;
    }

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_COMMIT = "extra_lol_commit";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    @InjectView(R.id.image)             AspectRatioImageView mImage;
    @InjectView(R.id.text_author)       TextView mTextAuthor;
    @InjectView(R.id.text_timestamp)    TextView mTextTimestamp;
    @InjectView(R.id.text_message)      TextView mTextMessage;

    @InjectView(R.id.author)    LinearLayout mAuthor;
    @InjectView(R.id.time)      LinearLayout mTime;
    @InjectView(R.id.message)   LinearLayout mMessage;



    @Inject
    CommitDetailPresenter mPresenter;

    private LolCommit mCommit;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup window flags if Lollipop
        if(BuildUtils.isLollipop()) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_detail);
        mPresenter.parseExtras(this, getIntent(), savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAppBar().setNavigationOnClickListener(this);

        View view = findViewById(R.id.appbar);
        view.postDelayed(() -> {
            int cx = view.getWidth() / 2;
            int cy = view.getHeight() / 2;
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.setDuration(500);
            view.setVisibility(View.VISIBLE);

            float tX = Utils.dpToPx(this, 64);
            long duration = 500;
            long delayMult = 150;

            mAuthor.setTranslationX(tX);
            mAuthor.animate()
                    .translationX(0)
                    .alpha(1)
                    .setDuration(duration)
                    .setStartDelay(delayMult)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .start();

            mTime.setTranslationX(tX);
            mTime.animate()
                    .translationX(0)
                    .alpha(1)
                    .setDuration(duration)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setStartDelay(delayMult * 2)
                    .start();

            mMessage.setTranslationX(tX);
            mMessage.animate()
                    .translationX(0)
                    .alpha(1)
                    .setDuration(duration)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .setStartDelay(delayMult * 3)
                    .start();

            anim.start();

        }, 300);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.saveInstanceState(outState);
    }



    /***********************************************************************************************
     *
     * View Methods
     *
     */

    @Override
    public void setGifImage(GifDrawable drawable) {
        mImage.setImageDrawable(drawable);
    }

    @Override
    public void setAuthor(String name, String email) {
        mTextAuthor.setText(String.format("%s\n%s", name, email));
    }

    @Override
    public void setTimestamp(String time) {
        mTextTimestamp.setText(time);
    }

    @Override
    public void setMessage(String message) {
        mTextMessage.setText(message);
    }

    @Override
    public void setCommitHash(String hash) {
        getSupportActionBar().setTitle(hash);
    }

    @Override
    public void setRepository(String repo) {
        getSupportActionBar().setSubtitle(repo);
    }

    /***********************************************************************************************
     *
     * Base Methods
     *
     */

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new CommitDetailModule(this))
                .inject(this);
    }

    @Override
    public void onClick(View v) {
        supportFinishAfterTransition();
    }
}
