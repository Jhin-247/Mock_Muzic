package com.tuanna21.mockproject_tuanna21.screen.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseActivity;
import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivityVersion2;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void setupDataBinding() {
        DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void setupAction() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, MainActivityVersion2.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    @Override
    protected void setupObserver() {

    }

    @Override
    protected void setupViewModel() {
    }

    @Override
    protected void initYourView() {
        setupFullScreen();
    }
}
