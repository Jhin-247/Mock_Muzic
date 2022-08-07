package com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.ItemHomeMainBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.inneradapters.FullScreenHomeAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.inneradapters.SmallSongHomeAdapter;

public class HomeMainAdapter extends BaseAdapter<ItemHomeMainBinding, Song> {
    private static final String TAG = HomeMainAdapter.class.getSimpleName();

    private static final int TYPE_HOT = 1;
    private static final int TYPE_PLAYLIST = 2;
    private static final int TYPE_RECENT_PLAYED = 3;
    private final SmallSongHomeAdapter mSmallAdapter;
    private final FullScreenHomeAdapter mFullScreenAdapter;

    public HomeMainAdapter(Activity mActivity) {
        super(mActivity);
        mSmallAdapter = new SmallSongHomeAdapter(mActivity);
        mFullScreenAdapter = new FullScreenHomeAdapter(mActivity);
    }

    @Override
    protected BaseHolder<ItemHomeMainBinding, Song> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemHomeMainBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemHomeMainBinding binding, int position) {
        mSmallAdapter.setData(getData());
        mFullScreenAdapter.setData(getData());
        binding.rcvInnerData.setAdapter(mSmallAdapter);
        switch (getItemViewType(position)) {
            case TYPE_HOT:
                binding.tvCategory.setText(
                        mActivity.getString(R.string.hot)
                );
                binding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                mActivity,
                                RecyclerView.HORIZONTAL,
                                false
                        )
                );
                binding.rcvInnerData.setAdapter(mSmallAdapter);
                break;
            case TYPE_PLAYLIST:
                binding.tvCategory.setText(
                        mActivity.getString(R.string.playlist)
                );
                binding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                mActivity,
                                RecyclerView.HORIZONTAL,
                                false
                        )
                );
                binding.rcvInnerData.setAdapter(mSmallAdapter);
                break;
            case TYPE_RECENT_PLAYED:
                binding.tvCategory.setText(
                        mActivity.getString(R.string.recent_play)
                );
                binding.rcvInnerData.setLayoutManager(
                        new LinearLayoutManager(
                                binding.getRoot().getContext(),
                                RecyclerView.VERTICAL,
                                false
                        )
                );
                binding.rcvInnerData.setAdapter(mFullScreenAdapter);
                binding.viewBottomLine.setVisibility(View.GONE);
                break;
        }
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
    public int getItemCount() {
        return 3;
    }
}
