package com.ftinc.lol52.ui.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ftinc.lol52.App;
import com.ftinc.lol52.AppComponent;
import com.ftinc.lol52.R;

import butterknife.ButterKnife;

/**
 * Project: CongestiveHeartFailure
 * Package: com.ftinc.chf.ui.model
 * Created by drew.heavner on 4/20/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(App.get(this).component());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getAppBar();
        ButterKnife.inject(this);
    }

    /**
     * Get the {@link Toolbar} from the layout automatically if it exists
     *
     * @return      the toolbar in the layout
     */
    protected Toolbar getAppBar() {
        if(this.mAppBar == null) {
            this.mAppBar = ButterKnife.findById(this, R.id.appbar);
            if(this.mAppBar != null) {
                this.setSupportActionBar(this.mAppBar);
            }
        }

        return this.mAppBar;
    }

    protected abstract void setupComponent(AppComponent appComponent);

}
