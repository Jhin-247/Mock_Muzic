package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.genresfragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Genres;
import com.tuanna21.mockproject_tuanna21.databinding.ItemGenresBinding;

public class GenresAdapter extends BaseAdapter<ItemGenresBinding, Genres> {
    private static final String TAG = GenresAdapter.class.getSimpleName();

    public GenresAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemGenresBinding, Genres> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemGenresBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemGenresBinding binding, int position) {
        Genres genres = getItemAt(position);
        Glide.with(binding.llLayout.getContext()).load(genres.getThumbId()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                binding.llLayout.setBackground(resource);
                Log.i(TAG, "onResourceReady: ");
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                binding.llLayout.setBackground(placeholder);
                Log.i(TAG, "onLoadCleared: ");
            }
        });
        binding.tvGenresName.setText(genres.getName());
        binding.tvGenresSongNumber.setText(mActivity.getString(R.string.song_number, genres.getSongNumber()));
    }
}
