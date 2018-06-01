package com.rxandroid.anhnt.rxdemo.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({
        TabPosition.TAB_ALL_MUSIC,
        TabPosition.TAB_ALL_AUDIO,
        TabPosition.TAB_CLASSIC,
        TabPosition.TAB_AMBIENT,
        TabPosition.TAB_COUNTRY,
        TabPosition.TAB_ALTER_NATIVE_ROCK,
        TabPosition.TAB_COUNT
})
public @interface TabPosition {
    int TAB_ALL_MUSIC = 0;
    int TAB_ALL_AUDIO = 1;
    int TAB_CLASSIC = 2;
    int TAB_AMBIENT = 3;
    int TAB_COUNTRY = 4;
    int TAB_ALTER_NATIVE_ROCK = 5;
    int TAB_COUNT = 6;
}
