package com.rxandroid.anhnt.rxdemo.data.source.local;

import android.content.Context;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.iterator.GetTrackLocalIterator;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;

import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static TrackLocalDataSource sTrackTrackLocalDataSource;
    private GetTrackLocalIterator mGetTrackLocalIterator;

    public static TrackLocalDataSource getInstance() {

        return sTrackTrackLocalDataSource;
    }

    public static void initContext(Context context) {
        if (sTrackTrackLocalDataSource == null) {
            sTrackTrackLocalDataSource = new TrackLocalDataSource(context);
        }
    }

    private TrackLocalDataSource(Context context) {
        mGetTrackLocalIterator = new GetTrackLocalIterator(context);
    }

    @Override
    public void getLocalTrack(TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mGetTrackLocalIterator.getTracks(listener);
    }
}
