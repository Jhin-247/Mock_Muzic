package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private static SongRepository sInstance;
    private List<Song> mSongs;


    private SongRepository() {
        mSongs = new ArrayList<>();
    }

    public static synchronized SongRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SongRepository();
        }
        return sInstance;
    }

    public void saveSongs(Cursor cursor) {
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
            mSongs = songList;
        }
    }

    public void saveSongs(List<Song> songs) {
        this.mSongs = songs;
    }

    public List<Song> getSong() {
        return mSongs;
    }

}
