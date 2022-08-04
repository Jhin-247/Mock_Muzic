package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSettingBinding;

public class SettingFragment extends BaseFragment {
    private FragmentSettingBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected ViewDataBinding getViewDataBinding() {
        return mBinding;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initYourView() {

    }
}