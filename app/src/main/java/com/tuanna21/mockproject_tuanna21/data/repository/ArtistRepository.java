package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.Context;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.data.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {
    private static ArtistRepository sInstance;


    private ArtistRepository() {
    }

    public static synchronized ArtistRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ArtistRepository();
        }
        return sInstance;
    }

    public List<Artist> getFakeArtists(Context context) {
        List<Artist> mArtist = new ArrayList<>();
        mArtist.add(new Artist(
                context.getString(R.string.beyonce),
                4,
                "",
                R.drawable.ic_beyonce,
                38
        ));
        mArtist.add(new Artist(
                context.getString(R.string.bebe_rexha),
                2,
                "",
                R.drawable.ic_bebe_rexha,
                17
        ));
        mArtist.add(new Artist(
                context.getString(R.string.maroon_5),
                5,
                "",
                R.drawable.ic_marron_5,
                46
        ));
        mArtist.add(new Artist(
                context.getString(R.string.michael_jackson),
                10,
                "",
                R.drawable.ic_michael_jackson,
                112
        ));
        mArtist.add(new Artist(
                context.getString(R.string.queen),
                3,
                "",
                R.drawable.ic_queen,
                32
        ));
        mArtist.add(new Artist(
                context.getString(R.string.yani),
                1,
                "",
                R.drawable.ic_yani,
                13
        ));

        return mArtist;
    }
}
