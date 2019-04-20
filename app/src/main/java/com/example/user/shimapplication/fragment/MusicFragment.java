package com.example.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.adapter.MusicPagerAdapter;

public class MusicFragment extends Fragment {

    MusicPagerAdapter musicPagerAdapter;
    TabLayout musicTab;

    public static MusicFragment newInstance(){
        return new MusicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        ViewPager musicViewPager = (ViewPager)view.findViewById(R.id.music_view_pager);

        musicPagerAdapter= new MusicPagerAdapter(getChildFragmentManager());
        musicViewPager.setAdapter(musicPagerAdapter);

        musicTab = (TabLayout)view.findViewById(R.id.music_tabs);
        musicTab.setupWithViewPager(musicViewPager);

        return view;
    }
}
