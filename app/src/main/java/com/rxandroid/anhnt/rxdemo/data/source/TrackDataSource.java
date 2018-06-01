package com.rxandroid.anhnt.rxdemo.data.source;

import com.rxandroid.anhnt.rxdemo.Track;

import java.util.List;

public interface TrackDataSource {
    interface LocalDataSource {
        void getLocalTrack(OnFetchDataListener<List<Track>> listener);
    }

    interface RemoteDataSource {
        void getRemoteTrack(String genre, int limit, int offset,
                            OnFetchDataListener<List<Track>> listener);

        void searchTrack(int limit, int offset, String key, OnFetchDataListener<List<Track>> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(T data);

        void onFetchDataFail(String message);
    }
}
