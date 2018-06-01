package com.rxandroid.anhnt.rxdemo.data.source.remote;

import com.rxandroid.anhnt.rxdemo.data.model.Genre;
import com.rxandroid.anhnt.rxdemo.data.source.GenreDataSource;

import java.util.List;

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {
    private static GenreRemoteDataSource sGenreRemoteDataSource;

    public static GenreRemoteDataSource getInstance() {
        if (sGenreRemoteDataSource == null) {
            sGenreRemoteDataSource = new GenreRemoteDataSource();
        }
        return sGenreRemoteDataSource;
    }

    @Override
    public void getRemoteGenres(GenreDataSource.OnFetchDataListener<Genre> listener) {

    }
}
