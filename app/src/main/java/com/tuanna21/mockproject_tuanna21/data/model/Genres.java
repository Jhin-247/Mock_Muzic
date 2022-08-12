package com.tuanna21.mockproject_tuanna21.data.model;

public class Genres {
    private String mName;
    private int mSongNumber;
    private int mThumbId;

    public Genres(String mName, int mSongNumber, int mThumbId) {
        this.mName = mName;
        this.mSongNumber = mSongNumber;
        this.mThumbId = mThumbId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getSongNumber() {
        return mSongNumber;
    }

    public void setSongNumber(int mSongNumber) {
        this.mSongNumber = mSongNumber;
    }

    public int getThumbId() {
        return mThumbId;
    }

    public void setThumbId(int mThumbId) {
        this.mThumbId = mThumbId;
    }
}
