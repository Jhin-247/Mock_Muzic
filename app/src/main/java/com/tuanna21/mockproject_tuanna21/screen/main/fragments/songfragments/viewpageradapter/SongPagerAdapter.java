package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.PlaylistFragment;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment.AllSongFragment;

public class SongPagerAdapter extends FragmentStateAdapter {

    private final AllSongFragment mAllSongFragment;

    public SongPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mAllSongFragment = new AllSongFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return mAllSongFragment;
            case 1:
                return new PlaylistFragment();
        }
        return new AllSongFragment();
    }

    public String getTitleAt(int position) {
        switch (position) {
            case 0:
                return "All songs";
            case 1:
                return "Playlists";
            case 2:
                return "Albums";
            case 3:
                return "Artists";
            default:
                return "Genres";
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
