package com.tuanna21.mockproject_tuanna21.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String path;
    private String duration;
    private String title;
    private String artist;
    private String songImage;
    private long albumId;
    private String album;

    public Song() {
    }

    public Song(int id, String path, String duration, String title, String artist, String songImage, long albumId, String album, long lastTimePlayed) {
        this.id = id;
        this.path = path;
        this.duration = duration;
        this.title = title;
        this.artist = artist;
        this.songImage = songImage;
        this.albumId = albumId;
        this.album = album;
        this.lastTimePlayed = lastTimePlayed;
    }

    public long getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastTimePlayed(long lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }

    private long lastTimePlayed;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }
}
