package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSongBinding;

public class SongFragment extends BaseFragment {
    private FragmentSongBinding mBinding;

    @Override
    protected void initData() {

    }

    @Override
    protected ViewDataBinding getViewDataBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_song, container, false);
        return mBinding;
    }
}