package com.tuanna21.mockproject_tuanna21.data.model;

public class Album {
    private int id;
    private String name;
    private String artist;
    private String albumArt;
    private int songNumber;

    private int mAlbumImageDefaultResource;

    public int getId() {
        return id;
    }

    public Album() {
    }

    public Album(int id, String name, String artist, String albumArt, int songNumber) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.albumArt = albumArt;
        this.songNumber = songNumber;
    }

    public Album(int id, String name, String artist, int songNumber, int mAlbumImageDefaultResource) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.songNumber = songNumber;
        this.mAlbumImageDefaultResource = mAlbumImageDefaultResource;
        this.albumArt = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbumImageDefaultResource() {
        return mAlbumImageDefaultResource;
    }

    public void setAlbumImageDefaultResource(int mAlbumImageDefaultResource) {
        this.mAlbumImageDefaultResource = mAlbumImageDefaultResource;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }
}
