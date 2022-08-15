package com.tuanna21.mockproject_tuanna21.screen.main.viewmodel;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.Callback;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.data.model.Artist;
import com.tuanna21.mockproject_tuanna21.data.model.Genres;
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
    private MutableLiveData<List<Genres>> mListGenres;
    private MutableLiveData<List<Album>> mListAlbums;
    private MutableLiveData<List<Artist>> mArtists;
    private MutableLiveData<BottomPlayBarStatus> mBottomStatus;
    private MutableLiveData<Album> mAlbumDetail;

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
        mListGenres = new MutableLiveData<>();
        mListAlbums = new MutableLiveData<>();
        mAlbumDetail = new MutableLiveData<>();
        mArtists = new MutableLiveData<>();

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

    public void loadDataWithoutPermission(Context context) {
        mPlayerController.addObserver(this);
        loadNavigationItems(context);
        loadSettingItems(context);
        loadGenres(context);
        loadArtist(context);
    }

    public void loadData(Context context) {
        loadSong(context);
        loadAlbum(context);
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

    private void loadGenres(Context context) {
        mAppRepository.loadGenres(
                context.getApplicationContext(),
                new Callback<List<Genres>>() {
                    @Override
                    public void success(List<Genres> data) {
                        mListGenres.postValue(data);
                    }

                    @Override
                    public void error(Exception exception) {
                        exception.printStackTrace();
                    }
                });
    }

    private void loadArtist(Context context) {
        mAppRepository.loadArtists(context, new Callback<List<Artist>>() {
            @Override
            public void success(List<Artist> data) {
                mArtists.postValue(data);
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void loadSong(Context context) {
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

    private void loadAlbum(Context context) {
        mAppRepository.loadAlbums(
                context,
                new Callback<List<Album>>() {
                    @Override
                    public void success(List<Album> data) {
                        mListAlbums.postValue(data);
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
    public void onCloseServiceSong() {
        mIsPlaying.setValue(false);
        mBottomStatus.setValue(BottomPlayBarStatus.HIDE);
        Log.i(TAG, "onCloseServiceSong: " + mBottomStatus.getValue());
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
            } else if (id == R.id.albumDetailFragment) {
                if (mIsCloseByUser) {
                    mBottomStatus.postValue(BottomPlayBarStatus.HIDE);
                } else if (mPlayerController.isPlaying()) {
                    mBottomStatus.postValue(BottomPlayBarStatus.SHOW_AND_PLAY);
                } else if (mPlayerController.hasData()) {
                    mBottomStatus.postValue(BottomPlayBarStatus.SHOW_AND_PAUSE);
                } else {
                    mBottomStatus.postValue(BottomPlayBarStatus.HIDE);
                }
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
        if (mPlayerController.getCurrentSong() == null) {
            return 0;
        }
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

    //album
    public void setAlbumToShowDetail(Album album) {
        mAlbumDetail.setValue(album);
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

    public LiveData<List<Genres>> getGenresList() {
        return mListGenres;
    }

    public LiveData<List<Album>> getAlbumList() {
        return mListAlbums;
    }

    public LiveData<Album> getAlbumDetail() {
        return mAlbumDetail;
    }

    public LiveData<List<Artist>> getArtists() {
        return mArtists;
    }

    public int getLastTabId() {
        return mLastId;
    }

}
