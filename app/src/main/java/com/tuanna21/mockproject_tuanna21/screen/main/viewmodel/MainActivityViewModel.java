package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuanna21.mockproject_tuanna21.base.BaseViewModel;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;

import java.util.List;


public class MainActivityViewModel extends BaseViewModel implements SongObserver {
    private final String TAG = MainActivityViewModel.class.getSimpleName();
    private MyPlayerController mPlayerController;
    private MutableLiveData<List<Song>> mSongs;
    private MutableLiveData<List<NavigationItem>> mSettingItems;
    private MutableLiveData<List<NavigationItem>> mNavigationItems;
    private MutableLiveData<Song> mCurrentSong;
    private MutableLiveData<BottomPlayBarStatus> mBottomPlayStatusBar;
    private boolean mCanShowBottomPlay;

    public MainActivityViewModel(Application application) {
        super(application);
    }

    @Override
    protected void initData() {
        mCanShowBottomPlay = true;
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

    public void loadSong() {
        mRepository.loadSong(new Callback<List<Song>>() {
            @Override
            public void success(List<Song> data) {
                mSongs.postValue(data);
                mPlayerController.setSongsToPlay(data);
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

    public boolean isPlayingSong() {
        return mPlayerController.isPlaying();
    }

    public int getCurrentSongTime() {
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

    public void playSong(Song song) {
        if (getSongs().getValue() != null) {
            mPlayerController.playFromIndex(getSongs().getValue().indexOf(song));
            mCurrentSong.setValue(song);
            if (mCanShowBottomPlay)
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
            if (mCanShowBottomPlay)
                setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PAUSE);
        } else {
            mPlayerController.resume();
            if (mCanShowBottomPlay)
                setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        }
    }

    public void setCanShowBottomPlay(boolean canShow) {
        this.mCanShowBottomPlay = canShow;
    }

    public void setBottomPlayStatus(BottomPlayBarStatus status) {
        Log.i(TAG, "setBottomPlayStatus: " + status + "---" + mCanShowBottomPlay);
        if (!mCanShowBottomPlay) {
            mBottomPlayStatusBar.setValue(BottomPlayBarStatus.HIDE);
        } else {
            mBottomPlayStatusBar.setValue(status);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPlayerController.removeObserver(MainActivityViewModel.this);
    }

    @Override
    public void onSongUpdate() {
        if (mCurrentSong.getValue() != mPlayerController.getCurrentSong()) {
            mCurrentSong.setValue(mPlayerController.getCurrentSong());
        }
        if (mPlayerController.isPlaying()) {
            setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        } else if (!mPlayerController.isPlaying() && mPlayerController.hasData()) {
            setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PAUSE);
        } else {
            setBottomPlayStatus(BottomPlayBarStatus.HIDE);
        }
    }

    public void seekTo(int progress) {
        mPlayerController.seekTo(progress);
    }
}
