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

import com.tuanna21.mockproject_tuanna21.adapter.navigation.NavigationAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSettingBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class SettingFragment extends BaseFragment {
    private FragmentSettingBinding mBinding;
    private NavigationAdapter mAdapter;
    private MainActivityViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setupToolbar() {
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText("Setting");
    }

    @Override
    protected void initData() {
        mAdapter = new NavigationAdapter(mViewModel.getSettingItems());
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
        mBinding = FragmentSettingBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initYourView() {
        mBinding.rcvSetting.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvSetting.addItemDecoration(new VerticalSpaceItemDecoration(35));
        mBinding.rcvSetting.setAdapter(mAdapter);
    }
}