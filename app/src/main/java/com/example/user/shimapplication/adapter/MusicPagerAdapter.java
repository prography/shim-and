package com.example.user.shimapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.shimapplication.fragment.MusicFirstFragment;
import com.example.user.shimapplication.fragment.MusicFourthFragment;
import com.example.user.shimapplication.fragment.MusicSecondFragment;
import com.example.user.shimapplication.fragment.MusicThirdFragment;

public class MusicPagerAdapter extends FragmentPagerAdapter {

    public MusicPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return MusicFirstFragment.newInstance();
            case 1:
                return MusicSecondFragment.newInstance();
            case 2:
                return MusicThirdFragment.newInstance();
            case 3:
                return MusicFourthFragment.newInstance();
            default:
                return null;
        }
    }

    private static int PAGE_NUMBER = 4;

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
                return "Sleep";
            case 2:
                return "Instrument";
            case 3:
                return "Nature";

                default:
                    return null;

        }
    }
}
