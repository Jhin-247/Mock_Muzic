package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.databinding.ItemMainPlaylistBinding;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.adapter.inneradapter.DefaultPlaylistAdapter;

public class PlaylistMainAdapter extends BaseAdapter<ItemMainPlaylistBinding, Song> {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_MY_PLAYLIST = 1;

    private DefaultPlaylistAdapter mDefaultAdapter;

    public PlaylistMainAdapter(Activity mActivity) {
        super(mActivity);
        mDefaultAdapter = new DefaultPlaylistAdapter(mActivity);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_DEFAULT;
            case 1:
                return TYPE_MY_PLAYLIST;
        }
        return super.getItemViewType(position);
    }

    @Override
    protected BaseHolder<ItemMainPlaylistBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemMainPlaylistBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    protected void bindView(ItemMainPlaylistBinding binding, int position) {
        int type = getItemViewType(position);
        Song song = getItemAt(position);
        switch (type) {
            case TYPE_DEFAULT:
                binding.tvPlaylistName.setVisibility(View.GONE);
                binding.tvViewAll.setVisibility(View.GONE);
                binding.rcvPlaylist.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
                binding.rcvPlaylist.setAdapter(mDefaultAdapter);
                break;
        }
    }


}