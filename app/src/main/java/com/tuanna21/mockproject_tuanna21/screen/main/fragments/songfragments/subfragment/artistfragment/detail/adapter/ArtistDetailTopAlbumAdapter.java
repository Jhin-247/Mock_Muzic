package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.detail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.databinding.ItemTopAlbumBinding;

public class ArtistDetailTopAlbumAdapter extends BaseAdapter<ItemTopAlbumBinding, Album> {

    public ArtistDetailTopAlbumAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemTopAlbumBinding, Album> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemTopAlbumBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemTopAlbumBinding binding, int position) {
        Album album = getItemAt(position);
        Glide.with(binding.ivAlbumImage).load(album.getAlbumArt()).error(album.getAlbumImageDefaultResource()).into(binding.ivAlbumImage);
        binding.tvSongName.setText(album.getName());
    }
}
