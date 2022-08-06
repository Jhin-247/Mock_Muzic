package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentPlaylistBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.adapter.PlaylistMainAdapter;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;

public class PlaylistFragment extends BaseFragment<MainActivityViewModel, FragmentPlaylistBinding> {
    @Override
    protected void initListener() {

    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_playlist;
    }

    @Override
    protected void initYourView() {
        PlaylistMainAdapter mAdapter = new PlaylistMainAdapter(mActivity);

        mBinding.rcvPlaylist.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rcvPlaylist.setAdapter(mAdapter);

        mViewModel.getSongs().observe(getViewLifecycleOwner(), mAdapter::setData);
    }
}
