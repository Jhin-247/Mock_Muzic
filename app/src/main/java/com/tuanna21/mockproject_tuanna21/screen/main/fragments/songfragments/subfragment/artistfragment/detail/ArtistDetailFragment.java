package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.detail;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentArtistDetailBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.detail.adapter.ArtistDetailMainAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

import java.util.List;

public class ArtistDetailFragment extends BaseFragment<MainViewModel, FragmentArtistDetailBinding> {
    private ArtistDetailMainAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new ArtistDetailMainAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {
        Glide.with(mBinding.toolbar.ivNavigationButton).load(R.drawable.ic_back).into(mBinding.toolbar.ivNavigationButton);
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText(R.string.artist_detail);
    }

    @Override
    protected void initObserver() {
        mViewModel.getCurrentPlayingSongList().observe(getViewLifecycleOwner(), songs -> mAdapter.setSong(songs));
        mViewModel.getAlbumList().observe(mActivity, albums -> mAdapter.setAlbum(albums));
    }

    @Override
    protected void initListener() {
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.onNavigateBack();
        });
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_artist_detail;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvSongs.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rcvSongs.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mBinding.rcvSongs.setAdapter(mAdapter);
    }
}
