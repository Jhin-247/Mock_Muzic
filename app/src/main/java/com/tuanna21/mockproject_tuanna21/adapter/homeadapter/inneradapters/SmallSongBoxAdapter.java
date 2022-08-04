package com.tuanna21.mockproject_tuanna21.adapter.homeadapter.inneradapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeBinding;
import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.List;

public class SmallSongBoxAdapter extends RecyclerView.Adapter<SmallSongBoxAdapter.HotHolder> {
    private List<Song> mSongs;
    private int mScreenWidth;

    public SmallSongBoxAdapter() {
    }

    public void setScreenWidth(int screenWidth) {
        this.mScreenWidth = screenWidth;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Song> data) {
        this.mSongs = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotHolder(
                ItemHomeBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HotHolder holder, int position) {
        Song song = mSongs.get(position);
        ViewGroup.LayoutParams layoutParams = holder.mBinding.cstLayout.getLayoutParams();
        layoutParams.width = mScreenWidth * 2 / 3;
        holder.mBinding.cstLayout.setLayoutParams(layoutParams);
        holder.mBinding.tvArtist.setText(song.getArtist());
        holder.mBinding.tvTitle.setText(song.getTitle());
        Glide.with(holder.mBinding.ivThumbnail).load(Uri.parse(song.getSongImage())).into(holder.mBinding.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : (Math.min(mSongs.size(), 10));
    }


    public static class HotHolder extends RecyclerView.ViewHolder {
        ItemHomeBinding mBinding;

        public HotHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
