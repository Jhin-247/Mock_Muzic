package com.tuanna21.mockproject_tuanna21.db.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tuanna21.mockproject_tuanna21.db.dao.SongDao;
import com.tuanna21.mockproject_tuanna21.db.model.Song;

@Database(entities = {Song.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
    private static SongDatabase sInstance;

    public static synchronized SongDatabase getInstance(Application application) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                            application.getApplicationContext(),
                            SongDatabase.class,
                            "Song_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }

    public abstract SongDao songDao();

}
