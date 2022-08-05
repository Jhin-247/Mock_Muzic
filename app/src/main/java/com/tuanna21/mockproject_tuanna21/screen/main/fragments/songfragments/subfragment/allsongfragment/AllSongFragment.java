package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;
import com.tuanna21.mockproject_tuanna21.db.model.Song;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;

import java.util.List;

public class AllSongFragment extends BaseFragment<MainActivityViewModel, FragmentAllSongBinding> {

    private AllSongAdapter mAdapter;

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
        mAdapter = new AllSongAdapter();
        mBinding.rcvSong.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rcvSong.setAdapter(mAdapter);
        mViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> mAdapter.setSongList(songs));
    }
}
