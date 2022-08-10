package com.tuanna21.mockproject_tuanna21.screen.splash;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    private SplashViewModel mViewModel;

    @Override
    protected void setupDataBinding() {
        DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void setupAction() {
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
