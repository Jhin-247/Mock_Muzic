package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentSongBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.viewpageradapter.SongPagerAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;

public class SongFragment extends BaseFragment<MainViewModel, FragmentSongBinding> {
    private SongPagerAdapter mAdapter;


    @Override
    protected void initData() {
        mAdapter = new SongPagerAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {
        mBinding.toolbar.tvTitle.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvTitle.setText(mActivity.getString(R.string.song));
        mBinding.toolbar.etSearch.setVisibility(View.GONE);
    }

    @Override
    protected void initObserver() {
    }

    @Override
    protected void initListener() {
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.openDrawer();
        });
    }

    @Override
    protected void initViewModel() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initYourView() {
        mBinding.viewPagerSongs.setAdapter(mAdapter);
        mBinding.viewPagerSongs.setOffscreenPageLimit(5);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPagerSongs, (tab, position) -> {
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