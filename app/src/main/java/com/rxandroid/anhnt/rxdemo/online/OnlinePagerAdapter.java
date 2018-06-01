package com.rxandroid.anhnt.rxdemo.online;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rxandroid.anhnt.rxdemo.online.genre.GenreFragment;
import com.rxandroid.anhnt.rxdemo.utils.TabPosition;
import com.rxandroid.anhnt.rxdemo.utils.TrackUtils;

public class    OnlinePagerAdapter extends FragmentPagerAdapter {
    public OnlinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GenreFragment.newInstance(TrackUtils.getGenre(position));
    }

    @Override
    public int getCount() {
        return TabPosition.TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TrackUtils.getGenre(position);
    }
}
