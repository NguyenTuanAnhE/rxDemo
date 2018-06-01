package com.rxandroid.anhnt.rxdemo.data.iterator;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrackRemoteIterator extends TrackRemote<List<Track>> {
    public TrackRemoteIterator(TrackDataSource.OnFetchDataListener listener) {
        super(listener);
    }

    @Override
    public List<Track> getData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        return new TrackRemoteHandle().getListTrack(jsonObject);
    }
}
