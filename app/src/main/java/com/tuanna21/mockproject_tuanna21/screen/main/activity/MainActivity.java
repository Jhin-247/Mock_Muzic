package com.tuanna21.mockproject_tuanna21.screen.main.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
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
        ToolbarListener {
    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;

    private void setupNavigationDrawer() {
        FakeItemAdapterAdapter mFakeItemAdapterAdapter = new FakeItemAdapterAdapter(mViewModel.getNavigationItems());
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
        if (!hasDatabase() && !checkPermission(READ_EXTERNAL_STORAGE)) {
            requestPermission(READ_EXTERNAL_STORAGE);
        } else if (!hasDatabase() && checkPermission(READ_EXTERNAL_STORAGE)) {
            mViewModel.requestSyncSongData(this);
        }
    }

    @Override
    protected void onPermissionRequested(Boolean result) {
        if (result)
            mViewModel.requestSyncSongData(this);
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mViewModel.setScreenHeight(getScreenHeight());
        mViewModel.setScreenWidth(getScreenWidth());
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
}
