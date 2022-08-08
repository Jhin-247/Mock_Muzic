package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAllSongBinding;

public class AllSongAdapter extends BaseAdapter<ItemAllSongBinding, Song> {

    private SongClickListener mClick;

    public AllSongAdapter(Activity mActivity) {
        super(mActivity);
    }

    public void setOnClick(SongClickListener mClick) {
        this.mClick = mClick;
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
        binding.getRoot().setOnClickListener(v -> mClick.onSongClick(song));
    }

    public interface SongClickListener {
        void onSongClick(Song song);
    }
}
