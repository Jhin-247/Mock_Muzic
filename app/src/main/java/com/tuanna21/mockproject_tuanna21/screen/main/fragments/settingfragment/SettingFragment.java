package com.tuanna21.mockproject_tuanna21.screen.main.fragments.settingfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSettingBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.fakeadapters.FakeItemAdapterAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class SettingFragment extends BaseFragment<MainViewModel, FragmentSettingBinding> {

    private FakeItemAdapterAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new FakeItemAdapterAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText(mActivity.getString(R.string.setting));
    }

    @Override
    protected void initObserver() {
        mViewModel.getSettingItems().observe(getViewLifecycleOwner(), navigationItems -> mAdapter.setData(navigationItems));
    }

    @Override
    protected void initListener() {
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.openDrawer();
        });
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainViewModel.class);
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
        mBinding.rcvSetting.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvSetting.addItemDecoration(new VerticalSpaceItemDecoration(35));
        mBinding.rcvSetting.setAdapter(mAdapter);
    }
}