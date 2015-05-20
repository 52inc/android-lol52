package com.ftinc.lol52.ui.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.drawer.Config;
import com.ftinc.kit.drawer.items.DrawerItem;

import java.util.List;

/**
 * Created by r0adkll on 5/19/15.
 */
public class LolDrawerConfig extends Config {

    /**
     * Default Constructor
     */
    public LolDrawerConfig(){}

    @Override
    protected void inflateItems(List<DrawerItem> list) {



    }

    @Override
    protected void onDrawerItemClicked(DrawerItem drawerItem) {

    }

    @Override
    protected View inflateHeader(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return null;
    }
}
