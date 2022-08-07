package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAllSongBinding;

import java.util.List;

public class AllSongAdapter extends BaseAdapter<ItemAllSongBinding, Song> {

    public AllSongAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemAllSongBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemAllSongBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemAllSongBinding binding, int position) {
        Song song = getItemAt(position);
        binding.tvSongArtist.setText(song.getArtist());
        binding.tvSongTitle.setText(song.getTitle());
        Glide.with(binding.ivThumbnail)
                .load(Uri.parse(song.getSongImage()))
                .error(R.drawable.ic_empty_song)
                .fitCenter()
                .into(binding.ivThumbnail);
    }
}
