package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.data.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumRepository {
    private static AlbumRepository sInstance;


    private AlbumRepository() {

    }

    public static synchronized AlbumRepository getInstance() {
        if (sInstance == null) {
            sInstance = new AlbumRepository();
        }
        return sInstance;
    }

    public List<Album> getAlbums(Context context) {
        List<Album> mAlbums = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS
        };
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Album album = new Album();
                album.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)));
                album.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)));
                album.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)));
                album.setAlbumArt(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)));
                album.setSongNumber(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
                mAlbums.add(album);
            }
            cursor.close();
        }
        return mAlbums;
    }

    public List<Album> getFakesAlbums(Context context) {
        List<Album> mAlbums = new ArrayList<>();
        mAlbums.add(new Album(
                1,
                context.getString(R.string.history),
                context.getString(R.string.michael_jackson),
                10,
                R.drawable.album_1
        ));
        mAlbums.add(new Album(
                1,
                context.getString(R.string.history),
                context.getString(R.string.michael_jackson),
                10,
                R.drawable.album_2
        ));
        return mAlbums;
    }
}
