package com.tuanna21.mockproject_tuanna21.view;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivityMainBinding;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;


public class MainActivity extends BaseActivity {
    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;
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
                    hideSplash();
                }

            }
    );

    private void hideSplash() {
        mBinding.ivWelcomeImage.setVisibility(View.GONE);
        mBinding.rltFunctionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingStatusBarTransparent();
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
}
