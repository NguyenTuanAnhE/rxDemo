package com.rxandroid.anhnt.rxdemo.data.iterator;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.data.source.TrackDataSource;

import java.util.ArrayList;
import java.util.List;

public class GetTrackLocalIterator {
    private Context mContext;

    public GetTrackLocalIterator(Context context) {
        mContext = context;
    }

    public void getTracks(TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        String[] projections = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID,
        };

        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projections,
                null,
                null,
                MediaStore.Audio.Media.TITLE + " ASC"
        );

        ArrayList<Track> tracks = new ArrayList<>();

        if (cursor == null) {
            listener.onFetchDataFail(mContext
                    .getString(R.string.error_get_track_local));
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            listener.onFetchDataFail(mContext
                    .getString(R.string.error_get_track_local));
        }

        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Track track = new Track();
            track.setTitle(cursor.getString(indexTitle));
            track.setUsername(cursor.getString(indexArtist));
            track.setDuration(cursor.getLong(indexDuration));
            track.setUri(cursor.getString(indexData));
            track.setId(cursor.getInt(indexAlbumId));

            tracks.add(track);
            cursor.moveToNext();
        }
        cursor.close();
        listener.onFetchDataSuccess(tracks);

    }
}
