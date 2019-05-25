package com.shim.user.shimapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shim.user.shimapplication.fragment.VideoFirstFragment;
import com.shim.user.shimapplication.fragment.VideoSecondFragment;
import com.shim.user.shimapplication.fragment.VideoThirdFragment;

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
                return "전체";
            case 1:
                return "아기";
            case 2:
                return "동물";
            default:
                return null;

        }
    }
}
