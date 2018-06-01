package com.rxandroid.anhnt.rxdemo.data.repositories;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource,
        TrackDataSource.RemoteDataSource {

    private static TrackRepository sTrackRepository;
    private TrackDataSource.LocalDataSource mLocalDataSource;
    private TrackDataSource.RemoteDataSource mRemoteDataSource;

    public static TrackRepository getInstance(TrackDataSource.LocalDataSource localDataSource,
                                              TrackDataSource.RemoteDataSource remoteDataSource) {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(localDataSource, remoteDataSource);
        }
        return sTrackRepository;
    }

    private TrackRepository(TrackDataSource.LocalDataSource localDataSource,
                            TrackDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void getRemoteTrack(String genre, int limit, int offset,
                               TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mRemoteDataSource.getRemoteTrack(genre, limit, offset, listener);
    }

    @Override
    public void searchTrack(int limit,int offset, String key, TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mRemoteDataSource.searchTrack(limit,offset, key, listener);
    }

    @Override
    public void getLocalTrack(TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mLocalDataSource.getLocalTrack(listener);
    }
}
