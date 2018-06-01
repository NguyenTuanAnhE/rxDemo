package com.rxandroid.anhnt.rxdemo.online.genre;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.repositories.TrackRepository;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;

import java.util.List;

public class GenrePresenter implements GenreContract.Presenter,
        TrackDataSource.OnFetchDataListener<List<Track>> {
    private GenreContract.View mView;
    private TrackRepository mTrackRepository;

    public GenrePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;

    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadTrackByGenre(String genre, int limit, int offset) {
        mTrackRepository.getRemoteTrack(genre, limit, offset, this);
    }


    @Override
    public void onFetchDataSuccess(List<Track> data) {
        mView.showListTrack(data);
    }

    @Override
    public void onFetchDataFail(String message) {
        mView.getListFail(message);
    }
}
