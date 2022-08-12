package com.tuanna21.mockproject_tuanna21.player;

import android.media.MediaPlayer;

import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.listener.SongCompleteListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPlayer {
    private static MyPlayer sInstance;
    private final MediaPlayer mPlayer;
    private final List<SongObserver> mObservers;


    private MyPlayer(SongCompleteListener mCompleteListener) {
        mPlayer = new MediaPlayer();
        mObservers = new ArrayList<>();
        mPlayer.setOnCompletionListener(mp -> {
            mCompleteListener.onSongComplete();
        });
    }

    public static synchronized MyPlayer getInstance(SongCompleteListener mCompleteListener) {
        if (sInstance == null) {
            sInstance = new MyPlayer(mCompleteListener);
        }
        return sInstance;
    }

    public void addObserver(SongObserver observer) {
        mObservers.add(observer);
    }

    public void removeObserver(SongObserver observer) {
        mObservers.remove(observer);
    }

    private void notifySong() {
        for (SongObserver observer : mObservers) {
            observer.onSongUpdate();
        }
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void play(Song song) {
        try {
            stopMusicAndRelease();
            mPlayer.setDataSource(song.getPath());
            mPlayer.prepare();
            mPlayer.start();
            notifySong();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            notifySong();
        }
    }

    public void stopMusicAndRelease() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            notifySong();
        }
        mPlayer.reset();
    }

    public void resume() {
        try {
            if (!mPlayer.isPlaying()) {
                mPlayer.start();
                notifySong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentSongTimePosition() {
        return mPlayer.getCurrentPosition();
    }

    public void seekTo(int time) {
        mPlayer.seekTo(time);
    }
}
