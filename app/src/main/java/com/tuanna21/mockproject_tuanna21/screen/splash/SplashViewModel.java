package com.tuanna21.mockproject_tuanna21.screen.splash;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivity;

public class SplashViewModel extends AndroidViewModel {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean checkPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context.getApplicationContext(),
                Manifest.permission.READ_CONTACTS
        ) == PERMISSION_GRANTED;
    }

    public void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
