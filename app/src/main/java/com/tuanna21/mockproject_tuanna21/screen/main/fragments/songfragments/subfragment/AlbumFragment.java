package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment;

import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAlbumBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class AlbumFragment extends BaseFragment<MainViewModel, FragmentAlbumBinding> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album;
    }

    @Override
    protected void initYourView() {

    }
}
