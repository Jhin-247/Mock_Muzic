package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAlbumBinding;

public class AlbumAdapter extends BaseAdapter<ItemAlbumBinding, Album> {
    public AlbumAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemAlbumBinding, Album> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemAlbumBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemAlbumBinding binding, int position) {
        Album album = getItemAt(position);
    }
}
