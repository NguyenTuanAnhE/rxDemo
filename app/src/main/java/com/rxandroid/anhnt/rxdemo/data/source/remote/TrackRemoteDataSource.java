package com.rxandroid.anhnt.rxdemo.data.source.remote;

import com.rxandroid.anhnt.rxdemo.BuildConfig;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.iterator.TrackRemoteIterator;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;
import com.rxandroid.anhnt.rxdemo.utils.Constants;
import com.rxandroid.anhnt.rxdemo.utils.TrackUtils;

import java.util.HashMap;
import java.util.List;

import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.API_KEY;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sTrackTrackRemoteDataSource;

    public static TrackRemoteDataSource getInstance() {
        if (sTrackTrackRemoteDataSource == null) {
            sTrackTrackRemoteDataSource = new TrackRemoteDataSource();
        }
        return sTrackTrackRemoteDataSource;
    }

    @Override
    public void getRemoteTrack(String genre, int limit, int offset,
                               TrackDataSource.OnFetchDataListener<List<Track>> listener) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put(Constants.ApiRequest.PARAMETER_GENRE, genre);
//        params.put(Constants.ApiRequest.OFFSET, String.valueOf(offset));
//        params.put(Constants.ApiRequest.LIMIT, String.valueOf(limit));
//        params.put(Constants.ApiRequest.CLIENT_ID, API_KEY);
        String url = TrackUtils.makeUrl(genre, limit, offset);
        new TrackRemoteIterator(listener).execute(url);
    }

    @Override
    public void searchTrack(int limit, int offset, String key, TrackDataSource.OnFetchDataListener<List<Track>> listener) {

    }
}
