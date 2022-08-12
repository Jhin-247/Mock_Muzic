package com.tuanna21.mockproject_tuanna21.data.repository;

import android.content.Context;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.data.model.Genres;

import java.util.ArrayList;
import java.util.List;

public class GenresRepository {
    private static GenresRepository sInstance;


    private GenresRepository() {

    }

    public static synchronized GenresRepository getInstance() {
        if (sInstance == null) {
            sInstance = new GenresRepository();
        }
        return sInstance;
    }

    public List<Genres> getSongGenres(Context context) {
        List<Genres> mGenres = new ArrayList<>();
        mGenres.add(new Genres(context.getString(R.string.genres_classical), 56, R.drawable.ic_classical));
        mGenres.add(new Genres(context.getString(R.string.genres_pop), 56, R.drawable.ic_pop));
        mGenres.add(new Genres(context.getString(R.string.genres_hip_hop), 56, R.drawable.ic_hip_hop));
        mGenres.add(new Genres(context.getString(R.string.genres_rock), 56, R.drawable.ic_rock));
        mGenres.add(new Genres(context.getString(R.string.genres_soul_and_r_and_b), 56, R.drawable.ic_soul));
        mGenres.add(new Genres(context.getString(R.string.genres_instrumental), 56, R.drawable.ic_instrumental));
        mGenres.add(new Genres(context.getString(R.string.genres_jazz), 56, R.drawable.ic_jazz));
        mGenres.add(new Genres(context.getString(R.string.genres_country_music), 56, R.drawable.ic_country_music));
        return mGenres;
    }
}
