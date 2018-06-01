package com.rxandroid.anhnt.rxdemo.online.genre;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rxandroid.anhnt.rxdemo.BaseFragment;
import com.rxandroid.anhnt.rxdemo.BuildConfig;
import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.repositories.TrackRepository;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;
import com.rxandroid.anhnt.rxdemo.data.source.local.TrackLocalDataSource;
import com.rxandroid.anhnt.rxdemo.data.source.remote.TrackRemoteDataSource;
import com.rxandroid.anhnt.rxdemo.utils.Constants;
import com.rxandroid.anhnt.rxdemo.utils.GenreType;

import java.util.ArrayList;
import java.util.List;

import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.API_KEY;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.CLIENT_ID;

public class GenreFragment extends BaseFragment implements GenreContract.View,
        TrackAdapter.OnTrackClickListener {
    private static final int LIMIT = 20;
    private RecyclerView mRecyclerTrack;
    private ProgressBar mProgressLoad;
    private TrackAdapter mTrackAdapter;
    private GenreContract.Presenter mPresenter;
    private List<Track> mTracks;
    private String mGenre;
    private int mOffset;
    private boolean isLoadMore;
    private int mPosition = -1;
    private long mDownloadId;
    private boolean mIsDownload;
    private DownloadManager manager;

    public static GenreFragment newInstance(String genre) {
        GenreFragment genreFragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(GenreType.ARGUMENT_GENRE, genre);
        genreFragment.setArguments(args);
        return genreFragment;
    }

    public GenreFragment() {
        mTracks = new ArrayList<>();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    protected void initComponents() {
        getContext().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mProgressLoad = getView().findViewById(R.id.progress_loading);
        mRecyclerTrack = getView().findViewById(R.id.recycler_track);
        mTrackAdapter = new TrackAdapter(getContext());
        mTrackAdapter.setListener(this);

        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);

        mPresenter = new GenrePresenter(trackRepository);
        mPresenter.setView(this);
        mRecyclerTrack.setAdapter(mTrackAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerTrack.setLayoutManager(layoutManager);

        mRecyclerTrack.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(layoutManager);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(onComplete);
    }

    private void loadMore(LinearLayoutManager layoutManager) {
        if (mTracks == null || mTracks.size() == 0) return;
        if (mGenre == null) return;
        if (layoutManager.findLastVisibleItemPosition() == mTracks.size() - 1) {
            if (isLoadMore) {
                mOffset = mOffset + LIMIT;
                loadMoreData(mGenre, mOffset);
                isLoadMore = false;
            }
        }
    }

    @Override
    protected void loadData() {
        mGenre = getArguments().getString(GenreType.ARGUMENT_GENRE);
        loadMoreData(mGenre, Constants.ApiRequest.OFFSET_VALUE);
    }

    private void loadMoreData(String genre, int offset) {
        mPresenter.loadTrackByGenre(genre,
                Constants.ApiRequest.LIMIT_VALUE, offset);
        mProgressLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListTrack(List<Track> tracks) {
        isLoadMore = true;
        mTracks.addAll(tracks);
        mTrackAdapter.updateData(tracks);
        mProgressLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getListFail(String message) {
        Log.d("TAG", "getListFail: " + message);
        mProgressLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onClickDownload(int position) {
        Track track = mTracks.get(position);
        if (!track.isDownloadable()) {
            Toast.makeText(getContext(), R.string.cannot_download, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPosition != position) {
            mPosition = position;
            mIsDownload = true;
            mTrackAdapter.setPosition(position, mIsDownload);
            downloadTrack(track);
        } else {
            cancelDownload();
            mIsDownload = false;
        }

    }

    private void downloadTrack(Track track) {
        String url = track.getDownloadUrl() + "?" + CLIENT_ID + "=" + API_KEY;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(track.getUsername());
        request.setTitle(track.getTitle());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, track.getTitle() + "/" + track.getId() + ".mp3");

// get download service and enqueue file
        manager = (DownloadManager) getContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        mDownloadId = manager.enqueue(request);
    }

    private void cancelDownload() {
        manager.remove(mDownloadId);
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            mTrackAdapter.setPosition(mPosition, mIsDownload);
            mPosition = -1;
        }
    };


}
