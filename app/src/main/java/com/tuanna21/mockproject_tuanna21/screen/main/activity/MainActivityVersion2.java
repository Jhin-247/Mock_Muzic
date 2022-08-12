package com.tuanna21.mockproject_tuanna21.screen.main.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivityMain2Binding;
import com.tuanna21.mockproject_tuanna21.listener.FragmentChangeListener;
import com.tuanna21.mockproject_tuanna21.listener.ToolbarListener;
import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;
import com.tuanna21.mockproject_tuanna21.player.SongObserver;
import com.tuanna21.mockproject_tuanna21.screen.main.fakeadapters.FakeItemAdapterAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;
import com.tuanna21.mockproject_tuanna21.service.SongService;

public class MainActivityVersion2 extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ToolbarListener,
        FragmentChangeListener,
        SongObserver {
    private static final String TAG = MainActivityVersion2.class.getSimpleName();
    private ActivityMain2Binding mBinding;
    private MainViewModel mViewModel;

    private FakeItemAdapterAdapter mFakeItemAdapterAdapter;
    private Intent mServiceIntent;

    private Handler mHandler;
    private NavHostFragment mNavHostFragment;
    private NavController mNavController;

    @Override
    protected void setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_2);
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.loadDataAndSetupMusicObserver(this);
    }

    @Override
    protected void setupAction() {
        if (!checkPermission(READ_EXTERNAL_STORAGE)) {
            requestPermission(READ_EXTERNAL_STORAGE);
        } else {
            if (mViewModel.checkIsLived(this)) {
                mViewModel.setupReopenAppDataWhilePlaying();
            } else {
                mViewModel.loadSong(this);
            }
        }
    }

    @Override
    protected void onPermissionRequested(Boolean result) {
        if (result) {
            mViewModel.loadSong(this);
        }
    }

    @Override
    protected void setupObserver() {
        mViewModel.getNavigationItems().observe(this, navigationItems -> mFakeItemAdapterAdapter.setData(navigationItems));
        mViewModel.getSongPlayingStatus().observe(this, aBoolean -> {
            if (aBoolean) {
                Glide.with(mBinding.bottomPlay.ivPlayPause).load(R.drawable.ic_pause).into(mBinding.bottomPlay.ivPlayPause);
            } else {
                Glide.with(mBinding.bottomPlay.ivPlayPause).load(R.drawable.ic_play).into(mBinding.bottomPlay.ivPlayPause);
            }
        });
        mViewModel.getCurrentPlayingSong().observe(this, song -> {
            mBinding.bottomPlay.tvSongTitle.setText(song.getTitle());
            mBinding.bottomPlay.tvSongArtist.setText(song.getArtist());
            mBinding.bottomPlay.skTime.setMax(Integer.parseInt(song.getDuration()));

            Glide.with(mBinding.bottomPlay.ivThumbnail).load(Uri.parse(song.getSongImage())).error(R.drawable.ic_empty_song).into(mBinding.bottomPlay.ivThumbnail);
        });
        mViewModel.getBottomStatus().observe(this, bottomPlayBarStatus -> {
            switch (bottomPlayBarStatus) {
                case HIDE:
                    mBinding.bottomPlay.llBottom.setVisibility(View.GONE);
                    break;
                case SHOW_AND_PLAY:
                case SHOW_AND_PAUSE:
                    mBinding.bottomPlay.llBottom.setVisibility(View.VISIBLE);
                    break;
            }
        });
        MyPlayerController.getInstance().addObserver(this);
    }

    @Override
    protected void initYourView() {
        initData();
        setupNavigation();
        setupNavigationDrawer();
        setupBottomPlay();
        setupListener();
    }

    private void initData() {
        mFakeItemAdapterAdapter = new FakeItemAdapterAdapter(this);
        mServiceIntent = new Intent(this, SongService.class);

        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (mNavHostFragment != null) {
            mNavController = mNavHostFragment.getNavController();
        }

        HandlerThread mHandlerThread = new HandlerThread("bottom_play_bar");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private void setupNavigationDrawer() {
        mBinding.rcvNavigation.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcvNavigation.setAdapter(mFakeItemAdapterAdapter);
        mBinding.navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupBottomPlay() {
        mBinding.bottomPlay.skTime.setPadding(0, 0, 0, 0);
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

    private void setupListener() {
        mBinding.bottomPlay.ivPlayNext.setOnClickListener(v -> mViewModel.playNextSong());
        mBinding.bottomPlay.ivPlayPrevious.setOnClickListener(v -> mViewModel.playPreviousSong());
        mBinding.bottomPlay.ivPlayPause.setOnClickListener(v -> mViewModel.playOrPause());
        mBinding.bottomPlay.ivClose.setOnClickListener(v -> {
            mViewModel.closeBottomPlay();
        });
        mBinding.bottomPlay.ivThumbnail.setOnClickListener(v -> {
            mBinding.bottomNavigation.setSelectedItemId(R.id.songPlayingFragment);
        });
        mBinding.bottomPlay.llSongInfo.setOnClickListener(v -> {
            mBinding.bottomNavigation.setSelectedItemId(R.id.songPlayingFragment);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void setupNavigation() {
        if (mNavHostFragment != null) {
            mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                mViewModel.changeCurrentTabId(navDestination.getId());
            });
            NavigationUI.setupWithNavController(mBinding.bottomNavigation, mNavController);
            if (mNavController.getCurrentDestination() != null) {
                mViewModel.changeCurrentTabId(mNavController.getCurrentDestination().getId());
            }
        }
        mBinding.bottomNavigation.setItemIconTintList(null);
    }

    @Override
    public void openDrawer() {
        mBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onNavigateBack() {
        mBinding.bottomNavigation.setSelectedItemId(mViewModel.getLastTabId());
    }

    @Override
    public void changeFragment(int id) {
        mBinding.bottomNavigation.setSelectedItemId(id);
    }

    @Override
    public void onSongUpdate() {
        if (mViewModel.canStartService(this)) {
            startService(mServiceIntent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (
                    mNavController != null &&
                            mNavController.getCurrentDestination() != null &&
                            mNavController.getCurrentDestination().getId() == R.id.songPlayingFragment
            ) {
                onNavigateBack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
