package com.tuanna21.mockproject_tuanna21.screen.splash;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding mBinding;
    private SplashViewModel mViewModel;

    @Override
    protected void setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void setupAction() {
        if (!checkPermission(READ_EXTERNAL_STORAGE)) {
            requestPermission(READ_EXTERNAL_STORAGE);
        } else {
            onPermissionRequested(true);
        }
    }

    @Override
    protected void onPermissionRequested(Boolean result) {
        Handler handler = new Handler();
        handler.postDelayed(() -> mViewModel.gotoMainActivity(SplashActivity.this), 2500);
    }

    @Override
    protected void setupObserver() {

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
