package com.tuanna21.mockproject_tuanna21.screen.main.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;

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

import com.google.android.material.navigation.NavigationView;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivityMainBinding;
import com.tuanna21.mockproject_tuanna21.listener.ToolbarListener;
import com.tuanna21.mockproject_tuanna21.screen.main.fakeadapters.FakeItemAdapterAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;


public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ToolbarListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_SONG = 1;
    private final FakeItemAdapterAdapter mFakeItemAdapterAdapter = new FakeItemAdapterAdapter(this);
    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;

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
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    protected void initYourView() {
        setupNavigation();
        setupNavigationDrawer();
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

        return new CursorLoader(getApplication(), audioCollectionUri, projection, where, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mViewModel.setSongCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
