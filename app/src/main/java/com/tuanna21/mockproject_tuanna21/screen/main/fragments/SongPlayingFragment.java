package com.tuanna21.mockproject_tuanna21.screen.main.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSongPlayingBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;
import com.tuanna21.mockproject_tuanna21.utils.ImageUtils;


public class SongPlayingFragment extends BaseFragment<MainActivityViewModel, FragmentSongPlayingBinding> {
    private Bitmap mBitmap;
    private Uri mSongImageUri;
    private Handler mHandler;

    @Override
    protected void initData() {
        HandlerThread handlerThread = new HandlerThread("custom_view_seekbar");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    protected void initToolbar() {
        Glide.with(mBinding.toolbar.ivNavigationButton).load(R.drawable.ic_back).into(mBinding.toolbar.ivNavigationButton);
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.onNavigateBack();
        });
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setText(getString(R.string.now_playing));
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initObserver() {
        mViewModel.getCurrentSong().observe(getViewLifecycleOwner(), song -> {
            mSongImageUri = Uri.parse(song.getSongImage());
            mBinding.songPlayingView.setMaxProgressAndBackToStart(Integer.parseInt(song.getDuration()));
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), mSongImageUri);
                mBitmap = ImageUtils.getCircleBitmapFromBitmap(mBitmap);
                mBinding.songPlayingView.setImage(mBitmap);
            } catch (Exception e) {
                mBinding.songPlayingView.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_defautl_image));
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song_playing;
    }

    @Override
    protected void initYourView() {
        setupCustomSeekbar();
    }

    private void setupCustomSeekbar() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBinding.songPlayingView.post(() -> mBinding.songPlayingView.setProgress(mViewModel.getCurrentSongTime()));
                mHandler.postDelayed(this, 1);
            }
        });
        mBinding.songPlayingView.setProgressChangedListener(progressChanged -> mViewModel.seekTo(progressChanged));
    }
}