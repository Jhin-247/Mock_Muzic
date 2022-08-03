package com.tuanna21.mockproject_tuanna21.db.repository;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;

import com.tuanna21.mockproject_tuanna21.db.dao.SongDao;
import com.tuanna21.mockproject_tuanna21.db.database.SongDatabase;
import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.List;

public class SongRepository {
    private final SongDao mSongDao;
    private final LiveData<List<Song>> mSongs;
    private final Handler mHandler;


    public SongRepository(Application application) {
        SongDatabase songDatabase = SongDatabase.getInstance(application);
        mSongDao = songDatabase.songDao();
        mSongs = mSongDao.getAllSongs();

        HandlerThread mHandlerThread = new HandlerThread("song_database_thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void insertSong(Song song){
        mHandler.post(() -> mSongDao.insertSong(song));
    }

    public LiveData<List<Song>> getSongs(){
        return mSongs;
    }

}
