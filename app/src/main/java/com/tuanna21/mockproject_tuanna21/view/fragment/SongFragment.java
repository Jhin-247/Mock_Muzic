package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSongBinding;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class SongFragment extends BaseFragment {
    private static final String TAG = SongFragment.class.getSimpleName();
    private MainActivityViewModel mViewModel;
    private FragmentSongBinding mBinding;

    @Override
    protected void initData() {
        mViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
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
        mBinding = FragmentSongBinding.inflate(inflater, container, false);
    }
}