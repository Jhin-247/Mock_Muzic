package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainViewModel;

public class AllSongFragment extends BaseFragment<MainViewModel, FragmentAllSongBinding>
        implements AllSongAdapter.SongClickListener {

    private AllSongAdapter mAdapter;

    private final String TAG = AllSongFragment.class.getSimpleName();

    @Override
    protected void initData() {
        mAdapter = new AllSongAdapter(mActivity);
        mAdapter.setOnClick(this);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initObserver() {
        mViewModel.getCurrentPlayingSongList().observe(mActivity, songs -> {
            mAdapter.setData(songs);
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
        return R.layout.fragment_all_song;
    }

    @Override
    protected void initYourView() {
        mBinding.rcvSong.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mBinding.rcvSong.setAdapter(mAdapter);
    }

    @Override
    public void onSongClick(Song song) {
        mViewModel.playSong(song);
        mFragmentChangeListener.changeFragment(R.id.songPlayingFragment);
    }
}
