package com.rxandroid.anhnt.rxdemo.data.source;

import com.rxandroid.anhnt.rxdemo.data.model.Genre;

import java.util.List;

public interface GenreDataSource {

    interface LocalDataSource {

        void getLocalGenres(OnFetchDataListener<Genre> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFail(String message);
    }

    interface RemoteDataSource {

        void getRemoteGenres(OnFetchDataListener<Genre> listener);
    }

}
