package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.adapter.navigation.FakeItemAdapterAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSettingBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class SettingFragment extends BaseFragment<MainActivityViewModel, FragmentSettingBinding> {

    private void setupToolbar() {
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText("Setting");
    }


    @Override
    protected void initListener() {
        //toolbar:
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.openDrawer();
        });
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initYourView() {
        setupToolbar();
        FakeItemAdapterAdapter mAdapter = new FakeItemAdapterAdapter(mViewModel.getSettingItems());

        mBinding.rcvSetting.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvSetting.addItemDecoration(new VerticalSpaceItemDecoration(35));
        mBinding.rcvSetting.setAdapter(mAdapter);
    }
}