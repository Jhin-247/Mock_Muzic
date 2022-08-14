package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment.detail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAlbumDetailBinding;
import com.tuanna21.mockproject_tuanna21.utils.SongUtils;

public class AlbumDetailAdapter extends BaseAdapter<ItemAlbumDetailBinding, Song> {
    private int chosenSong;

    public AlbumDetailAdapter(Activity mActivity) {
        super(mActivity);
        chosenSong = 0;
    }

    public void setCurrentSong(Song song) {
        int oldSong = chosenSong;
        chosenSong = getData().indexOf(song);
        notifyItemChanged(oldSong);
        notifyItemChanged(chosenSong);
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
        if (getData().indexOf(song) == chosenSong) {
            binding.ivPlaying.setVisibility(View.VISIBLE);
            binding.ivMore.setVisibility(View.GONE);
        } else {
            binding.ivPlaying.setVisibility(View.GONE);
            binding.ivMore.setVisibility(View.VISIBLE);
        }
        binding.tvSongName.setText(song.getTitle());
        binding.tvDuration.setText(SongUtils.getFormatCurrentSongTime(Integer.parseInt(song.getDuration())));
    }
}
