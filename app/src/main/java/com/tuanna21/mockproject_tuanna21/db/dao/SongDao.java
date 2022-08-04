package com.tuanna21.mockproject_tuanna21.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void insertSong(Song song);

    @Delete
    void deleteSong(Song song);

    @Query("SELECT * FROM table_song")
    LiveData<List<Song>> getAllSongs();

    @Update
    void updateSong(Song song);

}
