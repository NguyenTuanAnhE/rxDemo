package com.rxandroid.anhnt.rxdemo.service;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState;

public interface MediaServiceListener {
    void onFail(String error);

    void onChangeMediaState(@MediaPlayerState.MediaState int mediaState);

    void playTrack(Track track);

    void onShuffle(int shuffle);

    void onLoop(int loop);
}
