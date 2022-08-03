package com.tuanna21.mockproject_tuanna21.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongUtils {

    public static List<Song> getAllSongFromDevice(Context context) {
        List<Song> songList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
        };
        String where = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, where, null, MediaStore.Audio.Media.TITLE + " ASC");
        while (cursor.moveToNext()) {
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String songUri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            Uri imageUri = Uri.parse("content://media/external/audio/albumart");
            Uri imagePathUri = ContentUris.withAppendedId(imageUri, albumId);
            Song song = new Song();
            song.setArtist(artist);
            song.setTitle(title);
            song.setAlbumId(albumId);
            song.setDuration(String.valueOf(duration));
            song.setPath(songUri);
            song.setSongImage(imagePathUri.toString());
            song.setAlbum(album);
            songList.add(song);
        }
        cursor.close();
        return songList;
    }
}
