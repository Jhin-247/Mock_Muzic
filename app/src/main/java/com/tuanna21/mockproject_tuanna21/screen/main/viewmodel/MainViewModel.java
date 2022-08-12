package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.data.repository.Repository;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;
import com.tuanna21.mockproject_tuanna21.utils.SharedPreferencesUtils;

import java.util.List;

public class MainViewModel extends ViewModel implements SongObserver {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private MyPlayerController mPlayerController;
    private MutableLiveData<Song> mCurrentPlayingSong;
    private MutableLiveData<List<Song>> mCurrentPlayingSongList;
    private MutableLiveData<List<NavigationItem>> mSettingItems;
    private MutableLiveData<List<NavigationItem>> mNavigationItems;
    private MutableLiveData<Boolean> mIsPlaying;

    private MutableLiveData<BottomPlayBarStatus> mBottomStatus;

    private boolean mIsCloseByUser;
    private Repository mAppRepository;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private int mLastId;

    public MainViewModel() {
        initVariables();
    }

    private void initVariables() {
        mAppRepository = Repository.getInstance();
        mPlayerController = MyPlayerController.getInstance();
        mCurrentPlayingSong = new MutableLiveData<>();
        mCurrentPlayingSongList = new MutableLiveData<>();
        mSettingItems = new MutableLiveData<>();
        mNavigationItems = new MutableLiveData<>();
        mIsPlaying = new MutableLiveData<>();
        mBottomStatus = new MutableLiveData<>(BottomPlayBarStatus.HIDE);

        mHandlerThread = new HandlerThread("main_view_model");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mIsCloseByUser = false;
    }

    // Todo gọi để kiểm tra liệu App được mở mới hay mở lại app thông qua service (kill app -> mở lại từ service)
    public boolean checkIsLived(Context context) {
        return SharedPreferencesUtils.getInstance(context).isServiceAlive();
    }

    public void setupReopenAppDataWhilePlaying() {
        //TODO lấy lại dữ liệu thông qua controller
        mCurrentPlayingSong.setValue(mPlayerController.getCurrentSong());
        mCurrentPlayingSongList.setValue(mPlayerController.getCurrentSongs());
        if (mPlayerController.isPlaying()) {
            mBottomStatus.setValue(BottomPlayBarStatus.SHOW_AND_PLAY);
            mIsPlaying.setValue(true);
        } else {
            mBottomStatus.setValue(BottomPlayBarStatus.SHOW_AND_PAUSE);
            mIsPlaying.setValue(false);
        }
    }

    public void loadDataAndSetupMusicObserver(Context context) {
        mPlayerController.addObserver(this);
        loadNavigationItems(context);
        loadSettingItems(context);
    }

    private void loadNavigationItems(Context context) {
        mAppRepository.loadNavigationData(
                context.getApplicationContext(),
                new Callback<List<NavigationItem>>() {
                    @Override
                    public void success(List<NavigationItem> data) {
                        mNavigationItems.postValue(data);
                    }

                    @Override
                    public void error(Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    private void loadSettingItems(Context context) {
        mAppRepository.loadSettingScreenData(
                context.getApplicationContext(),
                new Callback<List<NavigationItem>>() {
                    @Override
                    public void success(List<NavigationItem> data) {
                        mSettingItems.postValue(data);
                    }

                    @Override
                    public void error(Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    public void loadSong(Context context) {
        mAppRepository.loadSong(
                context,
                new Callback<List<Song>>() {
                    @Override
                    public void success(List<Song> data) {
                        mCurrentPlayingSongList.postValue(data);
                        mPlayerController.setCurrentSongs(data);
                    }

                    @Override
                    public void error(Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    // override
    @Override
    public void onSongUpdate() {
        mCurrentPlayingSong.setValue(mPlayerController.getCurrentSong());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPlayerController.removeObserver(this);
        mHandlerThread.quitSafely();
    }

    // service
    public boolean canStartService(Context context) {
        return !SharedPreferencesUtils.getInstance(context).isServiceAlive();
    }

    // bottom navigation
    public void changeCurrentTabId(int id) {
        mHandler.postDelayed(() -> {
            if (id == R.id.songPlayingFragment) {
                mBottomStatus.postValue(BottomPlayBarStatus.HIDE);
            } else {
                mLastId = id;
                if (mIsCloseByUser) {
                    mBottomStatus.postValue(BottomPlayBarStatus.HIDE);
                } else if (mPlayerController.isPlaying()) {
                    mBottomStatus.postValue(BottomPlayBarStatus.SHOW_AND_PLAY);
                } else if (mPlayerController.hasData()) {
                    mBottomStatus.postValue(BottomPlayBarStatus.SHOW_AND_PAUSE);
                } else {
                    mBottomStatus.postValue(BottomPlayBarStatus.HIDE);
                }
            }
        }, 100);
    }

    // bottom play
    public void closeBottomPlay() {
        mBottomStatus.setValue(BottomPlayBarStatus.HIDE);
        mIsCloseByUser = true;
    }

    //music control
    public void playSong(Song song) {
        int mSongIndex = mPlayerController.getCurrentSongs().indexOf(song);
        if (mSongIndex != mPlayerController.getCurrentSongIndex()) {
            mPlayerController.playFromIndex(mSongIndex);
            mIsPlaying.setValue(true);
            mIsCloseByUser = false;
        }
    }

    public int getCurrentSongTime() {
        return mPlayerController.getCurrentSongTimePosition();
    }

    public int getCurrentSongTotalDuration() {
        return Integer.parseInt(mPlayerController.getCurrentSong().getDuration());
    }

    public void seekTo(int progress) {
        mPlayerController.seekTo(progress);
    }

    public String getFormatCurrentSongTime(int time) {
        String mResult;
        int mTime = time / 1000;
        int mSecond = mTime % 60;
        if (mSecond < 10) {
            mResult = ":0" + mSecond;
        } else {
            mResult = ":" + mSecond;
        }
        mResult = mTime / 60 + mResult;
        return mResult;
    }

    public void playNextSong() {
        mPlayerController.playNextSong();
        mIsPlaying.setValue(true);
        mIsCloseByUser = false;
    }

    public void playPreviousSong() {
        mPlayerController.playPreviousSong();
        mIsPlaying.setValue(true);
        mIsCloseByUser = false;
    }

    public void playOrPause() {
        if (mPlayerController.isPlaying()) {
            mPlayerController.pause();
            mIsPlaying.setValue(false);
        } else {
            try {
                mPlayerController.resume();
                mIsPlaying.setValue(true);
            } catch (Exception exception) {
                mPlayerController.playFromIndex(mPlayerController.getCurrentSongIndex());
            }
        }
    }


    //getter
    public LiveData<Song> getCurrentPlayingSong() {
        return mCurrentPlayingSong;
    }

    public LiveData<List<Song>> getCurrentPlayingSongList() {
        return mCurrentPlayingSongList;
    }

    public LiveData<List<NavigationItem>> getSettingItems() {
        return mSettingItems;
    }

    public LiveData<List<NavigationItem>> getNavigationItems() {
        return mNavigationItems;
    }

    public LiveData<BottomPlayBarStatus> getBottomStatus() {
        return mBottomStatus;
    }

    public LiveData<Boolean> getSongPlayingStatus() {
        return mIsPlaying;
    }

    public int getLastTabId(){
        return mLastId;
    }

}
