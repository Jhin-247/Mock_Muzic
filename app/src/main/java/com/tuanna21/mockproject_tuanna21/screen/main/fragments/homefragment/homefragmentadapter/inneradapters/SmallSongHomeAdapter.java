package com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.inneradapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeBinding;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;

public class SmallSongHomeAdapter extends BaseAdapter<ItemHomeBinding, Song> {

    private int mScreenWidth;

    public SmallSongHomeAdapter(Activity mActivity) {
        super(mActivity);
        mScreenWidth = new ScreenUtils().getScreenWidth(mActivity);
    }


    @Override
    protected BaseHolder<ItemHomeBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemHomeBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : (Math.min(getData().size(), 10));
    }

    @Override
    protected void bindView(ItemHomeBinding binding, int position) {
        Song song = getItemAt(position);
        ViewGroup.LayoutParams layoutParams = binding.cstLayout.getLayoutParams();
        layoutParams.width = mScreenWidth * 2 / 3;
        binding.cstLayout.setLayoutParams(layoutParams);
        binding.tvArtist.setText(song.getArtist());
        binding.tvTitle.setText(song.getTitle());
        Glide.with(binding.ivThumbnail).load(Uri.parse(song.getSongImage())).error(R.drawable.ic_hot_recommend).centerCrop().into(binding.ivThumbnail);
    }
}
