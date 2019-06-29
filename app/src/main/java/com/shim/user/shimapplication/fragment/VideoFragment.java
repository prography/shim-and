package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.VideoPagerAdapter;
import com.shim.user.shimapplication.data.Video;

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
//        videoTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        createTabIcons();

        return view;
    }

    private void createTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabOneText = (TextView) tabOne.findViewById(R.id.text_view);
        tabOneText.setText("전체");
        videoTab.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabTwoText = (TextView) tabTwo.findViewById(R.id.text_view);
        tabTwoText.setText("아기");
        videoTab.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabThreeText = (TextView) tabThree.findViewById(R.id.text_view);
        tabThreeText.setText("동물");
        videoTab.getTabAt(2).setCustomView(tabThree);
    }
}
