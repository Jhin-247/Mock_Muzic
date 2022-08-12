package com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentHomeBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.HomeMainAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment.AllSongAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class HomeFragment extends BaseFragment<MainViewModel, FragmentHomeBinding> implements AllSongAdapter.SongClickListener {
    private HomeMainAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new HomeMainAdapter(mActivity);
        mAdapter.setOnSongClickListener(this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {
        mViewModel.getCurrentPlayingSongList().observe(getViewLifecycleOwner(), songs -> mAdapter.setData(songs));
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
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvMainData.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvMainData.setAdapter(mAdapter);
    }

    @Override
    public void onSongClick(Song song) {
        mViewModel.playSong(song);
        mFragmentChangeListener.changeFragment(R.id.songPlayingFragment);
    }
}