package com.rxandroid.anhnt.rxdemo.data.iterator;

import android.support.annotation.Nullable;

import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.utils.Constants;
import com.rxandroid.anhnt.rxdemo.utils.TrackUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.CONNECT_TIMEOUT;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.READ_TIMEOUT;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.REQUEST_METHOD;

public class TrackRemoteHandle {

    public TrackRemoteHandle() {

    }

    @Nullable
    public String getJsonFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        connection.disconnect();
        return sb.toString();
    }

    public Track getTrack(JSONObject jsonObject) throws JSONException {
        JSONObject user = jsonObject.optJSONObject(Track.TrackEntry.USER);
        String artworkUrl = jsonObject.optString(Track.TrackEntry.ARTWORK_URL);
        String username = null;
        String userAvatar = null;
        if (user != null) {
            username = user.optString(Track.TrackEntry.USERNAME);
            userAvatar = user.optString(Track.TrackEntry.AVATAR_URL);

            if (artworkUrl.equals(Constants.ApiRequest.NULL)) {
                artworkUrl = userAvatar;
            }
        }
        Track track = new Track.TrackBuilder()
                .setArtworkUrl(artworkUrl)
                .setAvatarUrl(userAvatar)
                .setUsername(username)
                .setDescription(jsonObject.optString(Track.TrackEntry.DESCRIPTION))
                .setDownloadable(jsonObject.optBoolean(Track.TrackEntry.DOWNLOADABLE))
                .setDownloadUrl(jsonObject.optString(Track.TrackEntry.DOWNLOAD_URL))
                .setDuration(jsonObject.optLong(Track.TrackEntry.DURATION))
                .setId(jsonObject.optInt(Track.TrackEntry.ID))
                .setLikesCount(jsonObject.optInt(Track.TrackEntry.LIKES_COUNT))
                .setPlaybackCount(jsonObject.optInt(Track.TrackEntry.PLAYBACK_COUNT))
                .setTitle(jsonObject.optString(Track.TrackEntry.TITLE))
                .setUri(TrackUtils.makeStreamUrl(jsonObject.optString(Track.TrackEntry.URI)))
                .build();

        return track;
    }

    public List<Track> getListTrack(JSONObject jsonObject) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        JSONArray listTracks = jsonObject.getJSONArray(Track.TrackEntry.COLLECTION);
        for (int i = 0; i < listTracks.length(); i++) {
            JSONObject object = listTracks.getJSONObject(i)
                    .getJSONObject(Track.TrackEntry.TRACK);
            Track track = getTrack(object);
            if (track != null) {
                tracks.add(track);
            }
        }
        return tracks;
    }

    public List<Track> getSearchList(JSONArray jsonArray) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Track track = getTrack(object);
            if (track != null) {
                tracks.add(track);
            }
        }
        return tracks;
    }
}
