package com.tuanna21.mockproject_tuanna21.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater,container);
        initViewModel();
        initData();
        initYourView();
        setupToolbar();
        return getViewDataBinding().getRoot();
    }

    protected abstract void setupToolbar();

    protected abstract void initData();

    protected abstract ViewDataBinding getViewDataBinding();

    protected abstract void initViewModel();

    protected abstract void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract void initYourView();
}
