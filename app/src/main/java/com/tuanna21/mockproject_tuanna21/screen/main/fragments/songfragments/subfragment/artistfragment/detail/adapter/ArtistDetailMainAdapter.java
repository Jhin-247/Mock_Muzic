package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.detail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeMainBinding;

import java.util.List;

public class ArtistDetailMainAdapter extends BaseAdapter<ItemHomeMainBinding, Song> {
    private ArtistDetailTopAlbumAdapter mAlbumAdapter;
    private ArtistDetailTopSongAdapter mSongAdapter;

    public ArtistDetailMainAdapter(Activity mActivity) {
        super(mActivity);
        mAlbumAdapter = new ArtistDetailTopAlbumAdapter(mActivity);
        mSongAdapter = new ArtistDetailTopSongAdapter(mActivity);
    }

    public void setSong(List<Song> songs) {
        mSongAdapter.setData(songs);
        notifyItemChanged(1);
    }

    public void setAlbum(List<Album> albums) {
        mAlbumAdapter.setData(albums);
        notifyItemChanged(0);
    }

    @Override
    protected BaseHolder<ItemHomeMainBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemHomeMainBinding.inflate(
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
    protected void bindView(ItemHomeMainBinding binding, int position) {
        switch (position) {
            case 0:
                binding.tvCategory.setText(R.string.top_album);
                binding.rcvInnerData.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
                binding.rcvInnerData.setAdapter(mAlbumAdapter);
                break;
            case 1:
                binding.tvCategory.setText(R.string.top_songs);
                binding.rcvInnerData.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
                binding.rcvInnerData.setAdapter(mSongAdapter);
                break;
        }
    }
}
