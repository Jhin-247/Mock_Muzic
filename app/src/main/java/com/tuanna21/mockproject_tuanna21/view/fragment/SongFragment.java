package com.tuanna21.mockproject_tuanna21.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tuanna21.mockproject_tuanna21.adapter.viewpageradapter.SongPagerAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSongBinding;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class SongFragment extends BaseFragment {
    private static final String TAG = SongFragment.class.getSimpleName();
    private MainActivityViewModel mViewModel;
    private FragmentSongBinding mBinding;
    private SongPagerAdapter mAdapter;

    @Override
    protected void setupToolbar() {
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText("Song");
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        mAdapter = new SongPagerAdapter(requireActivity());
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
        mBinding = FragmentSongBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initYourView() {
        mBinding.viewPager.setAdapter(mAdapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, (tab, position) -> {
            tab.setText(mAdapter.getTitleAt(position));
        }).attach();
        if (calculateTabWidth(mBinding.tabLayout) < new ScreenUtils().getScreenWidth(requireActivity())) {
            mBinding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    //todo tính độ dài của tablayout và kéo dài theo màn hình
    private int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0);
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }
}