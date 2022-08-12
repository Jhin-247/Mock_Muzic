package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentPlaylistBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.adapter.PlaylistMainAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class PlaylistFragment extends BaseFragment<MainViewModel, FragmentPlaylistBinding> {
    private PlaylistMainAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new PlaylistMainAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {
//        mViewModel.getSongs().observe(getViewLifecycleOwner(), mAdapter::setData);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initViewModel() {
//        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_playlist;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvPlaylist.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rcvPlaylist.setAdapter(mAdapter);
    }
}
