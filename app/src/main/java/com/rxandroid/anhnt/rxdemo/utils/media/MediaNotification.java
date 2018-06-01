package com.rxandroid.anhnt.rxdemo.utils.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;
import com.rxandroid.anhnt.rxdemo.service.MediaService;
import com.rxandroid.anhnt.rxdemo.utils.TrackUtils;

import static com.rxandroid.anhnt.rxdemo.utils.MediaPlayerState.PAUSED;

public class MediaNotification implements TrackIconRemote.OnLoadBitmapListener {
    public static final int NOTIFICATION_ID = 21;
    private Service mService;
    private Track mTrack;
    private int mState;
    private Bitmap mBitmap;

    public MediaNotification(Service service) {
        mService = service;
    }

    public void getRemoteIcon(Track track) {
        mTrack = track;
        new TrackIconRemote(this)
                .execute(TrackUtils.getBetterArtwork(track.getArtworkUrl()));
    }

    public void setState(int state) {
        mState = state;
    }

    public int getState() {
        return mState;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

//    private PendingIntent getContentIntent() {
//        Intent intent = new Intent(mService, PlayerActivity.class);
//        intent.setAction(MediaService.ACTION_START_ACTIVITY);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(PlayerActivity.ARGUMENT_NEW_TRACK, false);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mService);
//        stackBuilder.addNextIntentWithParentStack(intent);
//        return stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//    }

    private PendingIntent getActionIntent(String action) {
        Intent intent = new Intent(mService, MediaService.class);
        intent.setAction(action);
        return PendingIntent.getService(mService, 0, intent, 0);
    }

    private int getPlayPauseIcon() {
        if (getState() == PAUSED) {
            return android.R.drawable.ic_media_play;
        }
        return android.R.drawable.ic_media_pause;
    }

    public Notification createNotification(Bitmap bitmap) {
        Notification notification = new NotificationCompat.Builder(mService, "")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getLargeIconBitmap(bitmap))
                .setContentTitle(mTrack.getTitle())
                .setContentText(mTrack.getUsername())
                //.setContentIntent(getContentIntent())
                .setDeleteIntent(getActionIntent(MediaService.ACTION_STOP_SERVICE))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setOngoing(false)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowCancelButton(false))
                .addAction(android.R.drawable.ic_media_previous,
                        MediaService.ACTION_PREVIOUS,
                        getActionIntent(MediaService.ACTION_PREVIOUS))
                .addAction(getPlayPauseIcon(),
                        MediaService.ACTION_PREVIOUS,
                        getActionIntent(MediaService.ACTION_PLAY_PAUSE))
                .addAction(android.R.drawable.ic_media_next,
                        MediaService.ACTION_PREVIOUS,
                        getActionIntent(MediaService.ACTION_NEXT))
                .build();
        return notification;
    }

    public void updateNotification(Bitmap bitmap) {
        Notification notification = createNotification(bitmap);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(mService);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void startForegroundService(Bitmap bitmap) {
        Notification notification = createNotification(bitmap);
        mService.startForeground(NOTIFICATION_ID, notification);
    }

    private Bitmap getLargeIconBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return bitmap;
        }
        return BitmapFactory.decodeResource(mService.getResources(), android.R.drawable.ic_media_play);
    }

    @Override
    public void onLoadSuccess(Bitmap bitmap) {
        mBitmap = bitmap;
        updateNotification(bitmap);
    }

    @Override
    public void onLoadFail() {

    }
}
