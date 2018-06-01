package com.rxandroid.anhnt.rxdemo.online.genre;

import com.rxandroid.anhnt.rxdemo.BasePresenter;
import com.rxandroid.anhnt.rxdemo.BaseView;
import com.rxandroid.anhnt.rxdemo.Track;

import java.util.List;

public interface GenreContract {
    interface View extends BaseView {
        void showListTrack(List<Track> tracks);

        void getListFail(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void loadTrackByGenre(String genre, int limit, int offset);
    }
}
