package com.tuanna21.mockproject_tuanna21.adapter.inneradapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeBinding;
import com.tuanna21.mockproject_tuanna21.databinding.ItemRecentPlayBinding;
import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.List;

public class FullScreenBoxAdapter extends RecyclerView.Adapter<FullScreenBoxAdapter.FullScreenHolder> {

    private List<Song> mSongList;

    public FullScreenBoxAdapter(){
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Song> data){
        this.mSongList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FullScreenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FullScreenHolder(
                ItemRecentPlayBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FullScreenHolder holder, int position) {
        Song song = mSongList.get(position);
        holder.mBinding.tvArtist.setText(song.getArtist());
        holder.mBinding.tvTitle.setText(song.getTitle());
    }

    @Override
    public int getItemCount() {
        return mSongList == null ? 0 : (Math.min(mSongList.size(), 10));
    }


    public static class FullScreenHolder extends RecyclerView.ViewHolder {
        ItemRecentPlayBinding mBinding;

        public FullScreenHolder(@NonNull ItemRecentPlayBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
