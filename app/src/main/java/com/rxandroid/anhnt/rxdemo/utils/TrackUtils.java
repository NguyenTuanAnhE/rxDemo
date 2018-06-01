package com.rxandroid.anhnt.rxdemo.utils;

import android.util.Log;

import com.rxandroid.anhnt.rxdemo.BuildConfig;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.API_KEY;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.CLIENT_ID;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.CROP;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.HOST;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.LARGE;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.LIMIT;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.OFFSET;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.PARAMETER_GENRE;
import static com.rxandroid.anhnt.rxdemo.utils.Constants.ApiRequest.PARAMETER_STREAM;

public class TrackUtils {
    public static String makeUrl(String url, Map<String, String> params) {

        StringBuilder stringBuilder = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Log.d("TAG", "makeUrl: " + entry.getKey());
            if (i == 2) {
                stringBuilder.append(entry.getKey()).append(entry.getValue());
            } else {
                stringBuilder.append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
            iterator.remove();
            i++;
        }
        Log.d("TAG", "makeUrl: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String makeUrl(String genre, int limit, int offset) {
        StringBuilder stringBuilder = new StringBuilder(HOST);
        stringBuilder.append(PARAMETER_GENRE).append(genre)
                .append("&").append(LIMIT).append("=").append(limit)
                .append("&").append(OFFSET).append("=").append(offset)
                .append("&").append(CLIENT_ID).append("=").append(API_KEY);
        return stringBuilder.toString();
    }

    public static String makeStreamUrl(String url) {
        String streamUrl = url + "/" +
                PARAMETER_STREAM +
                "?" +
                CLIENT_ID +
                "=" +
                API_KEY;
        return streamUrl;
    }

    public static String makeSearchUrl(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (i == 1) {
                stringBuilder.append(entry.getKey())
                        .append("?")
                        .append(entry.getValue());
            } else {
                stringBuilder.append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
            iterator.remove();
            i++;
        }
        Log.d("TAg", "makeSearchUrl: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String makeSearchUrl(int limit, int offset, String key) {
        String url = String.format("%s%s&%s=%d&%s=%s&%s=%s", Constants.ApiRequest.HOST_SEARCH,
                Constants.ApiRequest.SEARCH_FILTER, LIMIT, limit,
                CLIENT_ID, API_KEY,
                Constants.ApiRequest.PARAMETER_SEARCH, key);
        Log.d("TAG", "makeSearchUrl: " + url);
        return url;
    }

    public static String getGenre(int position) {
        switch (position) {
            case TabPosition.TAB_ALL_MUSIC:
                return GenreType.TAB_ALL_MUSIC;
            case TabPosition.TAB_ALL_AUDIO:
                return GenreType.TAB_ALL_AUDIO;
            case TabPosition.TAB_CLASSIC:
                return GenreType.TAB_CLASSIC;
            case TabPosition.TAB_AMBIENT:
                return GenreType.TAB_AMBIENT;
            case TabPosition.TAB_COUNTRY:
                return GenreType.TAB_COUNTRY;
            case TabPosition.TAB_ALTER_NATIVE_ROCK:
                return GenreType.TAB_ALTER_NATIVE_ROCK;
            default:
                return null;
        }
    }

    public static int getTabPosition(String genre) {
        switch (genre) {
            case GenreType.TAB_ALL_MUSIC:
                return TabPosition.TAB_ALL_MUSIC;
            case GenreType.TAB_ALL_AUDIO:
                return TabPosition.TAB_ALL_AUDIO;
            case GenreType.TAB_CLASSIC:
                return TabPosition.TAB_CLASSIC;
            case GenreType.TAB_AMBIENT:
                return TabPosition.TAB_AMBIENT;
            case GenreType.TAB_COUNTRY:
                return TabPosition.TAB_COUNTRY;
            case GenreType.TAB_ALTER_NATIVE_ROCK:
                return TabPosition.TAB_ALTER_NATIVE_ROCK;
            default:
                return 0;
        }
    }

    public static String getDuration(long time) {
        long min, sec, hour;
        time = time / 1000;
        hour = TimeUnit.SECONDS.toHours(time);
        if (hour == 0) {
            min = TimeUnit.SECONDS.toMinutes(time);
            sec = time - min * 60;
        } else {
            min = TimeUnit.SECONDS.toMinutes(time - hour * 60 * 60);
            sec = time - hour * 60 * 60 - min * 60;
        }

        String duration;
        if (hour == 0) {
            duration = String.format(Locale.ENGLISH, "%02d:%02d", min, sec);
        } else {
            duration = String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, min, sec);
        }

        return duration;
    }

    public static String getBetterArtwork(String artwork) {
        if (artwork == null) return null;
        if (artwork.contains(LARGE)) {
            artwork = artwork.replace(LARGE, CROP);
        }
        return artwork;
    }

}
