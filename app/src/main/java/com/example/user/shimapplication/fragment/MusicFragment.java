package com.example.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        musicTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        createTabIcons();

        return view;
    }

    private void createTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabOneText = (TextView) tabOne.findViewById(R.id.text_view);
        tabOneText.setText("전체");
        musicTab.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabTwoText = (TextView) tabTwo.findViewById(R.id.text_view);
        tabTwoText.setText("수면");
        musicTab.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabThreeText = (TextView) tabThree.findViewById(R.id.text_view);
        tabThreeText.setText("악기");
        musicTab.getTabAt(2).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabFourText = (TextView) tabFour.findViewById(R.id.text_view);
        tabFourText.setText("자연");
        musicTab.getTabAt(3).setCustomView(tabFour);
    }
}
