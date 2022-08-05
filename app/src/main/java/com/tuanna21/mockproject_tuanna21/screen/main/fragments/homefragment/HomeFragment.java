package com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.homefragment.homefragmentadapter.HomeMainAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentHomeBinding;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;

public class HomeFragment extends BaseFragment<MainActivityViewModel, FragmentHomeBinding> {
    private HomeMainAdapter mAdapter;

    @Override
    protected void initListener() {
        mBinding.toolbar.ivNavigationButton.setOnClickListener(v -> {
            mToolbarListener.openDrawer();
        });
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mActivity).get(MainActivityViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initYourView() {
        mAdapter = new HomeMainAdapter(new ScreenUtils().getScreenWidth(mActivity));
        mBinding.rcvMainData.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.rcvMainData.setAdapter(mAdapter);

        mViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> mAdapter.setListHomeCategory(songs));
    }
}