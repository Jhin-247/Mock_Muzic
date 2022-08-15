package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Artist;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentArtistBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.VerticalSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class ArtistFragment extends BaseFragment<MainViewModel, FragmentArtistBinding> implements ArtistAdapter.ArtistClickListener {
    private ArtistAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new ArtistAdapter(mActivity);
        mAdapter.setListener(this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {
        mViewModel.getArtists().observe(getViewLifecycleOwner(), artists -> mAdapter.setData(artists));
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
        return R.layout.fragment_artist;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvArtists.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mBinding.rcvArtists.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mBinding.rcvArtists.setAdapter(mAdapter);
    }

    @Override
    public void onArtistClick(Artist artist) {
        mFragmentChangeListener.changeFragment(R.id.artistDetailFragment);
    }
}
