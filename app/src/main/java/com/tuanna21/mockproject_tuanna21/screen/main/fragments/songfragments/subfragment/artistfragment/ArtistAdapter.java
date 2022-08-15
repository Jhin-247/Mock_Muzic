package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Artist;
import com.tuanna21.mockproject_tuanna21.databinding.ItemArtistsBinding;

public class ArtistAdapter extends BaseAdapter<ItemArtistsBinding, Artist> {
    private ArtistClickListener mListener;

    public ArtistAdapter(Activity mActivity) {
        super(mActivity);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListener(ArtistClickListener listener){
        this.mListener = listener;
        notifyDataSetChanged();
    }

    @Override
    protected BaseHolder<ItemArtistsBinding, Artist> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemArtistsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemArtistsBinding binding, int position) {
        Artist mArtist = getItemAt(position);
        binding.tvArtistName.setText(mArtist.getArtistName());
        binding.tvArtistAlbum.setText(mActivity.getString(R.string.album_number, mArtist.getAlbumNumber()));
        binding.tvArtistSongNumber.setText(mActivity.getString(R.string.song_number, mArtist.getSongNumber()));
        Glide.with(binding.ivArtistImage).load(mArtist.getThumbnail()).error(mArtist.getDefaultThumbnail()).into(binding.ivArtistImage);
        binding.getRoot().setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onArtistClick(mArtist);
            }
        });
    }

    public interface ArtistClickListener {
        void onArtistClick(Artist artist);
    }
}
