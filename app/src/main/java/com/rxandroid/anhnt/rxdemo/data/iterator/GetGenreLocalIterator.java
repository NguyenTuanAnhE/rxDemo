package com.rxandroid.anhnt.rxdemo.data.iterator;

import android.content.Context;
import android.content.res.Resources;

import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.data.model.Genre;
import com.rxandroid.anhnt.rxdemo.data.source.GenreDataSource;

import java.util.ArrayList;
import java.util.List;

public class GetGenreLocalIterator {
    private Context mContext;
    private static GetGenreLocalIterator sGetGenreLocalIterator;

    public static GetGenreLocalIterator getInstance(Context context) {
        if (sGetGenreLocalIterator == null) {
            sGetGenreLocalIterator = new GetGenreLocalIterator(context);
        }
        return sGetGenreLocalIterator;
    }

    private GetGenreLocalIterator(Context context) {
        mContext = context;
    }

    public void getGenre(GenreDataSource.OnFetchDataListener<Genre> mListener) {
        List<Genre> genres = new ArrayList<>();
        Resources resources = mContext.getResources();
        genres.add(new Genre(resources.getString(R.string.my_music), "", R.drawable.home_cover_my_music));
        genres.add(new Genre(resources.getString(R.string.all_track), "", R.drawable.home_cover_online_track));
        genres.add(new Genre(resources.getString(R.string.all_audio), "", R.drawable.home_cover_audio));
        genres.add(new Genre(resources.getString(R.string.genre_classic), "", R.drawable.home_cover_classical));
        genres.add(new Genre(resources.getString(R.string.genre_ambient), "", R.drawable.home_cover_ambient));
        genres.add(new Genre(resources.getString(R.string.genre_country), "", R.drawable.home_cover_country));
        genres.add(new Genre(resources.getString(R.string.genre_alter_native_rock), "", R.drawable.home_cover_rock));
        mListener.onFetchDataSuccess(genres);
    }

}
