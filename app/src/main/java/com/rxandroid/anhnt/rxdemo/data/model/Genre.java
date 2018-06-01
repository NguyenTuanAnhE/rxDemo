package com.rxandroid.anhnt.rxdemo.data.model;

public class Genre {

    private String mTitle;
    private String mTrack;
    private int mImage;

    public Genre(String title, String track, int image) {
        this.mTitle = title;
        this.mTrack = track;
        this.mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTrack() {
        return mTrack;
    }

    public void setTrack(String track) {
        this.mTrack = track;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }
}
