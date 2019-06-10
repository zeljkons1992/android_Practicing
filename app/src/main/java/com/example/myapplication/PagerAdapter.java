package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GlideLoaderExampleFragment glideLoaderExampleFragment = new GlideLoaderExampleFragment();
                return glideLoaderExampleFragment;
            case 1:
                PicassoLoaderExampleFragment picassoLoaderExampleFragment = new PicassoLoaderExampleFragment();
                return picassoLoaderExampleFragment;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
