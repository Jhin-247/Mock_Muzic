package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.genresfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentGenresBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.SquareSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class GenresFragment extends BaseFragment<MainViewModel, FragmentGenresBinding> {
    private GenresAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new GenresAdapter(mActivity);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {
        mViewModel.getGenresList().observe(getViewLifecycleOwner(), genres -> {
            mAdapter.setData(genres);
        });
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
        return R.layout.fragment_genres;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvGenres.setLayoutManager(new GridLayoutManager(mActivity, 2, RecyclerView.VERTICAL, false));
        mBinding.rcvGenres.addItemDecoration(new SquareSpaceItemDecoration(10, 10, 10, 10));
        mBinding.rcvGenres.setAdapter(mAdapter);
    }
}
