package com.tuanna21.mockproject_tuanna21.service;

import static com.tuanna21.mockproject_tuanna21.app.ApplicationController.NOTIFICATION_CHANNEL_ID;
import static com.tuanna21.mockproject_tuanna21.utils.Constants.SharedPref.IS_ALIVE;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;
import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivityVersion2;
import com.tuanna21.mockproject_tuanna21.utils.SharedPreferencesUtils;

public class SongService extends Service implements SongObserver {
    private static final int ACTION_PLAY_PREV = 10;
    private static final int ACTION_PLAY_PLAY = 11;
    private static final int ACTION_PLAY_NEXT = 12;
    private static final int ACTION_CLOSE = 13;
    private static final int ACTION_MISS_CLICK = 14;
    private static final String TAG = "SongService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyPlayerController.getInstance().addObserver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //todo đánh dấu service vẫn đang sống để lấy lại dữ liệu khi mở lại app từ service
        SharedPreferencesUtils.getInstance(getApplicationContext()).saveBoolean(IS_ALIVE, true);
        if (intent != null && intent.hasExtra("user_action")) {
            handleAction(intent.getIntExtra("user_action", ACTION_MISS_CLICK));
        } else {
            createNotification();
        }
        return START_NOT_STICKY;
    }

    private void handleAction(int userAction) {
        switch (userAction) {
            case ACTION_CLOSE:
                stopSelf();
                stopForeground(true);
                break;
            case ACTION_PLAY_PLAY:
                if (MyPlayerController.getInstance().isPlaying()) {
                    MyPlayerController.getInstance().pause();
                } else {
                    MyPlayerController.getInstance().resume();
                    if (!MyPlayerController.getInstance().isPlaying()) {
                        MyPlayerController.getInstance().resume();
                    }
                }
                updateNotification();
                break;
            case ACTION_PLAY_NEXT:
                MyPlayerController.getInstance().playNextSong();
                updateNotification();
                break;
            case ACTION_PLAY_PREV:
                MyPlayerController.getInstance().playPreviousSong();
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
        MyPlayerController.getInstance().removeObserver(this);
        MyPlayerController.getInstance().stopMusicAndClear();
        //todo đánh dấu app đã chết hẳn
        SharedPreferencesUtils.getInstance(getApplicationContext()).saveBoolean(IS_ALIVE, false);
    }

    public void createNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_player);
        if (MyPlayerController.getInstance().isPlaying()) {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        }
        remoteViews.setTextViewText(R.id.tv_song_name, MyPlayerController.getInstance().getCurrentSong().getTitle());
        remoteViews.setTextViewText(R.id.tv_song_ft, MyPlayerController.getInstance().getCurrentSong().getArtist());
        remoteViews.setOnClickPendingIntent(R.id.btn_play_prev, getPendingIntent(ACTION_PLAY_PREV));
        remoteViews.setOnClickPendingIntent(R.id.btn_close, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_play, getPendingIntent(ACTION_PLAY_PLAY));
        remoteViews.setOnClickPendingIntent(R.id.btn_play_next, getPendingIntent(ACTION_PLAY_NEXT));
        Intent intentActivity = new Intent(this, MainActivityVersion2.class);
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
        if (MyPlayerController.getInstance().isPlaying()) {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.btn_play, R.drawable.ic_play);
        }
        remoteViews.setTextViewText(R.id.tv_song_name, MyPlayerController.getInstance().getCurrentSong().getTitle());
        remoteViews.setTextViewText(R.id.tv_song_ft, MyPlayerController.getInstance().getCurrentSong().getArtist());
        remoteViews.setOnClickPendingIntent(R.id.btn_play_prev, getPendingIntent(ACTION_PLAY_PREV));
        remoteViews.setOnClickPendingIntent(R.id.btn_close, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_play, getPendingIntent(ACTION_PLAY_PLAY));
        remoteViews.setOnClickPendingIntent(R.id.btn_play_next, getPendingIntent(ACTION_PLAY_NEXT));
        Intent intentActivity = new Intent(this, MainActivityVersion2.class);
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
        Log.i(TAG, "onSongUpdate: from UI");
    }
}
