package com.tuanna21.mockproject_tuanna21.base;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;
import com.tuanna21.mockproject_tuanna21.utils.SharedPreferencesUtils;

public abstract class BaseActivity extends AppCompatActivity {


    private final ActivityResultLauncher<String> mRequestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            this::onPermissionRequested
    );

    protected void onPermissionRequested(Boolean result) {
    }

    protected void requestPermission(String permission) {
        mRequestPermission.launch(permission);
    }

    protected boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(
                getApplicationContext(),
                permission
        ) == PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setupDataBinding();
        setupViewModel();
        setupAction();
        setupObserver();
        initYourView();
    }

    protected abstract void setupDataBinding();
    protected abstract void setupViewModel();
    protected abstract void setupAction();
    protected abstract void setupObserver();
    protected abstract void initYourView();

    protected void setupFullScreen() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }
}
