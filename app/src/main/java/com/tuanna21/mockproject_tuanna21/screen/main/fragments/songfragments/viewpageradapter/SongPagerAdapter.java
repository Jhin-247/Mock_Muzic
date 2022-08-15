package com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.albumfragment.AlbumFragment;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.artistfragment.ArtistFragment;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.genresfragment.GenresFragment;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.playlistfragment.PlaylistFragment;
import com.tuanna21.mockproject_tuanna21.screen.main.fragments.songfragments.subfragment.allsongfragment.AllSongFragment;

import java.util.ArrayList;
import java.util.List;

public class SongPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> mListFragments;
    public SongPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mListFragments = new ArrayList<>();
        mListFragments.add(new AllSongFragment());
        mListFragments.add(new PlaylistFragment());
        mListFragments.add(new AlbumFragment());
        mListFragments.add(new ArtistFragment());
        mListFragments.add(new GenresFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mListFragments.get(position);
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
        return mListFragments.size();
    }
}
