package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAlbumBinding;

public class AlbumAdapter extends BaseAdapter<ItemAlbumBinding, Album> {
    private AlbumClickListener mListener;

    public AlbumAdapter(Activity mActivity, AlbumClickListener mListener) {
        super(mActivity);
        this.mListener = mListener;
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
        Album mAlbum = getItemAt(position);
        binding.tvAlbumName.setText(mAlbum.getName());
        binding.tvAlbumArtist.setText(mAlbum.getArtist());
        binding.tvAlbumSongNumber.setText(
                mActivity.getString(R.string.song_number, mAlbum.getSongNumber())
        );
        binding.getRoot().setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onAlbumClick(mAlbum);
            }
        });
        Glide.with(binding.ivAlbumImage).load(mAlbum.getAlbumArt()).error(mAlbum.getAlbumImageDefaultResource()).into(binding.ivAlbumImage);
    }

    public interface AlbumClickListener {
        void onAlbumClick(Album album);
    }
}
