package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment.detail;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAlbumDetailBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class AlbumDetailFragment extends BaseFragment<MainViewModel, FragmentAlbumDetailBinding> {
    private AlbumDetailAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new AlbumDetailAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {
        Glide.with(mBinding.toolbar.ivNavigationButton).load(R.drawable.ic_back).into(mBinding.toolbar.ivNavigationButton);
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText(R.string.album_detail);
    }

    @Override
    protected void initObserver() {
        mViewModel.getAlbumDetail().observe(getViewLifecycleOwner(), album -> {
            Glide.with(mBinding.ivSongThumbnail).load(album.getAlbumArt()).error(album.getAlbumImageDefaultResource()).into(mBinding.ivSongThumbnail);
            Glide.with(mBinding.cstSummary).load(album.getAlbumArt()).error(album.getAlbumImageDefaultResource()).into(
                    new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            mBinding.cstSummary.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            mBinding.cstSummary.setBackground(placeholder);
                        }
                    }
            );
        });
        mViewModel.getCurrentPlayingSongList().observe(getViewLifecycleOwner(), songs -> mAdapter.setData(songs));
        mViewModel.getCurrentPlayingSong().observe(getViewLifecycleOwner(), song -> mAdapter.setCurrentSong(song));
    }

    @Override
    protected void initListener() {
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> Navigation.findNavController(mBinding.getRoot()).navigateUp());
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album_detail;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvSongs.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rcvSongs.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mBinding.rcvSongs.setAdapter(mAdapter);
    }
}
