package com.tuanna21.mockproject_tuanna21.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongUtils {

    private static final String TAG = "SongUtils";

    public static List<Song> getAllSongFromDevice(Context context) {
        Uri audioCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            audioCollection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            audioCollection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }


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
        Cursor cursor = contentResolver.query(audioCollection, null, null, null, MediaStore.Audio.Media.TITLE + " ASC");
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

    //todo testing after
    // https://stackoverflow.com/questions/39051474/how-to-get-all-mp3-files-from-internal-and-external-storage-in-android
    // https://stackoverflow.com/questions/70060700/how-to-access-whatsapp-folder-of-android-11-by-using-mediastore-api
    // https://developer.android.com/training/data-storage/shared/media#direct-file-paths

    public static void getFileListInFolder(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download");

//        File file = context.getApplicationContext().getExternalFilesDir(Environment.MEDIA_SHARED);

        String path = file.getPath();
        Log.d(TAG, "getFileListInFolder:path " + path);
        File directory = null;
        try {
            directory = new File(path);
            Log.i(TAG, "dir = " + directory);
        } catch (Exception e) {
            Log.i(TAG, "Uri e = " + e);
        }
        File[] files = directory.listFiles();
        Log.i(TAG, "getFileListInFolder:files " + files);
        for (File f : files) {
            Log.d(TAG, "getFileListInFolder: " + f.getName());
        }
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Log.i(TAG, "FileName:" + files[i].getName());
            }
        }
    }
}
