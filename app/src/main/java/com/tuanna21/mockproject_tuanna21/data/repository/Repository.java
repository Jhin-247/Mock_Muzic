package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository sInstance;
    private final SongRepository mSongRepository;

    private final Handler mHandler;

    private Repository(Context context) {
        mSongRepository = SongRepository.getInstance();
        HandlerThread mHandlerThread = new HandlerThread("repository_thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

    }

    public static synchronized Repository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Repository(context);
        }
        return sInstance;
    }

    public void loadSong(Context context, Callback<List<Song>> callback) {
        mHandler.post(() -> {
            try {
                List<Song> mSongList = mSongRepository.loadSong(context);
                callback.success(mSongList);
            } catch (Exception e) {
                callback.error(e);
            }

        });
    }

    public SongRepository getSongRepository() {
        return mSongRepository;
    }

    public void loadSettingScreenData(Context context, Callback<List<NavigationItem>> callback) {
        mHandler.post(() -> {
            try {
                List<NavigationItem> navigationItems = new ArrayList<>();
                navigationItems.add(new NavigationItem(context.getString(R.string.display), R.drawable.ic_display));
                navigationItems.add(new NavigationItem(context.getString(R.string.audio), R.drawable.ic_audio));
                navigationItems.add(new NavigationItem(context.getString(R.string.headset), R.drawable.ic_headset));
                navigationItems.add(new NavigationItem(context.getString(R.string.lock_screen), R.drawable.ic_lock_screen));
                navigationItems.add(new NavigationItem(context.getString(R.string.advanced), R.drawable.ic_advanced));
                navigationItems.add(new NavigationItem(context.getString(R.string.other), R.drawable.ic_other));
                callback.success(navigationItems);
            } catch (Exception e) {
                callback.error(e);
            }

        });
    }

    public void loadNavigationData(Context context, Callback<List<NavigationItem>> callback) {
        mHandler.post(() -> {
            try {
                List<NavigationItem> navigationItems = new ArrayList<>();
                navigationItems.add(new NavigationItem(context.getString(R.string.themes), R.drawable.ic_theme));
                navigationItems.add(new NavigationItem(context.getString(R.string.ringtone_cutter), R.drawable.ic_rington_cutter));
                navigationItems.add(new NavigationItem(context.getString(R.string.sleep_timer), R.drawable.ic_sleep_timer));
                navigationItems.add(new NavigationItem(context.getString(R.string.equaliser), R.drawable.ic_equaliser));
                navigationItems.add(new NavigationItem(context.getString(R.string.driver_mode), R.drawable.ic_driver_mode));
                navigationItems.add(new NavigationItem(context.getString(R.string.hidden_folder), R.drawable.ic_hidden_folder));
                navigationItems.add(new NavigationItem(context.getString(R.string.scan_media), R.drawable.ic_scan));
                callback.success(navigationItems);
            } catch (Exception e) {
                callback.error(e);
            }

        });
    }


}
