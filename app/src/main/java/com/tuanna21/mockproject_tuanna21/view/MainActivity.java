package com.tuanna21.mockproject_tuanna21.view;

import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.adapter.navigation.NavigationAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivityMainBinding;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MainActivityViewModel mViewModel;
    private final ActivityResultLauncher<String> mRequestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        mViewModel.requestSyncSongData(MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "Please allow permission!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
    );
    private ActivityMainBinding mBinding;

    @Override
    protected void setupToolbar() {
        setSupportActionBar(mBinding.toolbar.toolbar);
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mBinding.drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    @Override
    protected void setupNavigationDrawer() {
        NavigationAdapter mNavigationAdapter = new NavigationAdapter(mViewModel.getNavigationItems());
        mBinding.rcvNavigation.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcvNavigation.setAdapter(mNavigationAdapter);
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

    @Override
    protected void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);
        }
    }

    @Override
    protected void setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void setupAction() {
        if (!mViewModel.isFirstTimeInit(this)) {
//            mViewModel.loadDataFromDB();
        } else if (!mViewModel.checkPermission(this)) {
            mRequestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            mViewModel.requestSyncSongData(this);
        }
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mViewModel.setScreenHeight(new ScreenUtils().getScreenHeight(this));
        mViewModel.setScreenWidth(new ScreenUtils().getScreenWidth(this));
    }

    @Override
    protected void initYourView() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
