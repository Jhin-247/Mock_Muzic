package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tuanna21.mockproject_tuanna21.R;
import com.tuanna21.mockproject_tuanna21.base.BaseFragment;
import com.tuanna21.mockproject_tuanna21.data.model.Song;
import com.tuanna21.mockproject_tuanna21.databinding.FragmentAllSongBinding;
import com.tuanna21.mockproject_tuanna21.screen.main.activity.MainActivity;
import com.tuanna21.mockproject_tuanna21.screen.main.viewmodel.MainActivityViewModel;

public class AllSongFragmentListener extends BaseFragment<MainActivityViewModel, FragmentAllSongBinding>
        implements AllSongAdapter.SongClickListener {

    private AllSongAdapterV2 mAdapter;

    private String TAG = "AllSongFragmentListener";

    @Override
    protected void initData() {
        mAdapter = new AllSongAdapterV2(mActivity);
        mAdapter.setOnClick(this);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initObserver() {
        mViewModel.getSongs().observe(mActivity, songs -> {
            mAdapter.setData(songs);
        });
    }

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
        mBinding.rcvSong.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rcvSong.setAdapter(mAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.rcvSong.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.i(TAG, "onScrollChange: " + "\nscrollX: " + scrollX + "\nscrollY: " + scrollY + "\noldScrollX: " + oldScrollX + "\noldScrollY: " + oldScrollY);
                }
            });

        }
    }

    @Override
    public void onSongClick(Song song) {
        mViewModel.playSong(song);
        if (mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).showBottomPlay();
        }
    }
}
