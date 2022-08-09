package com.tuanna21.mockproject_tuanna21.data.model;

public class Album {
    private int id;
    private String title;
    private String artist;
    private String albumArt;
    private int numberOfSong;
    private int albumDrawable;

    public int getAlbumDrawable() {
        return albumDrawable;
    }

    public void setAlbumDrawable(int albumDrawable) {
        this.albumDrawable = albumDrawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getNumberOfSong() {
        return numberOfSong;
    }

    public void setNumberOfSong(int numberOfSong) {
        this.numberOfSong = numberOfSong;
    }
}
