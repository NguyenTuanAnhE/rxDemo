package com.rxandroid.anhnt.rxdemo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.utils.media.MediaManager;
import com.rxandroid.anhnt.rxdemo.utils.media.MediaNotification;

import java.util.ArrayList;
import java.util.List;

import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PAUSED;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PLAYING;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PREPARING;

public class MediaService extends Service implements MediaServiceListener {
    public static final String ACTION_START_MUSIC = "start music";
    public static final String ARGUMENT_POSITION = "position";
    public static final String ARGUMENT_LIST = "list track";
    public static final String ACTION_START_ACTIVITY = "start activity";
    public static final String ACTION_PREVIOUS = "previous track";
    public static final String ACTION_PLAY_PAUSE = "play pause track";
    public static final String ACTION_NEXT = "next track";
    public static final String ACTION_STOP_SERVICE = "stop service";
    private final IBinder mBinder = new MediaBinder();
    private MediaManager mMediaManager;
    private int mPosition;
    private List<Track> mTracks;
    private List<MediaServiceListener> mListeners;
    private MediaNotification mMediaNotification;

    public static Intent getMediaServiceIntent(Context context, List<Track> tracks, int position) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(ACTION_START_MUSIC);
        intent.putParcelableArrayListExtra(ARGUMENT_LIST,
                (ArrayList<? extends Parcelable>) tracks);
        intent.putExtra(ARGUMENT_POSITION, position);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaManager = new MediaManager(getApplicationContext());
        mMediaManager.setListener(this);
        mMediaNotification = new MediaNotification(this);
        mListeners = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    public void setListener(MediaServiceListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(MediaServiceListener listener) {
        mListeners.remove(listener);
    }

    public int getCurrentPosition() {
        return mMediaManager.getCurrentPosition();
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public Track getCurrentTrack() {
        return mMediaManager.getCurrentTrack();
    }

    public int getState() {
        return mMediaManager.getState();
    }

    public int getLoop() {
        return mMediaManager.getLoop();
    }

    public int getShuffle() {
        return mMediaManager.getShuffle();
    }

    public long getCurrentDuration() {
        return mMediaManager.getCurrentDuration();
    }

    public int getShufflePosition() {
        return mMediaManager.getShufflePosition();
    }

    /**
     * Return true if click on a playing track
     */
    public boolean isNewSong() {
        return mMediaManager.isNewSong();
    }

    public boolean isNewSong(Track track) {
        return mMediaManager.isANewSong(track);
    }

    public void setNewSong(boolean isNewSong) {
        mMediaManager.setNewSong(isNewSong);
    }

    /**
     * Handle intent received to control music
     */
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        switch (intent.getAction()) {
            //handle intent start a track
            case ACTION_START_MUSIC:
                mPosition = intent.getIntExtra(ARGUMENT_POSITION, 0);
                mTracks = intent.getParcelableArrayListExtra(ARGUMENT_LIST);
                playTrack(mTracks, mPosition);
                break;
            case ACTION_PREVIOUS:
                playPreviousTrack();
                break;
            case ACTION_PLAY_PAUSE:
                if (mMediaManager.getState() == PREPARING) return;
                playPauseTrack();
                break;
            case ACTION_NEXT:
                playNextTrack();
                break;
            case ACTION_STOP_SERVICE:
                stopSelf();
                break;
        }
    }

    /**
     * Play track when click on a  item in list tracks
     */
    public void playTrack(List<Track> tracks, int position) {
        if (mMediaManager == null) return;
        mMediaManager.playTracks(tracks, position);
    }

    /**
     * Play track with position of item in list
     */
    public void playTrack(int position) {
        if (mMediaManager == null) return;
        mMediaManager.playTracks(position);
    }

    /**
     * Play or pause a track if track is played
     */
    public void playPauseTrack() {
        mMediaManager.playAndPause();
    }

    /**
     * Play next track
     */
    public void playNextTrack() {
        mMediaManager.nextTrack();
    }

    /**
     * Play previous track
     */
    public void playPreviousTrack() {
        mMediaManager.previousTrack();
    }

    /**
     * Shuffle list track
     */
    public void shuffleSong() {
        mMediaManager.switchShuffleState();
    }

    /**
     * Loop if a track play complete by loop mode
     */
    public void loopSong() {
        mMediaManager.switchLoopState();
    }

    /**
     * Seek to position
     */
    public void seekTo(int position) {
        mMediaManager.seekTo(position);
    }

    /**
     * Callback when get error while playing track
     */
    @Override
    public void onFail(String error) {
        for (MediaServiceListener listener : mListeners) {
            listener.onFail(error);
        }
    }

    @Override
    public void onChangeMediaState(int mediaState) {
        switch (mediaState) {
            case PREPARING:
                break;
            case PLAYING:
                updateNotification(mediaState);
                Log.d("TAG", "onChangeMediaState: ");
                startForeground(MediaNotification.NOTIFICATION_ID,
                        mMediaNotification.createNotification(mMediaNotification.getBitmap()));
                break;
            case PAUSED:
                updateNotification(mediaState);
                stopForeground(false);
                break;
        }

        for (MediaServiceListener listener : mListeners) {
            listener.onChangeMediaState(mediaState);
        }
    }

    /**
     * Update notification ui match with current state
     *
     * @param state
     */
    private void updateNotification(int state) {
        mMediaNotification.setState(state);
        mMediaNotification.updateNotification(mMediaNotification.getBitmap());
        if (state == PLAYING) {

        } else {

        }
    }

    @Override
    public void playTrack(Track track) {
        mMediaNotification.getRemoteIcon(getCurrentTrack());
        mMediaNotification.startForegroundService(null);
        mMediaNotification.setState(mMediaManager.getState());
        for (MediaServiceListener listener : mListeners) {
            listener.playTrack(track);
        }
    }

    /**
     * Callback when shuffle mode change
     */
    @Override
    public void onShuffle(int shuffle) {
        for (MediaServiceListener listener : mListeners) {
            listener.onShuffle(shuffle);
        }
    }

    /**
     * Callback when loop mode change
     */
    @Override
    public void onLoop(int loop) {
        for (MediaServiceListener listener : mListeners) {
            listener.onLoop(loop);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ");
        if (mMediaManager == null) return;
        mMediaManager.destroy();
    }

    public class MediaBinder extends Binder {
        public MediaService getService() {
            return MediaService.this;
        }
    }
}
