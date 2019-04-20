package com.example.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Main;
import com.example.user.shimapplication.data.handler.ShowMainHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentPagerAdapter adapterViewPager;

    public static HomeFragment newInstance(){

        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager vpPager = (ViewPager)view.findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        return view;
    }

    public void onPageSelected(int position){

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return HomeFirstFragment.newInstance();

                case 1:
                    return HomeSecondFragment.newInstance();

                case 2:
                    return HomeThirdFragment.newInstance();

                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

    }

}
