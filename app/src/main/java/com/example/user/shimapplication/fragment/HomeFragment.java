package com.example.user.shimapplication.fragment;

import android.app.Activity;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.relex.circleindicator.CircleIndicator;

import com.bumptech.glide.Glide;
import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Main;
import com.example.user.shimapplication.data.handler.ShowMainHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.shimapplication.activity.MainActivity.isPlaying;
import static com.example.user.shimapplication.activity.MainActivity.mainList;
import static com.example.user.shimapplication.activity.MainActivity.mp;
import static com.example.user.shimapplication.activity.MainActivity.playingPosition;

public class HomeFragment extends Fragment {
    FragmentPagerAdapter adapterViewPager;
    public ImageView mainFirstImage;
    public ImageView mainSecondImage;
    public ImageView mainThirdImage;

    public static HomeFragment newInstance(){

        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final ViewPager vpPager = (ViewPager)view.findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        mainFirstImage = (ImageView)view.findViewById(R.id.main_first_image);
        mainSecondImage = (ImageView)view.findViewById(R.id.main_second_image);
        mainThirdImage = (ImageView)view.findViewById(R.id.main_third_image);


        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int position){
                MusicResetTask resetTask = new MusicResetTask();
                resetTask.execute(position);
                /*
                mp.reset();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if(mainList.size()!=0){
                    try{
                        mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                                +mainList.get(position).getMain_music());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.setLooping(true);
                    mp.start();
                    isPlaying = true;
                    playingPosition = 0;
                }
                */
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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

    private class MusicResetTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... positions) {
            mp.reset();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if(mainList.size()!=0){
                try{
                    mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                            +mainList.get(positions[0]).getMain_music());
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.setLooping(true);
                mp.start();
                isPlaying = true;
                playingPosition = 0;
            }
            return 0;
        }
    }
}

