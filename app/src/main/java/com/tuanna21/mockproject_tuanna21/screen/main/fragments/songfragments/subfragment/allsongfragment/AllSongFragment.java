package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;

public class AllSongFragment extends BaseFragment<MainActivityViewModel, FragmentAllSongBinding> {

    private AllSongAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new AllSongAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initObserver() {
        mViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> mAdapter.setData(songs));
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_song;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvSong.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rcvSong.setAdapter(mAdapter);
    }
}
