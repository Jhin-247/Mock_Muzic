package com.tuanna21.mockproject_tuanna21.data.model;

public class Artist {
    private String mArtistName;
    private int mAlbumNumber;
    private String mThumbnail;
    private int mDefaultThumbnail;
    private int mSongNumber;

    public Artist(String mArtistName, int mAlbumNumber, String mThumbnail, int mDefaultThumbnail, int mSongNumber) {
        this.mArtistName = mArtistName;
        this.mAlbumNumber = mAlbumNumber;
        this.mThumbnail = mThumbnail;
        this.mDefaultThumbnail = mDefaultThumbnail;
        this.mSongNumber = mSongNumber;
    }

    public int getSongNumber() {
        return mSongNumber;
    }

    public void setSongNumber(int mSongNumber) {
        this.mSongNumber = mSongNumber;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public int getAlbumNumber() {
        return mAlbumNumber;
    }

    public void setAlbumNumber(int mAlbumNumber) {
        this.mAlbumNumber = mAlbumNumber;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public int getDefaultThumbnail() {
        return mDefaultThumbnail;
    }

    public void setDefaultThumbnail(int mDefaultThumbnail) {
        this.mDefaultThumbnail = mDefaultThumbnail;
    }
}
