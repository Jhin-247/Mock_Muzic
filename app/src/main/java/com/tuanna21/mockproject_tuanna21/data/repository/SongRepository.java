package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private static SongRepository sInstance;

    private SongRepository() {

    }

    public static synchronized SongRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SongRepository();
        }
        return sInstance;
    }

    public List<Song> loadSong(Context context) {
        Uri audioCollectionUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            audioCollectionUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            audioCollectionUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM
        };
        String where = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        Cursor cursor = context.getContentResolver().query(audioCollectionUri, projection, where, null, MediaStore.Audio.Media.TITLE + " ASC");

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
            cursor.close();
        }
        return songList;
    }

}
