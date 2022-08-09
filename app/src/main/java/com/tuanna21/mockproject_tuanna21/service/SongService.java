package com.tuanna21.mockproject_tuanna21.service;

import static com.tuanna21.mockproject_tuanna21.app.ApplicationController.NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;
import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivity;

public class SongService extends Service implements SongObserver {
    private static final int ACTION_PLAY_PREV = 10;
    private static final int ACTION_PLAY_PLAY = 11;
    private static final int ACTION_PLAY_NEXT = 12;
    private static final int ACTION_CLOSE = 13;
    private static final int ACTION_MISS_CLICK = 14;
    private static final String TAG = "SongService";
    private MyPlayerController mMusicController;
    private Handler mHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicController = MyPlayerController.getInstance();
        mMusicController.addObserver(this);
        HandlerThread mHandlerThread = new HandlerThread("Service_thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("user_action")) {
            handleAction(intent.getIntExtra("user_action", ACTION_MISS_CLICK));
        } else {
            if (mMusicController.hasData()) {
                createNotification();
            }
        }
        return START_NOT_STICKY;
    }

    private void handleAction(int userAction) {
        switch (userAction) {
            case ACTION_CLOSE:
                stopSelf();
                stopForeground(true);
                mMusicController.stopMusicAndClear();
                break;
            case ACTION_PLAY_PLAY:
                if (mMusicController.isPlaying()) {
                    mMusicController.pause();
                } else {
                    mMusicController.resume();
                    if (!mMusicController.isPlaying()) {
                        mMusicController.resume();
                    }
                }
                updateNotification();
                break;
            case ACTION_PLAY_NEXT:
                mMusicController.playNextSong();
                updateNotification();
                break;
            case ACTION_PLAY_PREV:
                mMusicController.playPreviousSong();
                updateNotification();
                break;
            case ACTION_MISS_CLICK:
                break;
        }
    }

    private PendingIntent getPendingIntent(int action) {
        Intent intent = new Intent(this, SongService.class);
        intent.putExtra("user_action", action);
        return PendingIntent.getService(getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMusicController.removeObserver(this);
    }

    public void createNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_player);
//        remoteViews.setProgressBar(R.id.timer, MusicPlayer.getInstance().getTotalTime(), MusicPlayer.getInstance().getCurrentTime(), false);
        if (mMusicController.isPlaying()) {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        }
        remoteViews.setTextViewText(R.id.tv_song_name, mMusicController.getCurrentSong().getTitle());
        remoteViews.setTextViewText(R.id.tv_song_ft, mMusicController.getCurrentSong().getArtist());
//        remoteViews.setImageViewResource(R.id.song_image_view, song.getSongImage());
        remoteViews.setOnClickPendingIntent(R.id.btn_play_prev, getPendingIntent(ACTION_PLAY_PREV));
        remoteViews.setOnClickPendingIntent(R.id.btn_close, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_play, getPendingIntent(ACTION_PLAY_PLAY));
        remoteViews.setOnClickPendingIntent(R.id.btn_play_next, getPendingIntent(ACTION_PLAY_NEXT));
        Intent intentActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(getApplication(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)
                .setCustomBigContentView(remoteViews)
                .build();
        startForeground(1, notification);
    }

    public void updateNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_player);
//        remoteViews.setProgressBar(R.id.timer, MusicPlayer.getInstance().getTotalTime(), MusicPlayer.getInstance().getCurrentTime(), false);
        if (mMusicController.isPlaying()) {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        }
        remoteViews.setTextViewText(R.id.tv_song_name, mMusicController.getCurrentSong().getTitle());
        remoteViews.setTextViewText(R.id.tv_song_ft, mMusicController.getCurrentSong().getArtist());
//        remoteViews.setImageViewResource(R.id.song_image_view, song.getSongImage());
        remoteViews.setOnClickPendingIntent(R.id.btn_play_prev, getPendingIntent(ACTION_PLAY_PREV));
        remoteViews.setOnClickPendingIntent(R.id.btn_close, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_play, getPendingIntent(ACTION_PLAY_PLAY));
        remoteViews.setOnClickPendingIntent(R.id.btn_play_next, getPendingIntent(ACTION_PLAY_NEXT));
        Intent intentActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(getApplication(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)
                .setCustomBigContentView(remoteViews)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onSongUpdate() {
        updateNotification();
    }
}
