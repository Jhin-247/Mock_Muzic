package com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.inneradapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemRecentPlayBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment.AllSongAdapter;

public class FullScreenHomeAdapter extends BaseAdapter<ItemRecentPlayBinding, Song> {
    private AllSongAdapter.SongClickListener mSongClickListener;

    public FullScreenHomeAdapter(Activity activity) {
        super(activity);
    }

    public void setSongClickListener(AllSongAdapter.SongClickListener mSongClickListener) {
        this.mSongClickListener = mSongClickListener;
    }

    @Override
    protected BaseHolder<ItemRecentPlayBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemRecentPlayBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : (Math.min(getData().size(), 10));
    }

    @Override
    protected void bindView(ItemRecentPlayBinding binding, int position) {
        Song song = getItemAt(position);
        binding.tvArtist.setText(song.getArtist());
        binding.tvTitle.setText(song.getTitle());
        binding.getRoot().setOnClickListener(v -> {
            if (mSongClickListener != null) {
                mSongClickListener.onSongClick(song);
            }
        });
    }
}
