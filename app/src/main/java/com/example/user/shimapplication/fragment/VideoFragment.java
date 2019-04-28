package com.example.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.adapter.VideoPagerAdapter;
import com.example.user.shimapplication.data.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private RecyclerView videoContainerView;
    private List<Video> videoList = new ArrayList<>();
    VideoPagerAdapter videoPagerAdapter;
    TabLayout videoTab;

    public static VideoFragment newInstance(){
        return new VideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_video,container,false);

        ViewPager videoViewPager = (ViewPager)view.findViewById(R.id.video_view_pager);

        videoPagerAdapter= new VideoPagerAdapter(getChildFragmentManager());
        videoViewPager.setAdapter(videoPagerAdapter);

        videoTab = (TabLayout)view.findViewById(R.id.video_tabs);
        videoTab.setupWithViewPager(videoViewPager);

        return view;
    }
}
