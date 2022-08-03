package com.tuanna21.mockproject_tuanna21.adapter.homeadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.adapter.inneradapters.FullScreenBoxAdapter;
import com.tuanna21.mockproject_tuanna21.adapter.inneradapters.SmallSongBoxAdapter;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeMainBinding;
import com.tuanna21.mockproject_tuanna21.db.model.Song;

import java.util.List;

public class HomeMainAdapter extends RecyclerView.Adapter<HomeMainAdapter.HomeMainHolder> {
    private static final String TAG = HomeMainAdapter.class.getSimpleName();

    private static final int TYPE_HOT = 1;
    private static final int TYPE_PLAYLIST = 2;
    private static final int TYPE_RECENT_PLAYED = 3;
    private final SmallSongBoxAdapter mSmallAdapter;
    private final FullScreenBoxAdapter mFullScreenAdapter;
    private List<Song> mSongList;

    public HomeMainAdapter(int width) {
        mSmallAdapter = new SmallSongBoxAdapter();
        mFullScreenAdapter = new FullScreenBoxAdapter();
        mSmallAdapter.setScreenWidth(width);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListHomeCategory(List<Song> songList) {
        this.mSongList = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeMainHolder(
                ItemHomeMainBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HOT;
            case 1:
                return TYPE_PLAYLIST;
            case 2:
                return TYPE_RECENT_PLAYED;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMainHolder holder, int position) {
        mSmallAdapter.setData(mSongList);
        mFullScreenAdapter.setData(mSongList);
        holder.mBinding.rcvInnerData.setAdapter(mSmallAdapter);
        switch (getItemViewType(position)) {
            case TYPE_HOT:
                holder.mBinding.tvCategory.setText(
                        holder.mBinding.rcvInnerData.getContext().getString(R.string.hot)
                );
                holder.mBinding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                holder.mBinding.getRoot().getContext(),
                                RecyclerView.HORIZONTAL,
                                false
                        )
                );
                holder.mBinding.rcvInnerData.setAdapter(mSmallAdapter);
                break;
            case TYPE_PLAYLIST:
                holder.mBinding.tvCategory.setText(
                        holder.mBinding.rcvInnerData.getContext().getString(R.string.playlist)
                );
                holder.mBinding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                holder.mBinding.getRoot().getContext(),
                                RecyclerView.HORIZONTAL,
                                false
                        )
                );
                holder.mBinding.rcvInnerData.setAdapter(mSmallAdapter);
                break;
            case TYPE_RECENT_PLAYED:
                holder.mBinding.tvCategory.setText(
                        holder.mBinding.rcvInnerData.getContext().getString(R.string.recent_play)
                );
                holder.mBinding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                holder.mBinding.getRoot().getContext(),
                                RecyclerView.VERTICAL,
                                false
                        )
                );
                holder.mBinding.rcvInnerData.setAdapter(mFullScreenAdapter);
                holder.mBinding.viewBottomLine.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class HomeMainHolder extends RecyclerView.ViewHolder {
        ItemHomeMainBinding mBinding;

        public HomeMainHolder(@NonNull ItemHomeMainBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
