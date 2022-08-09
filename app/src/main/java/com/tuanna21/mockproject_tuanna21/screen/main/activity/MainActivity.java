package com.tuanna21.mockproject_tuanna21.screen.main.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
        ToolbarListener {
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
            mViewModel.loadSong();
        }
    }

    @Override
    protected void onPermissionRequested(Boolean result) {
        if (result) {
            mViewModel.loadSong();
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
        mViewModel.getBottomStatus().observe(this, new Observer<BottomPlayBarStatus>() {
            @Override
            public void onChanged(BottomPlayBarStatus bottomPlayBarStatus) {
                changeBottomBarStatus(bottomPlayBarStatus);
            }
        });
    }

    private void changeBottomBarStatus(BottomPlayBarStatus bottomPlayBarStatus) {
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

        mBinding.bottomPlay.ivClose.setOnClickListener(v -> mViewModel.setBottomPlayStatus(BottomPlayBarStatus.HIDE));

        mBinding.bottomPlay.ivPlayPause.setOnClickListener(this::onClick);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void openDrawer() {
        mBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    private void onClick(View v) {
        mViewModel.playOrPause();
    }
}
