package com.rxandroid.anhnt.rxdemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private String mArtworkUrl;
    private String mDescription;
    private boolean mDownloadable;
    private String mDownloadUrl;
    private long mDuration;
    private int mId;
    private int mLikesCount;
    private String mTitle;
    private String mUri;
    private String mUsername;
    private String mAvatarUrl;
    private int mPlaybackCount;

    public Track() {

    }

    protected Track(Parcel in) {
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadUrl = in.readString();
        mDuration = in.readLong();
        mId = in.readInt();
        mLikesCount = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUsername = in.readString();
        mAvatarUrl = in.readString();
        mPlaybackCount = in.readInt();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeString(mDownloadUrl);
        dest.writeLong(mDuration);
        dest.writeInt(mId);
        dest.writeInt(mLikesCount);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeString(mUsername);
        dest.writeString(mAvatarUrl);
        dest.writeInt(mPlaybackCount);
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public static Creator<Track> getCREATOR() {
        return CREATOR;
    }

    public static class TrackEntry {
        public static String COLLECTION = "collection";
        public static String TRACK = "track";
        public static String ARTWORK_URL = "artwork_url";
        public static String DESCRIPTION = "description";
        public static String DOWNLOADABLE = "downloadable";
        public static String DOWNLOAD_URL = "download_url";
        public static String DURATION = "duration";
        public static String ID = "id";
        public static String PLAYBACK_COUNT = "playback_count";
        public static String TITLE = "title";
        public static String URI = "uri";
        public static String USER = "user";
        public static String AVATAR_URL = "avatar_url";
        public static String LIKES_COUNT = "likes_count";
        public static String USERNAME = "username";
    }

    public static class TrackBuilder {

        private Track mTrack;

        public TrackBuilder() {
            mTrack = new Track();
        }

        public TrackBuilder setArtworkUrl(String artworkUrl) {
            mTrack.setArtworkUrl(artworkUrl);
            return this;
        }

        public TrackBuilder setDescription(String description) {
            mTrack.setDescription(description);
            return this;
        }

        public TrackBuilder setDownloadUrl(String downloadUrl) {
            mTrack.setDownloadUrl(downloadUrl);
            return this;
        }

        public TrackBuilder setDownloadable(boolean downloadable) {
            mTrack.setDownloadable(downloadable);
            return this;
        }

        public TrackBuilder setDuration(long duration) {
            mTrack.setDuration(duration);
            return this;
        }

        public TrackBuilder setPlaybackCount(int playbackCount) {
            mTrack.setPlaybackCount(playbackCount);
            return this;
        }

        public TrackBuilder setId(int id) {
            mTrack.setId(id);
            return this;
        }

        public TrackBuilder setTitle(String title) {
            mTrack.setTitle(title);
            return this;
        }

        public TrackBuilder setUri(String uri) {
            mTrack.setUri(uri);
            return this;
        }

        public TrackBuilder setUsername(String username) {
            mTrack.setUsername(username);
            return this;
        }

        public TrackBuilder setAvatarUrl(String avatarUrl) {
            mTrack.setAvatarUrl(avatarUrl);
            return this;
        }

        public TrackBuilder setLikesCount(int likesCount) {
            mTrack.setLikesCount(likesCount);
            return this;
        }

        public Track build() {
            return mTrack;
        }
    }

}
