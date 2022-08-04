package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.tuanna21.mockproject_tuanna21.adapter.viewpageradapter.SongPagerAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;

public class AllSongFragment extends BaseFragment {
    private FragmentAllSongBinding mBinding;

    @Override
    protected void setupToolbar() {
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
        mBinding = FragmentAllSongBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initYourView() {
    }
}
