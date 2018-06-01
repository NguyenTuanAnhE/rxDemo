package com.rxandroid.anhnt.rxdemo.utils;

import android.app.Application;

import com.rxandroid.anhnt.rxdemo.data.source.local.GenreLocalDataSource;
import com.rxandroid.anhnt.rxdemo.data.source.local.TrackLocalDataSource;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GenreLocalDataSource.initContext(this);
        TrackLocalDataSource.initContext(this);
    }
}
