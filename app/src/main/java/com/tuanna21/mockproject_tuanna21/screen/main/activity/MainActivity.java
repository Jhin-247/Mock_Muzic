package com.tuanna21.mockproject_tuanna21.screen.main.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivityMainBinding;
import com.tuanna21.mockproject_tuanna21.listener.ToolbarListener;
import com.tuanna21.mockproject_tuanna21.screen.main.fakeadapters.FakeItemAdapterAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.BottomPlayBarStatus;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;
import com.tuanna21.mockproject_tuanna21.service.SongService;


public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ToolbarListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_SONG = 1;
    private final FakeItemAdapterAdapter mFakeItemAdapterAdapter = new FakeItemAdapterAdapter(this);
    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;
    private Handler mHandler;

    private void setupNavigationDrawer() {
        mBinding.rcvNavigation.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcvNavigation.setAdapter(mFakeItemAdapterAdapter);
        mBinding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);
        }
        mBinding.bottomNavigation.setItemIconTintList(null);
    }

    @Override
    protected void setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void setupAction() {
        if (!checkPermission(READ_EXTERNAL_STORAGE)) {
            requestPermission(READ_EXTERNAL_STORAGE);
        } else {
            LoaderManager.getInstance(this).initLoader(LOAD_SONG, null, this);
        }
    }

    @Override
    protected void onPermissionRequested(Boolean result) {
        if (result) {
            LoaderManager.getInstance(this).initLoader(LOAD_SONG, null, this);
        }
    }

    @Override
    protected void setupObserver() {
        mViewModel.getNavigationItems().observe(this, mFakeItemAdapterAdapter::setData);
        mViewModel.getCurrentSong().observe(this, song -> {
            mBinding.bottomPlay.skTime.setProgress(0);
            mBinding.bottomPlay.skTime.setMax(Integer.parseInt(song.getDuration()));
            mBinding.bottomPlay.tvSongTitle.setText(song.getTitle());
            mBinding.bottomPlay.tvSongArtist.setText(song.getArtist());
            Glide.with(mBinding.bottomPlay.ivThumbnail).load(song.getSongImage()).error(R.drawable.ic_empty_song).fitCenter().into(mBinding.bottomPlay.ivThumbnail);
        });
        mViewModel.getBottomStatus().observe(this, this::changBottomBarStatus);
    }

    private void changBottomBarStatus(BottomPlayBarStatus bottomPlayBarStatus) {
        switch (bottomPlayBarStatus) {
            case HIDE:
                mBinding.bottomPlay.llBottom.setVisibility(View.GONE);
                break;
            case SHOW_AND_PLAY:
                mBinding.bottomPlay.llBottom.setVisibility(View.VISIBLE);
                Glide.with(mBinding.bottomPlay.ivPlayPause).load(R.drawable.ic_pause).into(mBinding.bottomPlay.ivPlayPause);
                break;
            case SHOW_AND_PAUSE:
                mBinding.bottomPlay.llBottom.setVisibility(View.VISIBLE);
                Glide.with(mBinding.bottomPlay.ivPlayPause).load(R.drawable.ic_play).into(mBinding.bottomPlay.ivPlayPause);
                break;
        }
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    protected void initYourView() {
        initData();
        setupNavigation();
        setupNavigationDrawer();

        setupListener();
        setupBottomPlayView();
        connectService();
    }

    private void handlerSeekbar() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBinding.bottomPlay.skTime.setProgress(mViewModel.getCurrentSongTime());
                mHandler.postDelayed(this, 1);
            }
        });
        mBinding.bottomPlay.skTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mViewModel.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initData() {
        HandlerThread handlerThread = new HandlerThread("seekbar_thread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }

    private void setupBottomPlayView() {
        mBinding.bottomPlay.skTime.setPadding(0, 0, 0, 0);
        handlerSeekbar();
    }

    private void connectService() {
        Intent mIntent = new Intent(this, SongService.class);
        startService(mIntent);
    }

    private void setupListener() {
        mBinding.bottomPlay.ivPlayPrevious.setOnClickListener(v -> {
            mViewModel.playPreviousSong();
            mViewModel.setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        });

        mBinding.bottomPlay.ivPlayNext.setOnClickListener(v -> {
            mViewModel.playNextSong();
            mViewModel.setBottomPlayStatus(BottomPlayBarStatus.SHOW_AND_PLAY);
        });

        mBinding.bottomPlay.ivClose.setOnClickListener(v -> {
            mViewModel.setBottomPlayStatus(BottomPlayBarStatus.HIDE);
        });

        mBinding.bottomPlay.ivPlayPause.setOnClickListener(v -> {
            mViewModel.playOrPause();
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void openDrawer() {
        mBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri audioCollectionUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            audioCollectionUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            audioCollectionUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM
        };
        String where = MediaStore.Audio.Media.IS_MUSIC + " = 1";

        return new CursorLoader(getApplication(), audioCollectionUri, projection, where, null, MediaStore.Audio.Media.TITLE + " ASC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mViewModel.setSongCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
