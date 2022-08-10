package com.tuanna21.mockproject_tuanna21.player;

import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.listener.SongCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlayerController implements SongCompleteListener {
    private static MyPlayerController sInstance;
    private final MyPlayer mPlayer;
    private List<Song> mCurrentSongs;
    private boolean mCanPlayNext, mCanPlayPrevious;
    private int mCurrentSongIndex = -1;

    private MyPlayerController() {
        mPlayer = MyPlayer.getInstance(this);
        mCanPlayNext = false;
        mCanPlayPrevious = false;
        mCurrentSongs = new ArrayList<>();
    }

    public static synchronized MyPlayerController getInstance() {
        if (sInstance == null) {
            sInstance = new MyPlayerController();
        }
        return sInstance;
    }

    public List<Song> getCurrentSongs() {
        return mCurrentSongs;
    }

    public void setCurrentSongs(List<Song> mCurrentSongs) {
        this.mCurrentSongs = mCurrentSongs;
    }

    public boolean hasData() {
        return (mCurrentSongIndex != -1);
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void addObserver(SongObserver observer) {
        mPlayer.addObserver(observer);
    }

    public void removeObserver(SongObserver observer) {
        mPlayer.removeObserver(observer);
    }

    public void setSongsToPlay(List<Song> songs) {
        this.mCurrentSongs = songs;
        mCurrentSongIndex = 0;
        setCanPlayStatus(false, mCurrentSongIndex != (mCurrentSongs.size() - 1));
    }

    public void playFromIndex(int index) {
        if(index != mCurrentSongIndex) {
            this.mCurrentSongIndex = index;
            mPlayer.play(mCurrentSongs.get(mCurrentSongIndex));
            setCanPlayStatus(mCurrentSongIndex != 0, mCurrentSongIndex != (mCurrentSongs.size() - 1));
        }
    }

    public void setSongsAndPlayFromIndex(List<Song> songs, int index) {
        setSongsToPlay(songs);
        playFromIndex(index);
    }

    private void setCanPlayStatus(boolean canPlayPrevious, boolean canPlayNext) {
        this.mCanPlayNext = canPlayNext;
        this.mCanPlayPrevious = canPlayPrevious;
    }

    public void playNextSong() {
        if (mCanPlayNext) {
            mCurrentSongIndex++;
            mPlayer.play(mCurrentSongs.get(mCurrentSongIndex));
            setCanPlayStatus(true, mCurrentSongIndex != (mCurrentSongs.size() - 1));
        }
    }

    private void nextSong() {
        if (mCanPlayNext) {
            mCurrentSongIndex++;
            stopMusicAndClear();
            mPlayer.play(mCurrentSongs.get(mCurrentSongIndex));
            setCanPlayStatus(true, mCurrentSongIndex != (mCurrentSongs.size() - 1));
        }
    }

    public void playPreviousSong() {
        if (mCanPlayPrevious) {
            mCurrentSongIndex--;
            mPlayer.play(mCurrentSongs.get(mCurrentSongIndex));
            setCanPlayStatus(mCurrentSongIndex != 0, true);
        }
    }

    public Song getCurrentSong() {
        return mCurrentSongs.get(mCurrentSongIndex);
    }

    public int getCurrentSongTimePosition() {
        return mPlayer.getCurrentSongTimePosition();
    }

    public void pause() {
        mPlayer.pause();
    }

    public void stopMusicAndClear() {
        mPlayer.stopMusicAndRelease();
    }

    public void resume() {
        mPlayer.resume();
    }

    @Override
    public void onSongComplete() {
        if (mCanPlayNext && (getCurrentSongTimePosition() > Integer.parseInt(mCurrentSongs.get(mCurrentSongIndex).getDuration()) / 2)) {
            nextSong();
        }
    }

    public void seekTo(int progress) {
        mPlayer.seekTo(progress);
    }
}
