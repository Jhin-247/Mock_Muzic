package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {
    //    private final SongRepository mSongRepository;
    private final MutableLiveData<List<Song>> mSongs;

    public MainActivityViewModel(Application application) {
        super(application);
        mSongs = new MutableLiveData<>();
//        mSongRepository = new SongRepository(application);
//        mSongs = mSongRepository.getSongs();
    }

    public LiveData<List<Song>> getSongs() {
        return mSongs;
    }


    public List<NavigationItem> getNavigationItems() {
        List<NavigationItem> navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.themes), R.drawable.ic_theme));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.ringtone_cutter), R.drawable.ic_rington_cutter));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.sleep_timer), R.drawable.ic_sleep_timer));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.equaliser), R.drawable.ic_equaliser));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.driver_mode), R.drawable.ic_driver_mode));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.hidden_folder), R.drawable.ic_hidden_folder));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.scan_media), R.drawable.ic_scan));

        return navigationItems;
    }

    public List<NavigationItem> getSettingItems() {
        List<NavigationItem> navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.display), R.drawable.ic_display));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.audio), R.drawable.ic_audio));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.headset), R.drawable.ic_headset));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.lock_screen), R.drawable.ic_lock_screen));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.advanced), R.drawable.ic_advanced));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.other), R.drawable.ic_other));

        return navigationItems;
    }

    public void setSongCursor(Cursor cursor) {
        List<Song> songList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            long time = System.currentTimeMillis();
            while (cursor.moveToNext()) {
                Uri imageUri = Uri.parse("content://media/external/audio/albumart");
                Uri imagePathUri = ContentUris.withAppendedId(imageUri, cursor.getLong(5));

                songList.add(new Song(
                        cursor.getInt(0),
                        cursor.getString(1),
                        String.valueOf(cursor.getLong(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        imagePathUri.toString(),
                        cursor.getLong(5),
                        cursor.getString(6),
                        time
                ));
            }
            mSongs.setValue(songList);
        }
    }
}
