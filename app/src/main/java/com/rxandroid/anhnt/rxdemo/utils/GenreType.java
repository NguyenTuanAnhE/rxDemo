package com.rxandroid.anhnt.rxdemo.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({
        GenreType.TAB_ALL_MUSIC,
        GenreType.TAB_ALL_AUDIO,
        GenreType.TAB_CLASSIC,
        GenreType.TAB_AMBIENT,
        GenreType.TAB_COUNTRY,
        GenreType.TAB_ALTER_NATIVE_ROCK,
        GenreType.ARGUMENT_GENRE
})

public @interface GenreType {
    String MY_MUSIC = "My Music";
    String TAB_ALL_MUSIC = "All-Music";
    String TAB_ALL_AUDIO = "All-Audio";
    String TAB_CLASSIC = "Classical";
    String TAB_AMBIENT = "Ambient";
    String TAB_COUNTRY = "Country";
    String TAB_ALTER_NATIVE_ROCK = "AlterNativeRock";
    String ARGUMENT_GENRE = "argument genre";
}

