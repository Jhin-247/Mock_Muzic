package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuanna21.mockproject_tuanna21.base.BaseViewModel;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends BaseViewModel {
    private MutableLiveData<List<Song>> mSongs;
    private MutableLiveData<List<NavigationItem>> mSettingItems;
    private MutableLiveData<List<NavigationItem>> mNavigationItems;

    public MainActivityViewModel(Application application) {
        super(application);
    }

    @Override
    protected void initData() {
        mSongs = new MutableLiveData<>();
        mSettingItems = new MutableLiveData<>();
        mNavigationItems = new MutableLiveData<>();
    }

    @Override
    protected void loadData() {
        loadSettingData();
        loadNavigationData();
    }

    private void loadSettingData() {
        mRepository.loadSettingScreenData(getApplication(), new Callback<List<NavigationItem>>() {
            @Override
            public void success(List<NavigationItem> data) {
                mSettingItems.postValue(data);
                Log.i(this.getClass().getSimpleName(), "success: " + data.size());
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void loadNavigationData() {
        mRepository.loadNavigationData(getApplication(), new Callback<List<NavigationItem>>() {
            @Override
            public void success(List<NavigationItem> data) {
                mNavigationItems.postValue(data);
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public LiveData<List<Song>> getSongs() {
        return mSongs;
    }

    public LiveData<List<NavigationItem>> getSettingItems() {
        return mSettingItems;
    }

    public LiveData<List<NavigationItem>> getNavigationItems() {
        return mNavigationItems;
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
