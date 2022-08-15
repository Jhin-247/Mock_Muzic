package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.detail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAlbumDetailBinding;
import com.tuanna21.mockproject_tuanna21.utils.SongUtils;

public class ArtistDetailTopSongAdapter extends BaseAdapter<ItemAlbumDetailBinding, Song> {
    public ArtistDetailTopSongAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemAlbumDetailBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemAlbumDetailBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemAlbumDetailBinding binding, int position) {
        Song song = getItemAt(position);
        binding.tvSongName.setText(song.getTitle());
        binding.tvDuration.setText(SongUtils.getFormatCurrentSongTime(Integer.parseInt(song.getDuration())));
        binding.ivPlaying.setVisibility(View.GONE);
    }
}
