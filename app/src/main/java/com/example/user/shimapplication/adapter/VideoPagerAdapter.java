package com.example.user.shimapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.shimapplication.fragment.VideoFirstFragment;
import com.example.user.shimapplication.fragment.VideoSecondFragment;
import com.example.user.shimapplication.fragment.VideoThirdFragment;

public class VideoPagerAdapter extends FragmentPagerAdapter {

    public VideoPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return VideoFirstFragment.newInstance();
            case 1:
                return VideoSecondFragment.newInstance();
            case 2:
                return VideoThirdFragment.newInstance();
            default:
                return null;
        }
    }

    private static int PAGE_NUMBER = 3;

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "All";
            case 1:
                return "Baby";
            case 2:
                return "Animal";
            default:
                return null;

        }
    }
}
