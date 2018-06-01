package com.rxandroid.anhnt.rxdemo.utils.media;

import android.content.Context;
import android.media.MediaPlayer;

import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.service.MediaServiceListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.ERROR;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.IDLE;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.LOOP_ALL;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.LOOP_NO;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.LOOP_ONE;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.NO_SHUFFLE;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PAUSED;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PLAYING;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PREPARED;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PREPARING;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.SHUFFLE;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.STOPPED;


public class MediaManager implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mState;
    private int mLoop;
    private int mShuffle;
    private int mCurrentPosition;
    private int mShufflePosition;
    private boolean mEndOfList;
    private boolean mNewSong;
    private List<Track> mTracks;
    private List<Track> mShuffleTracks;
    private Track mCurrentTrack;
    private MediaServiceListener mListener;

    public MediaManager(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
        mTracks = new ArrayList<>();
        mShuffleTracks = new ArrayList<>();
        mState = IDLE;
    }

    public void setListener(MediaServiceListener listener) {
        mListener = listener;
    }

    public void setNewSong(boolean newSong) {
        mNewSong = newSong;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public int getShufflePosition() {
        return mShufflePosition;
    }

    public int getState() {
        return mState;
    }

    public int getLoop() {
        return mLoop;
    }

    public int getShuffle() {
        return mShuffle;
    }

    public long getCurrentDuration() {
        if (mMediaPlayer == null) return 0;
        return mMediaPlayer.getCurrentPosition();
    }

    public boolean isNewSong() {
        return mNewSong;
    }

    public boolean isEnd() {
        return mEndOfList;
    }


    private boolean isEndOfList() {
        return getCurrentTracks().indexOf(mCurrentTrack)
                == getCurrentTracks().size() - 1;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public Track getCurrentTrack() {
        return getCurrentTracks().get(getCurrentPosition());
    }

    /**
     * @return Current playing tracks
     */
    private List<Track> getCurrentTracks() {
        if (mShuffle == SHUFFLE) {
            return mShuffleTracks;
        }
        return mTracks;
    }

    /**
     * @param track
     * @return
     */
    public boolean isANewSong(Track track) {
        if (track == null) {
            return false;
        }
        if (getCurrentTrack() == null) {
            return true;
        }
        if (mState == IDLE) {
            return true;
        }
        return track.getId() != getCurrentTrack().getId();
    }

    public void playTracks(List<Track> tracks, int position) {
        if (tracks == null || position < 0) {
            return;
        }
        mTracks = tracks;
        //mShuffleTracks = getShuffleTracks(mTracks);
        playTracks(position);
    }

    private List<Track> getShuffleTracks(List<Track> tracks) {
        List<Track> result = new ArrayList<>();
        result.addAll(tracks);
        Collections.shuffle(result);
        return result;
    }

    /**
     *
     */

    public void playTracks(int position) {
        List<Track> tracks = getCurrentTracks();
        Track track = tracks.get(position);
        if (!isANewSong(track)) {
            mNewSong = false;
            return;
        }
        mCurrentPosition = position;
        playTracks(track);
    }

    /**
     * @param track
     */
    private void playTracks(Track track) {
        if (mState != ERROR) {
            reset();
        }
        mListener.playTrack(track);
        mState = PREPARING;
        mNewSong = true;
        mListener.onChangeMediaState(mState);
        try {
            mMediaPlayer.setDataSource(track.getUri());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            mListener.onFail(e.getMessage());
        }
        mCurrentTrack = track;
    }

    public void nextTrack() {
        if (isEndOfList()) {
            if (mLoop != LOOP_ALL) {
                mListener.onFail(mContext.getString(R.string.error_end_of_list));
                return;
            }
            mCurrentPosition = -1;
        }
        mCurrentPosition++;
        playTracks(getCurrentTracks().get(mCurrentPosition));

    }

    public void previousTrack() {
        if (mCurrentPosition == 0) {
            mCurrentPosition = getCurrentTracks().size();
        }
        mCurrentPosition--;
        playTracks(getCurrentTracks().get(mCurrentPosition));
    }

    public void pauseTrack() {
        mMediaPlayer.pause();
        mState = PAUSED;
        mListener.onChangeMediaState(mState);
    }

    public void playTrack() {
        mMediaPlayer.start();
        mState = PLAYING;
        mListener.onChangeMediaState(mState);
    }


    public void playAndPause() {
        switch (mState) {
            case PLAYING:
                pauseTrack();
                break;
            case PAUSED:
            case STOPPED:
            case PREPARED:
                playTrack();
                break;
        }
    }

    public void switchShuffleState() {
        if (mShuffle == NO_SHUFFLE) {

            mShuffleTracks.clear();
            mShuffleTracks.addAll(mTracks);
            Collections.shuffle(mTracks);
            mShufflePosition = mCurrentPosition;
            mCurrentPosition = 0;
            mShuffle = SHUFFLE;
        } else {
            mShufflePosition = mTracks.indexOf(mShuffleTracks.get(mCurrentPosition));
            if (mShufflePosition >= 0) {
                mCurrentPosition = mShufflePosition;
                mTracks.clear();
                mTracks.addAll(mShuffleTracks);
                mShuffle = NO_SHUFFLE;
            }
        }
        mListener.onShuffle(mShuffle);
    }

    public void switchLoopState() {
        //set loop mode
        switch (mLoop) {
            case LOOP_NO:
                mLoop = LOOP_ONE;
                break;
            case LOOP_ONE:
                mMediaPlayer.setLooping(false);
                mLoop = LOOP_ALL;
                break;
            case LOOP_ALL:
                mLoop = LOOP_NO;
                break;
        }
        //update ui loop button
        mListener.onLoop(mLoop);
    }

    /**
     * Play a track again when complete
     */
    private void loopOneTrack() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    /**
     * Play list track again when track in the end of list play complete
     */
    private void loopAllTracks() {
        mNewSong = true;
        nextTrack();
    }

    /**
     * Seek media player to a position to play
     */
    public void seekTo(int position) {
        if (mMediaPlayer == null) return;
        if (mState == PLAYING || mState == PAUSED) {
            mMediaPlayer.seekTo(position);
        }
    }

    /**
     * Reset media player
     */
    public void reset() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    /**
     * Destroy media player
     */
    public void destroy() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        mState = PLAYING;
        mListener.onChangeMediaState(mState);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mLoop) {
            case LOOP_NO:
                nextTrack();
                break;
            case LOOP_ONE:
                loopOneTrack();
                break;
            case LOOP_ALL:
                loopAllTracks();
                break;
        }
    }

//    public interface OnMediaListener {
//        void onFail(String error);
//
//        void onPreparing(Track track);
//
//        void onPause(Track track, boolean reLoad);
//
//        void onPlay(Track track, boolean reLoad);
//
//        void onPrepared(Track track);
//
//        void onShuffle(int shuffle);
//
//        void onLoop(int loop);
//    }
}
