package com.tuanna21.mockproject_tuanna21.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;
import com.tuanna21.mockproject_tuanna21.viewmodel.MainActivityViewModel;

public class AllSongFragment extends BaseFragment<MainActivityViewModel, FragmentAllSongBinding> {

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
    }
}
