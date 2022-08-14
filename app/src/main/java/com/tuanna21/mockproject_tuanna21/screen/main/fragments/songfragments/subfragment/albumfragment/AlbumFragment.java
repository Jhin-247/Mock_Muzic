package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Album;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAlbumBinding;
import com.tuanna21.mockproject_tuanna21.itemdecorator.SquareSpaceItemDecoration;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class AlbumFragment extends BaseFragment<MainViewModel, FragmentAlbumBinding> implements AlbumAdapter.AlbumClickListener {
    private AlbumAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new AlbumAdapter(mActivity, this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initObserver() {
        mViewModel.getAlbumList().observe(getViewLifecycleOwner(), albums -> mAdapter.setData(albums));
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


        mBinding.rcvAlbums.setLayoutManager(new GridLayoutManager(mActivity, 2, RecyclerView.VERTICAL, false));
        mBinding.rcvAlbums.addItemDecoration(new SquareSpaceItemDecoration(20, 20, 20, 20));
        mBinding.rcvAlbums.setAdapter(mAdapter);
    }

    @Override
    public void onAlbumClick(Album album) {
        mViewModel.setAlbumToShowDetail(album);
        Navigation.findNavController(mBinding.getRoot()).navigate(R.id.albumDetailFragment);
    }
}
