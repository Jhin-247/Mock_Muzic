package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemAllSongBinding;

import java.util.ArrayList;
import java.util.List;

public class AllSongAdapterV2 extends RecyclerView.Adapter<AllSongAdapterV2.AllSongHolder> {

    private AllSongAdapter.SongClickListener mClick;

    private List<Song> songs;

    public AllSongAdapterV2(Activity mActivity) {
        songs = new ArrayList<>();
    }

    public void setData(List<Song> data){
        this.songs = data;
        notifyDataSetChanged();
    }

    public void setOnClick(AllSongAdapter.SongClickListener mClick) {
        this.mClick = mClick;
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
        Log.i("onBindViewHolder", "onBindViewHolder: " + position);
        Song song = songs.get(position);
        holder.mBinding.tvSongArtist.setText(song.getArtist());
        holder.mBinding.tvSongTitle.setText(song.getTitle());
        Glide.with(holder.mBinding.ivThumbnail)
                .load(Uri.parse(song.getSongImage()))
                .error(R.drawable.ic_empty_song)
                .fitCenter()
                .into(holder.mBinding.ivThumbnail);
        holder.mBinding.getRoot().setOnClickListener(v -> mClick.onSongClick(song));
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }

    public static class AllSongHolder extends RecyclerView.ViewHolder{
        ItemAllSongBinding mBinding;
        public AllSongHolder(@NonNull ItemAllSongBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
