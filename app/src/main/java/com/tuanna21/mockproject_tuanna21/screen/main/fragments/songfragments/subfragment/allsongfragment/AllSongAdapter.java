package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAllSongBinding;
import com.tuanna21.mockproject_tuanna21.data.model.Song;

import java.util.List;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.AllSongHolder> {

    private List<Song> mSongList;

    @SuppressLint("NotifyDataSetChanged")
    public void setSongList(List<Song> songList) {
        this.mSongList = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllSongHolder(
                ItemAllSongBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AllSongHolder holder, int position) {
        Song song = mSongList.get(position);
        holder.mBinding.tvSongArtist.setText(song.getArtist());
        holder.mBinding.tvSongTitle.setText(song.getTitle());
        Glide.with(holder.mBinding.ivThumbnail)
                .load(Uri.parse(song.getSongImage()))
                .error(R.drawable.ic_empty_song)
                .fitCenter()
                .into(holder.mBinding.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mSongList == null ? 0 : mSongList.size();
    }

    public static class AllSongHolder extends RecyclerView.ViewHolder {
        ItemAllSongBinding mBinding;

        public AllSongHolder(@NonNull ItemAllSongBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
