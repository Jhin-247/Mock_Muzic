package com.tuanna21.mockproject_tuanna21.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tuanna21.mockproject_tuanna21.data.repository.Repository;

public abstract class BaseViewModel extends AndroidViewModel {
    protected Repository mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication().getApplicationContext());
        initData();
        loadData();
    }

    protected abstract void initData();
    protected abstract void loadData();
}
