package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.adapter.homeadapter.HomeMainAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentHomeBinding;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class HomeFragment extends BaseFragment {
    private HomeMainAdapter mAdapter;
    private FragmentHomeBinding mBinding;
    private MainActivityViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initData() {
        mAdapter = new HomeMainAdapter(mViewModel.getScreenWidth());
        mBinding.rcvMainData.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvMainData.setAdapter(mAdapter);

        mViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> mAdapter.setListHomeCategory(songs));
    }

    @Override
    protected ViewDataBinding getViewDataBinding() {
        return mBinding;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
    }

    @Override
    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initYourView() {

    }
}