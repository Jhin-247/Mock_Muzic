package com.tuanna21.mockproject_tuanna21.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivitySplashBinding;
import com.tuanna21.mockproject_tuanna21.viewmodel.SplashViewModel;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    private final ActivityResultLauncher<String> mRequestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewModel.gotoMainActivity(SplashActivity.this);
                        }
                    }, 2500);
                }

            }
    );
    private ActivitySplashBinding mBinding;
    private SplashViewModel mViewModel;

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void setupNavigationDrawer() {
    }

    @Override
    protected void setupNavigation() {
    }

    @Override
    protected void setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void setupAction() {
        if (!mViewModel.checkPermission(this)) {
            mRequestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    @Override
    protected void initYourView() {
        setupFullScreen();

    }
}
