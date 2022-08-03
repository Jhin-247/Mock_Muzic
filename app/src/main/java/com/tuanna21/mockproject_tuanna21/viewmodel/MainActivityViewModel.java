package com.tuanna21.mockproject_tuanna21.viewmodel;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.tuanna21.mockproject_tuanna21.utils.Constants.SharedPref.HAS_DATABASE;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.db.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.db.model.Song;
import com.tuanna21.mockproject_tuanna21.db.repository.SongRepository;
import com.tuanna21.mockproject_tuanna21.utils.SharedPreferencesUtils;
import com.tuanna21.mockproject_tuanna21.utils.SongUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<List<Song>> mSongs;
    private int mScreenHeight;
    private int mScreenWidth;
    private final SongRepository mSongRepository;


    public MainActivityViewModel(Application application) {
        super(application);
        mSongs = new MutableLiveData<>();
        mSongRepository = new SongRepository(application);
        mSongs = mSongRepository.getSongs();
    }

    public LiveData<List<Song>> getSongs() {
        return mSongs;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.mScreenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.mScreenWidth = screenWidth;
    }

    public boolean checkPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context.getApplicationContext(),
                Manifest.permission.READ_CONTACTS
        ) == PERMISSION_GRANTED;
    }

    public boolean isFirstTimeInit(Context context) {
        return !SharedPreferencesUtils.getInstance(context.getApplicationContext()).hasDatabaseData();
    }

    public void requestSyncSongData(Context context) {
        List<Song> songList = SongUtils.getAllSongFromDevice(context);
        for (Song song : songList) {
            mSongRepository.insertSong(song);
        }
        SharedPreferencesUtils.getInstance(context).saveBoolean(HAS_DATABASE, true);
    }

    public List<NavigationItem> getNavigationItems(){
        List<NavigationItem> navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.themes),R.drawable.ic_theme));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.ringtone_cutter),R.drawable.ic_rington_cutter));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.sleep_timer),R.drawable.ic_sleep_timer));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.equaliser),R.drawable.ic_equaliser));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.driver_mode),R.drawable.ic_driver_mode));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.hidden_folder),R.drawable.ic_hidden_folder));
        navigationItems.add(new NavigationItem(getApplication().getApplicationContext().getString(R.string.scan_media),R.drawable.ic_logo_splash_screen));

        return navigationItems;
    }
}
