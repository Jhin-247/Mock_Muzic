package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.adapter.inneradapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.databinding.ItemDefaultPlaylistBinding;
import com.tuanna21.mockproject_tuanna21.data.model.Song;

public class DefaultPlaylistAdapter extends BaseAdapter<ItemDefaultPlaylistBinding, Song> {

    public DefaultPlaylistAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    protected BaseHolder<ItemDefaultPlaylistBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemDefaultPlaylistBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemDefaultPlaylistBinding binding, int position) {
        binding.tvPlaylistName.setText(mActivity.getString(R.string.playlist));
        binding.tvPlaylistSongNumber.setText(binding.getRoot().getContext().getString(R.string.song_number));
    }
}
