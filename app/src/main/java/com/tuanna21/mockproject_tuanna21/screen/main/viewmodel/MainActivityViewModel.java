package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuanna21.mockproject_tuanna21.base.BaseViewModel;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends BaseViewModel implements SongObserver {
    private MyPlayerController mPlayerController;
    private MutableLiveData<List<Song>> mSongs;
    private MutableLiveData<List<NavigationItem>> mSettingItems;
    private MutableLiveData<List<NavigationItem>> mNavigationItems;
    private MutableLiveData<Song> mCurrentSong;
    private MutableLiveData<BottomPlayBarStatus> mBottomPlayStatusBar;

    public MainActivityViewModel(Application application) {
        super(application);
    }

    @Override
    protected void initData() {
        mSongs = new MutableLiveData<>();
        mSettingItems = new MutableLiveData<>();
        mNavigationItems = new MutableLiveData<>();
        mCurrentSong = new MutableLiveData<>();
        mBottomPlayStatusBar = new MutableLiveData<>(BottomPlayBarStatus.HIDE);
        mPlayerController = MyPlayerController.getInstance();
        mPlayerController.addObserver(MainActivityViewModel.this);
    }


    @Override
    protected void loadData() {
        loadSettingData();
        loadNavigationData();
    }

    public LiveData<Song> getCurrentSong() {
        return mCurrentSong;
    }

    private void loadSettingData() {
        mRepository.loadSettingScreenData(getApplication(), new Callback<List<NavigationItem>>() {
            @Override
            public void success(List<NavigationItem> data) {
                mSettingItems.postValue(data);
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

    public int getCurrentSongTime(){
        return mPlayerController.getCurrentSongTimePosition();
    }

    public LiveData<List<NavigationItem>> getSettingItems() {
        return mSettingItems;
    }

    public LiveData<List<NavigationItem>> getNavigationItems() {
        return mNavigationItems;
    }

    public LiveData<BottomPlayBarStatus> getBottomStatus() {
        return mBottomPlayStatusBar;
    }

    public void setSongCursor(Cursor cursor) {
        List<Song> songList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            long time = System.currentTimeMillis();
            int i = 0;
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
            if (mPlayerController.isPlaying()) {
                mSongs.setValue(mPlayerController.getCurrentSongs());
                mCurrentSong.setValue(mPlayerController.getCurrentSong());
                mBottomPlayStatusBar.setValue(BottomPlayBarStatus.SHOW_AND_PLAY);
            } else if (!mPlayerController.isPlaying() && mPlayerController.hasData()) {
                mSongs.setValue(mPlayerController.getCurrentSongs());
                mCurrentSong.setValue(mPlayerController.getCurrentSong());
                mBottomPlayStatusBar.setValue(BottomPlayBarStatus.SHOW_AND_PAUSE);
            } else {
                mSongs.setValue(songList);
                mPlayerController.setSongsToPlay(songList);
                mCurrentSong.setValue(mPlayerController.getCurrentSong());
                mBottomPlayStatusBar.setValue(BottomPlayBarStatus.HIDE);
            }
        }
    }

    public void playSong(Song song) {
        if (getSongs().getValue() != null) {
            mPlayerController.playFromIndex(getSongs().getValue().indexOf(song));
            mCurrentSong.setValue(song);
            setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        }
    }

    public void playPreviousSong() {
        mPlayerController.playPreviousSong();
    }

    public void playNextSong() {
        mPlayerController.playNextSong();
    }

    public void playOrPause() {
        if (mPlayerController.isPlaying()) {
            mPlayerController.pause();
            setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PAUSE);
        } else {
            mPlayerController.resume();
            setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        }
    }

    public void setBottomPlayStatus(BottomPlayBarStatus status) {
        mBottomPlayStatusBar.setValue(status);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPlayerController.removeObserver(MainActivityViewModel.this);
    }

    @Override
    public void onSongUpdate() {
        mCurrentSong.setValue(mPlayerController.getCurrentSong());
        if (mPlayerController.isPlaying()) {
            mBottomPlayStatusBar.setValue(BottomPlayBarStatus.SHOW_AND_PLAY);
        } else if (!mPlayerController.isPlaying() && mPlayerController.hasData()) {
            mBottomPlayStatusBar.setValue(BottomPlayBarStatus.SHOW_AND_PAUSE);
        } else {
            mBottomPlayStatusBar.setValue(BottomPlayBarStatus.HIDE);
        }
    }

    public void seekTo(int progress) {
        mPlayerController.seekTo(progress);
    }
}
