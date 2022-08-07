package com.tuanna21.mockproject_tuanna21.screen.splash;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.tuanna21.mockproject_tuanna21.base.BaseViewModel;
import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivity;

public class SplashViewModel extends BaseViewModel {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {

    }

    public void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
