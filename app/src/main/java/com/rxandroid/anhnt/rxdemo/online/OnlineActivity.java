package com.rxandroid.anhnt.rxdemo.online;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rxandroid.anhnt.rxdemo.BaseActivity;
import com.rxandroid.anhnt.rxdemo.MainActivity;
import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.service.MediaService;
import com.rxandroid.anhnt.rxdemo.service.MediaServiceListener;
import com.rxandroid.anhnt.rxdemo.utils.TabPosition;
import com.rxandroid.anhnt.rxdemo.utils.TrackUtils;

import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.IDLE;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PLAYING;
import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PREPARING;

public class OnlineActivity extends BaseActivity implements OnlineContract.View,
        View.OnClickListener, MediaServiceListener {
    private static final String ARGUMENT_GENRE = "genre";
    private TabLayout mTabGenre;
    private ViewPager mPagerGenre;
    private OnlinePagerAdapter mPagerAdapter;
    private MediaService mMediaService;
    private boolean mIsBound;
    private RoundedImageView mRoundedImageView;
    private View mLayoutBottom;
    private TextView mTextTitleSmall;
    private TextView mTextArtistSmall;
    private RoundedImageView mImageArtworkSmall;
    private ImageButton mImagePlay;
    private ProgressBar mProgressLoading;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            mMediaService = binder.getService();
            mMediaService.setListener(OnlineActivity.this);
            mIsBound = true;
            if (mMediaService.getState() != IDLE) {
                showSmallControlView(mMediaService.getCurrentTrack());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mIsBound = false;
        }
    };

    public static Intent getOnlineIntent(Context context, String genre) {
        Intent intent = new Intent(context, OnlineActivity.class);
        intent.putExtra(ARGUMENT_GENRE, genre);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsBound) {
            showSmallControlView(mMediaService.getCurrentTrack());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaService.removeListener(this);
        unbindService(mConnection);
        mIsBound = false;
    }

    @Override
    protected boolean getFullScreen() {
        return false;
    }

    @Override
    protected boolean showHomeEnable() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_online;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_free_speech_red;
    }

    @Override
    public void initComponents() {
        mLayoutBottom = findViewById(R.id.include_small_control);
        mTabGenre = findViewById(R.id.tab_online);
        mPagerGenre = findViewById(R.id.view_pager_genre);
        mImageArtworkSmall = findViewById(R.id.image_artwork_small);
        mImagePlay = findViewById(R.id.image_small_play);
        mTextArtistSmall = findViewById(R.id.text_artist_small);
        mTextTitleSmall = findViewById(R.id.text_title_small);
        mProgressLoading = findViewById(R.id.progress_small_loading);
        mLayoutBottom.setBackgroundColor(getResources()
                .getColor(R.color.color_red_tomato));
        mLayoutBottom.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.image_search_background).setOnClickListener(this);
        findViewById(R.id.image_small_next).setOnClickListener(this);
        findViewById(R.id.image_small_previous).setOnClickListener(this);

        mPagerAdapter = new OnlinePagerAdapter(getSupportFragmentManager());
        mPagerGenre.setAdapter(mPagerAdapter);
        mTabGenre.setupWithViewPager(mPagerGenre);
        mPagerGenre.setOffscreenPageLimit(TabPosition.TAB_COUNT);
        setSelectedTab();
    }

    private void setSelectedTab() {
        Intent intent = getIntent();
        if (intent != null) {
            String genre = intent.getStringExtra(ARGUMENT_GENRE);
            if (genre != null) {
                mPagerGenre.setCurrentItem(TrackUtils.getTabPosition(genre));
            }
        }
    }

    private void showSmallControlView(Track track) {
        mLayoutBottom.setVisibility(View.VISIBLE);
        updateDataSmallView(track);
        updatePlayButton(mMediaService.getState());
    }

    private void updateDataSmallView(Track track) {
        mTextTitleSmall.setText(track.getTitle());
        mTextArtistSmall.setText(track.getUsername());
        Glide.with(getApplicationContext())
                .load(track.getArtworkUrl())
                .into(mImageArtworkSmall);
    }

    private void updatePlayButton(int mediaState) {
        switch (mediaState) {
            case PREPARING:
                loadSongProgress();
                break;
            case PLAYING:
                loadTrackSuccess(R.drawable.ic_pause);
                break;
            default:
                loadTrackSuccess(R.drawable.ic_play);
                break;
        }
    }

    private void loadTrackSuccess(int resId) {
        mImagePlay.setBackgroundResource(resId);
        mProgressLoading.setVisibility(View.INVISIBLE);
        mImagePlay.setVisibility(View.VISIBLE);
    }

    private void playPauseSong() {
        if (mMediaService == null) return;
        mMediaService.playPauseTrack();

    }

    private void nextTrack() {
        if (mMediaService == null) return;
        mMediaService.playNextTrack();
    }

    private void previousTrack() {
        if (mMediaService == null) return;
        mMediaService.playPreviousTrack();
    }


    private void loadSongProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
        mImagePlay.setVisibility(View.INVISIBLE);
    }

    private void loadSongSuccess() {
        mProgressLoading.setVisibility(View.INVISIBLE);
        mImagePlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.image_search_background:
                break;
            case R.id.image_small_previous:
                previousTrack();
                break;
            case R.id.image_small_play:
                playPauseSong();
                break;
            case R.id.image_small_next:
                nextTrack();
                break;
            case R.id.include_small_control:
                break;
            default:
                break;
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        loadSongSuccess();
    }

    @Override
    public void onChangeMediaState(int mediaState) {
        updatePlayButton(mediaState);
    }

    @Override
    public void playTrack(Track track) {
        updateDataSmallView(track);
    }

    @Override
    public void onShuffle(int shuffle) {

    }

    @Override
    public void onLoop(int loop) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
